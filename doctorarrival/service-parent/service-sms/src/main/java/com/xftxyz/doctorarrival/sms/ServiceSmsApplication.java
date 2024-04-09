package com.xftxyz.doctorarrival.sms;

import com.xftxyz.doctorarrival.config.LogAspect;
import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@Import({LogAspect.class, RabbitMQConfig.class})
public class ServiceSmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSmsApplication.class, args);
    }
}
