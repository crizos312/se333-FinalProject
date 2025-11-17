package org.apache.commons.lang3.builder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EqualsBuilderExtraTest {

    @Test
    public void testAppendDeepArray() {
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(new int[][]{{1,2},{3,4}}, new int[][]{{1,2},{3,4}});
        assertTrue(eb.isEquals());
    }

    @Test
    public void testAppendSuper() {
        EqualsBuilder eb = new EqualsBuilder();
        eb.appendSuper(true);
        eb.append("a", "a");
        assertTrue(eb.isEquals());
        eb.appendSuper(false);
        assertFalse(eb.isEquals());
    }

    @Test
    public void testAppendObjectArraysDifferentLength() {
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(new Object[]{1}, new Object[]{1,2});
        assertFalse(eb.isEquals());
    }
}
