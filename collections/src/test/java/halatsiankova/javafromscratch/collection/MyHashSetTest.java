package halatsiankova.javafromscratch.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyHashSetTest {

    @Test
    void shouldReturnTrueWhenHashSetIsEmpty() {
        Set<Integer> set = new HashSet<>();
        assertTrue(set.isEmpty());
    }

    @Test
    void shouldReturnFalseWhenHashSetIsNotEmpty() {
        Set<Integer> set = new HashSet<>();
        set.put(1);
        assertFalse(set.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenElementAdded() {
        Set<Integer> set = new HashSet<>();
        boolean actual = set.put(1);
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrueWhenSetContainsElement() {
        Set<Integer> set = new HashSet<>();
        set.put(1);
        assertTrue(set.contains(1));
    }

    @Test
    void shouldReturnFalseWhenSetNotContainsElement() {
        Set<Integer> set = new HashSet<>();
        set.put(1);
        assertFalse(set.contains(12));
    }

    @Test
    void shouldReturnTrueAndRemovedObject() {
        Set<Integer> set = new HashSet<>();
        set.put(1);
        assertTrue(set.delete(1));
        assertEquals(0, set.size());
    }

    @Test
    void shouldReturnFalseAndNotChangedSetWhenSetDoesNotContainThisElement() {
        Set<Integer> actual = new HashSet<>();
        actual.put(1);
        assertFalse(actual.delete(5));
        Set<Integer> expected = new HashSet<>();
        expected.put(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturn1WhenSize1() {
        Set<Integer> set = new HashSet<>();
        set.put(1);
        assertEquals(1, set.size());
    }

    @Test
    void shouldReturn0WhenSize0() {
        Set<Integer> set = new HashSet<>();
        assertEquals(0, set.size());
    }

    @Test
    void shouldReturnString() {
        Set<Integer> set = new HashSet<>();
        set.put(null);
        set.put(11);
        String expected = "[null, 11]";
        assertEquals(expected, set.toString());
    }

    @Test
    void shouldReturnEmptyStringWhenSizeEqualZero() {
        Set<Integer> set = new HashSet<>();
        String expected = "[]";
        assertEquals(expected, set.toString());
    }
}
