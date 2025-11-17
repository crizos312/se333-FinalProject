package org.apache.commons.lang3.reflect;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Targeted coverage tests for TypeUtils uncovered lines.
 */
public class TypeUtilsCoverageTest {

    @Test
    public void testConstructor() {
        // Constructor coverage
        TypeUtils instance = new TypeUtils();
        assertNotNull(instance);
    }

    @Test
    public void testGetTypeArgumentsParameterizedType() throws Exception {
        // Test getTypeArguments(ParameterizedType) - line 498
        class StringList extends ArrayList<String> {}
        
        Type superclass = StringList.class.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Map<TypeVariable<?>, Type> result = TypeUtils.getTypeArguments((ParameterizedType) superclass);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }

    @Test
    public void testIsAssignableToTypeVariable() throws Exception {
        // Cover TypeVariable assignment paths - line 427+
        TypeVariable<?>[] typeVars = List.class.getTypeParameters();
        if (typeVars.length > 0) {
            TypeVariable<?> listE = typeVars[0];
            
            // null type to TypeVariable
            assertTrue(TypeUtils.isAssignable(null, listE));
            
            // TypeVariable to itself
            assertTrue(TypeUtils.isAssignable(listE, listE));
            
            // Class to TypeVariable
            assertFalse(TypeUtils.isAssignable(String.class, listE));
        }
    }

    @Test
    public void testIsAssignableGenericArrayType() throws Exception {
        // Cover GenericArrayType assignment paths - line 262+
        class Container<T> {
            public T[] array;
        }
        
        Type arrayType = Container.class.getDeclaredField("array").getGenericType();
        
        // null to GenericArrayType
        assertTrue(TypeUtils.isAssignable(null, arrayType));
        
        // Non-array to GenericArrayType
        assertFalse(TypeUtils.isAssignable(String.class, arrayType));
    }

    @Test
    public void testGetRawTypeWithAssigningType() throws Exception {
        // Cover getRawType(Type, Type) - public method
        class StringList extends ArrayList<String> {}
        
        Type superclass = StringList.class.getGenericSuperclass();
        Class<?> raw = TypeUtils.getRawType(superclass, null);
        assertEquals(ArrayList.class, raw);
    }

    @Test
    public void testIsInstanceWithNull() {
        // Cover isInstance null/edge cases - line 846+
        assertTrue(TypeUtils.isInstance(null, String.class)); // null is instance of any type
        assertFalse(TypeUtils.isInstance("test", null));
        assertTrue(TypeUtils.isInstance("test", String.class));
        assertTrue(TypeUtils.isInstance("test", Object.class));
        assertFalse(TypeUtils.isInstance(123, String.class));
    }

    @Test
    public void testGetImplicitBoundsTypeVariable() {
        // Cover getImplicitBounds - line 910+
        TypeVariable<?>[] typeVars = List.class.getTypeParameters();
        if (typeVars.length > 0) {
            Type[] bounds = TypeUtils.getImplicitBounds(typeVars[0]);
            assertNotNull(bounds);
            assertTrue(bounds.length >= 1);
        }
    }

