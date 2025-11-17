package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.Locale;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.builder.*;
import org.apache.commons.lang3.text.ExtendedMessageFormat;
import org.apache.commons.lang3.Conversion;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.AnnotationUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.JavaVersion;

/**
 * Targeted tests for top uncovered classes identified by JaCoCo.
 * Focusing on: TypeUtils (81 missed), ToStringStyle (42 missed), 
 * EqualsBuilder (39 missed), StandardToStringStyle (39 missed), etc.
 */
public class TargetedCoverageTests {

    // ========== TypeUtils Tests (81 missed lines) ==========
    
    @Test
    public void testTypeUtilsIsAssignableWithParameterizedTypes() {
        // Test generic type assignments
        Type listString = new ParameterizedType() {
            public Type[] getActualTypeArguments() { return new Type[]{String.class}; }
            public Type getRawType() { return List.class; }
            public Type getOwnerType() { return null; }
        };
        
        Type listObject = new ParameterizedType() {
            public Type[] getActualTypeArguments() { return new Type[]{Object.class}; }
            public Type getRawType() { return List.class; }
            public Type getOwnerType() { return null; }
        };
        
        // List<Object> is assignable from List<String>
        assertFalse(TypeUtils.isAssignable(listString, listObject));
    }
    
    @Test
    public void testTypeUtilsIsAssignableWithWildcardTypes() {
        // Test wildcard type checking
        WildcardType wildcardType = new WildcardType() {
            public Type[] getUpperBounds() { return new Type[]{Object.class}; }
            public Type[] getLowerBounds() { return new Type[0]; }
        };
        
        assertTrue(TypeUtils.isAssignable(String.class, wildcardType));
    }
    
    @Test
    public void testTypeUtilsIsAssignableWithGenericArrayType() {
        // Test generic array type checking
        GenericArrayType genericArrayType = new GenericArrayType() {
            public Type getGenericComponentType() { return String.class; }
        };
        
        assertNotNull(genericArrayType);
        assertTrue(TypeUtils.isAssignable(String[].class, genericArrayType));
    }
    
    @Test
    public void testTypeUtilsIsAssignableWithTypeVariable() throws Exception {
        // Test type variable assignments
        Method method = List.class.getMethod("get", int.class);
        Type returnType = method.getGenericReturnType();
        
        if (returnType instanceof TypeVariable) {
            TypeVariable<?> typeVar = (TypeVariable<?>) returnType;
            assertNotNull(typeVar.getName());
        }
    }

    // ========== ToStringStyle Tests (42 missed lines) ==========
    
