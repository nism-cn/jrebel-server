package org.nism.jrebel.utils;


import java.util.Base64;

/**
 * Base64 util
 *
 * @author inism
 */
public class Base64Util {

    private Base64Util() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] decode(String s) {
        return Base64.getDecoder().decode(s);
    }

    public static byte[] encodeBase64(byte[] b) {
        return Base64.getEncoder().encode(b);
    }

    public static byte[] decodeBase64(byte[] b) {
        return Base64.getDecoder().decode(b);
    }

}
