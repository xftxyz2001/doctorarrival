package com.xftxyz.doctorarrival.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival", exclude = DataSourceAutoConfiguration.class)
public class ServiceSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
