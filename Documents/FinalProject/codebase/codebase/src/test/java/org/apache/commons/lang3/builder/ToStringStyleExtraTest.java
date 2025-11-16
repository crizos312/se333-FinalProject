package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;

import org.junit.Test;

public class ToStringStyleExtraTest {

    private static class TestStyle extends ToStringStyle {
        private static final long serialVersionUID = 1L;
    }

    @Test
    public void testArrayStartEnd() {
        ToStringStyle style = new TestStyle();
        style.setArrayStart("[");
        style.setArrayEnd("]");
        style.setArraySeparator(",");
        ToStringBuilder builder = new ToStringBuilder(this, style);
        builder.append("arr", new int[]{1,2,3});
        String s = builder.toString();
        assertTrue(s.contains("["));
        assertTrue(s.contains("]"));
    }

    @Test
    public void testNullText() {
        ToStringStyle style = new TestStyle();
        style.setNullText("<NULL>");
        ToStringBuilder builder = new ToStringBuilder(this, style);
        builder.append("field", (Object)null);
        String s = builder.toString();
        assertTrue(s.contains("<NULL>"));
    }

    @Test
    public void testFieldSeparatorAtStart() {
        ToStringStyle style = new TestStyle();
        style.setFieldSeparatorAtStart(true);
        style.setFieldSeparator(",");
        ToStringBuilder builder = new ToStringBuilder(new Object(), style);
        builder.append("a", 1);
        String s = builder.toString();
        assertNotNull(s);
    }

    @Test
    public void testContentStart() {
        ToStringStyle style = new TestStyle();
        style.setContentStart("{");
        style.setContentEnd("}");
        ToStringBuilder builder = new ToStringBuilder(new Object(), style);
        builder.append("x", "y");
        String s = builder.toString();
        assertTrue(s.contains("{"));
        assertTrue(s.contains("}"));
    }
}

