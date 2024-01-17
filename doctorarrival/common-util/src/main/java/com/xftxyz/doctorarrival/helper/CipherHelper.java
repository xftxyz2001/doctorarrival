package com.xftxyz.doctorarrival.helper;

import static com.xftxyz.doctorarrival.constant.Constants.ALGORITHM_AES;
import static com.xftxyz.doctorarrival.constant.Constants.ALGORITHM_RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class CipherHelper {
    private final Cipher decryptCipher;

    private final Cipher encryptCipher;

    public CipherHelper(PublicKey publicKey) {
        try {
            decryptCipher = Cipher.getInstance(ALGORITHM_RSA);
            decryptCipher.init(Cipher.DECRYPT_MODE, publicKey);

            encryptCipher = Cipher.getInstance(ALGORITHM_RSA);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CipherHelper(PrivateKey privateKey) {
        try {
            decryptCipher = Cipher.getInstance(ALGORITHM_RSA);
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

            encryptCipher = Cipher.getInstance(ALGORITHM_RSA);
            encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CipherHelper(SecretKey secretKey) {
        try {
            decryptCipher = Cipher.getInstance(ALGORITHM_AES);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);

            encryptCipher = Cipher.getInstance(ALGORITHM_AES);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] encryptedData) {
        try {
            return decryptCipher.doFinal(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] data) {
        try {
            return encryptCipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
