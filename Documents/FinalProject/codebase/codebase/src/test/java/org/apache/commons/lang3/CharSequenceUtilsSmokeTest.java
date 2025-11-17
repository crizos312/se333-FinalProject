package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;

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
