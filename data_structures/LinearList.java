package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The LinearList implementation Class for {@link LinearListADT.class}.
 *
 * @version     0.1.0 01 Oct 2015
 * @author      Kent Vo
 */
public class LinearList<E> implements LinearListADT<E> {
/* A doubly linked list implementation for the LinearListADT interface. */

    private Node front = new Node((E) null);
    private Node rear = new Node((E) null);
    public static final int DEFAULT_MAX_CAPACITY = 10000;
    private int currentSize = 0;
    private int maxSize;
 
    /**
     * Set the list to the default size.
     */
    public LinearList() {
        maxSize = DEFAULT_MAX_CAPACITY;
        front.next = rear;
        rear.prev = front;
    }

    /**
     * Set the list to a specific size.
     *
     * @param arraySize the specified size
     */
    public LinearList(int arraySize) {
        maxSize = arraySize;
        front.next = rear;
        rear.prev = front;
    }

    /**
     * Create the doubly linked list node.
     */
    protected class Node {
        E item;
        Node next = null;
        Node prev = null;
        public Node(E element) {
            item = element;
        }
 
        public Node(E element, Node prevNode, Node nextNode) {
            item = element;
            prev = prevNode;
            prevNode.next = this;
            this.prev = prevNode;

            next = nextNode;
            nextNode.prev = this;
            this.next = nextNode;
        }
 
        public void remove() {
            prev.next = next;
            next.prev = prev;
        }
    }
 
    /**
     * Add an object to the beginning of the list.
     *
     * @param obj the object to add
     * @return false if list is full; otherwise true
     * @throws NullPointerException if object is null
     */
    public boolean addFirst(E obj) {
        if (isFull()) {
            return false;
        } else if (obj == ((E) null)) {
            throw new NullPointerException("data is null");
        } else {
            Node newNode = new Node(obj, front, front.next);
            currentSize++;
            return true;
        }
    }
 
    /**
     * Add an object to the end of the list.
     *
     * @param obj the object to add
     * @return false if list is full; otherwise true
     */
    public boolean addLast(E obj) {
        if (isFull()) {
            return false;
        } else if (obj == (E) (null)) {
            return false;
        } else {
            Node newNode = new Node(obj, rear.prev, rear);
            currentSize++;
            return true;
        }
    }

    /**
     * Remove the object at the beginning of the list.
     *
     * @return the removed object; otherwise null if list is empty
     */
    public E removeFirst() {
        if (isEmpty()) {
            return ((E)null);
        } else {
            E item = front.next.item;
            front.next.next.prev = front;
            front.next = front.next.next; 
            currentSize--;
            return item;
        }
    }
 
    /**
    * Remove the object at the end of the list.
    *
    * @return the removed object; otherwise null if list is empty
    */
    public E removeLast() {
        if (isEmpty()) {
            return ((E) null);
        } else {
            E item = rear.prev.item;
            rear.prev.prev.next = rear;
            rear.prev = rear.prev.prev; 
            currentSize--;
            return item;
        }
    }
 
    /**
     * Remove a specific object from the list.
     *
     * @param obj the object to remove
     * @return the removed object; otherwise null
     */
    public E remove(E obj) {
        Node tempNode = front.next;
        if (obj == ((E)null)) {
            return null;
        } else if (isEmpty()) {
            return null;
        } else if (contains(obj)) {
            while (tempNode != null) {
                if (obj != null && ((Comparable<E>)(tempNode.item)).compareTo(obj) == 0) {
                    tempNode.remove();
                    currentSize--;
                    return obj;
                } else {
                    tempNode = tempNode.next;
                }
            }
        }
        return null;
    }

    /**
     * Return the object at the beginning of the list.
     *
     * @return the object at the beginning of the list
     */
    public E peekFirst() {
        return (isEmpty() ? null : front.next.item);
    }
 
    /**
     * Return the object at the end of the list.
     *
     * @return the object at the end of the list
     */
    public E peekLast() {
        return (isEmpty() ? null : rear.prev.item);
    }
 
    /**
     * Verify the specific object exist in the list.
     *
     * @param obj the specified object to verify
     * @return true if the object is found; otherwise false
     */
    public boolean contains(E obj) { 
        return (find(obj) != null ? true : false);
    }
 
    /**
     * Find the first occurence of a specific object in the list.
     *
     * @param obj the specified object to find
     * @return the found object; otherwise null
     */
    public E find(E obj) {
        Node tempNode = front.next;
        if (obj == ((E)null)) {
            return null;
        }

        while (tempNode.item != null) {
            if (((Comparable<E>)(tempNode.item)).compareTo(obj) == 0) {
                return tempNode.item;
            }
            tempNode = tempNode.next;
        }
        return ((E)null);
    }

    /**
     * Return the list to an empty state.
     */
    public void clear() {
        currentSize = 0;
        front.next = rear;
        rear.prev = front;
    }
 
    /**
     * Verify if the list is empty.
     *
     * @return true if list is empty; otherwise false
     */
    public boolean isEmpty() {
        return (currentSize == 0);
    }

    /**
     * Verify if the list is full.
     */
    public boolean isFull() {
        return (currentSize == maxSize);
    }
 
    /**
     * Return the number of object in the list.
     *
     * @return the integer value for the current size of the list
     */
    public int size() {
        return currentSize;
    }
 
    /**
     * Return the iterator.
     *
     * @return the iterator
     */
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }
 
    /**
     * Implementation for the iterator of the object in the list.
     *
     * @throws ConcurrentModificationException if iterating pass current size
     * @throws NoSuchElementException if object does not exist
     */
    private class IteratorHelper implements Iterator<E> {
        private Node left = front;
        private Node right = left.next;
        private E item1;
        private int index = 0;
 
        public IteratorHelper() {}
 
        public boolean hasNext() {
            return (right.item != (E) null);
        }
        
        public boolean hasPrevious() {
            return (left.item != (E) null);
        }
 
        public E next() {
            if (index >= currentSize) {
                throw new ConcurrentModificationException();
            } else if (!hasNext()) {
                throw new NoSuchElementException("No such element in iterator next");
            } else {
                item1 = right.item;
                left = right;
                right = right.next;
                index++;
            }
            return item1;
        }
    
        public void remove() {
            if (right != null) {
                Node tempNode = right;
                right = right.next;
                tempNode.remove();
                currentSize--;
            }
        }
    }
}
