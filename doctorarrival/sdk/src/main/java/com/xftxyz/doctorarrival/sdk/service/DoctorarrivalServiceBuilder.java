package com.xftxyz.doctorarrival.sdk.service;

import java.io.FileInputStream;
import java.security.PrivateKey;

import com.xftxyz.doctorarrival.helper.KeyHelper;

public class DoctorarrivalServiceBuilder {
    public static DoctorarrivalService build(String hospitalCode, String privateKeyLocation) {
        try (FileInputStream fis = new FileInputStream(privateKeyLocation)) {
            byte[] privateKeyBytes = new byte[fis.available()];
            fis.read(privateKeyBytes);
            PrivateKey privateKey = KeyHelper.getPrivateKey(privateKeyBytes);
            return new DoctorarrivalService(hospitalCode, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("初始化失败", e);
        }
    }
}
