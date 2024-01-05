package com.xftxyz.doctorarrival.sdk.helper;

import java.util.Base64;

public class Base64Helper {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static String encodeToString(byte[] bytes) {
        return encoder.encodeToString(bytes);
    }

    public static byte[] decode(String str) {
        return decoder.decode(str);
    }
}
