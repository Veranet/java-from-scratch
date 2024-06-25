package halatsiankova.javafromscratch.collection;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MyHashSetTest {

    @Nested
    public class IsEmpty {
        @Test
        void shouldReturnTrueWhenHashSetIsEmpty() {
            MyHashSet<Integer> map = new MyHashSet<>();
            assertTrue(map.isEmpty());
        }

        @Test
        void shouldReturnFalseWhenHashSetIsNotEmpty() {
            MyHashSet<Integer> map = new MyHashSet<>();
            map.put(1);
            assertFalse(map.isEmpty());
        }
    }

    @Nested
    public class Put {

        @Test
        void shouldReturnTrueWhenElementAdded() {
            MyHashSet<Integer> map = new MyHashSet<>();
            boolean actual = map.put(1);
            assertTrue(actual);
        }
    }

    @Nested
    public class Contains {
        @Test
        void shouldReturnTrueWhenSetContainsElement() {
            MyHashSet<Integer> set = new MyHashSet<>();
            set.put(1);
            assertTrue(set.contains(1));
        }

        @Test
        void shouldReturnFalseWhenSetNotContainsElement() {
            MyHashSet<Integer> set = new MyHashSet<>();
            set.put(1);
            assertFalse(set.contains(12));
        }
    }

    @Nested
    public class Delete {
        @Test
        void shouldReturnTrueAndRemovedObjectWhenMethodWasCalled() {
            MyHashSet<Integer> set = new MyHashSet<>();
            set.put(1);
            assertTrue(set.delete(1));
            assertEquals(0, set.size());
        }

        @Test
        void shouldReturnFalseAndNotChangedSetWhenMethodWasCalled() {
            MyHashSet<Integer> actual = new MyHashSet<>();
            actual.put(1);
            assertFalse(actual.delete(5));
            MyHashSet<Integer> expected = new MyHashSet<>();
            expected.put(1);
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class Size {
        @Test
        void shouldReturn1WhenSize1() {
            MyHashSet<Integer> set = new MyHashSet<>();
            set.put(1);
            assertEquals(1, set.size());
        }

        @Test
        void shouldReturn0WhenSize0() {
            MyHashSet<Integer> set = new MyHashSet<>();
            assertEquals(0, set.size());
        }
    }

    @Nested
    public class ToString {
        @Test
        void shouldReturnStringWhenMethodWasCalled() {
            MyHashSet<Integer> set = new MyHashSet<>();
            set.put(null);
            set.put(11);
            String expected = "[null, 11]";
            assertEquals(expected, set.toString());
        }

        @Test
        void shouldReturnEmptyStringWhenSizeEqualZero() {
            MyHashSet<Integer> set = new MyHashSet<>();
            String expected = "[]";
            assertEquals(expected, set.toString());
        }
    }
}
