package com.xftxyz.doctorarrival.task.service.impl;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import com.xftxyz.doctorarrival.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void visitNotification() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_TASK, RabbitMQConfig.ROUTING_TASK_NOTIFICATION, new Date());
    }

    @Override
    public void updateOrderStatus() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_TASK, RabbitMQConfig.ROUTING_TASK_ORDER_STATUS, new Date());
    }
}
