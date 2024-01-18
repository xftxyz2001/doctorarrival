package com.xftxyz.doctorarrival.order.autoconfigure;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


// @Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayAutoConfiguration {

    @Bean
    public AlipayConfig alipayConfig(AlipayProperties alipayProperties) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(alipayProperties.getServerUrl());
        alipayConfig.setAppId(alipayProperties.getAppId());
        alipayConfig.setPrivateKey(alipayProperties.getPrivateKey());
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayProperties.getAlipayPublicKey());
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }

    @Bean
    public AlipayClient alipayClient(AlipayConfig alipayConfig) throws AlipayApiException {
        return new DefaultAlipayClient(alipayConfig);
    }
}
