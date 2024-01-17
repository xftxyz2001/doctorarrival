package com.xftxyz.doctorarrival.processor;

import java.io.IOException;

import javax.crypto.SecretKey;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.helper.Base64Helper;
import com.xftxyz.doctorarrival.helper.CipherHelper;
import com.xftxyz.doctorarrival.helper.KeyHelper;

public class EncryptionRequestProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final CipherHelper cipherHelper;

    public EncryptionRequestProcessor(String base64SecretKey) {
        byte[] secretKeyEncoded = Base64Helper.decode(base64SecretKey);
        SecretKey secretKey = KeyHelper.getSecretKey(secretKeyEncoded);
        cipherHelper = new CipherHelper(secretKey);
    }

    // 解密
    public <T> T decrypt(String encryptedBase64, Class<T> clazz)
            throws StreamReadException, DatabindException, IOException {
        byte[] encryptedData = Base64Helper.decode(encryptedBase64);
        byte[] decryptedData = cipherHelper.decrypt(encryptedData);
        return objectMapper.readValue(decryptedData, clazz);
    }

    // 加密
    public String encrypt(Object object) throws JsonProcessingException {
        byte[] data = objectMapper.writeValueAsBytes(object);
        return Base64Helper.encodeToString(cipherHelper.encrypt(data));
    }
}
