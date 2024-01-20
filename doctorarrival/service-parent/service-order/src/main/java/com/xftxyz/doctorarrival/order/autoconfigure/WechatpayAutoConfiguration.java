package com.xftxyz.doctorarrival.order.autoconfigure;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


// @Configuration
@EnableConfigurationProperties(WechatpayProperties.class)
public class WechatpayAutoConfiguration {

    @Bean
    public NativePayService nativePayService(WechatpayProperties wechatpayProperties) {
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(wechatpayProperties.getMerchantId())
                        .privateKeyFromPath(wechatpayProperties.getPrivateKeyPath())
                        .merchantSerialNumber(wechatpayProperties.getMerchantSerialNumber())
                        .apiV3Key(wechatpayProperties.getApiV3Key())
                        .build();
        // 构建service
        return new NativePayService.Builder().config(config).build();
    }
}
