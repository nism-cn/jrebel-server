package org.nism.jrebel.utils;

/**
 * hex util
 *
 * @author inism
 */
public class HexUtil {

    private HexUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder s = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                s.append(0);
            }
            s.append(hv);
        }
        return s.toString();
    }

}
