package org.apache.commons.lang3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConversionExtraTest {

    @Test
    public void testHexDigitMsb0ToBinary() {
        boolean[] arr = Conversion.hexDigitMsb0ToBinary('F');
        assertNotNull(arr);
        assertEquals(4, arr.length);
    }

    @Test
    public void testBinaryToHexDigitMsb0_4bits() {
        char c = Conversion.binaryToHexDigitMsb0_4bits(new boolean[]{true, false, true, false});
        assertTrue(Character.isLetterOrDigit(c));
    }

    @Test
    public void testIntArrayToLong() {
        int[] src = {1, 2};
        long l = Conversion.intArrayToLong(src, 0, 0L, 0, 2);
        assertTrue(l != 0);
    }

    @Test
    public void testByteArrayToShort() {
        byte[] src = {(byte)0xFF, (byte)0x01};
        short s = Conversion.byteArrayToShort(src, 0, (short)0, 0, 2);
        assertNotEquals(0, s);
    }
}
