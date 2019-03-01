import java.util.ArrayList;
import java.util.List;
import java.util.EmptyStackException;


/**
 * Simple stack using ArrayList
 * 
 * @author Richard Liu
 * @since  Feb 27, 2019
 */
public class ArrayStack<E> implements Stack<E> {
	private List<E> elements;
	
	public ArrayStack() {
		elements = new ArrayList<E>();
	}
	
	/**
	 * Checks if the ArrayStack is empty
	 * 
	 * @return	true if the stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	/**
	 * Get the top element in the stack
	 * 
	 * @return	Top element of the stack
	 */
	public E peek() {
		if (isEmpty())
			throw new EmptyStackException();
		return elements.get(elements.size()-1);
	}
	
	/**
	 * Add an object to the top of the stack
	 * 
	 * @param obj	The object to push to the top of the stack
	 */
	public void push(E obj) {
		elements.add(obj);
	}
	
	/**
	 * Remove an object from the top of the stack
	 * 
	 * @return	Object that was at the top of the stack
	 */
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();
		return elements.remove(elements.size()-1);
	}
	
	public String toString() {
		if (isEmpty()) return "[]";
		
		String str = "";
		for (int i = 0; i < elements.size(); i++)
			str += (i > 0 ? ", " : "") + elements.get(i).toString();
		return "[ " + str + " ]";
	}
}