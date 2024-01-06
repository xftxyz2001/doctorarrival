package com.xftxyz.doctorarrival.common.helper;

import static com.xftxyz.doctorarrival.common.constant.Constants.AES_KEY_SIZE;
import static com.xftxyz.doctorarrival.common.constant.Constants.ALGORITHM_AES;
import static com.xftxyz.doctorarrival.common.constant.Constants.ALGORITHM_RSA;
import static com.xftxyz.doctorarrival.common.constant.Constants.RSA_KEY_SIZE;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyHelper {

    // 密钥生成器
    private static final KeyPairGenerator keyPairGenerator;
    private static final KeyGenerator keyGenerator;

    // 密钥工厂
    private static final KeyFactory rsaKeyFactory;

    // 初始化
    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGenerator.initialize(RSA_KEY_SIZE);

            keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES);
            keyGenerator.init(AES_KEY_SIZE);

            rsaKeyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 生成密钥对
    public static KeyPair generateKeyPair() {
        return keyPairGenerator.generateKeyPair();
    }

    // 生成密钥
    public static SecretKey generateKey() {
        return keyGenerator.generateKey();
    }

    // 获取公钥
    public static PublicKey getPublicKey(byte[] publicKeyEncoded) throws InvalidKeySpecException {
        return rsaKeyFactory.generatePublic(new X509EncodedKeySpec(publicKeyEncoded));
    }

    // 获取私钥
    public static PrivateKey getPrivateKey(byte[] privateKeyEncoded) throws InvalidKeySpecException {
        return rsaKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyEncoded));
    }

    // 获取密钥
    public static SecretKey getSecretKey(byte[] secretKeyEncoded) {
        return new SecretKeySpec(secretKeyEncoded, ALGORITHM_AES);
    }
}
