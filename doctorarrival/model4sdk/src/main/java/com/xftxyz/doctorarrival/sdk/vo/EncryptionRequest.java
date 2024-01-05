package com.xftxyz.doctorarrival.sdk.vo;

import lombok.Data;

@Data
public class EncryptionRequest {
    /**
     * 医院编号
     */
    private String hospitalCode;

    /**
     * 加密数据
     */
    private String data;
}

