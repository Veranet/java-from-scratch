package halatsiankova.javafromscratch.collection;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {
    /**
     * Returns the number of key-value mappings in this map
     * @return the number of key-value mappings in this map
     */
    int size();

    /**
     * Returns true if this map contains no key-value mappings
     * @return true if this map contains no key-value mappings
     */
    boolean isEmpty();

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced by the specified value.
     * @throws ClassCastException if the class of the specified key or value
     * prevents it from being stored in this map
     * @throws IllegalArgumentException if some property of the specified key
     * or value prevents it from being stored in this map
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    V put(K key, V value);

    /**
     * Returns true if this map contains a mapping for the specified key
     * @throws ClassCastException – if the key is of an inappropriate type for this map.
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    boolean containsKey(Object key);

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key
     */
    V get(Object key);

    /**
     * Removes the mapping for a key from this map if it is present.
     * Returns the value to which this map previously associated the key,
     * or null if the map contained no mapping for the key.
     * @throws ClassCastException if the key is of an inappropriate type for this map
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    V delete(Object key);

    interface Entry<K, V> {

        /**
         * Returns the key corresponding to this entry.
         * @return the key corresponding to this entry
         */
        K getKey();

        /**
         * Returns the value corresponding to this entry.
         * @return the value corresponding to this entry
         */
        V getValue();
    }
}
