package halatsiankova.javafromscratch.collection;

import java.util.Iterator;

public class MyHashSet<T> extends AbstractCollection<T> implements Set<T> {
    private final MyHashMap<T, Boolean> map;

    public MyHashSet() {
        map = new MyHashMap<>();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean put(T element) {
        return map.put(element, true) == null;
    }

    public boolean contains(Object element) {
        return map.containsKey(element);
    }

    public boolean delete(Object element) {
        T result = (T) map.delete(element);
        return result != null;
    }

    public int size() {
        return map.size();
    }

    /**
     * @inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T> {
        private final Iterator<Map.Entry<T, Boolean>> it;

        public SetIterator() {
            it = map.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public void remove() {
            it.remove();        }
    }
}
