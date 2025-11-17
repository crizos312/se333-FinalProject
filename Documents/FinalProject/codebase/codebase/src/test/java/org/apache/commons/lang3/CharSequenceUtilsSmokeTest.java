package org.apache.commons.lang3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class CharSequenceUtilsSmokeTest {

    @Test
    public void testSubSequenceBasic() {
        CharSequence cs = CharSequenceUtils.subSequence("abc", 1);
        assertEquals("bc", cs.toString());
    }

    @Test
    public void testSubSequenceNull() {
        assertNull(CharSequenceUtils.subSequence(null, 0));
    }
}
