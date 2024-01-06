package com.xftxyz.doctorarrival.common.helper;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairHelper {

    // 加密算法
    public static final String ALGORITHM = "RSA";

    // 填充方式
    public static final String PADDING = "RSA/ECB/PKCS1Padding";

    // 密钥长度
    public static final int KEY_SIZE = 1024;

    // 块大小
    public static final int BLOCK_SIZE = 64; // < KEY_SIZE / 8

    private static final KeyPairGenerator keyPairGenerator;

    private static final KeyFactory keyFactory;

    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);

            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 生成密钥对
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return keyPairGenerator.generateKeyPair();
    }

    // 获取公钥
    public static PublicKey getPublicKey(byte[] publicKeyEncoded) throws InvalidKeySpecException {
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyEncoded));
    }

    // 获取私钥
    public static PrivateKey getPrivateKey(byte[] privateKeyEncoded) throws InvalidKeySpecException {
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyEncoded));
    }
}
