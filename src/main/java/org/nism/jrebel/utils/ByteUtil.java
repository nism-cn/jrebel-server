package org.nism.jrebel.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * byte util
 *
 * @author inism
 */
public class ByteUtil {

    private ByteUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static String convert(final byte[] b) {
        return b == null ? null : new String(Base64Util.encodeBase64(b), UTF8);
    }

    public static byte[] convert(final String s) {
        return s == null ? null : Base64Util.decodeBase64(s.getBytes(UTF8));
    }

}
