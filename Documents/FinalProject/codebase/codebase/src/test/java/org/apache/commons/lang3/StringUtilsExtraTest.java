package org.apache.commons.lang3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StringUtilsExtraTest {

    @Test
    public void testAbbreviate() {
        assertEquals("abc...", StringUtils.abbreviate("abcdefg", 6));
        assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", 10));
    }

    @Test
    public void testCapitalize() {
        assertEquals("Cat", StringUtils.capitalize("cat"));
        assertEquals("", StringUtils.capitalize(""));
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    public void testChomp() {
        assertEquals("foo", StringUtils.chomp("foo\n"));
        assertEquals("foo", StringUtils.chomp("foo\r\n"));
        assertEquals("foo", StringUtils.chomp("foo"));
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(StringUtils.containsIgnoreCase("abc", "AB"));
        assertFalse(StringUtils.containsIgnoreCase("abc", "XY"));
    }

    @Test
    public void testDefaultString() {
        assertEquals("", StringUtils.defaultString(null));
        assertEquals("abc", StringUtils.defaultString("abc"));
        assertEquals("default", StringUtils.defaultString(null, "default"));
    }

    @Test
    public void testDeleteWhitespace() {
        assertEquals("abc", StringUtils.deleteWhitespace("  ab  c  "));
        assertEquals("", StringUtils.deleteWhitespace(""));
        assertNull(StringUtils.deleteWhitespace(null));
    }

    @Test
    public void testDifference() {
        assertEquals("", StringUtils.difference("abcdefghijk", "abcdefgh"));
        assertEquals("ijk", StringUtils.difference("abcdefgh", "abcdefghijk"));
    }

    @Test
    public void testGetCommonPrefix() {
        assertEquals("abc", StringUtils.getCommonPrefix(new String[]{"abcde", "abcfg", "abcxy"}));
        assertEquals("", StringUtils.getCommonPrefix(new String[]{"abc", "xyz"}));
    }

    @Test
    public void testIndexOfDifference() {
        assertEquals(3, StringUtils.indexOfDifference("abcde", "abcfg"));
        assertEquals(-1, StringUtils.indexOfDifference("abc", "abc"));
    }

    @Test
    public void testJoin() {
        assertEquals("a,b,c", StringUtils.join(new String[]{"a", "b", "c"}, ","));
        assertEquals("", StringUtils.join(new String[]{}, ","));
    }

    @Test
    public void testLeftPad() {
        assertEquals("  abc", StringUtils.leftPad("abc", 5));
        assertEquals("zzabc", StringUtils.leftPad("abc", 5, 'z'));
    }

    @Test
    public void testOverlay() {
        assertEquals("aXYfg", StringUtils.overlay("abcdefg", "XY", 1, 5));
    }

    @Test
    public void testRemoveEnd() {
        assertEquals("www.domain", StringUtils.removeEnd("www.domain.com", ".com"));
        assertEquals("abc", StringUtils.removeEnd("abc", ".com"));
    }

    @Test
    public void testRepeat() {
        assertEquals("aaa", StringUtils.repeat("a", 3));
        assertEquals("", StringUtils.repeat("a", 0));
    }

    @Test
    public void testReverse() {
        assertEquals("cba", StringUtils.reverse("abc"));
        assertNull(StringUtils.reverse(null));
    }

    @Test
    public void testSplit() {
        assertArrayEquals(new String[]{"a", "b", "c"}, StringUtils.split("a b c"));
        assertArrayEquals(new String[]{"a", "b", "c"}, StringUtils.split("a,b,c", ","));
    }

    @Test
    public void testStripEnd() {
        assertEquals("ab", StringUtils.stripEnd("abccc", "c"));
        assertEquals("abc", StringUtils.stripEnd("abc", null));
    }

    @Test
    public void testSwapCase() {
        assertEquals("AbC", StringUtils.swapCase("aBc"));
    }

    @Test
    public void testUncapitalize() {
        assertEquals("cat", StringUtils.uncapitalize("Cat"));
    }
}
