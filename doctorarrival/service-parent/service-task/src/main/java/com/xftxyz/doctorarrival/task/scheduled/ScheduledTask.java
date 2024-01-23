package com.xftxyz.doctorarrival.task.scheduled;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 就诊通知
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void visitNotification() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_TASK, RabbitMQConfig.ROUTING_TASK_NOTIFICATION, new Date());
    }

    /**
     * TODO 更新医院、科室、排班信息
     */

}
