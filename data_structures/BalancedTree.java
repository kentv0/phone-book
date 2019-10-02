package data_structures;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * The BalancedTree implementation Class for {@link DictionaryADT.class}.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
public class BalancedTree <K extends Comparable<K>, V>
        implements DictionaryADT<K, V> {
/* A red and black tree implementation for the DictionaryADT interface. */

    TreeMap<K, V> mapTree;

    /**
     * Create the default instance of the BalancedTree.
     */
    public BalancedTree() {
        mapTree= new TreeMap<K, V>();
    }

    /**
     * Verify an object is identified by the provided key exist in the tree.
     *
     * @param key the provided key
     * @return true if the object is found; otherwise false
     */
    public boolean contains(K key) {
       return mapTree.containsKey(key);
    }

    /**
     * Add the provided key and value pair to the tree.
     *
     * @param key the provided key
     * @param value the provided value
     * @return true
     */
    public boolean add(K key, V value) {
        mapTree.put(key, value);
        return true;
    }

    /**
     * Delete the key and value pair identified by the provided key.
     *
     * @param key the provided key
     * @return true if key/value pair found and removed; otherwise false
     */
    public boolean delete(K key) {
        V status = mapTree.remove(key);
        return status != null;
    }

    /**
     * Return the value identified by the provided key.
     *
     * @param key the provided key
     * @return the value identified by the key
     */
    public V getValue(K key) {
        return mapTree.get(key);
    }

    /**
     * Return the key associated with the provided value.
     *
     * @param value the provided value
     * @return the first occurrence key found; otherwise null
     */
    public K getKey(V value) {
        for(Entry<K, V> entry : mapTree.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Return the number of key/value pairs in the tree.
     *
     * @return the current size of the tree 
     */
    public int size() {
        return mapTree.size();
    }

    /**
     * Verify if the tree is full.
     *
     * @return false always since the tree size is dynamic
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Verify if the tree is empty.
     *
     * @return true if tree size is less than 1; otherwise false
     */
    public boolean isEmpty() {
        return (this.size() < 1);
    }

    /**
     * Return the tree to an empty state.
     */
    public void clear() {
        mapTree.clear();
    }

    /**
     * Return the iterator of the key in the tree.
     *
     * @return iterator of the key
     */
    public Iterator<K> keys() {
       return mapTree.keySet().iterator();
    }

    /**
     * Return the iterator of the value in the tree.
     *
     * @return iterator of the value
     */
    public Iterator<V> values() {
        return mapTree.values().iterator();
    }
}
