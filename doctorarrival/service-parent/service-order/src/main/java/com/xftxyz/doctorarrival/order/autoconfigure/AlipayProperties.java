package com.xftxyz.doctorarrival.order.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "pay.ali")
public class AlipayProperties {
    private String serverUrl;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;

}
