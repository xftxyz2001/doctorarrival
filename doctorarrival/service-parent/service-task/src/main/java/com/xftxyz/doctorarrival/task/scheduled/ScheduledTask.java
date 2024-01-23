package com.xftxyz.doctorarrival.task.scheduled;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final RabbitTemplate rabbitTemplate;

    // 生成cron表达式 https://cron.qqe2.com/

    /**
     * 就诊通知
     */
    @Scheduled(cron = "0 0 8 * * ?") // 每天8点执行一次
    public void visitNotification() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_TASK, RabbitMQConfig.ROUTING_TASK_NOTIFICATION, new Date());
    }

    /**
     * 更新订单状态
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟执行一次
    public void updateOrderStatus() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_TASK, RabbitMQConfig.ROUTING_TASK_ORDER_STATUS, new Date());
    }

    /**
     * TODO 更新医院、科室、排班信息
     */

}
