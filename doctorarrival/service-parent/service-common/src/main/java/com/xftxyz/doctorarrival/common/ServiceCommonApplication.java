package com.xftxyz.doctorarrival.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
public class ServiceCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCommonApplication.class, args);
    }
}
