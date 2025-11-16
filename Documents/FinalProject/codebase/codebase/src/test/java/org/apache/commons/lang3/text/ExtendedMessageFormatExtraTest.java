package org.apache.commons.lang3.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.Format;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

public class ExtendedMessageFormatExtraTest {

    private static class UpperFormat extends Format {
        private static final long serialVersionUID = 1L;
        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, java.text.FieldPosition pos) {
            String s = obj == null ? "null" : obj.toString();
            toAppendTo.append(s.toUpperCase(Locale.ROOT));
            return toAppendTo;
        }
        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return null;
        }
    }

    private static class UpperFactory implements FormatFactory {
        @Override
        public Format getFormat(String name, String arguments, Locale locale) {
            return new UpperFormat();
        }
    }

    @Test
    public void testCustomFormatFactoryApplies() {
        Map<String, FormatFactory> registry = new HashMap<String, FormatFactory>();
        registry.put("upper", new UpperFactory());
        ExtendedMessageFormat emf = new ExtendedMessageFormat("Hello {0,upper}", registry);
        String out = emf.format(new Object[]{"world"});
        assertEquals("Hello WORLD", out);
        assertTrue(emf.toPattern().contains("upper"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFormatThrows() {
        ExtendedMessageFormat emf = new ExtendedMessageFormat("{0}", Collections.<String, FormatFactory>emptyMap());
        emf.setFormat(0, new UpperFormat());
    }
}
