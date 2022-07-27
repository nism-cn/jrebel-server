package org.nism.jrebel.core;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.nism.jrebel.util.ByteUtil;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * jrebel generator
 *
 * @author inism
 */
public class Generator {
    private static final byte[] B = ByteUtil.convert(C.K);
    private static final BouncyCastleProvider D = new BouncyCastleProvider();

    private static PrivateKey getKey() {
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Generator.B);
        try {
            return KeyFactory.getInstance("RSA", Generator.D).generatePrivate(spec);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] getKey(final byte[] array) {
        try {
            final Signature instance = Signature.getInstance("SHA1withRSA", Generator.D);
            instance.initSign(getKey());
            instance.update(array);
            return instance.sign();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("License Server installation error 0000000F2", e);
        }
    }

}
