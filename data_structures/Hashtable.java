package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Hashtable implementation Class for {@link DictionaryADT.class}.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
public class Hashtable<K, V> implements DictionaryADT<K, V> {
/* Building a hashtable with chaining using the LinearList implementation. */

    LinearList<DictionaryNode<K, V>>[] list;
    int currentSize;
    int maxSize;
    int tableSize;
    long modCounter;
    
    private class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
    /* A DictionaryNode implementation for the Comparable interface. */
        K key;
        V value;
        public DictionaryNode(K k, V v) {
            key = k;
            value = v;
        }
    
        public int compareTo(DictionaryNode<K, V> node) {
            return ((Comparable<K>)key).compareTo((K)node.key);
        }
    }

    /**
     * Set the table to a specific size.
     *
     * @param n the specified size
     */
    public Hashtable(int n) {
        currentSize = 0;
        maxSize = n;
        modCounter = 0;
        tableSize = (int) (maxSize * 1.3f);
        list =  new LinearList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            list[i] = new LinearList<DictionaryNode<K, V>>();
        }
    }

    /**
     * Verify an object is identified by the provided key exist in the table.
     *
     * @param key the provided key
     * @return true if the object is found; otherwise false
     */
    public boolean contains(K key) {
        return list[getHashCode(key)]
                .contains(new DictionaryNode<K, V>(key, null));
    }

    /**
     * Add the provided key and value pair to the table.
     *
     * @param key the provided key
     * @param value the provided value
     * @return false if table is full or has duplicate; otherwise true
     */
    public boolean add(K key, V value) {
        if (isFull()) {
            return false;
        }

        if ((list[getHashCode(key)] != null) && (list[getHashCode(key)]
                .contains(new DictionaryNode<K, V>(key, null)))) {
            return false;
        }
        list[getHashCode(key)].addLast(new DictionaryNode<K, V>(key, value));
        currentSize++;
        modCounter++;
        return true;
    }
       
    /**
     * Delete the key and value pair identified by the provided key.
     *
     * @param key the provided key
     * @return true if key/value pair found and removed; otherwise false
     */
    public boolean delete(K key) {
        if (isEmpty()) {
            return false;
        }

        if (!list[getHashCode(key)]
                .contains(new DictionaryNode<K, V>(key, null))) {
            return false;
        }
        list[getHashCode(key)].removeFirst();
        currentSize--;
        modCounter--;
        return true;
    }
    
    /**
     * Return the value identified by the provided key.
     *
     * @param key the provided key
     * @return null if key not found or table is empty; otherwise the value
     */
    public V getValue(K key) {
        DictionaryNode<K, V> temp =
            list[getHashCode(key)].find(new DictionaryNode<K, V>(key, null));
        return (temp == null ? null : temp.value);
    }

    /**
     * Return the key associated with the provided value.
     *
     * @param value the provided value
     * @return the first occurrence key found; otherwise null
     */
    public K getKey(V value) {
        K va = null;
        for (int i = 0; i < tableSize; i++) {
            if (list[i] == null) {
                continue;
            }
            LinearList<DictionaryNode<K, V>> tmp1
                    = new LinearList<DictionaryNode<K, V>>();

            while (list[i].size() > 0) {
                DictionaryNode<K, V> tmp = list[i].removeFirst();
                tmp1.addLast(tmp);
                if (((Comparable<V>)tmp.value).compareTo((V)value) == 0) {
                    va = tmp.key;
                    while (list[i].size() > 0) {
                        tmp1.addLast(list[i].removeFirst());
                    }
                }
            }
            list[i] = tmp1;

            if (va != null) {
                return va;
            }
        }
        return null;
    }
       
    /**
     * Return the number of key/value pairs in the dictionary.
     *
     * @return the current size of the dictionary
     */
    public int size() {
        return currentSize;
    }
    
    /**
     * Verify if the dictionary is full.
     *
     * @return true if the dictionary is at max capacity; otherwise false
     */
    public boolean isFull() {
        return (currentSize == maxSize);
    }
    
    /**
     * Verify if the dictionary is empty.
     *
     * @return true if dictionary contain no key/value pairs; otherwise false
     */
    public boolean isEmpty() {
        return (currentSize == 0);
    }

    /**
     * Return the dictionary to an empty state.
     */
    public void clear() {
        currentSize = 0;
        for (int i = 0; i < tableSize; i++) {
            list[i] = new LinearList<DictionaryNode<K, V>>();
        }/**
 * The PhoneBook application Class for the dictionary.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
    }
    
    /**
     * Return the key iterator helper.
     *
     * @return the key interator
     */
    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    }
    
    /**
     * Return the value iterator helper.
     *
     * @return the value iterator
     */
    public Iterator<V> values() {
        return new ValueIteratorHelper();
    }

    /**
     * Return the iterator of the key in the dictionary
     * in ascrighting sorted order.
     * 
     * @return the iteration of the key in the dictionary
     */
    private class KeyIteratorHelper extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }

        public K next() {
            return (K) nodes[idx++].key;
        }
    }
    
    /**
     * Return the iterator of the value in the dictionary
     * in ascrighting sorted order.
     *
     * @return the iteration of the value in the dictionary
     */
    private class ValueIteratorHelper extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }
        public V next() {// Returns the number of key/value pairs currently stored
            return (V) nodes[idx++].value;
        }
    }
    
    /**
     * Implementation for the iterator of the key/value pairs in the dictionary.
     *
     * @throws ConcurrentModificationException if iterating is modified fail-fast
     * @throws UnsupportedOperationException for iterator remove operation
     */
    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K, V>[] nodes;
        protected int idx;
        protected long modCheck;
        public IteratorHelper() {
            nodes = new DictionaryNode[currentSize];
            idx = 0;
            int j = 0;
            modCheck = modCounter;
            for (int i = 0; i < tableSize; i++)
                for (DictionaryNode n : list[i])
                    nodes[j++] = n;
            quickSort(nodes, 0, currentSize - 1);
        }
         
        public boolean hasNext() {
            if (modCheck != modCounter) {
                throw new ConcurrentModificationException();
            }
            return (idx < currentSize);
        }
    
        public abstract E next();
    
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Quick sort the dictionary in ascrighting sorted order. 
     *
     * @param nodes the dictionary node
     * @param low the start of the dictionary
     * @param high the end of the dictionary
     */
    private void quickSort(DictionaryNode<K, V>[] nodes, int low, int high) {
        int left = low;
        int right = high;
        int mid = ((right - left) / 2) + left;
        DictionaryNode<K, V> pivot = nodes[mid];
        while (left <= right) {        
            while ((nodes[left]).compareTo(pivot) < 0) {
                left++;
            }

            while ((nodes[right]).compareTo(pivot) > 0) {
                right--;
            }

            if (left <= right) {
                DictionaryNode<K, V> temp = nodes[left];
                nodes[left] = nodes[right];
                nodes[right] = temp;
                left++;
                right--;
            }
        }

        if (low < right) {
            quickSort(nodes, low, right);
        }

        if (left < high) {
            quickSort(nodes, left, high);
        }
    }
    
    /**
     * Return the hash code of a specific key.
     *
     * @param key the specified key
     * @return the integer representing the hash code of the key
     */
    private int getHashCode(K key) {
        return (key.hashCode() % tableSize);
    }
}
