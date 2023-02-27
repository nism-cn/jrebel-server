package org.nism.jrebel.utils;

import org.nism.jrebel.core.C;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;

/**
 * rsa util
 *
 * @author inism
 */
public class RsaUtil {

    private RsaUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 传入秘钥为ASN格式
     * 私钥签名程序，privateKey是私钥base64编码字符串，即私钥文件数据中，中间的主体部分
     */
    public static String sign(String content) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Signature signature = Signature.getInstance("MD5WithRSA");

            PrivateKey priKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(C.MODULUS, C.PRIVATE_EXPONENT));
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return HexUtil.bytesToHexString(signed);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
