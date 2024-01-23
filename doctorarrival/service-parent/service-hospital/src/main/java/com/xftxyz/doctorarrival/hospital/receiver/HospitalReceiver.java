package com.xftxyz.doctorarrival.hospital.receiver;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalReceiver {

    private final HospitalSideService hospitalSideService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_DIRECT_ORDER),
            key = RabbitMQConfig.ROUTING_ORDER)
    )
    public void orderSubmitOrUpdateListener(OrderInfo orderInfo) {
        if (OrderInfo.ORDER_STATUS_UNPAID.equals(orderInfo.getOrderStatus())) {
            hospitalSideService.submitOrder(orderInfo);
        } else {
            hospitalSideService.updateOrder(orderInfo);
        }
    }
}
