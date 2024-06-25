package halatsiankova.javafromscratch.collection;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    @Nested
    public class Put {
        @Test
        void shouldAddElementWhenListIsEmpty() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            assertArrayEquals(new Object[]{1}, list.toArray());
        }

        @Test
        void shouldAddElementWhenMethodWasCalled() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(58);
            list.put(55);
            String expected = "[58, 55]";
            assertEquals(55, list.get(1));
            assertEquals(expected, list.toString());
        }

        @Test
        void shouldAddNullWhenNull() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(null);
            assertNull(list.get(0));
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(
                    IndexOutOfBoundsException.class, () -> list.put(-2, 2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexLargerSize() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(
                    IndexOutOfBoundsException.class, () -> list.put(2, 2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        public void shouldThrowConcurrentModificationExceptionWhenIteratorIsRunning() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            Iterator<Integer> iterator = list.iterator();
            list.put(1, 0);
            assertThrows(ConcurrentModificationException.class, iterator::next);
        }
    }

    @Nested
    public class Get {

        @Test
        void shouldReturnObjectFromIndexWhenMethodWasCalled() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            assertEquals(1, list.get(0));
        }

        @Test
        void shouldReturnObjectIsNullFromIndexWhenNull() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(null);
            assertEquals(null, list.get(0));
        }

        @Test
        void shouldReturnsElementOnIndexWhenIndexInTheEnd() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            assertEquals(3, list.get(2));
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(-2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexGreaterThenSize() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexEqualsSize() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
            String expected = "Index: 1, size: 1";
            assertEquals(expected, e.getMessage());
        }
    }

    @Nested
    public class Size {

        @Test
        void shouldReturnZeroWhenObjectWasCreated() {
            MyArrayList<Integer> list = new MyArrayList<>();
            assertEquals(0, list.size());
        }

        @Test
        void shouldReturn3WhenSize3() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(1);
            list.put(1);
            assertEquals(3, list.size());
        }
    }

    @Nested
    public class ToArray {

        @Test
        void shouldReturnArrayOfObjectsWhenListIsEmpty() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            Object[] actual = list.toArray();
            Integer[] expected = new Integer[]{1, 2};
            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldReturnNullWhenObjectIsNull() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(null);
            Object[] expected = new Object[]{null};
            Object[] actual = list.toArray();
            assertArrayEquals(expected, actual);
        }
    }

    @Nested
    public class Delete {

        @Test
        void shouldRemovedObjectByIndexWhenIndexInMiddle() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            Integer[] expected = new Integer[]{1,3};
            Object actual = list.delete(1);
            assertEquals(2, actual);
            assertArrayEquals(expected, list.toArray());
        }

        @Test
        void shouldRemovedObjectByIndexWhenIndexEqualZero() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            Integer[] expected = new Integer[]{2,3};
            Object actual = list.delete(0);
            assertEquals(1, actual);
            assertArrayEquals(expected, list.toArray());
        }

        @Test
        void shouldRemovedObjectByIndexWhenIndexInTheEnd() {
            MyArrayList<Integer> list = new MyArrayList<>();
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
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            list.put(3);
            list.delete(1);
            assertEquals(2, list.size());
        }

        @Test
        void shouldRemovedObjectByIndexWhenObjectIsNull() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(null);
            Object actual = list.delete(0);
            assertEquals(null, actual);
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexIsNegative() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(-2));
            String expected = "Index: -2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexGreaterThenSize() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(2));
            String expected = "Index: 2, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        void shouldThrowIndexOutOfBoundsExceptionWhenIndexEqualsSize() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            Exception e = assertThrows(IndexOutOfBoundsException.class, () -> list.delete(1));
            String expected = "Index: 1, size: 1";
            assertEquals(expected, e.getMessage());
        }

        @Test
        public void shouldThrowConcurrentModificationExceptionWhenIteratorIsRunning() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(1);
            list.put(2);
            Iterator<Integer> iterator = list.iterator();
            list.delete(1);
            assertThrows(ConcurrentModificationException.class, iterator::next);
        }
    }

    @Nested
    public class IsEmpty {

        @Test
        void shouldTrueWhenCollectionIsEmpty() {
            MyArrayList<Integer> list = new MyArrayList<>();
            assertTrue(list.isEmpty());
        }

        @Test
        void shouldFalseWhenCollectionIsNotEmpty() {
            MyArrayList<Integer> list = new MyArrayList<>();
            list.put(5);
            assertFalse(list.isEmpty());
        }
    }
}