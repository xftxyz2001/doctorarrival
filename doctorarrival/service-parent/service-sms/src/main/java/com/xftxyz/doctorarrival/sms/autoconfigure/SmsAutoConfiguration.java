package com.xftxyz.doctorarrival.sms.autoconfigure;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.xftxyz.doctorarrival.sms.service.SmsService;
import com.xftxyz.doctorarrival.sms.service.impl.SmsServiceConsoleImpl;
import com.xftxyz.doctorarrival.sms.service.impl.SmsServiceImpl;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "aliyun.sms")
    public StaticCredentialProvider staticCredentialProvider(SmsProperties smsProperties) {
        return StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(smsProperties.getAccessKeyId())
                .accessKeySecret(smsProperties.getAccessKeySecret())
                .build());
    }

    @Bean
    @ConditionalOnBean(StaticCredentialProvider.class)
    public AsyncClient asyncClient(StaticCredentialProvider staticCredentialProvider, SmsProperties smsProperties) {
        return AsyncClient.builder().region(smsProperties.getRegion())
                .credentialsProvider(staticCredentialProvider)
                .overrideConfiguration(ClientOverrideConfiguration.create()
                        .setEndpointOverride(smsProperties.getEndpoint()))
                .build();
    }

    @Bean
    @ConditionalOnBean(AsyncClient.class)
    public SmsService smsServiceImpl(StringRedisTemplate stringRedisTemplate, AsyncClient asyncClient, SmsProperties smsProperties) {
        return new SmsServiceImpl(stringRedisTemplate, asyncClient, smsProperties);
    }

    @Bean
    @ConditionalOnMissingBean(AsyncClient.class)
    public SmsService smsServiceConsoleImpl(StringRedisTemplate stringRedisTemplate) {
        return new SmsServiceConsoleImpl(stringRedisTemplate);
    }

}
