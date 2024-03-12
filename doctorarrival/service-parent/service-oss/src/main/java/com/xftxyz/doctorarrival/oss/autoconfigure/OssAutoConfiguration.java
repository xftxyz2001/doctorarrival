package com.xftxyz.doctorarrival.oss.autoconfigure;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.oss.service.impl.FileServiceImpl;
import com.xftxyz.doctorarrival.oss.service.impl.FileServiceLocalImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "aliyun.oss", value = "endpoint")
    public OSSClientBuilder ossClientBuilder() {
        return new OSSClientBuilder();
    }

    @Bean
    @ConditionalOnBean(OSSClientBuilder.class)
    public OSS ossClient(OSSClientBuilder ossClientBuilder, OssProperties ossProperties) {
        return ossClientBuilder.build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    @Bean
    @ConditionalOnBean(OSS.class)
    public FileService fileServiceImpl(OSS ossClient, OssProperties ossProperties) {
        return new FileServiceImpl(ossClient, ossProperties);
    }

    @Bean
    @ConditionalOnMissingBean(OSS.class)
    public FileService fileServiceLocalImpl() {
        return new FileServiceLocalImpl();
    }

}
