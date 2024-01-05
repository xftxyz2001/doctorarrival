package com.xftxyz.doctorarrival.common.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.common.helper.Base64Helper;
import com.xftxyz.doctorarrival.common.helper.KeyPairHelper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class EncryptionRequestProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final PublicKey publicKey;

    public EncryptionRequestProcessor(String base64PublicKey) throws InvalidKeySpecException {
        byte[] publicKeyEncoded = Base64Helper.decode(base64PublicKey);
        this.publicKey = KeyPairHelper.getPublicKey(publicKeyEncoded);
    }

    // 解密
    public <T> T decrypt(String encryptedBase64, Class<T> clazz) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] encryptedData = Base64Helper.decode(encryptedBase64);
        Cipher cipher = Cipher.getInstance(KeyPairHelper.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return objectMapper.readValue(decryptedData, clazz);
    }

    // 加密
    public String encrypt(Object object) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] data = objectMapper.writeValueAsBytes(object);
        Cipher cipher = Cipher.getInstance(KeyPairHelper.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data);
        return Base64Helper.encodeToString(encryptedData);
    }
}
