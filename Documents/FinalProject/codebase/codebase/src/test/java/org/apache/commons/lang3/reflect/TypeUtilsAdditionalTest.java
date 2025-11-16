package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.junit.Test;

public class TypeUtilsAdditionalTest {

    static class Base<T> {}
    static class Child extends Base<Integer> {}
    static class Bounded<T extends Number & Comparable<T>> {}

    @Test
    public void testIsArrayTypeWithGenericArray() {
        GenericArrayType gat = new GenericArrayType() {
            public Type getGenericComponentType() { return String.class; }
        };
        assertTrue(TypeUtils.isArrayType(gat));
        assertEquals(String.class, TypeUtils.getArrayComponentType(gat));
    }

    @Test
    public void testGetRawTypeFromTypeVariableAgainstSubclass() {
        TypeVariable<?> typeVar = Base.class.getTypeParameters()[0];
        Class<?> raw = TypeUtils.getRawType(typeVar, Child.class);
        assertEquals(Integer.class, raw);
    }

    @Test
    public void testGetImplicitBoundsOnTypeVariable() {
        TypeVariable<?> tv = Bounded.class.getTypeParameters()[0];
        Type[] bounds = TypeUtils.getImplicitBounds(tv);
        assertTrue(bounds.length >= 1);
    }
}
