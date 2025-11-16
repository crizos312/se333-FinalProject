package org.apache.commons.lang3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassUtilsExtraTest {

    @Test
    public void testGetPackageName() {
        assertEquals("java.lang", ClassUtils.getPackageName(String.class));
        assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(ClassUtils.class));
    }

    @Test
    public void testGetShortClassName() {
        assertEquals("String", ClassUtils.getShortClassName(String.class));
        assertEquals("String", ClassUtils.getShortClassName("java.lang.String"));
    }

    @Test
    public void testGetAllInterfaces() {
        java.util.List<?> interfaces = ClassUtils.getAllInterfaces(String.class);
        assertNotNull(interfaces);
        assertTrue(interfaces.size() > 0);
    }

    @Test
    public void testGetAllSuperclasses() {
        java.util.List<?> superclasses = ClassUtils.getAllSuperclasses(String.class);
        assertNotNull(superclasses);
        assertTrue(superclasses.contains(Object.class));
    }

    @Test
    public void testIsPrimitiveOrWrapper() {
        assertTrue(ClassUtils.isPrimitiveOrWrapper(int.class));
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Integer.class));
        assertFalse(ClassUtils.isPrimitiveOrWrapper(String.class));
    }

    @Test
    public void testPrimitiveToWrapper() {
        assertEquals(Integer.class, ClassUtils.primitiveToWrapper(int.class));
        assertEquals(Boolean.class, ClassUtils.primitiveToWrapper(boolean.class));
    }

    @Test
    public void testWrapperToPrimitive() {
        assertEquals(int.class, ClassUtils.wrapperToPrimitive(Integer.class));
        assertEquals(boolean.class, ClassUtils.wrapperToPrimitive(Boolean.class));
    }

    @Test
    public void testIsInnerClass() {
        class Inner {}
        assertTrue(ClassUtils.isInnerClass(Inner.class));
        assertFalse(ClassUtils.isInnerClass(String.class));
    }

    @Test
    public void testConvertClassNamesToClasses() throws Exception {
        java.util.List<String> names = java.util.Arrays.asList("java.lang.String", "java.lang.Integer");
        java.util.List<Class<?>> classes = ClassUtils.convertClassNamesToClasses(names);
        assertEquals(2, classes.size());
        assertEquals(String.class, classes.get(0));
        assertEquals(Integer.class, classes.get(1));
    }

    @Test
    public void testGetClass() throws Exception {
        assertEquals(String.class, ClassUtils.getClass("java.lang.String"));
        assertEquals(int.class, ClassUtils.getClass("int"));
    }
}
