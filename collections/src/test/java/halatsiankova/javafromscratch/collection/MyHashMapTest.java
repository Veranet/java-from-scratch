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
    class Put {
        @Test
        void shouldReturnOldValueAndAddedEntryInMapWhenByThisKeyExistEntry() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            Integer actual = map.put(3, 23);
            assertEquals(13, actual);
            assertEquals(23, map.get(3));
        }

        @Test
        void shouldReturnNullWhenByThisKeyDoesNotExistEntry() {
            Map<Integer, Integer> map = new HashMap<>();
            Integer actual = map.put(1, 11);
            assertNull(actual);
        }
    }

    @Nested
    class Get {
        @Test
        void shouldReturnValueWhenMapContainsKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, 11);
            Integer expected = 11;
            Integer actual = map.get(1);
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnNullWhenMapDoesNotContainKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(1, 11);
            Integer actual = map.get(4);
            assertNull(actual);
        }
    }

    @Nested
    class Size {
        @Test
        void shouldReturn1WhenSize1() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertEquals(1, map.size());
        }

        @Test
        void shouldReturn0WhenSize0() {
            Map<Integer, Integer> map = new HashMap<>();
            assertEquals(0, map.size());
        }
    }

    @Nested
    class IsEmpty {
        @Test
        void shouldReturnTrueWhenMapIsEmpty() {
            Map<Integer, Integer> map = new HashMap<>();
            assertTrue(map.isEmpty());
        }

        @Test
        void shouldReturnFalseWhenMapIsNotEmpty() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertFalse(map.isEmpty());
        }
    }

    @Nested
    class ContainsKey {
        @Test
        void shouldReturnTrueWhenMapContainsKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertTrue(map.containsKey(3));
        }

        @Test
        void shouldReturnFalseWhenMapNotContainsKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertFalse(map.containsKey(4));
        }
    }

    @Nested
    class Delete {
        @Test
        void shouldReturnValueAndRemoveOnKeyWhenMapContainsKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertEquals(13, map.delete(3));
        }

        @Test
        void shouldReturnValueAndRemoveOnKeyWhenMapContains() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            map.put(18, 22);
            assertEquals(22, map.delete(18));
        }

        @Test
        void shouldReturnNullWhenMapNotContainsKey() {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(3, 13);
            assertNull(map.delete(8));
        }
    }

    @Nested
    class Equals {
        @Test
        void shouldReturnTrueWhenObjectsAreEquals() {
            Map<Integer, Integer> mapThis = new HashMap<>();
            mapThis.put(3, 13);
            Map<Integer, Integer> mapThat = new HashMap<>();
            mapThat.put(3, 13);
            assertEquals(mapThis, mapThat);
        }

        @Test
        void shouldReturnFalseWhenObjectsAreNotEquals() {
            Map<Integer, Integer> mapThis = new HashMap<>();
            mapThis.put(3, 3);
            Map<Integer, Integer> mapThat = new HashMap<>();
            mapThat.put(13, 13);
            assertNotEquals(mapThis, mapThat);
        }

        @Test
        void shouldReturnFalseWhenOneObjectIsNull() {
            Map<Integer, Integer> mapThis = new HashMap<>();
            mapThis.put(3, 13);
            assertNotEquals(mapThis, null);
        }
    }

    @Nested
    class ToString {
        @Test
        void shouldReturnString() {
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "A");
            map.put(2, "AB");
            map.put(3, null);
            map.put(null, "ABC");
            String expected = "[null=ABC, 1=A, 2=AB, 3=null]";
            assertEquals(expected, map.toString());
        }

        @Test
        void shouldReturnEmptyStringWhenSizeEqualZero() {
            Map<Integer, String> map = new HashMap<>();
            String expected = "[]";
            assertEquals(expected, map.toString());
        }
    }
}