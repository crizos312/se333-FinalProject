import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.DateUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GeneratedUtilInvocationTests {

    @Test
    public void testStringUtilsBasicOps() {
        assertTrue(StringUtils.isBlank("   \t"));
        assertFalse(StringUtils.isBlank("x"));
        assertEquals("HELLO", StringUtils.upperCase("hello"));
        assertEquals("hello", StringUtils.lowerCase("HELLO"));
        assertEquals("Hello", StringUtils.capitalize("hello"));
        assertEquals("hello", StringUtils.uncapitalize("Hello"));
        assertEquals(2, StringUtils.countMatches("abcabc", "abc"));
        assertEquals("ab...", StringUtils.abbreviate("abcdef", 5));
    }

    @Test
    public void testDateUtilsAddAndTruncate() {
        Date now = new Date();
        Date plusDays = DateUtils.addDays(now, 5);
        assertTrue(plusDays.after(now));
        Date truncated = DateUtils.truncate(now, Calendar.DATE);
        assertTrue(truncated.getTime() <= now.getTime());
    }

    @Test
    public void testClassUtilsPrimitiveWrapperMaps() {
        assertEquals(Integer.TYPE, ClassUtils.wrapperToPrimitive(Integer.class));
        assertEquals(Integer.class, ClassUtils.primitiveToWrapper(Integer.TYPE));
        assertTrue(ClassUtils.isPrimitiveOrWrapper(Integer.class));
    }

    @Test
    public void testReflectionToStringBuilderBasic() {
        String repr = ReflectionToStringBuilder.toString(this);
        assertNotNull(repr);
        assertTrue(repr.contains("GeneratedUtilInvocationTests"));
    }

    public static class SampleFieldContainer { private int number = 42; }

    @Test
    public void testFieldUtilsAccess() throws IllegalAccessException {
        SampleFieldContainer c = new SampleFieldContainer();
        java.lang.reflect.Field f = FieldUtils.getField(SampleFieldContainer.class, "number", true);
        assertNotNull(f);
        f.setAccessible(true);
        assertEquals(42, f.getInt(c));
        f.set(c, 100);
        assertEquals(100, f.getInt(c));
    }
}
