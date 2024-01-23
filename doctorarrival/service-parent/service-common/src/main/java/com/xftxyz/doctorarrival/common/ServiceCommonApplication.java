package com.xftxyz.doctorarrival.common;

import com.xftxyz.doctorarrival.config.MybatisPlusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@Import(MybatisPlusConfig.class)
public class ServiceCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCommonApplication.class, args);
    }
}
