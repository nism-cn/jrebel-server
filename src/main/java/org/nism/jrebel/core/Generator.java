package org.nism.jrebel.core;

import java.io.ObjectInputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * jrebel generator
 *
 * @author inism
 */
public class Generator {

    private Generator() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] getKey(final byte[] array) {
        try {
            final Signature instance = Signature.getInstance("SHA1withRSA");
            ObjectInputStream in = new ObjectInputStream(Generator.class.getResourceAsStream("/rsa.key"));
            PrivateKey rsa = (PrivateKey) in.readObject();
            instance.initSign(rsa);
            instance.update(array);
            return instance.sign();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("License Server installation error 0000000F2", e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
