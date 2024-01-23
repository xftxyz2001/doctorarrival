package com.xftxyz.doctorarrival.order.receiver;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import com.xftxyz.doctorarrival.order.service.OrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class OrderReceiver {

    private final OrderInfoService orderInfoService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_TASK_NOTIFICATION, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_DIRECT_TASK),
            key = RabbitMQConfig.ROUTING_TASK_NOTIFICATION)
    )
    public void visitNotificationListener(Date date) {
        orderInfoService.visitNotification();
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_TASK_ORDER_STATUS, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_DIRECT_TASK),
            key = RabbitMQConfig.ROUTING_TASK_ORDER_STATUS)
    )
    public void updateOrderStatusListener(Date date) {
        orderInfoService.updateOrderStatus();
    }
}
