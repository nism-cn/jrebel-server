package org.nism.jrebel;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.nism.jrebel.core.C;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.*;

/**
 * RSA算法实现过程为：
 * <p>
 * 1. 随意选择两个大的质数p和q，p不等于q，计算N=pq。
 * 2. 根据欧拉函数，不大于N且与N互质的整数個数為(p-1)(q-1)。
 * 3. 选择一个整数e与(p-1)(q-1)互质，并且e小于(p-1)(q-1)。
 * 4. 用以下这个公式计算d：d× e ≡ 1 (mod (p-1)(q-1))。
 * 5. 将p和q的记录销毁。
 * <p>
 * 以上内容中，(N,e)是公钥，(N,d)是私钥。
 *
 * @author Administrator
 */
public class RSADemo {

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    static byte[] pkcs1PrivateKeyByte = Base64.decode("MIICXgIBAAKBgQC/Iv5ebLS2QoU/F88jSvrTpMxh0S3fKFy9/VOvdasZ3Bro+sf6kU//WI5crqidiQA4m2NxckMHUc/FUPazAQto/bPJDVagk9rB+UahTZ6gzxfTa4GkpM3jTXAppJfls9Qu1IzLLXoKgsT91wYdm21e1/4otmagTYa5pwAiwfACEQIDAQABAoGBAIy1/1uWXad760pahdSuo19iCFDOxR1vQm9f6tbWIoNkJa90x/owNht+nzeeIWXwcOg7hQcEnlHqKbJSe3umfjWjk35MoV06imNwDa3joF5shVGBVGtKHPcboDGniCONO+HyIc9UNNi84pWXHFlijV5fDmec6odUpsqWmkxXPQetAkEA63V/cnns4W//0ZQLMv0OnJg5fDECAFB189OTj0A6kILfBuTfUpDPA/ioUGU4r8r2jHy+1b6cUD68gvbAAGVgmwJBAM/PpZhLuIKQYNcTwYbmOGCb9OMoxezAXI2mzzsTIU0fHEVnWYvssTYDf5PqRMeaOzECJnUbjvFWMRnCbbRvBMMCQCTUVSIH1jiQ9zfF61aHZKCz4tH9LG32J+0CnCMdDcwK3G3MoO3ePrNFUrZ4jrxYh+YDoSn3zaVzmrL1e6TUNp8CQQCgw7cL1qhq+V6xhKsWvUuoEX6lrYlQ2o+/VejDfs0oaITqfEWeJgICEzrDJ10GPZ7FDzDJMASpV1Cs6OkNyUUZAkEAuSkubxXXJTqaTha8JI+Fa6Db6XUqv0A2f4hmt8hqFgBbUCZRkayKVRo0J9roSffGCvt/T4MKcXbUss7/OEVaKg==");
    static byte[] pkcs1PublicKeyByte = Base64.decode("MIGJAoGBAL8i/l5stLZChT8XzyNK+tOkzGHRLd8oXL39U691qxncGuj6x/qRT/9YjlyuqJ2JADibY3FyQwdRz8VQ9rMBC2j9s8kNVqCT2sH5RqFNnqDPF9NrgaSkzeNNcCmkl+Wz1C7UjMstegqCxP3XBh2bbV7X/ii2ZqBNhrmnACLB8AIRAgMBAAE=");


    private PublicKey publicKey;
    private PrivateKey privateKey;

    public static void main(String[] args) {
//        signTest();
        getKeyPair();
    }

