import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList - Linked list with methods to add, remove, set, etc. Keeps
 *	track of a head and a tail when traversing the list.
 *
 *	@author Richard Liu
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
		// copy empty list
		if (oldList.head == null) {
			head = tail = null;
		} else {
			// copy over the old head
			head = new ListNode<E>(oldList.head.getValue());

			// keep track of pointer to old list node
			ListNode<E> other = oldList.head;
			// and new list node
			ListNode<E> cur = head;
			// while still more nodes to copy
			while (other.getNext() != null) {
				// copy the node
				cur.setNext(new ListNode<E>(other.getNext().getValue(), other.getNext().getNext()));
				// advance pointers to nodes
				cur = cur.getNext();
				other = other.getNext();
			}
			// set tail to the end of the list
			tail = cur;
		}
	}

	/** Clears the list of elements */
	public void clear() {
		head = tail = null;
	}

	/** Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj) {
		// node to insert
		ListNode<E> node = new ListNode<E>(obj);
		// if empty list, set head and tail to new node
		if (head == null)
			head = tail = node;
		else {
			// otherwise add it to the tail
			tail.setNext(node);
			tail = node;
		}
		return true;
	}

	/** Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj) {
		// current node to track
		ListNode<E> node = head;
		// node to add
		ListNode<E> newNode = new ListNode<E>(obj);

		// if adding to first index
		if (index == 0) {
			// insert before current head
			if (head != null)
				newNode.setNext(head);
			else
				// or set the tail and the head to newNode
				tail = newNode;
			// head is now at newNode
			head = newNode;
			return true;
		}

		// loop through nodes
		int i;
		for (i = 0; node.getNext() != null; i++) {
			if (i+1 == index) {
				// if right index, insert into list
				newNode.setNext(node.getNext());
				node.setNext(newNode);
				return true;
			}
			node = node.getNext();
		}
		// if adding to end of list
		if (i+1 == index) {
			// add to tail and update tail
			tail.setNext(newNode);
			tail = newNode;
			return true;
		}

		// no index in list, throw exception
		throw new NoSuchElementException();
	}

	/** @return the number of elements in this list */
	public int size() {
		// no head is empty list
		if (head == null) return 0;

		// loop through list
		ListNode<E> node = head;
		int i;
		for (i = 0; node.getNext() != null; i++) {
			node = node.getNext();
		}
		// size is last index + 1
		return i+1;
	}

	/** Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index) {
		// keep track of "current" node
		ListNode<E> node = head;
		for (int i = 0; node != null; i++) {
			// return node at the index
			if (i == index) return node;
			node = node.getNext();
		}

		// no node found, throw exception
		throw new NoSuchElementException();
	}

	/** Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj) {
		// get current node at index
		ListNode<E> node = get(index);
		// and the old value to return
		E oldValue = node.getValue();
		// update the value of the node
		node.setValue(obj);

		return oldValue;
	}

	/** Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index) {
		// keep track of curren tnode
		ListNode<E> node = head;

		// removing head
		if (index == 0) {
			// cannot remove head of empty list
			if (head == null)
				throw new NoSuchElementException();
			else {
				// set head to next after current head
				ListNode<E> removed = head;
				head = head.getNext();
				// return removed
				return removed.getValue();
			}
		}

		// loop through nodes
		for (int i = 0; node != null; i++) {
			// once index is reaced
			if (i+1 == index) {
				// remove the node
				ListNode<E> removed = node.getNext();
				// link previous to next node
				node.setNext(removed.getNext());
				// return removed node
				return removed.getValue();
			}
			node = node.getNext();
		}

		// index not in list
		throw new NoSuchElementException();
	}

	/** @return true if list is empty; false otherwise */
	public boolean isEmpty() {
		// empty list --> head is null
		return head == null;
	}

	/** Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object) {
		// keep track of current node
		ListNode<E> node = head;

		// loop through nodes
		for (int i = 0; node != null; i++) {
			// once an object is reached which is equal, return true
			if (node.getValue().equals(object)) {
				return true;
			}
			node = node.getNext();
		}

		// no object found, false
		return false;
	}

	/** Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E element) {
		// track the current node
		ListNode<E> node = head;

		// loop through nodes
		for (int i = 0; node != null; i++) {
			// once found object, return the index
			if (node.getValue().equals(element)) {
				return i;
			}
			node = node.getNext();
		}

		// object node found, return -1
		return -1;
	}

	/** Prints the list of elements */
	public void printList()
	{
		// keep track of node
		ListNode<E> ptr = head;
		// while still more nodes
		while (ptr != null)
		{
			// print the node
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}


}
