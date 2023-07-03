package org.nism.jrebel.core;

import java.io.ObjectInputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * @author inism
 */
public class G {

    private G() {
        throw new IllegalStateException();
    }

    public static byte[] getKey(final byte[] array) {
        String path = System.getProperty("java.vm.name").contains("HotSpot") ? "/rsa.key" : "/rsa.openjdk.key";
        try (ObjectInputStream in = new ObjectInputStream(G.class.getResourceAsStream(path))) {
            final Signature instance = Signature.getInstance("SHA1withRSA");
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
