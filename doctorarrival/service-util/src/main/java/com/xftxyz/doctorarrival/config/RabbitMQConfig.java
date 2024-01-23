package com.xftxyz.doctorarrival.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

// @Configuration
public class RabbitMQConfig {

    /**
     * 预约下单/订单状态变更
     */
    public static final String EXCHANGE_DIRECT_ORDER = "exchange.direct.order";
    public static final String ROUTING_ORDER = "order";
    public static final String QUEUE_ORDER = "queue.order";

    /**
     * 短信
     */
    public static final String EXCHANGE_DIRECT_SMS = "exchange.direct.sms";
    public static final String ROUTING_SMS = "sms";
    public static final String QUEUE_SMS = "queue.sms";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "exchange.direct.task";
    public static final String ROUTING_TASK_NOTIFICATION = "task.notification";
    public static final String QUEUE_TASK_NOTIFICATION = "queue.task.notification";

    // 交换机
    @Bean
    public DirectExchange exchangeDirectOrder() {
        return new DirectExchange(EXCHANGE_DIRECT_ORDER, true, false);
    }

    @Bean
    public DirectExchange exchangeDirectSms() {
        return new DirectExchange(EXCHANGE_DIRECT_SMS, true, false);
    }

    @Bean
    public DirectExchange exchangeDirectTask() {
        return new DirectExchange(EXCHANGE_DIRECT_TASK, true, false);
    }

    // Converter
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

