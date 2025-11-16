package org.apache.commons.lang3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ValidateExtraTest {

    @Test(expected = NullPointerException.class)
    public void testNotNull() {
        Validate.notNull(null);
    }

    @Test
    public void testNotNullPass() {
        assertEquals("test", Validate.notNull("test"));
    }

    @Test(expected = NullPointerException.class)
    public void testNotNullWithMessage() {
        Validate.notNull(null, "Object must not be null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsTrue() {
        Validate.isTrue(false);
    }

    @Test
    public void testIsTruePass() {
        Validate.isTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsTrueWithMessage() {
        Validate.isTrue(false, "Expression must be true");
    }

    @Test(expected = NullPointerException.class)
    public void testNotEmptyArray() {
        Validate.notEmpty((Object[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyArrayEmpty() {
        Validate.notEmpty(new Object[0]);
    }

    @Test
    public void testNotEmptyArrayPass() {
        Object[] array = {"test"};
        assertArrayEquals(array, Validate.notEmpty(array));
    }

    @Test(expected = NullPointerException.class)
    public void testNotEmptyString() {
        Validate.notEmpty((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyStringEmpty() {
        Validate.notEmpty("");
    }

    @Test
    public void testNotEmptyStringPass() {
        assertEquals("test", Validate.notEmpty("test"));
    }

    @Test(expected = NullPointerException.class)
    public void testNotBlank() {
        Validate.notBlank(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotBlankEmpty() {
        Validate.notBlank("   ");
    }

    @Test
    public void testNotBlankPass() {
        assertEquals("test", Validate.notBlank("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoNullElements() {
        Validate.noNullElements(new Object[]{" test", null});
    }

    @Test
    public void testNoNullElementsPass() {
        Object[] array = {"test", "value"};
        assertArrayEquals(array, Validate.noNullElements(array));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testValidIndex() {
        Validate.validIndex(new Object[]{"test"}, 5);
    }

    @Test
    public void testValidIndexPass() {
        Object[] array = {"test", "value"};
        assertArrayEquals(array, Validate.validIndex(array, 1));
    }

    @Test(expected = IllegalStateException.class)
    public void testValidState() {
        Validate.validState(false);
    }

    @Test
    public void testValidStatePass() {
        Validate.validState(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMatchesPattern() {
        Validate.matchesPattern("abc", "[0-9]+");
    }

    @Test
    public void testMatchesPatternPass() {
        Validate.matchesPattern("123", "[0-9]+");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInclusiveBetween() {
        Validate.inclusiveBetween(1, 10, 15);
    }

    @Test
    public void testInclusiveBetweenPass() {
        Validate.inclusiveBetween(1, 10, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExclusiveBetween() {
        Validate.exclusiveBetween(1, 10, 1);
    }

    @Test
    public void testExclusiveBetweenPass() {
        Validate.exclusiveBetween(1, 10, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsInstanceOf() {
        Validate.isInstanceOf(String.class, 123);
    }

    @Test
    public void testIsInstanceOfPass() {
        Validate.isInstanceOf(String.class, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsAssignableFrom() {
        Validate.isAssignableFrom(String.class, Integer.class);
    }

    @Test
    public void testIsAssignableFromPass() {
        Validate.isAssignableFrom(CharSequence.class, String.class);
    }
}