    public static void signTest() {
        RSADemo rsa = new RSADemo();
        byte[] data = "你好".getBytes();

        //加密  此处用公钥加密 也可以反过来 用私钥加密 用公钥解密
        byte[] eData = rsa.encryptData(data, rsa.publicKey);
        String eDataEncode = Base64.encode(eData);

        System.out.println("加密后: " + eDataEncode);

        //解密
        byte[] dData = rsa.decryptData(Base64.decode(eDataEncode), rsa.privateKey);
        System.out.println("解密后: " + new String(dData));

        String SIGN_sha256 = "SHA256WithRSA";
        String SIGN_sh1 = "SHA1WithRSA";

        String sign = rsa.sign(data, SIGN_sha256);
        String sign2 = rsa.sign(data, SIGN_sh1);
        //签名
        System.out.println(SIGN_sha256 + "签名:" + sign);
        System.out.println(SIGN_sh1 + "  签名:" + sign2);

        boolean verify = rsa.verify(data, sign, SIGN_sha256);
        boolean verify2 = rsa.verify(data, sign2, SIGN_sh1);

        System.out.println(SIGN_sha256 + "验签:" + verify);
        System.out.println(SIGN_sh1 + "验签:" + verify2);
    }

    public static void getKeyPair() {

        //通过byte[]可以再度将公钥或私钥还原出来
        PublicKey publicKey2 = formatPKCS8PublicKey(formatPublicKeyPKCS1ToPKCS8(pkcs1PublicKeyByte));

//        PrivateKey privateKey2 = formatPKCS8PrivateKey(formatPrivateKeyPKCS1ToPKCS8(pkcs1PrivateKeyByte));
        PrivateKey privateKey2 = formatPKCS8PrivateKey(formatPrivateKeyPKCS1ToPKCS8(Base64.decode(C.K)));

        RSAPrivateKey formatPKCS1PrivateKey = formatPKCS1PrivateKey(formatPrivateKeyPKCS8ToPKCS1(privateKey2.getEncoded()));

        RSAPrivateKey formatPKCS1PrivateKey_2 = formatPKCS1PrivateKey(formatPrivateKeyPKCS8ToPKCS1_2(privateKey2.getEncoded()));

        ASN1Encodable formatPKCS1PrivateKey_2_2 = formatPKCS1PrivateKey_2(formatPrivateKeyPKCS8ToPKCS1_2(privateKey2.getEncoded()));

        RSAPublicKey formatPKCS1PublicKey = formatPKCS1PublicKey(formatPublicKeyPKCS8ToPKCS1(publicKey2.getEncoded()));

        ASN1Encodable formatPKCS1PublicKey_2 = formatPKCS1PublicKey_2(formatPublicKeyPKCS8ToPKCS1(publicKey2.getEncoded()));

    }

    public static byte[] formatPrivateKeyPKCS1ToPKCS8(byte[] pkcs1PrivateKeyByte) {
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);
        RSAPrivateKey privateKey = RSAPrivateKey.getInstance(pkcs1PrivateKeyByte);
        //另一种方式
        ASN1Sequence privateKey1 = ASN1Sequence.getInstance(pkcs1PrivateKeyByte);

        byte[] pkcs8Bytes = null;

        try {
            PrivateKeyInfo privKeyInfo = new PrivateKeyInfo(algorithmIdentifier, privateKey);
            PrivateKeyInfo privKeyInfo1 = new PrivateKeyInfo(algorithmIdentifier, privateKey1);
            pkcs8Bytes = privKeyInfo.getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pkcs8Bytes;

    }

