package org.apache.commons.lang3.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Targeted coverage tests for EqualsBuilder uncovered lines.
 */
public class EqualsBuilderCoverageTest {

    static class TestObject {
        private int value;
        private String name;
        
        public TestObject(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Test
    public void testReflectionEqualsWithNulls() {
        // Cover null handling in reflectionEquals
        assertTrue(EqualsBuilder.reflectionEquals(null, null));
        assertFalse(EqualsBuilder.reflectionEquals(new TestObject(1, "a"), null));
        assertFalse(EqualsBuilder.reflectionEquals(null, new TestObject(1, "a")));
    }

    @Test
    public void testReflectionEqualsWithSameInstance() {
        TestObject obj = new TestObject(1, "test");
        assertTrue(EqualsBuilder.reflectionEquals(obj, obj));
    }

    @Test
    public void testReflectionEqualsWithDifferentClasses() {
        TestObject obj1 = new TestObject(1, "test");
        String obj2 = "test";
        assertFalse(EqualsBuilder.reflectionEquals(obj1, obj2));
    }

    @Test
    public void testReflectionEqualsWithExcludeFields() {
        TestObject obj1 = new TestObject(1, "test");
        TestObject obj2 = new TestObject(2, "test");
        
        // Excluding 'value' field, should be equal
        assertTrue(EqualsBuilder.reflectionEquals(obj1, obj2, "value"));
        
        // Including all fields, should not be equal
        assertFalse(EqualsBuilder.reflectionEquals(obj1, obj2));
    }

    @Test
    public void testAppendSuper() {
        EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(true);
        assertTrue(builder.isEquals());
        
        builder.appendSuper(false);
        assertFalse(builder.isEquals());
    }

    @Test
    public void testAppendWithNullObjects() {
        EqualsBuilder builder = new EqualsBuilder();
        
        // Both null
        builder.append((Object) null, (Object) null);
        assertTrue(builder.isEquals());
        
        // Left null
        builder = new EqualsBuilder();
        builder.append((Object) null, "test");
        assertFalse(builder.isEquals());
        
        // Right null
        builder = new EqualsBuilder();
        builder.append("test", (Object) null);
        assertFalse(builder.isEquals());
    }

    @Test
    public void testAppendWithDifferentArrayTypes() {
        EqualsBuilder builder = new EqualsBuilder();
        
        int[] intArray = {1, 2, 3};
        long[] longArray = {1L, 2L, 3L};
        
        builder.append(intArray, longArray);
        assertFalse(builder.isEquals());
    }

    @Test
    public void testAppendPrimitiveArrays() {
        EqualsBuilder builder = new EqualsBuilder();
        
        // long arrays
        long[] arr1 = {1L, 2L, 3L};
        long[] arr2 = {1L, 2L, 3L};
        builder.append(arr1, arr2);
        assertTrue(builder.isEquals());
        
        // int arrays
        builder = new EqualsBuilder();
        int[] intArr1 = {1, 2, 3};
        int[] intArr2 = {1, 2, 3};
        builder.append(intArr1, intArr2);
        assertTrue(builder.isEquals());
        
        // short arrays
        builder = new EqualsBuilder();
        short[] shortArr1 = {1, 2, 3};
        short[] shortArr2 = {1, 2, 3};
        builder.append(shortArr1, shortArr2);
        assertTrue(builder.isEquals());
        
        // char arrays
        builder = new EqualsBuilder();
        char[] charArr1 = {'a', 'b', 'c'};
        char[] charArr2 = {'a', 'b', 'c'};
        builder.append(charArr1, charArr2);
        assertTrue(builder.isEquals());
        
        // byte arrays
        builder = new EqualsBuilder();
        byte[] byteArr1 = {1, 2, 3};
        byte[] byteArr2 = {1, 2, 3};
        builder.append(byteArr1, byteArr2);
        assertTrue(builder.isEquals());
        
        // double arrays
        builder = new EqualsBuilder();
        double[] dblArr1 = {1.0, 2.0, 3.0};
        double[] dblArr2 = {1.0, 2.0, 3.0};
        builder.append(dblArr1, dblArr2);
        assertTrue(builder.isEquals());
        
        // float arrays
        builder = new EqualsBuilder();
        float[] fltArr1 = {1.0f, 2.0f, 3.0f};
        float[] fltArr2 = {1.0f, 2.0f, 3.0f};
        builder.append(fltArr1, fltArr2);
        assertTrue(builder.isEquals());
        
        // boolean arrays
        builder = new EqualsBuilder();
        boolean[] boolArr1 = {true, false, true};
        boolean[] boolArr2 = {true, false, true};
        builder.append(boolArr1, boolArr2);
        assertTrue(builder.isEquals());
    }

    @Test
    public void testAppendWithDifferentLengthArrays() {
        EqualsBuilder builder = new EqualsBuilder();
        
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2};
        
        builder.append(arr1, arr2);
        assertFalse(builder.isEquals());
    }

    @Test
    public void testAppendFloatingPointSpecialValues() {
        EqualsBuilder builder = new EqualsBuilder();
        
        // NaN values
        builder.append(Double.NaN, Double.NaN);
        assertTrue(builder.isEquals());
        
        builder = new EqualsBuilder();
        builder.append(Float.NaN, Float.NaN);
        assertTrue(builder.isEquals());
        
        // Infinity values
        builder = new EqualsBuilder();
        builder.append(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertTrue(builder.isEquals());
        
        builder = new EqualsBuilder();
        builder.append(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        assertTrue(builder.isEquals());
    }

    @Test
    public void testReset() {
        EqualsBuilder builder = new EqualsBuilder();
        builder.append("test", "other");
        assertFalse(builder.isEquals());
        
        builder.reset();
        assertTrue(builder.isEquals());
        
        builder.append("test", "test");
        assertTrue(builder.isEquals());
    }
}
