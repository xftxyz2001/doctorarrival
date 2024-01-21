package com.xftxyz.mock.mockhospital.config;

import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {


    @Bean
    public DoctorarrivalService doctorarrivalService(@Value("${doctorarrival.hospitalCode}") String hospitalCode,
                                                     @Value("${doctorarrival.privateKeyLocation}") String privateKeyLocation) {
        return DoctorarrivalServiceBuilder.build(hospitalCode, privateKeyLocation);
    }
}
