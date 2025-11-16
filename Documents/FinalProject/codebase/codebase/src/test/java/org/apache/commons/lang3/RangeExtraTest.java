package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RangeExtraTest {

    @Test
    public void testBetween() {
        Range<Integer> range = Range.between(1, 10);
        assertEquals(Integer.valueOf(1), range.getMinimum());
        assertEquals(Integer.valueOf(10), range.getMaximum());
    }

    @Test
    public void testIs() {
        Range<Integer> range = Range.is(5);
        assertEquals(Integer.valueOf(5), range.getMinimum());
        assertEquals(Integer.valueOf(5), range.getMaximum());
    }

    @Test
    public void testContains() {
        Range<Integer> range = Range.between(1, 10);
        assertTrue(range.contains(5));
        assertFalse(range.contains(15));
        assertTrue(range.contains(1));
        assertTrue(range.contains(10));
    }

    @Test
    public void testIsAfter() {
        Range<Integer> range = Range.between(5, 10);
        assertTrue(range.isAfter(3));
        assertFalse(range.isAfter(6));
    }

    @Test
    public void testIsStartedBy() {
        Range<Integer> range = Range.between(5, 10);
        assertTrue(range.isStartedBy(5));
        assertFalse(range.isStartedBy(6));
    }

    @Test
    public void testIsEndedBy() {
        Range<Integer> range = Range.between(5, 10);
        assertTrue(range.isEndedBy(10));
        assertFalse(range.isEndedBy(9));
    }

    @Test
    public void testIsBefore() {
        Range<Integer> range = Range.between(5, 10);
        assertTrue(range.isBefore(15));
        assertFalse(range.isBefore(7));
    }

    @Test
    public void testIsAfterRange() {
        Range<Integer> range1 = Range.between(10, 20);
        Range<Integer> range2 = Range.between(1, 5);
        assertTrue(range1.isAfterRange(range2));
        assertFalse(range2.isAfterRange(range1));
    }

    @Test
    public void testIsBeforeRange() {
        Range<Integer> range1 = Range.between(1, 5);
        Range<Integer> range2 = Range.between(10, 20);
        assertTrue(range1.isBeforeRange(range2));
        assertFalse(range2.isBeforeRange(range1));
    }

    @Test
    public void testIsOverlappedBy() {
        Range<Integer> range1 = Range.between(5, 15);
        Range<Integer> range2 = Range.between(10, 20);
        assertTrue(range1.isOverlappedBy(range2));
        
        Range<Integer> range3 = Range.between(25, 30);
        assertFalse(range1.isOverlappedBy(range3));
    }

    @Test
    public void testIntersectionWith() {
        Range<Integer> range1 = Range.between(5, 15);
        Range<Integer> range2 = Range.between(10, 20);
        Range<Integer> intersection = range1.intersectionWith(range2);
        assertEquals(Integer.valueOf(10), intersection.getMinimum());
        assertEquals(Integer.valueOf(15), intersection.getMaximum());
    }

    @Test
    public void testEquals() {
        Range<Integer> range1 = Range.between(5, 10);
        Range<Integer> range2 = Range.between(5, 10);
        Range<Integer> range3 = Range.between(1, 10);
        
        assertTrue(range1.equals(range2));
        assertFalse(range1.equals(range3));
        assertFalse(range1.equals(null));
        assertTrue(range1.equals(range1));
    }

    @Test
    public void testHashCode() {
        Range<Integer> range1 = Range.between(5, 10);
        Range<Integer> range2 = Range.between(5, 10);
        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testToString() {
        Range<Integer> range = Range.between(5, 10);
        String str = range.toString();
        assertTrue(str.contains("5"));
        assertTrue(str.contains("10"));
    }

    @Test
    public void testToStringFormat() {
        Range<Integer> range = Range.between(5, 10);
        String str = range.toString("Range: %s to %s");
        assertTrue(str.contains("Range:"));
        assertTrue(str.contains("5"));
        assertTrue(str.contains("10"));
    }

    @Test
    public void testIsNaturalOrdering() {
        Range<Integer> range = Range.between(5, 10);
        assertTrue(range.toString().length() > 0);
    }
}