    @Test
    public void testToStringStyleWithObjects() {
        // Use ToStringBuilder which internally uses ToStringStyle
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("field1", "value1");
        builder.append("field2", 42);
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("field1"));
    }
    
    @Test
    public void testToStringStyleShortPrefix() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("name", "test");
        builder.append("count", 10);
        
        String result = builder.toString();
        assertNotNull(result);
    }
    
    @Test
    public void testToStringStyleSimple() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
        builder.append("a", 1);
        builder.append("b", 2);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    // ========== EqualsBuilder Tests (39 missed lines) ==========
    
    @Test
    public void testEqualsBuilderReflectionEquals() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        
        assertFalse(EqualsBuilder.reflectionEquals(obj1, obj2, false));
        assertTrue(EqualsBuilder.reflectionEquals(obj1, obj1, false));
    }
    
    @Test
    public void testEqualsBuilderAppendArrays() {
        EqualsBuilder builder = new EqualsBuilder();
        
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {4, 5, 6};
        
        builder.append(array1, array2);
        assertTrue(builder.isEquals());
        
        builder.append(array1, array3);
        assertFalse(builder.isEquals());
    }
    
    @Test
    public void testEqualsBuilderWithCollections() {
        EqualsBuilder builder = new EqualsBuilder();
        
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<String> list2 = Arrays.asList("a", "b", "c");
        List<String> list3 = Arrays.asList("x", "y", "z");
        
        builder.append(list1, list2);
        assertTrue(builder.isEquals());
        
        builder.append(list1, list3);
        assertFalse(builder.isEquals());
    }

    // ========== StandardToStringStyle Tests (39 missed lines) ==========
    
    @Test
    public void testStandardToStringStyleBasic() {
        StandardToStringStyle style = new StandardToStringStyle();
        ToStringBuilder builder = new ToStringBuilder(this, style);
        builder.append("field1", "value1");
        builder.append("field2", 42);
        
        String result = builder.toString();
        assertNotNull(result);
    }
    
    @Test
    public void testStandardToStringStyleWithNulls() {
        StandardToStringStyle style = new StandardToStringStyle();
        ToStringBuilder builder = new ToStringBuilder(this, style);
        builder.append("nullField", (Object)null);
        
        String result = builder.toString();
        assertNotNull(result);
    }

    // ========== ToStringBuilder Tests (38 missed lines) ==========
    
    @Test
    public void testToStringBuilderVariousMethods() {
        Object testObj = new Object();
        
        ToStringBuilder builder = new ToStringBuilder(testObj);
        builder.append("field1", "value1");
        builder.append("field2", 123);
        builder.append("field3", true);
        builder.appendSuper("superString");
        
        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testToStringBuilderWithArrays() {
        Object testObj = new Object();
        
        ToStringBuilder builder = new ToStringBuilder(testObj);
        builder.append("intArray", new int[]{1, 2, 3});
        builder.append("strArray", new String[]{"a", "b"});
        
        String result = builder.toString();
        assertNotNull(result);
    }

    // ========== ExtendedMessageFormat Tests (35 missed lines) ==========
    
    @Test
    public void testExtendedMessageFormatBasicUsage() {
        ExtendedMessageFormat format = new ExtendedMessageFormat("Hello {0}", Locale.US);
        
        String result = format.format(new Object[]{"World"});
        assertEquals("Hello World", result);
    }
    
    @Test
    public void testExtendedMessageFormatMultipleArgs() {
        ExtendedMessageFormat format = new ExtendedMessageFormat("{0} + {1} = {2}", Locale.US);
        
        String result = format.format(new Object[]{1, 2, 3});
        assertEquals("1 + 2 = 3", result);
    }

    // ========== Conversion Tests (32 missed lines) ==========
    
    @Test
    public void testConversionIntToByteArray() {
        byte[] bytes = new byte[4];
        byte[] result = Conversion.intToByteArray(255, 0, bytes, 0, 4);
        assertNotNull(result);
        assertEquals(4, result.length);
    }
    
    @Test
    public void testConversionLongToIntArray() {
        int[] ints = new int[2];
        int[] result = Conversion.longToIntArray(12345L, 0, ints, 0, 2);
        assertNotNull(result);
    }

    // ========== CompareToBuilder Tests (22 missed lines) ==========
    
    @Test
    public void testCompareToBuilderWithArrays() {
        CompareToBuilder builder = new CompareToBuilder();
        
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 4};
        
        builder.append(arr1, arr2);
        assertTrue(builder.toComparison() < 0);
    }
    
    @Test
    public void testCompareToBuilderReflection() {
        String str1 = "abc";
        String str2 = "abc";
        
        int result = CompareToBuilder.reflectionCompare(str1, str2);
        assertEquals(0, result);
    }

    // ========== StringUtils Tests (21 missed lines) ==========
    
    @Test
    public void testStringUtilsEdgeCases() {
        assertNull(StringUtils.substring(null, 0));
        assertEquals("", StringUtils.substring("", 0));
        assertEquals("bc", StringUtils.substring("abc", 1));
        
        assertTrue(StringUtils.containsIgnoreCase("Hello", "HELLO"));
        assertFalse(StringUtils.containsIgnoreCase("Hello", "WORLD"));
    }
    
    @Test
    public void testStringUtilsReplace() {
        assertEquals("Hello Java", StringUtils.replace("Hello World", "World", "Java"));
        assertEquals("aaabbbccc", StringUtils.replace("abcabcabc", "abc", "abc"));
    }

    // ========== AnnotationUtils Tests (21 missed lines) ==========
    
    @Test
    public void testAnnotationUtilsIsValidAnnotationMemberType() {
        assertTrue(AnnotationUtils.isValidAnnotationMemberType(String.class));
        assertTrue(AnnotationUtils.isValidAnnotationMemberType(int.class));
        assertTrue(AnnotationUtils.isValidAnnotationMemberType(Class.class));
        assertFalse(AnnotationUtils.isValidAnnotationMemberType(List.class));
    }

    // ========== StrTokenizer Tests (19 missed lines) ==========
    
    @Test
    public void testStrTokenizerBasicUsage() {
        StrTokenizer tokenizer = new StrTokenizer("a,b,c", ',');
        
        List<String> tokens = tokenizer.getTokenList();
        assertEquals(3, tokens.size());
        assertEquals("a", tokens.get(0));
        assertEquals("b", tokens.get(1));
        assertEquals("c", tokens.get(2));
    }
    
    @Test
    public void testStrTokenizerWithQuotes() {
        StrTokenizer tokenizer = new StrTokenizer("'a,b',c", ',');
        tokenizer.setQuoteChar('\'');
        
        List<String> tokens = tokenizer.getTokenList();
        assertTrue(tokens.size() >= 1);
    }

    // ========== ExceptionUtils Tests (15 missed lines) ==========
    
    @Test
    public void testExceptionUtilsRootCause() {
        Exception cause = new Exception("Root");
        Exception wrapper = new Exception("Wrapper", cause);
        
        Throwable root = ExceptionUtils.getRootCause(wrapper);
        assertEquals(cause, root);
    }
    
    @Test
    public void testExceptionUtilsStackTrace() {
        Exception e = new Exception("Test");
        String stackTrace = ExceptionUtils.getStackTrace(e);
        
        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("Test"));
    }

    // ========== ClassUtils Tests (14 missed lines) ==========
    
    @Test
    public void testClassUtilsGetPackageName() {
        assertEquals("java.lang", ClassUtils.getPackageName(String.class));
        assertEquals("java.util", ClassUtils.getPackageName(List.class));
    }
    
    @Test
    public void testClassUtilsGetShortClassName() {
        assertEquals("String", ClassUtils.getShortClassName(String.class));
        assertEquals("List", ClassUtils.getShortClassName(List.class));
    }

    // ========== JavaVersion Tests (14 missed lines) ==========
    
    @Test
    public void testJavaVersionAtLeast() {
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_4));
        assertFalse(JavaVersion.JAVA_1_4.atLeast(JavaVersion.JAVA_1_5));
    }
    
    @Test
    public void testJavaVersionToString() {
        assertNotNull(JavaVersion.JAVA_1_8.toString());
        assertNotNull(JavaVersion.JAVA_1_7.toString());
    }

    // ========== StringUtils additional edge cases ==========
    
    @Test
    public void testStringUtilsIndexOfCases() {
        assertEquals(2, StringUtils.indexOf("abcdef", "cd"));
        assertEquals(-1, StringUtils.indexOf("abcdef", "xyz"));
    }
    
    @Test
    public void testStringUtilsLastIndexOfCases() {
        assertEquals(4, StringUtils.lastIndexOf("ababab", "ab"));
        assertEquals(-1, StringUtils.lastIndexOf("ababab", "xyz"));
    }
}
