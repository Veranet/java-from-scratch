package halatsiankova.javafromscratch.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

public class MyHashMap<K, V> implements Map<K, V> {
    private Entry<K, V>[] table;
    private int size;
    static final float LOAD_FACTOR = 0.75f;
    private int modCount;

    public MyHashMap() {
        table = new Entry[16];
    }

    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException       if the class of the specified key or value
     *                                  prevents it from being stored in this map
     * @throws IllegalArgumentException if some property of the specified key
     *                                  or value prevents it from being stored in this map
     */
    @Override
    public V put(K key, V value) {
        final int hash = calcHash(key);
        final int index = calcTable(hash, table.length);
        Entry<K, V> entry = table[index];
        while (entry != null && (entry.hash != hash || !Objects.equals(entry.key, key))) {
            entry = entry.next;
        }
        if (entry == null) {
            table[index] = new Entry<>(key, value, hash, table[index]);
            size++;
            resize();
            modCount++;
            return null;
        } else {
            final V oldValue = entry.value;
            entry.value = value;
            modCount++;
            return oldValue;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException if the key is of an inappropriate type for this map
     */
    @Override
    public V get(final Object key) {
        final Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
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
     * {@inheritDoc}
     *
     * @throws ClassCastException – if the key is of an inappropriate type for this map.
     */
    @Override
    public boolean containsKey(Object key) {
        return findEntry(key) != null;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ClassCastException if the key is of an inappropriate type for this map.
     */
    @Override
    public V delete(Object key) {
        int hash = calcHash(key);
        int basket = Math.abs(hash % table.length);
        Entry<K, V> entry;
        if ((entry = table[basket]) != null) {
            Entry<K, V> prev = null, node;
            K k;
            if (entry.hash == hash && ((k = entry.key) == key || (key != null && key.equals(k)))) {
                prev = entry;
            } else if ((node = entry.next) != null) {
                do {
                    if (node.hash == hash && ((k = node.key) == key || (key != null && key.equals(k)))) {
                        prev = node;
                        break;
                    }
                    entry = node;
                } while ((node = node.next) != null);
            }
            if (prev != null) {
                if (prev == entry) {
                    table[basket] = prev.next;
                } else {
                    entry.next = prev.next;
                }
                --size;
                modCount++;
                return prev.value;
            }
        }
        return null;
    }

    /**
     * Computes hashCode HashMap
     *
     * @return int a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (Entry<K, V> entry : this.table) {
            hash = hash + (entry == null ? 0 : entry.hashCode());
        }
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        final Map that = (Map) obj;
        if (that.size() != this.size) {
            return false;
        }
        for (final Map.Entry<K, V> entry : this) {
            final V value = entry.getValue();
            if (!Objects.equals(value, that.get(entry.getKey()))) {
                return false;
            }
            if (value == null && !that.containsKey(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the object
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        final int size = this.size();
        int index = 0;
        for (final Map.Entry<K, V> entry : this) {
            stringBuilder.append(entry.toString());
            index++;
            if (index != size) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private Entry<K, V> findEntry(final Object key) {
        final int hash = calcHash(key);
        final int basket = Math.abs(hash % table.length);
        Entry<K, V> entry = table[basket];
        while (entry != null && (entry.hash != hash || !Objects.equals(entry.getKey(), key))) {
            entry = entry.next;
        }
        return entry;
    }

    private static <T> int calcHash(T key) {
        return key == null ? 0 : key.hashCode();
    }

    private int calcTable(final int hash, final int length) {
        return Math.abs(hash % length);
    }

    private void resize() {
        if (size > table.length * LOAD_FACTOR) {
            final Entry<K, V>[] newTable = new Entry[table.length * 2];
            for (final Map.Entry<K, V> entry : this) {
                final int hash = calcHash(entry.getKey());
                final int indexBasket = Math.abs(hash % newTable.length);
                newTable[indexBasket] = new Entry<>(entry.getKey(),
                        entry.getValue(),
                        hash,
                        newTable[indexBasket]);
            }
            table = newTable;
        }
    }

    /**
     * Returns a MapIterator
     */
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<Map.Entry<K, V>> {
        int nextIndex;
        Entry<K, V> nextEntry;
        Entry<K, V> currentEntry;
        Entry<K, V> prevEntry;
        boolean checkCallMethodNext = false;

        private int expectedModCount = modCount;

        /**
         * Find first entry != null.
         */
        public MapIterator() {
            findNextEntry();
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements. else returns false.
         */
        @Override
        public boolean hasNext() {
            checkForModification();
            return nextEntry != null;
        }

        private void findNextEntry() {
            while (nextIndex < table.length) {
                if (table[nextIndex] != null) {
                    nextEntry = table[nextIndex++];
                    break;
                }
                nextIndex++;
            }
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return element
         * @throws ConcurrentModificationException when the collection was modified while the iterator was running.
         */
        @Override
        public Entry<K, V> next() {
            checkForModification();
            final Entry<K, V> current = nextEntry;
            if (currentEntry != null) {
                prevEntry = currentEntry;
            }
            if (nextEntry.next != null) {
                nextEntry = nextEntry.next;
            } else {
                nextEntry = null;
                findNextEntry();
            }
            currentEntry = current;
            checkCallMethodNext = true;
            return current;
        }

        /**
         * Removes from the collection the last element returned by this iterator (optional operation).
         * This method can be called only once per call to next.
         *
         * @throws IllegalStateException           when element is null and when remove called without calling next
         * @throws ConcurrentModificationException when the collection was modified while the iterator was running.
         */
        public void remove() {
            checkForModification();
            if (!checkCallMethodNext) {
                throw new IllegalArgumentException("The call to the remove method should be made only " +
                        "after calling the next method.");
            }
            int currentBasket = calcTable(calcHash(currentEntry.key), table.length);
            if (prevEntry == null) {
                currentEntry = null;
                table[currentBasket] = null;
            } else if (currentEntry.next != null) {
                currentEntry = new Entry<>(prevEntry.key, prevEntry.value, prevEntry.hash, currentEntry.next);
                table[currentBasket] = currentEntry;
            } else {
                prevEntry.next = nextEntry;
            }
            checkCallMethodNext = false;
            size--;
            expectedModCount = modCount;
        }

        private void checkForModification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private final int hash;
        private Entry<K, V> next;

        public Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return 31 * hash + calcHash(value);
        }

        public final String toString() {
            return (key == null ? null : key.toString()) + "="
                    + (value == null ? null : value.toString());
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue());
            }
            return false;
        }
    }
}
