package halatsiankova.javafromscratch.collection;

import java.util.Iterator;
import java.util.Objects;

/**
 * A simple implementation of a HashSet.
 */
public class HashSet<T> extends AbstractCollection<T> implements Set<T> {
    private final HashMap<T, Boolean> map;

    public HashSet() {
        map = new HashMap<>();
    }

    /**
     * @inheritDoc}
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * @inheritDoc}
     */
    public boolean put(T element) {
        return map.put(element, true) == null;
    }

    /**
     * @inheritDoc}
     */
    public boolean contains(Object element) {
        return map.containsKey(element);
    }

    /**
     * @inheritDoc}
     */
    public boolean delete(Object element) {
        T result = (T) map.delete(element);
        return result != null;
    }

    /**
     * @inheritDoc}
     */
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
     * @inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashSet<?> myHashSet)) return false;
        return Objects.equals(map, myHashSet.map);
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
