package com.xftxyz.doctorarrival.oss;

import com.xftxyz.doctorarrival.config.LogAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@Import(LogAspect.class)
public class ServiceOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }
}
