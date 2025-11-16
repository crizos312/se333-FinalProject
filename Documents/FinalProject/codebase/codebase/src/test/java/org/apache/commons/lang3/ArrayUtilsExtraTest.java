package org.apache.commons.lang3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayUtilsExtraTest {

    @Test
    public void testClone() {
        int[] arr = {1, 2, 3};
        int[] cloned = ArrayUtils.clone(arr);
        assertArrayEquals(arr, cloned);
        assertNotSame(arr, cloned);
    }

    @Test
    public void testContains() {
        assertTrue(ArrayUtils.contains(new int[]{1, 2, 3}, 2));
        assertFalse(ArrayUtils.contains(new int[]{1, 2, 3}, 5));
    }

    @Test
    public void testIndexOf() {
        assertEquals(1, ArrayUtils.indexOf(new int[]{1, 2, 3}, 2));
        assertEquals(-1, ArrayUtils.indexOf(new int[]{1, 2, 3}, 5));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(ArrayUtils.isEmpty(new int[]{}));
        assertFalse(ArrayUtils.isEmpty(new int[]{1}));
        assertTrue(ArrayUtils.isEmpty((Object[])null));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(3, ArrayUtils.lastIndexOf(new int[]{1, 2, 3, 2}, 2));
        assertEquals(-1, ArrayUtils.lastIndexOf(new int[]{1, 2, 3}, 5));
    }

    @Test
    public void testNullToEmpty() {
        assertArrayEquals(new int[]{}, ArrayUtils.nullToEmpty((int[])null));
        assertArrayEquals(new int[]{1, 2}, ArrayUtils.nullToEmpty(new int[]{1, 2}));
    }

    @Test
    public void testReverse() {
        int[] arr = {1, 2, 3};
        ArrayUtils.reverse(arr);
        assertArrayEquals(new int[]{3, 2, 1}, arr);
    }

    @Test
    public void testSubarray() {
        int[] arr = {1, 2, 3, 4, 5};
        assertArrayEquals(new int[]{2, 3, 4}, ArrayUtils.subarray(arr, 1, 4));
    }

    @Test
    public void testRemoveElement() {
        int[] arr = {1, 2, 3};
        int[] result = ArrayUtils.removeElement(arr, 2);
        assertArrayEquals(new int[]{1, 3}, result);
    }

    @Test
    public void testToMap() {
        Object[][] data = {{"a", "1"}, {"b", "2"}};
        java.util.Map<Object, Object> map = ArrayUtils.toMap(data);
        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
    }

    @Test
    public void testToPrimitive() {
        Integer[] arr = {1, 2, 3};
        assertArrayEquals(new int[]{1, 2, 3}, ArrayUtils.toPrimitive(arr));
    }

    @Test
    public void testToObject() {
        int[] arr = {1, 2, 3};
        assertArrayEquals(new Integer[]{1, 2, 3}, ArrayUtils.toObject(arr));
    }
}
