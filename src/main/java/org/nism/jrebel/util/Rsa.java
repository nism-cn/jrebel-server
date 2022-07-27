package org.nism.jrebel.util;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.nism.jrebel.core.C;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;

/**
 * rsa util
 *
 * @author inism
 */
public class Rsa {

    public static String sign(String content) {
        return Rsa.sign(content.getBytes(), C.RSA_KEY22);
    }

    public static String sign2(String content) {
        return Rsa.sign2(content.getBytes(), C.RSA_KEY33);
    }

    /**
     * 传入秘钥为ASN格式
     * 私钥签名程序，privateKey是私钥base64编码字符串，即私钥文件数据中，中间的主体部分
     */
    public static String sign(byte[] content, String privateKey) {
        try {
            byte[] keyByte = Base64.decode(privateKey);
            ASN1InputStream in = new ASN1InputStream(keyByte);
            ASN1Primitive obj = in.readObject();
            RSAPrivateKey pStruct = RSAPrivateKey.getInstance(obj);
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(pStruct.getModulus(), pStruct.getPrivateExponent());
            return getHexString(content, spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入秘钥为PKCS#8私钥非加密格式
     * 私钥签名程序，privateKey是私钥base64编码字符串，即私钥文件数据中，中间的主体部分
     */
    public static String sign2(byte[] content, String privateKey) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            return getHexString(content, spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHexString(byte[] content, KeySpec spec) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(spec);
        Signature signature = Signature.getInstance("MD5WithRSA");
        signature.initSign(priKey);
        signature.update(content);
        byte[] signed = signature.sign();
        return Hex.bytesToHexString(signed);
    }
}
