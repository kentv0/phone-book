package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E> implements LinearListADT<E> {
	protected class Node<E> {		// Node class.
		E data;
		Node<E> next, prev;

		public Node(E obj) {		// Two fields:
			data = obj;				// Generic parameterized type data.
			next = prev = null;			// Points to the next element in list.
			}
	}
	
    int maxSize = DEFAULT_MAX_CAPACITY;
    int currentSize = 0;
    Node<E> head = null, tail = null;		// Initial start: head = tail = null.
	
	public boolean addFirst(E obj) {
	    Node<E> newNode = new Node<E> (obj);
	    if(head == null) 
		    head = tail = newNode;
	    else {
		newNode.next = head;
		head = newNode;
	    }
		currentSize++;
		return true;
		}

	public boolean addLast(E obj) {
	    Node<E> newNode = new Node<E> (obj);
		if(tail == null)
			tail = head = newNode;
		else {
			tail.next = newNode;
			tail = newNode;
			}
		currentSize++;
		return true;
		}
	
	public E removeFirst() { 
	    if(isEmpty())
		    return null;
	    E tmp = head.data;
	    if(head == tail)		// If there's only one node in list.
	    	head = tail = null;
	    else {					// Adds new pointer w/ null field to position before the first node to remove it.
	    	Node<E> next = head.next;
			head.next = null;
			head = next;
	    	}
	    currentSize--;
		return tmp;
		}

	public E removeLast() {
		if(isEmpty())
			return null;
		E tmp = tail.data;
		Node<E> prev = null, curr = head;
		if(head == tail)
			head = tail = null;		// If there's only one node in list.
		else {
			prev = null;
			curr = head;
			while(curr != tail) {	// Starts through from head to tail.
				prev = curr;
				curr = curr.next;
				}
			prev.next = null;		// Nulls last node's field to remove.
			tail = prev;
					}
				currentSize--;
				return tmp;
				}
	
	public E remove(E obj) {
		Node<E> prev = null, curr = head;
		while(curr != null && ((Comparable<E>)obj).compareTo(curr.data) != 0) {
			prev = curr;
			curr = curr.next;
			}
		if(curr == null) 			// Specified object not found in list.
			return null;
		else if(prev == null)		// Removes specified object if found at head.
			head = head.next;
		else
			prev.next = curr.next;		// Removes specified object at current position.
		currentSize--;
		return obj;
	}

	public E peekFirst(){
	    return head.data;			// Returns data at first node.
	}

	public E peekLast(){			// Returns data at last node.
	    return tail.data;
	}

	public boolean contains(E obj) {
	    Node<E> tmp = head;
	    while(tmp != null) {
	    	if(((Comparable<E>)tmp.data).compareTo(obj) == 0)
	    		return true;
	    	tmp = tmp.next;			// Searches by comparing specified object to list. 
	    	}
	    		return false;
	    		}
	
	public E find(E obj) {
		Node<E> tmp = head;
		while(tmp != null) {
			if(tmp.data == obj)
				return obj;
			tmp = tmp.next;
			}
				return null;
				}
	
	public void clear() {		// Truncating all nodes in between by nullifying both fields of head/tail.
	    head = tail = null;
		currentSize = 0;
		}
	
    public boolean isEmpty() { return (this.size() == 0); }
    
	public boolean isFull() { return false; }

	public int size() { return currentSize; }
	
	public Iterator<E> iterator() {	
	    return new IteratorHelper();
	}
	
    class IteratorHelper implements Iterator<E>{
	    Node<E> iterIndex;
 
		public IteratorHelper() {
		    iterIndex = (Node<E>)head;
			}
		
		public boolean hasNext() {
			return iterIndex != null;
			}
		
		public E next() {
		    if(!hasNext()) 
			    throw new NoSuchElementException();
			E tmp = iterIndex.data;
			iterIndex = iterIndex.next;	
			return tmp;		
			}

		public void remove() {
		    throw new UnsupportedOperationException();	
			}	
    }
}

