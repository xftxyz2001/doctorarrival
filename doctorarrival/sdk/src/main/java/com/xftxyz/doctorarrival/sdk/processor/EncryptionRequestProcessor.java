package com.xftxyz.doctorarrival.sdk.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.helper.Base64Helper;
import com.xftxyz.doctorarrival.helper.CipherHelper;

import javax.crypto.SecretKey;
import java.io.IOException;

public class EncryptionRequestProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private CipherHelper cipherHelper;

    public EncryptionRequestProcessor(SecretKey secretKey) {
        cipherHelper = new CipherHelper(secretKey);
    }

    // 解密
    public <T> T decrypt(String encryptedBase64, Class<T> clazz) throws IOException {
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
