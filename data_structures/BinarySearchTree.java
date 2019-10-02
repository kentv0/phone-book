package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * The BinarySearchTree implementation Class for {@link DictionaryADT.class}.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
public class BinarySearchTree <K extends Comparable<K>, V>
        implements DictionaryADT<K, V> {
/* A binary search tree implementation for the DictionartADT interface. */

	public treeNode<K, V> parentNode;
	public int treeSize;
	public int modCounter;
	
	private class treeNode<K, V> implements Comparable<treeNode<K, V>> {
    /* A treeNode implementation for the Comparable interface. */
		public K key;
		public V value;
		public treeNode<K, V> leftChild;
		public treeNode<K, V> rightChild;
		
		public treeNode (K k, V v) {
			key = k;
			value = v;
		}
		
		@Override
		public int compareTo(treeNode<K, V> o) {
            return (o.key != null
                    ? ((Comparable<K>)key).compareTo((K) o.key)
                    : ((Comparable<V>)value).compareTo((V) o.value));
		}
	}

    /**
     * Create the default instance of the BinarySearchTree.
     */
	public BinarySearchTree () {
		modCounter = 0;
		clear();
	}

    /**
     * Verify an object is identified by the provided key exist in the tree.
     *
     * @param key the provided key
     * @return true if the object is found; otherwise false if null
     */
	@Override
	public boolean contains(K key) {
		return ((getValue(key) != null) ? true : false);
	}

    /**
     * Add the provided key and value pair to the tree.
     *
     * @param key the provided key
     * @param value the provided value
     * @return true
     */
	@Override
	public boolean add(K key, V value) {
		treeNode<K ,V> newNode = new treeNode<K, V>(key, value);
		if(parentNode == null) {
			parentNode = newNode;
			treeSize++;
			modCounter++;
			return true;
		}
		treeNode<K, V> tmpNode = parentNode;

		while(tmpNode != null) {
			if(tmpNode.compareTo(newNode) > 0) {
				if(tmpNode.leftChild == null) {
					tmpNode.leftChild = newNode;
					treeSize++;
					break;
				}
				tmpNode = tmpNode.leftChild;
				continue;
			}
			
			if(tmpNode.compareTo(newNode) < 0) {
				if(tmpNode.rightChild == null) {
					tmpNode.rightChild = newNode;
					treeSize++;
					break;
				}
				tmpNode = tmpNode.rightChild;
				continue;
			}
		}
		modCounter++;
		return true;
	}

    /**
     * Delete the key and value pair identified by the provided key.
     *
     * @param key the provided key
     * @return true if key/value pair found and removed; otherwise false
     */
	@Override
	public boolean delete(K key) {
		treeNode<K, V> delNode = find(key, null);
		if(delNode == null) {
			return false;
        }
		
		if((delNode.leftChild == null) && (delNode.rightChild == null)) {
			treeNode<K, V> pNode = getParent(delNode.key);
			if(pNode == null) {
				parentNode = null;
				treeSize--;
				modCounter++;
				return true;
			}
			
			if((pNode.leftChild != null) && (delNode.compareTo(pNode.leftChild) == 0)) {
				pNode.leftChild = null;
            } else {
				pNode.rightChild = null;
            }
			treeSize--;
			modCounter++;
			return true;
		} else if((delNode.leftChild != null) && (delNode.rightChild != null)) {
			treeNode<K, V> s = getSuccessor(delNode);
			treeNode<K, V> pNode = getParent(delNode.key);
			if(pNode == null) {
				s.leftChild = parentNode.leftChild;
				s.rightChild = parentNode.rightChild;
				parentNode = s;

				treeSize--;
				modCounter++;
				return true;
			}
			s.rightChild = delNode.rightChild;
			s.leftChild = delNode.leftChild;

			if((pNode.leftChild != null) && (delNode.compareTo(pNode.leftChild) == 0)) {
				pNode.leftChild = s;
            } else {
                pNode.rightChild = s;
            }
			treeSize--;
			modCounter++;
			return true;
		} else if((delNode.leftChild != null) || (delNode.rightChild != null)) {
			treeNode<K, V> pNode = getParent(delNode.key);

			if(pNode == null) {
				if(parentNode.leftChild != null) {
					parentNode = parentNode.leftChild;
                } else {
					parentNode = parentNode.rightChild;
                }
				treeSize--;
				modCounter++;
				return true;
			}
				
			if(delNode.leftChild != null) {
				if((pNode.leftChild != null) && (delNode.compareTo(pNode.leftChild) == 0)) {
					pNode.leftChild = delNode.leftChild;
                } else {
					pNode.rightChild = delNode.leftChild;
                }	
			} else {
				if((pNode.leftChild != null) && (delNode.compareTo(pNode.leftChild) == 0)) {
					pNode.leftChild = delNode.rightChild;
                } else {
					pNode.rightChild = delNode.rightChild;
                }
			}
			modCounter++;
			treeSize--;
			return true;
		}
		return false;
	}
	
    /**
     * Return the child node of the tree.
     *
     * @param startNode the starting node of the tree
     * @return the child node; otherwise null if none exist
     */
	private treeNode<K, V> getSuccessor(treeNode<K, V> startNode) {
		// No need for successor
		if((startNode.rightChild == null) || (startNode.leftChild == null)) {
			return null;
        }
		treeNode<K, V> minNode = startNode.rightChild;
		
		if(minNode.leftChild == null) {
			if(minNode.rightChild != null) {
				startNode.rightChild = minNode.rightChild;
            } else {
				startNode.rightChild = null;
            }
			return minNode;
		}
		
		while(minNode.leftChild != null) {
			minNode = minNode.leftChild;
        }
		treeNode<K, V> pNode = getParent(minNode.key);
		
		// If successor has right node
		if(minNode.rightChild != null) {
			pNode.leftChild = minNode.rightChild;
        } else if(pNode.compareTo(parentNode) != 0) {
			pNode.leftChild = null;
        }	
		return minNode;
	}

    /**
     * Find a specific key/value pair in the tree.
     *
     * @param key the specified key
     * @param value the specified value
     * @return the key/value pair; otherwise null if not found
     */
	private treeNode<K, V> find (K key, V value) {
		treeNode<K, V> searchNode = parentNode;
		treeNode<K, V> compNode = new treeNode(key, value);
		
		if(key != null) {
			while((searchNode != null) && (compNode.compareTo(searchNode) != 0)) {
				if(compNode.compareTo(searchNode) > 0) {
					searchNode = searchNode.rightChild;
                } else {
					searchNode = searchNode.leftChild;
                }
			}
			return searchNode;
		} else {
			Iterator<K> keys = new keyIteratorHelper<K>();
			while(keys.hasNext()) {
				treeNode<K, V> tmpNode = find(keys.next(), null);
				if(tmpNode.compareTo(compNode) == 0) {
					return tmpNode;
                }
			}
			return null;
		}
	}

    /**
     * Return the parent node of the tree identified by the provided key.
     *
     * @param key the provided key
     * @return the parent node of the tree
     */
	private treeNode<K, V> getParent (K key) {
		treeNode<K, V> searchNode = parentNode;
		treeNode<K, V> compNode = new treeNode(key, null);
		treeNode<K, V> parent = null;
		
		while((searchNode != null) && (compNode.compareTo(searchNode) != 0)) {
			if(compNode.compareTo(searchNode) > 0) {
				parent = searchNode;
				searchNode = searchNode.rightChild;
			} else {
				parent = searchNode;
				searchNode = searchNode.leftChild;
			}
		}
		return parent;
	}
	
    /**
     * Return the value identified by the provided key.
     *
     * @param key the provided key
     * @return the value identified by the key; otherwise null if not found
     */
	@Override
	public V getValue(K key) {
		treeNode<K, V> tmpNode = find(key, null);
		return (tmpNode != null ? tmpNode.value : null);
	}
	
    /**
     * Return the key associated with the provided value.
     *
     * @param value the provided value
     * @return the first occurrence key; otherwise null if not found
     */
	@Override
	public K getKey(V value) {
		treeNode<K, V> tmpNode = find(null, value);
		return (tmpNode != null ? tmpNode.key : null);
	}

    /**
     * Return the number of key/value pairs in the tree.
     *
     * @return the current size of the tree 
     */
	@Override
	public int size() {
		return treeSize;
	}

    /**
     * Verify if the tree is full.
     *
     * @return false always since the tree size is dynamic
     */
	@Override
	public boolean isFull() {
		return false;
	}

    /**
     * Verify if the tree is empty.
     *
     * @return true if tree size is equal to 1; otherwise false
     */
	@Override
	public boolean isEmpty() {
		return (treeSize == 0);
	}

    /**
     * Return the tree to an empty state.
     */
	@Override
	public void clear() {
		treeSize = 0;
		parentNode = null;
	}

    /**
     * Return the key iterator helper.
     *
     * @return the key interator
     */
	@Override
	public Iterator<K> keys() {
		return new keyIteratorHelper();
	}

    /**
     * Return the value iterator helper.
     *
     * @return the value iterator
     */
	@Override
	public Iterator<V> values() {
		return new valueIteratorHelper();
	}
	
    /**
     * Return the iterator of the key in the tree.
     * 
     * @return the iteration of the key in the tree 
     */
	public class keyIteratorHelper<K> extends IteratorHelper<K> {
		@Override
		public K next() {
			if(iterMod != modCounter) {
				throw new ConcurrentModificationException();
            }
	    	treeNode<K, V> r = (treeNode<K, V>) iterNode;

	    	if(iterNode.rightChild != null) {
	    	 	iterNode = iterNode.rightChild;
	    	 	while (iterNode.leftChild != null) {
    	 		    iterNode = iterNode.leftChild;
                }
	    	 	return r.key;
	     	} else { 
	     		while(true) {
	     			if(getParent(iterNode.key) == null) {
     					iterNode = null;
		     			return r.key;
		     		}

		     	    if(getParent(iterNode.key).leftChild == iterNode) {
	     				iterNode = getParent(iterNode.key);
         				return r.key;
		     		}
	     			iterNode = getParent(iterNode.key);
	     		}
	     	}
		}
	}

    /**
     * Return the iterator of the value in the tree.
     *
     * @return the iteration of the value in the dictionary
     */
	public class valueIteratorHelper<V> extends IteratorHelper<V> {
	    @Override
	    public V next() {
	        if(iterMod != modCounter) {
		    	throw new ConcurrentModificationException();
            }
	        treeNode<K, V> r = (treeNode<K, V>) iterNode;

		    if(iterNode.rightChild != null) {
	            iterNode = iterNode.rightChild;
	        	while(iterNode.leftChild != null) {
		            iterNode = iterNode.leftChild;
                }
		        return r.value;
		    } else {
		        while(true) {
                    if(getParent(iterNode.key) == null) {
		         		iterNode = null;
		         		return r.value;
		         	}
		         	
                    if(getParent(iterNode.key).leftChild == iterNode) {
		         	    iterNode = getParent(iterNode.key);
		         		return r.value;
		         	}
		         	iterNode = getParent(iterNode.key);
		        }
		    }
		}
	}
	
    /**
     * Implementation for the iterator of the key/value pairs in the tree.
     */
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected treeNode<K, V> iterNode; 
		protected int iterMod;
		
		public IteratorHelper () {
			iterNode = parentNode;
			iterMod = modCounter;
			if(iterNode == null) {
				return;
            }

			while(iterNode.leftChild != null) {
				iterNode = iterNode.leftChild;
            }
		}
		
		public boolean hasNext () {
			return iterNode != null;
		}
		
		public abstract E next();
	}
}
