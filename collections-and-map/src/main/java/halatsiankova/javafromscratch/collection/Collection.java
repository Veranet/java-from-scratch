package halatsiankova.javafromscratch.collection;

public interface Collection<T> extends Iterable<T> {
    /**
     * Returns collection length
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Return true if the collection is empty
     * @return true if the collection is empty
     */
    boolean isEmpty();

    /**
     * Add an object to the end of the collection, returns true if the collection has changed
     * @throws ClassCastException – if the class of the specified element prevents
     * it from being added to this collection
     * @throws NullPointerException – if the specified element is null
     * and this collection does not permit null elements
     * @throws IllegalArgumentException – if some property of the element prevents it from
     * being added to this collection
     * @throws IllegalStateException – if the element cannot be added at this time due to
     * insertion restrictions
     * @throws UnsupportedOperationException – if the add operation is not supported by
     * this collection
     * @param element element to be appended to this list
     * @return true if this collection changed as a result of the call
     */
    boolean put(T element);

    /**
     * Returns true if the collection contains an object
     * @throws ClassCastException – if the type of the specified element
     * is incompatible with this collection
     * @param element – element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    boolean contains(Object element);

    /**
     * Removes an object from the collection.
     * Returns true - if the collection has changed
     * @throws ClassCastException – if the type of the specified element is
     * incompatible with this collection
     * @throws UnsupportedOperationException - if the remove operation
     * is not supported by this collection
     * @param element – element to be removed from this list, if present
     * @return true if the collection has changed
     */
    boolean delete(T element);
}