    @Test
    public void testGetImplicitUpperBoundsWildcard() throws Exception {
        // Cover getImplicitUpperBounds - line 926+
        class Container<T> {
            public List<? extends Number> upper;
            public List<?> unbounded;
        }
        
        Type upperType = Container.class.getDeclaredField("upper").getGenericType();
        if (upperType instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) upperType).getActualTypeArguments();
            if (args.length > 0 && args[0] instanceof WildcardType) {
                Type[] bounds = TypeUtils.getImplicitUpperBounds((WildcardType) args[0]);
                assertNotNull(bounds);
                assertTrue(bounds.length >= 1);
            }
        }
        
        Type unboundedType = Container.class.getDeclaredField("unbounded").getGenericType();
        if (unboundedType instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) unboundedType).getActualTypeArguments();
            if (args.length > 0 && args[0] instanceof WildcardType) {
                Type[] bounds = TypeUtils.getImplicitUpperBounds((WildcardType) args[0]);
                assertNotNull(bounds);
                assertEquals(1, bounds.length);
                assertEquals(Object.class, bounds[0]);
            }
        }
    }

    @Test
    public void testIsAssignableToClass() {
        // Cover more isAssignable(Type, Class) branches - line 113+
        // null to Class
        assertTrue(TypeUtils.isAssignable(null, String.class));
        assertTrue(TypeUtils.isAssignable(null, Object.class));
        
        // Class to null
        assertFalse(TypeUtils.isAssignable(String.class, null));
        
        // Primitive and wrapper types
        assertTrue(TypeUtils.isAssignable(Integer.TYPE, Integer.class));
        assertTrue(TypeUtils.isAssignable(Integer.class, Integer.TYPE));
        
        // Array types
        assertTrue(TypeUtils.isAssignable(String[].class, Object.class));
        assertTrue(TypeUtils.isAssignable(String[].class, Object[].class));
    }

    @Test
    public void testGetTypeArgumentsEdgeCases() throws Exception {
        // Cover getTypeArguments edge cases - line 547+
        
        // Class to Class (raw types)
        Map<TypeVariable<?>, Type> result = TypeUtils.getTypeArguments(ArrayList.class, Collection.class);
        assertNotNull(result);
        
        // Same class
        result = TypeUtils.getTypeArguments(List.class, List.class);
        assertNotNull(result);
    }

    @Test
    public void testGetTypeArgumentsWithIncompatibleClasses() {
        // Cover incompatible class hierarchy case - line 653+
        Map<TypeVariable<?>, Type> result = TypeUtils.getTypeArguments(String.class, List.class);
        assertNull(result);
    }

    @Test
    public void testDetermineTypeArguments() throws Exception {
        // Cover determineTypeArguments - line 710+
        class StringList extends ArrayList<String> {}
        
        Type superclass = StringList.class.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            Map<TypeVariable<?>, Type> result = TypeUtils.determineTypeArguments(
                ArrayList.class, 
                (ParameterizedType) superclass
            );
            assertNotNull(result);
        }
    }

    @Test
    public void testGetTypeArgumentsWithVariableSubstitution() throws Exception {
        // Cover type variable resolution via getTypeArguments
        class StringList extends ArrayList<String> {}
        
        Type superclass = StringList.class.getGenericSuperclass();
        Map<TypeVariable<?>, Type> result = TypeUtils.getTypeArguments(superclass, Collection.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testNormalizeUpperBounds() {
        // Cover normalizeUpperBounds - line 877+
        Type[] bounds = new Type[] { Object.class, String.class };
        Type[] normalized = TypeUtils.normalizeUpperBounds(bounds);
        assertNotNull(normalized);
        
        // When Object is in bounds with other types, Object should be removed
        assertEquals(1, normalized.length);
        assertEquals(String.class, normalized[0]);
        
        // Single bound
        bounds = new Type[] { String.class };
        normalized = TypeUtils.normalizeUpperBounds(bounds);
        assertEquals(1, normalized.length);
        assertEquals(String.class, normalized[0]);
    }

    @Test
    public void testTypesSatisfyVariables() throws Exception {
        // Cover typesSatisfyVariables - line 960+
        TypeVariable<?>[] typeVars = List.class.getTypeParameters();
        if (typeVars.length > 0) {
            Map<TypeVariable<?>, Type> assignments = new HashMap<TypeVariable<?>, Type>();
            assignments.put(typeVars[0], String.class);
            
            boolean satisfied = TypeUtils.typesSatisfyVariables(assignments);
            assertTrue(satisfied);
            
            // Empty map
            satisfied = TypeUtils.typesSatisfyVariables(new HashMap<TypeVariable<?>, Type>());
            assertTrue(satisfied);
        }
    }

    @Test
    public void testGetTypeArgumentsComplexHierarchy() {
        // Cover complex type hierarchy resolution via public API
        Map<TypeVariable<?>, Type> result = TypeUtils.getTypeArguments(ArrayList.class, Collection.class);
        assertNotNull(result);
        
        // Test with direct superclass
        result = TypeUtils.getTypeArguments(ArrayList.class, AbstractList.class);
        assertNotNull(result);
    }

    @Test
    public void testIsAssignableWildcardTypeCases() throws Exception {
        // Cover more WildcardType isAssignable cases - line 338+
        class Container<T> {
            public List<? extends Number> upper;
            public List<? super Integer> lower;
        }
        
        Type upperType = Container.class.getDeclaredField("upper").getGenericType();
        Type lowerType = Container.class.getDeclaredField("lower").getGenericType();
        
        // Various wildcard assignments
        assertTrue(TypeUtils.isAssignable(upperType, upperType));
        assertTrue(TypeUtils.isAssignable(lowerType, lowerType));
    }
}
