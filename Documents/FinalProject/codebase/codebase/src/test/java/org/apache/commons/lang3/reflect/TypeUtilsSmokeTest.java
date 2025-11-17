package org.apache.commons.lang3.reflect;

import org.junit.Test;
import java.lang.reflect.Type;
import static org.junit.Assert.*;

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
