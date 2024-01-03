package com.xftxyz.doctorarrival.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
public class ServiceHospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospitalApplication.class, args);
    }
}