    public static byte[] formatPrivateKeyPKCS8ToPKCS1(byte[] pksc8PrivateKeyByte) {
        PrivateKeyInfo pki = PrivateKeyInfo.getInstance(pksc8PrivateKeyByte);
        byte[] pkcs1Bytes = null;
        try {
            ASN1Encodable parsePrivateKey = pki.parsePrivateKey();
            pkcs1Bytes = parsePrivateKey.toASN1Primitive().getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pkcs1Bytes;

    }

    public static byte[] formatPrivateKeyPKCS8ToPKCS1_2(byte[] pksc8PrivateKeyByte) {
        PrivateKeyInfo pki = PrivateKeyInfo.getInstance(pksc8PrivateKeyByte);
        byte[] pkcs1Bytes = null;
        try {
            RSAPrivateKey parsePrivateKey = RSAPrivateKey.getInstance(pki.parsePrivateKey());
            pkcs1Bytes = parsePrivateKey.getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pkcs1Bytes;

    }

    public static byte[] formatPublicKeyPKCS1ToPKCS8(byte[] pkcs1PublicKeyByte) {
        RSAPublicKey rsaPub = RSAPublicKey.getInstance(pkcs1PublicKeyByte);
        byte[] pkcs8Bytes = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey generatePublic = kf.generatePublic(new RSAPublicKeySpec(rsaPub.getModulus(), rsaPub.getPublicExponent()));
            pkcs8Bytes = generatePublic.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pkcs8Bytes;

    }

    public static RSAPrivateKeyStructure formatPkcs8ToPkcs1(String rawKey) throws Exception {
        PrivateKeyInfo pki = PrivateKeyInfo.getInstance(pkcs1PrivateKeyByte);
        RSAPrivateKeyStructure pkcs1Key = RSAPrivateKeyStructure.getInstance(pki.getPrivateKey());
        return pkcs1Key;
    }

    public static byte[] formatPublicKeyPKCS8ToPKCS1(byte[] pkcs8PublicKeyByte) {
        ASN1Sequence publicKeyASN1Object = ASN1Sequence.getInstance(pkcs8PublicKeyByte);
        ASN1Encodable derBitStringASN1Encodable = publicKeyASN1Object.getObjectAt(1);
        DERBitString derBitStringObject = DERBitString.getInstance(derBitStringASN1Encodable);
        return derBitStringObject.getBytes();

    }

    public static RSAPublicKey formatPKCS1PublicKey(byte[] pkcs1PublicKeyByte) {
        return RSAPublicKey.getInstance(pkcs1PublicKeyByte);
    }

    public static ASN1Encodable formatPKCS1PublicKey_2(byte[] pkcs1PublicKeyByte) {
        return ASN1Sequence.getInstance(pkcs1PublicKeyByte);
    }

    public static RSAPrivateKey formatPKCS1PrivateKey(byte[] pkcs1PrivateKeyByte) {
        return RSAPrivateKey.getInstance(pkcs1PrivateKeyByte);
    }

    public static ASN1Encodable formatPKCS1PrivateKey_2(byte[] pkcs1PrivateKeyByte) {
        return ASN1Sequence.getInstance(pkcs1PrivateKeyByte);
    }


    public RSADemo() {

        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);// 密钥位数
            // 动态生成密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            publicKey = keyPair.getPublic();
            // 私钥
            privateKey = keyPair.getPrivate();

            //获得公钥私钥的比特编码
            byte[] publicKeyByte = publicKey.getEncoded();
            byte[] privateKeyByte = privateKey.getEncoded();

            //通过byte[]可以再度将公钥或私钥还原出来
            formatPKCS8PublicKey(publicKeyByte);
            formatPKCS8PrivateKey(privateKeyByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用公钥加密
     */
    public byte[] encryptData(byte[] data, PublicKey publicKey) {

        try {

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 加密前设定加密方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 传入编码数据并返回编码结果
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;

            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            //不分段加密
            //encryptedData=cipher.doFinal(data);

            return encryptedData;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     * @return
     */
    public byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 通过公钥byte[]将公钥还原，适用于RSA算法
     */
    public static PublicKey formatPKCS8PublicKey(byte[] pkcs8PublicKeyByte) {
        PublicKey publicKey = null;
        EncodedKeySpec keySpec = new X509EncodedKeySpec(pkcs8PublicKeyByte);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;

    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     */
    public static PrivateKey formatPKCS8PrivateKey(byte[] pkcs8PrivateKeyByte) {
        PrivateKey privateKey = null;
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8PrivateKeyByte);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public String sign(byte[] data, String SIGN_ALGORITHMS) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(data);
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verify(byte[] srcData, String sign, String SIGN_ALGORITHMS) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(srcData);
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

