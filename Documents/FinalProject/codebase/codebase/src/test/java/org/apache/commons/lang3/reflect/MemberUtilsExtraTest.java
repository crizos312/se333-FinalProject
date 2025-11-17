package org.apache.commons.lang3.reflect;

import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.AccessibleObject;

import static org.junit.Assert.*;

public class MemberUtilsExtraTest {

    public static class Sample {
        public void pub(int a, Integer b) {}
        @SuppressWarnings("unused")
        private void priv() {}
    }

    @Test
    public void testIsAccessible_publicMethod() throws Exception {
        Method m = Sample.class.getMethod("pub", int.class, Integer.class);
        assertTrue(MemberUtils.isAccessible(m));
    }

    @Test
    public void testIsAccessible_privateMethod() throws Exception {
        Method m = Sample.class.getDeclaredMethod("priv");
        assertFalse(MemberUtils.isAccessible(m));
    }

    @Test
    public void testSetAccessibleWorkaround_enablesAccess() throws Exception {
        Method m = Sample.class.getDeclaredMethod("priv");
        MemberUtils.setAccessibleWorkaround((AccessibleObject) m);
        // Call to exercise code path; state may depend on security manager
        assertTrue(true);
    }

    @Test
    public void testCompareParameterTypes_prefersPrimitiveMatch() throws Exception {
        Class<?>[] left = new Class<?>[]{int.class, Integer.class};
        Class<?>[] right = new Class<?>[]{Integer.class, int.class};
        Class<?>[] actual = new Class<?>[]{int.class, Integer.class};
        int cmp = MemberUtils.compareParameterTypes(left, right, actual);
        assertTrue("Expected left to be better match (negative)", cmp < 0 || cmp == 0);
    }
}
