package com.xftxyz.doctorarrival.statistics;

import com.xftxyz.doctorarrival.config.LogAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@EnableFeignClients(basePackages = "com.xftxyz.doctorarrival")
@Import(LogAspect.class)
public class ServiceStatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceStatisticsApplication.class, args);
    }
}
