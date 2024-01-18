package com.xftxyz.doctorarrival.order.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "pay.wx")
public class WechatpayProperties {

    private String merchantId;
    private String privateKeyPath;
    private String merchantSerialNumber;
    private String apiV3Key;
}
