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
     * {@inheritDoc}
     */
    @Override
    boolean contains(Object element);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Object element);

    @Override
    Iterator<T> iterator();
}
