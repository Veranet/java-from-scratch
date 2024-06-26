package halatsiankova.javafromscratch.collection;

import java.util.Iterator;

public interface List<T> extends Collection<T> {
    /**
     * Add an object to the list at position index, the elements behind it should move to the right.
     * Returns true if the collection has changed
     * @throws UnsupportedOperationException if the add operation is not supported by this list
     * @throws ClassCastException if the class of the specified element prevents
     * it from being added to this list
     * @throws NullPointerException  if the specified element is null and
     * this list does not permit null elements
     * @throws IllegalArgumentException if some property of this element prevents
     * it from being added to this list
     * @param element element to be inserted
     * @param index index at which the specified element is to be inserted
     * @return true if the collection has changed
     */
    boolean put(int index, T element);

    /**
     * Take object at position index
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index < 0 || index >= size())
     * @param index index of the element to return
     * @return T the element at the specified position in this list
     */
    T get(int index);

    /**
     * Remove the object at position index and returns the removed object
     * @throws UnsupportedOperationException if the remove operation is not supported by this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     * @param index the index of the element to be removed
     * @return T the element previously at the specified position
     */
    T delete(int index);

    /**
     * Return an array with all elements of the collection
     * @return - an array containing all elements in this list in proper sequence
     */
    Object[] toArray();

    /**
     * Return an iterator over elements in this list
     * Returns a list iterator over the elements in this list
     * @return - list iterator over the elements in this list
     */
    Iterator<T> iterator();
}
