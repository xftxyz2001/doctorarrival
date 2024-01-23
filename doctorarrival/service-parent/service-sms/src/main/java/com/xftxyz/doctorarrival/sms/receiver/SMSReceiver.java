package com.xftxyz.doctorarrival.sms.receiver;

import com.xftxyz.doctorarrival.config.RabbitMQConfig;
import com.xftxyz.doctorarrival.sms.service.SmsService;
import com.xftxyz.doctorarrival.vo.sms.NotificationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SMSReceiver {

    private final SmsService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_SMS, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.EXCHANGE_DIRECT_SMS),
            key = RabbitMQConfig.ROUTING_SMS)
    )
    public void sendAppointmentReminderListener(NotificationVO notificationVO) {
        smsService.sendAppointmentReminder(notificationVO);
    }
}
