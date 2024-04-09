package com.xftxyz.doctorarrival.task;

import com.xftxyz.doctorarrival.config.LogAspect;
import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.xftxyz.doctorarrival")
@EnableScheduling // 开启定时任务
@Import({LogAspect.class, RabbitMQConfig.class})
public class ServiceTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTaskApplication.class, args);
    }
}
