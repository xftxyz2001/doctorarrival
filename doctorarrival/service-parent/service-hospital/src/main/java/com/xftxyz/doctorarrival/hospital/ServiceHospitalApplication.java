package com.xftxyz.doctorarrival.hospital;

import com.xftxyz.doctorarrival.config.LogAspect;
import com.xftxyz.doctorarrival.config.MybatisPlusConfig;
import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@EnableFeignClients(basePackages = "com.xftxyz.doctorarrival")
@Import({LogAspect.class, MybatisPlusConfig.class, RabbitMQConfig.class})
public class ServiceHospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospitalApplication.class, args);
    }
}
