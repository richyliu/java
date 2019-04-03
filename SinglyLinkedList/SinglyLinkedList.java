import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - Linked list with methods to add, remove, set, etc. Keeps
 *  track of a head and a tail when traversing the list.
 *
 *	@author	Richard Liu
 *	@since	April 1, 2019
 */
public class SinglyLinkedList<E extends Comparable<E>>
{
	/* Fields */
	private ListNode<E> head, tail;		// head and tail pointers to list
	
	/* No-args Constructors */
	public SinglyLinkedList() {
		head = tail = null;
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList) {
		head = new ListNode<E>(oldList.head.getValue(), oldList.head.getNext());
		tail = new ListNode<E>(oldList.tail.getValue(), oldList.tail.getNext());
	}
	
	/**	Clears the list of elements */
	public void clear() {
		head = tail = null;
	}
	
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		ListNode<E> node = new ListNode<E>(obj);
		if (head == null)
			head = tail = node;
		else {
			tail.setNext(node);
			tail = node;
		}
		return true;
	}
	
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		ListNode<E> node = head;
		ListNode<E> newNode = new ListNode<E>(obj);
		
		if (index == 0) {
			if (head == null) {
				head = newNode;
				return true;
			} else {
				newNode.setNext(head);
				head = newNode;
				return true;
			}
		}
		
		int i;
		for (i = 0; node.getNext() != null; i++) {
			if (i+1 == index) {
				newNode.setNext(node.getNext());
				node.setNext(newNode);
				return true;
			}
			node = node.getNext();
		}
		if (i+1 == index) {
			tail.setNext(newNode);
			tail = newNode;
			return true;
		}
		
		throw new NoSuchElementException();
	}
	
	/**	@return the number of elements in this list */
	public int size() {
		if (head == null) return 0;
		
		ListNode<E> node = head;
		int i;
		for (i = 0; node.getNext() != null; i++) {
			node = node.getNext();
		}
		return i+1;
	}
	
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		ListNode<E> node = head;
		for (int i = 0; node != null; i++) {
			if (i == index) return node;
			node = node.getNext();
		}
		
		throw new NoSuchElementException();
	}
	
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj) {
		ListNode<E> node = head;
		ListNode<E> newNode = new ListNode<E>(obj);
		
		if (index == 0) {
			if (head == null)
				throw new NoSuchElementException();
			else {
				ListNode<E> replaced = head;
				newNode.setNext(head.getNext());
				head = newNode;
				return replaced.getValue();
			}
		}
		
		for (int i = 0; node != null; i++) {
			if (i+1 == index) {
				ListNode<E> replaced = node.getNext();
				newNode.setNext(replaced.getNext());
				node.setNext(newNode);
				return replaced.getValue();
			}
			node = node.getNext();
		}
		
		throw new NoSuchElementException();
	}
	
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {
		ListNode<E> node = head;
		
		if (index == 0) {
			if (head == null)
				throw new NoSuchElementException();
			else {
				ListNode<E> removed = head;
				head = head.getNext();
				return removed.getValue();
			}
		}
		
		for (int i = 0; node != null; i++) {
			if (i+1 == index) {
				ListNode<E> removed = node.getNext();
				node.setNext(removed.getNext());
				return removed.getValue();
			}
			node = node.getNext();
		}
		
		throw new NoSuchElementException();
	}
	
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty() {
		return head == null;
	}
	
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) {
		ListNode<E> node = head;
		
		for (int i = 0; node != null; i++) {
			if (node.getValue().equals(object)) {
				return true;
			}
			node = node.getNext();
		}
		
		return false;
	}
	
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) {
		ListNode<E> node = head;
		
		for (int i = 0; node != null; i++) {
			if (node.getValue().equals(element)) {
				return i;
			}
			node = node.getNext();
		}
		
		return -1;
	}
	
	/**	Prints the list of elements */
	public void printList()
	{
		ListNode<E> ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
	

}