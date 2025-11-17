package org.apache.commons.lang3.reflect;

import java.lang.reflect.Type;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TypeUtilsSmokeTest {

    @Test
    public void testIsAssignableSimpleClasses() {
        // String -> Object
        assertTrue(TypeUtils.isAssignable(String.class, (Type) Object.class));
        // Object -> String
        assertFalse(TypeUtils.isAssignable(Object.class, (Type) String.class));
    }

    @Test
    public void testIsAssignableNullType() {
        // null -> Object (non-primitive) is assignable
        assertTrue(TypeUtils.isAssignable((Type) null, (Type) Object.class));
        // null -> int (primitive) is not assignable
        assertFalse(TypeUtils.isAssignable((Type) null, (Type) Integer.TYPE));
    }
}
