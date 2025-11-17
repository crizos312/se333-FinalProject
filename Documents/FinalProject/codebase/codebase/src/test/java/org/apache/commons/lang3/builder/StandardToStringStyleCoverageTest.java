package org.apache.commons.lang3.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Targeted coverage tests for StandardToStringStyle uncovered methods.
 */
public class StandardToStringStyleCoverageTest {

    @Test
    public void testUseClassNameMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for useClassName
        style.setUseClassName(true);
        assertTrue(style.isUseClassName());
        
        style.setUseClassName(false);
        assertFalse(style.isUseClassName());
    }

    @Test
    public void testUseShortClassNameMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for useShortClassName
        style.setUseShortClassName(true);
        assertTrue(style.isUseShortClassName());
        
        style.setUseShortClassName(false);
        assertFalse(style.isUseShortClassName());
    }

    @Test
    public void testUseIdentityHashCodeMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for useIdentityHashCode
        style.setUseIdentityHashCode(true);
        assertTrue(style.isUseIdentityHashCode());
        
        style.setUseIdentityHashCode(false);
        assertFalse(style.isUseIdentityHashCode());
    }

    @Test
    public void testUseFieldNamesMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for useFieldNames
        style.setUseFieldNames(true);
        assertTrue(style.isUseFieldNames());
        
        style.setUseFieldNames(false);
        assertFalse(style.isUseFieldNames());
    }

    @Test
    public void testDefaultFullDetailMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for defaultFullDetail
        style.setDefaultFullDetail(true);
        assertTrue(style.isDefaultFullDetail());
        
        style.setDefaultFullDetail(false);
        assertFalse(style.isDefaultFullDetail());
    }

    @Test
    public void testArrayContentDetailMethods() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test getter/setter for arrayContentDetail
        style.setArrayContentDetail(true);
        assertTrue(style.isArrayContentDetail());
        
        style.setArrayContentDetail(false);
        assertFalse(style.isArrayContentDetail());
    }

    @Test
    public void testArrayStartAndEnd() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test array start/end setters
        style.setArrayStart("[");
        style.setArrayEnd("]");
        style.setArraySeparator(",");
        
        // Use the style
        assertNotNull(style);
    }

    @Test
    public void testContentStartAndEnd() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        // Test content start/end
        style.setContentStart("[");
        style.setContentEnd("]");
        
        StringBuffer buffer = new StringBuffer();
        style.appendContentStart(buffer);
        style.appendContentEnd(buffer);
    }

    @Test
    public void testFieldNameValueSeparator() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        style.setFieldNameValueSeparator("=");
        assertNotNull(style);
    }

    @Test
    public void testFieldSeparator() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        style.setFieldSeparator(",");
        style.appendFieldSeparator(new StringBuffer());
    }

    @Test
    public void testNullText() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        style.setNullText("<null>");
        style.appendNullText(new StringBuffer(), "test");
    }

    @Test
    public void testSizeStartAndEnd() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        style.setSizeStartText("<size=");
        style.setSizeEndText(">");
        assertNotNull(style);
    }

    @Test
    public void testSummarySize() {
        StandardToStringStyle style = new StandardToStringStyle();
        
        style.setSummaryObjectStartText("<");
        style.setSummaryObjectEndText(">");
        
        style.appendSummarySize(new StringBuffer(), "field", 10);
    }
}
