package com.xftxyz.doctorarrival.sdk.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.sdk.helper.Base64Helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class EncryptionRequestProcessor {

    public static final String ALGORITHM = "RSA";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Cipher decryptCipher;

    private final Cipher encryptCipher;


    public EncryptionRequestProcessor(PrivateKey privateKey) {
        try {
            decryptCipher = Cipher.getInstance(ALGORITHM);
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

            encryptCipher = Cipher.getInstance(ALGORITHM);
            encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    // 解密
    public <T> T decrypt(String encryptedBase64, Class<T> clazz) throws IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] encryptedData = Base64Helper.decode(encryptedBase64);
        byte[] decryptedData = decryptCipher.doFinal(encryptedData);
        return objectMapper.readValue(decryptedData, clazz);
    }

    // 加密
    public String encrypt(Object object) throws IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] data = objectMapper.writeValueAsBytes(object);
        byte[] encryptedData = encryptCipher.doFinal(data);
        return Base64Helper.encodeToString(encryptedData);
    }
}
