package halatsiankova.javafromscratch.collection;

public interface Collection<T> extends Iterable<T> {
    /**
     * Returns collection length
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Return true if the collection is empty
     *
     * @return true if the collection is empty
     */
    boolean isEmpty();

    /**
     * Add an object to the end of the collection, returns true if the collection has changed
     *
     * @param element element to be appended to this list
     * @return true if this collection changed as a result of the call
     * @throws ClassCastException            – if the class of the specified element prevents
     *                                       it from being added to this collection
     * @throws NullPointerException          – if the specified element is null
     *                                       and this collection does not permit null elements
     * @throws IllegalArgumentException      – if some property of the element prevents it from
     *                                       being added to this collection
     * @throws IllegalStateException         – if the element cannot be added at this time due to
     *                                       insertion restrictions
     * @throws UnsupportedOperationException – if the add operation is not supported by
     *                                       this collection
     */
    boolean put(T element);
}
