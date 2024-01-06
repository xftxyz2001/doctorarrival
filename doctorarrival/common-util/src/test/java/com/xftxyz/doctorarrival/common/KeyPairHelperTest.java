package com.xftxyz.doctorarrival.common;

import com.xftxyz.doctorarrival.common.helper.KeyPairHelper;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import static com.xftxyz.doctorarrival.common.helper.KeyPairHelper.ALGORITHM;
import static com.xftxyz.doctorarrival.common.helper.KeyPairHelper.PADDING;

public class KeyPairHelperTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyPair keyPair = KeyPairHelper.generateKeyPair();

        Cipher decryptCipher = Cipher.getInstance(PADDING);
        decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        System.out.println("decryptCipher.getBlockSize() = " + decryptCipher.getBlockSize());

        Cipher encryptCipher = Cipher.getInstance(PADDING);
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        System.out.println("encryptCipher.getBlockSize() = " + encryptCipher.getBlockSize());
    }
}
