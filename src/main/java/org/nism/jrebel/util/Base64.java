package org.nism.jrebel.util;


/**
 * Base64 util
 *
 * @author inism
 */
public class Base64 {

    public static byte[] decode(String s) {
        return java.util.Base64.getDecoder().decode(s);
    }

    public static byte[] encodeBase64(byte[] b) {
        return java.util.Base64.getEncoder().encode(b);
    }

    public static byte[] decodeBase64(byte[] b) {
        return java.util.Base64.getDecoder().decode(b);
    }
}
