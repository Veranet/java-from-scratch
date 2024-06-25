package halatsiankova.javafromscratch.collection;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyHashMapTest {

    @Nested
    public class Put {
        @Test
        void shouldReturnOldValueAndAddedEntryInMapWhenByThisKeyExistEntry() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            Integer actual = map.put(3, 23);
            assertEquals(13, actual);
            assertEquals(23, map.get(3));
        }

        @Test
        void shouldReturnNullWhenByThisKeyDoesntExistEntry() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            Integer actual = map.put(1, 11);
            assertNull(actual);
        }
    }

    @Nested
    public class Get {
        @Test
        void shouldReturnValueWhenMapContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(1, 11);
            Integer expected = 11;
            Integer actual = map.get(1);
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnNullWhenMapNotContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(1, 11);
            Integer actual = map.get(4);
            assertNull(actual);
        }
    }

    @Nested
    public class Size {
        @Test
        void shouldReturn1WhenSize1() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertEquals(1, map.size());
        }

        @Test
        void shouldReturn0WhenSize0() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            assertEquals(0, map.size());
        }
    }

    @Nested
    public class IsEmpty {
        @Test
        void shouldReturnTrueWhenMapIsEmpty() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            assertTrue(map.isEmpty());
        }

        @Test
        void shouldReturnFalseWhenMapIsNotEmpty() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertFalse(map.isEmpty());
        }
    }

    @Nested
    public class ContainsKey {
        @Test
        void shouldReturnTrueWhenMapContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertTrue(map.containsKey(3));
        }

        @Test
        void shouldReturnFalseWhenMapNotContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertFalse(map.containsKey(4));
        }
    }

    @Nested
    public class Delete {
        @Test
        void shouldReturnValueAndRemovedOnKeyWhenMapContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertEquals(13, map.delete(3));
        }

        @Test
        void shouldReturnValueAndRemovedOnKeyWhenMapContains() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            map.put(18, 22);
            assertEquals(22, map.delete(18));
        }

        @Test
        void shouldReturnNullWhenMapNotContainsKey() {
            MyHashMap<Integer, Integer> map = new MyHashMap<>();
            map.put(3, 13);
            assertNull(map.delete(8));
        }
    }

    @Nested
    public class Equals {
        @Test
        void shouldReturnTrueWhenObjectsAreEquals() {
            MyHashMap<Integer, Integer> mapThis = new MyHashMap<>();
            mapThis.put(3, 13);
            MyHashMap<Integer, Integer> mapThat = new MyHashMap<>();
            mapThat.put(3, 13);
            assertEquals(mapThis, mapThat);
        }

        @Test
        void shouldReturnFalseWhenObjectsAreEquals() {
            MyHashMap<Integer, Integer> mapThis = new MyHashMap<>();
            mapThis.put(3, 3);
            MyHashMap<Integer, Integer> mapThat = new MyHashMap<>();
            mapThat.put(13, 13);
            assertNotEquals(mapThis, mapThat);
        }

        @Test
        void shouldReturnFalseWhenOneObjectIsNull() {
            MyHashMap<Integer, Integer> mapThis = new MyHashMap<>();
            mapThis.put(3, 13);
            MyHashMap<Integer, Integer> mapThat = null;
            assertNotEquals(mapThis, mapThat);
        }
    }

    @Nested
    public class ToString {
        @Test
        void shouldReturnStringWhenMethodWasCalled() {
            MyHashMap<Integer, String> map = new MyHashMap<>();
            map.put(1, "A");
            map.put(2, "AB");
            map.put(3, null);
            map.put(null, "ABC");
            final String expected = "[null=ABC, 1=A, 2=AB, 3=null]";
            assertEquals(expected, map.toString());
        }

        @Test
        void shouldReturnEmptyStringWhenSizeEqualZero() {
            MyHashMap<Integer, String> map = new MyHashMap<>();
            String expected = "[]";
            assertEquals(expected, map.toString());
        }
    }
}