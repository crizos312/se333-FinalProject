package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ObjectUtilsExtraTest {

    @Test
    public void testDefaultIfNull() {
        assertEquals("default", ObjectUtils.defaultIfNull(null, "default"));
        assertEquals("value", ObjectUtils.defaultIfNull("value", "default"));
    }

    @Test
    public void testFirstNonNull() {
        assertEquals("first", ObjectUtils.firstNonNull("first", "second"));
        assertEquals("second", ObjectUtils.firstNonNull(null, "second"));
        assertNull(ObjectUtils.firstNonNull(null, null));
    }

    @Test
    public void testEquals() {
        assertTrue(ObjectUtils.equals(null, null));
        assertTrue(ObjectUtils.equals("test", "test"));
        assertFalse(ObjectUtils.equals("test", "other"));
        assertFalse(ObjectUtils.equals(null, "test"));
    }

    @Test
    public void testNotEquals() {
        assertFalse(ObjectUtils.notEqual(null, null));
        assertFalse(ObjectUtils.notEqual("test", "test"));
        assertTrue(ObjectUtils.notEqual("test", "other"));
    }

    @Test
    public void testHashCode() {
        assertEquals(0, ObjectUtils.hashCode(null));
        assertEquals("test".hashCode(), ObjectUtils.hashCode("test"));
    }

    @Test
    public void testHashCodeMulti() {
        int hash = ObjectUtils.hashCodeMulti("a", "b", "c");
        assertTrue(hash != 0);
    }

    @Test
    public void testIdentityToString() {
        String str = "test";
        String result = ObjectUtils.identityToString(str);
        assertTrue(result.contains("java.lang.String"));
        assertTrue(result.contains("@"));
    }

    @Test
    public void testToString() {
        assertEquals("", ObjectUtils.toString(null));
        assertEquals("default", ObjectUtils.toString(null, "default"));
        assertEquals("test", ObjectUtils.toString("test"));
    }

    @Test
    public void testMin() {
        assertEquals(Integer.valueOf(1), ObjectUtils.min(1, 2, 3));
        assertEquals(Integer.valueOf(1), ObjectUtils.min(3, 2, 1));
        assertNull(ObjectUtils.min((Integer[]) null));
    }

    @Test
    public void testMax() {
        assertEquals(Integer.valueOf(3), ObjectUtils.max(1, 2, 3));
        assertEquals(Integer.valueOf(3), ObjectUtils.max(3, 2, 1));
        assertNull(ObjectUtils.max((Integer[]) null));
    }

    @Test
    public void testCompare() {
        assertEquals(0, ObjectUtils.compare(null, null));
        assertEquals(-1, ObjectUtils.compare(null, "test"));
        assertEquals(1, ObjectUtils.compare("test", null));
        assertEquals(0, ObjectUtils.compare("test", "test"));
    }

    @Test
    public void testMedian() {
        assertEquals(Integer.valueOf(2), ObjectUtils.median(1, 2, 3));
        assertEquals(Integer.valueOf(2), ObjectUtils.median(3, 1, 2));
    }

    @Test
    public void testMode() {
        assertEquals("a", ObjectUtils.mode("a", "b", "a"));
        assertNull(ObjectUtils.mode("a", "b", "c"));
    }

    @Test
    public void testClone() {
        int[] array = {1, 2, 3};
        int[] cloned = ObjectUtils.clone(array);
        assertNotNull(cloned);
        assertNotSame(array, cloned);
        assertNull(ObjectUtils.clone(null));
    }

    @Test
    public void testCloneIfPossible() {
        String str = "test";
        assertEquals("test", ObjectUtils.cloneIfPossible(str));
        Object notCloneable = new Object();
        assertSame(notCloneable, ObjectUtils.cloneIfPossible(notCloneable));
    }
}
