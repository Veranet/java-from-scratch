package halatsiankova.javafromscratch.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A simple implementation of an ArrayList.
 *
 * @param <T> the type of elements in this list
 */
public class MyArrayList<T> implements Collection<T> {
    private T[] data;
    private int size;
    protected int modCount;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this.data = (T[]) new Object[10];
        this.size = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(T element) {
        put(size, element);
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @return true if this collection changed as a result of the call
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size})
     */
    public boolean put(int index, T element) {
        checkIndexForAdd(index);
        if (size + 1 >= data.length) {
            resize();
        }
        size++;
        System.arraycopy(data, index, data, index + 1, size - index - 1);
        data[index] = element;
        modCount++;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size})
     */
    public T get(int index) {
        checkIndexInCollection(index, size);
        return data[index];
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (Object e : this) {
            array[index++] = e;
        }
        return array;
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size})
     */
    public T delete(int index) {
        checkIndexInCollection(index, size);
        T result = data[index];
        this.size--;
        System.arraycopy(data, index + 1, data, index, data.length - index - 1);
        modCount++;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Increases the capacity of this list, if necessary, to ensure that it can
     * hold at least the number of elements specified by the minimum capacity argument.
     */
    private void resize() {
        int newLength = data.length * 2;
        if (data.length == 0) {
            newLength = 1;
        }
        T[] dataNew = (T[]) new Object[newLength];
        System.arraycopy(data, 0, dataNew, 0, size);
        data = dataNew;
    }

    /**
     * Checks if the given index is valid for adding a new element.
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size})
     */
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
    }

    /**
     * Checks if the given index is valid for accessing an element.
     *
     * @param index  the index to check
     * @param length the length of the collection
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= length})
     */
    private void checkIndexInCollection(int index, int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + length);
        }
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        Iterator<T> it = this.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            T element = it.next();
            if (element == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(element);
            }
            if (i != size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    public ArrayListIterator iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {

        private int current;

        private int expectedModCount = modCount;

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements. else returns false
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return element
         */
        @Override
        public T next() {
            checkForModification();
            return data[current++];
        }


        /**
         * Removes from the collection the last element returned by this iterator (optional operation).
         * This method can be called only once per call to next.
         */
        @Override
        public void remove() {
            checkForModification();
            MyArrayList.this.delete(--current);
            expectedModCount = modCount;
        }

        private void checkForModification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
