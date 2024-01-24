package com.xftxyz.doctorarrival.order.client;

import com.xftxyz.doctorarrival.vo.order.OrderStatisticVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-order", path = "/admin/order/info", contextId = "orderInfoAdmin")
@Repository
public interface OrderInfoAdminClient {
    @PostMapping("/inner/statistic")
    OrderStatisticVO statistic(@RequestBody OrderStatisticVO orderStatisticVO);
}
