package com.xftxyz.doctorarrival.common.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.common.helper.Base64Helper;
import com.xftxyz.doctorarrival.common.helper.KeyPairHelper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class EncryptionRequestProcessor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Cipher decryptCipher;

    private final Cipher encryptCipher;

    public EncryptionRequestProcessor(String base64PublicKey) throws InvalidKeySpecException {
        byte[] publicKeyEncoded = Base64Helper.decode(base64PublicKey);
        PublicKey publicKey = KeyPairHelper.getPublicKey(publicKeyEncoded);
        try {
            decryptCipher = Cipher.getInstance(KeyPairHelper.PADDING);
            decryptCipher.init(Cipher.DECRYPT_MODE, publicKey);

            encryptCipher = Cipher.getInstance(KeyPairHelper.PADDING);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    // 解密
    public <T> T decrypt(String encryptedBase64, Class<T> clazz) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] encryptedData = Base64Helper.decode(encryptedBase64);
        ByteArrayOutputStream decryptedStream = new ByteArrayOutputStream();
        // int chunkSize = decryptCipher.getBlockSize();  // 获取RSA块大小
        for (int i = 0; i < encryptedData.length; i += KeyPairHelper.BLOCK_SIZE) {
            int length = Math.min(KeyPairHelper.BLOCK_SIZE, encryptedData.length - i);
            byte[] chunk = new byte[length];
            System.arraycopy(encryptedData, i, chunk, 0, length);
            byte[] decryptedChunk = decryptCipher.doFinal(chunk);
            decryptedStream.write(decryptedChunk);
        }
        byte[] decryptedData = decryptedStream.toByteArray();
        return objectMapper.readValue(decryptedData, clazz);
    }

    // 加密
    public String encrypt(Object object) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] data = objectMapper.writeValueAsBytes(object);
        ByteArrayOutputStream encryptedStream = new ByteArrayOutputStream();
        // int chunkSize = encryptCipher.getBlockSize();
        for (int i = 0; i < data.length; i += KeyPairHelper.BLOCK_SIZE) {
            int length = Math.min(KeyPairHelper.BLOCK_SIZE, data.length - i);
            byte[] chunk = new byte[length];
            System.arraycopy(data, i, chunk, 0, length);
            byte[] encryptedChunk = encryptCipher.doFinal(chunk);
            encryptedStream.write(encryptedChunk);
        }
        byte[] encryptedData = encryptedStream.toByteArray();
        return Base64Helper.encodeToString(encryptedData);
    }
}
