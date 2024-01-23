package com.xftxyz.doctorarrival.hospital.client;

import com.xftxyz.doctorarrival.domain.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// 使用消息队列rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_DIRECT_ORDER, RabbitMQConfig.ROUTING_ORDER, orderInfo);
@Deprecated
@FeignClient(value = "service-hospital", path = "/api/hospital/side", contextId = "hospitalSide")
@Repository
public interface HospitalSideApiClient {

    @PostMapping("/order")
    Boolean submitOrderInner(@RequestBody OrderInfo orderInfo);

    @PostMapping("/order/status")
    Boolean updateOrderInner(@RequestBody OrderInfo orderInfo);
}
