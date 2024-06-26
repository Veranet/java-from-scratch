package halatsiankova.javafromscratch.collection;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyArrayListTest {

    @Nested
    class Put {
        @Test
        void shouldAddElementWhenListIsEmpty() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            assertArrayEquals(new Object[]{1}, list.toArray());
        }

        @Test
        void shouldAddElement() {
            List<Integer> list = new ArrayList<>();
            list.put(58);
            list.put(55);
            String expected = "[58, 55]";
            assertEquals(55, list.get(1));
            assertEquals(expected, list.toString());
        }

        @Test
        void shouldAddNullWhenNull() {
            List<Integer> list = new ArrayList<>();
            list.put(null);
            assertNull(list.get(0));
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(
                    IndexOutOfBoundsException.class, () -> list.put(-2, 2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexLargerSize() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(
                    IndexOutOfBoundsException.class, () -> list.put(2, 2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowConcurrentModificationExceptionWhenIteratorIsRunning() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            Iterator<Integer> iterator = list.iterator();
            list.put(1, 0);
            assertThrows(ConcurrentModificationException.class, iterator::next);
        }
    }

    @Nested
    class Get {
        @Test
        void shouldReturnObjectFromIndexWhenMethodWasCalled() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            assertEquals(1, list.get(0));
        }

        @Test
        void shouldReturnObjectIsNullFromIndexWhenNull() {
            List<Integer> list = new ArrayList<>();
            list.put(null);
            assertEquals(null, list.get(0));
        }

        @Test
        void shouldReturnsElementOnIndexWhenIndexInTheEnd() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            assertEquals(3, list.get(2));
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexGreaterThenSize() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexEqualsSize() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
            String expected = "Index: 1, size: 1";
            assertEquals(expected, e.getMessage());
        }
    }

    @Nested
    class Size {
        @Test
        void shouldReturnZeroWhenObjectWasCreated() {
            List<Integer> list = new ArrayList<>();
            assertEquals(0, list.size());
        }

        @Test
        void shouldReturn3WhenSize3() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(1);
            list.put(1);
            assertEquals(3, list.size());
        }
    }

    @Nested
    class ToArray {

        @Test
        void shouldReturnArrayOfObjects() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            Object[] actual = list.toArray();
            Integer[] expected = new Integer[]{1, 2};
            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldReturnNullWhenObjectIsNull() {
            List<Integer> list = new ArrayList<>();
            list.put(null);
            Object[] expected = new Object[]{null};
            Object[] actual = list.toArray();
            assertArrayEquals(expected, actual);
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldRemoveObjectByIndexWhenIndexInMiddle() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            Integer[] expected = new Integer[]{1,3};
            Object actual = list.delete(1);
            assertEquals(2, actual);
            assertArrayEquals(expected, list.toArray());
        }

        @Test
        void shouldRemoveObjectByIndexWhenIndexEqualZero() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            Integer[] expected = new Integer[]{2,3};
            Object actual = list.delete(0);
            assertEquals(1, actual);
            assertArrayEquals(expected, list.toArray());
        }

        @Test
        void shouldRemoveObjectByIndexWhenIndexInTheEnd() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            Integer[] expected = new Integer[]{1,2};
            Object actual = list.delete(2);
            assertEquals(3, actual);
            assertArrayEquals(expected, list.toArray());
        }

        @Test
        void shouldChangeSizeWhenMethodWasCalled() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            list.delete(1);
            assertEquals(2, list.size());
        }

        @Test
        void shouldRemoveObjectByIndexWhenObjectIsNull() {
            List<Integer> list = new ArrayList<>();
            list.put(null);
            Object actual = list.delete(0);
            assertEquals(null, actual);
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(-2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexGreaterThenSize() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexEqualsSize() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(1));
            String expected = "Index: 1, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        public void shouldThrowConcurrentModificationExceptionWhenIteratorIsRunning() {
            List<Integer> list = new ArrayList<>();
            list.put(1);
            list.put(2);
            Iterator<Integer> iterator = list.iterator();
            list.delete(1);
            assertThrows(ConcurrentModificationException.class, iterator::next);
        }
    }

    @Nested
    class IsEmpty {
        @Test
        void shouldReturnTrueWhenCollectionIsEmpty() {
            List<Integer> list = new ArrayList<>();
            assertTrue(list.isEmpty());
        }

        @Test
        void shouldReturnFalseWhenCollectionIsNotEmpty() {
            List<Integer> list = new ArrayList<>();
            list.put(5);
            assertFalse(list.isEmpty());
        }
    }

    @Nested
    class ToString {
        @Test
        void shouldReturnStringWithAllElements() {
            List<String> list = new ArrayList<>();
            list.put("Hi");
            list.put("friend");

            String expected = "[Hi, friend]";
            assertEquals(expected, list.toString());
        }

        @Test
        void shouldReturnEmptyStringWhenSizeEqualZero() {
            List<String> list = new ArrayList<>();
            String expected = "[]";
            assertEquals(expected, list.toString());
        }

        @Test
        void shouldReturnEmptyStringWhenObjectIsNull() {
            List<String> list = new ArrayList<>();
            list.put(null);
            String expected = "[null]";
            assertEquals(expected, list.toString());
        }
    }
}
