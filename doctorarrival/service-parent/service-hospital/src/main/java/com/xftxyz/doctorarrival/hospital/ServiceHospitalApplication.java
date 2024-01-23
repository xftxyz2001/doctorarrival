package com.xftxyz.doctorarrival.hospital;

import com.xftxyz.doctorarrival.config.MybatisPlusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@EnableFeignClients(basePackages = "com.xftxyz.doctorarrival")
@Import(MybatisPlusConfig.class)
public class ServiceHospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospitalApplication.class, args);
    }
}
