package halatsiankova.javafromscratch.collection;

import java.util.Iterator;

public interface Set<T> extends Collection<T> {
    /**
     * {@inheritDoc}
     */
    int size();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isEmpty();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean put(T element);

    /**
     * Remove an object from the collection.
     * Returns true if the collection has changed
     * @throws ClassCastException if the type of the specified element is
     * incompatible with this collection
     * @throws UnsupportedOperationException if the remove operation
     * is not supported by this collection
     * @param element element to be removed from this list, if present
     * @return true if the collection has changed
     */
    boolean delete(T element);

    /**
     * Return true if the collection contains an object
     * @throws ClassCastException if the type of the specified element
     * is incompatible with this collection
     * @param element element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    boolean contains(Object element);

    @Override
    Iterator<T> iterator();
}
