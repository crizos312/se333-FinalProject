package org.apache.commons.lang3.builder;

import java.lang.reflect.Field;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Targeted coverage tests for ToStringBuilder uncovered lines.
 */
public class ToStringBuilderCoverageTest {

    @After
    public void tearDown() throws Exception {
        // Clean up the static style registry to avoid affecting other tests
        Field registryField = ToStringStyle.class.getDeclaredField("REGISTRY");
        registryField.setAccessible(true);
        ThreadLocal<?> registry = (ThreadLocal<?>) registryField.get(null);
        registry.remove();
    }

    static class TestObject {
        private int value = 42;
        private String name = "test";
        
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    @Test
    public void testReflectionToStringWithNull() {
        // Cover null handling - returns "<null>" string
        assertEquals("<null>", ToStringBuilder.reflectionToString(null));
    }

    @Test
    public void testReflectionToStringWithStyle() {
        TestObject obj = new TestObject();
        
        // Default style
        String result = ToStringBuilder.reflectionToString(obj, ToStringStyle.DEFAULT_STYLE);
        assertNotNull(result);
        assertTrue(result.contains("value"));
        assertTrue(result.contains("42"));
        
        // Short prefix style
        result = ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
        assertNotNull(result);
        
        // Multi-line style
        result = ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
        assertNotNull(result);
    }

    @Test
    public void testReflectionToStringWithOutputTransients() {
        TestObject obj = new TestObject();
        
        // With transients
        String result = ToStringBuilder.reflectionToString(obj, null, true);
        assertNotNull(result);
        
        // Without transients
        result = ToStringBuilder.reflectionToString(obj, null, false);
        assertNotNull(result);
    }

    @Test
    public void testReflectionToStringUpToClass() {
        TestObject obj = new TestObject();
        
        // Reflect up to Object
        String result = ToStringBuilder.reflectionToString(obj, null, false, Object.class);
        assertNotNull(result);
        
        // Reflect up to TestObject
        result = ToStringBuilder.reflectionToString(obj, null, false, TestObject.class);
        assertNotNull(result);
    }

    @Test
    public void testAppendToStringWithNull() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        // Append null object's toString
        builder.appendToString(null);
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendPrimitives() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        // Append various primitives
        builder.append("boolean", true);
        builder.append("byte", (byte) 1);
        builder.append("char", 'A');
        builder.append("double", 3.14);
        builder.append("float", 2.71f);
        builder.append("int", 42);
        builder.append("long", 100L);
        builder.append("short", (short) 5);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("boolean"));
        assertTrue(result.contains("true"));
    }

    @Test
    public void testAppendArrays() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        // Object array
        builder.append("objects", new String[]{"a", "b", "c"});
        
        // Primitive arrays
        builder.append("ints", new int[]{1, 2, 3});
        builder.append("longs", new long[]{1L, 2L});
        builder.append("booleans", new boolean[]{true, false});
        builder.append("bytes", new byte[]{1, 2});
        builder.append("chars", new char[]{'a', 'b'});
        builder.append("doubles", new double[]{1.0, 2.0});
        builder.append("floats", new float[]{1.0f, 2.0f});
        builder.append("shorts", new short[]{1, 2});
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testAppendWithFullDetail() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        // With full detail
        builder.append("field", "value", true);
        builder.append("number", 42, true);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("field"));
        assertTrue(result.contains("value"));
    }

    @Test
    public void testAppendAsObjectToString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        
        TestObject obj = new TestObject();
        builder.appendAsObjectToString(obj);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    @Test
    public void testGetSetDefaultStyle() {
        ToStringStyle original = ToStringBuilder.getDefaultStyle();
        assertNotNull(original);
        
        try {
            ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
            assertEquals(ToStringStyle.MULTI_LINE_STYLE, ToStringBuilder.getDefaultStyle());
        } finally {
            // Restore original
            ToStringBuilder.setDefaultStyle(original);
        }
    }

    @Test
    public void testBuild() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("field", "value");
        
        String result1 = builder.build();
        String result2 = builder.toString();
        
        // build() and toString() should produce similar output
        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1.contains("field"));
        assertTrue(result1.contains("value"));
    }

    @Test
    public void testGetStyle() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        assertEquals(ToStringStyle.SHORT_PREFIX_STYLE, builder.getStyle());
    }

    @Test
    public void testGetObject() {
        TestObject obj = new TestObject();
        ToStringBuilder builder = new ToStringBuilder(obj);
        assertSame(obj, builder.getObject());
    }

    @Test
    public void testGetStringBuffer() {
        ToStringBuilder builder = new ToStringBuilder(this);
        StringBuffer buffer = builder.getStringBuffer();
        assertNotNull(buffer);
    }
}
