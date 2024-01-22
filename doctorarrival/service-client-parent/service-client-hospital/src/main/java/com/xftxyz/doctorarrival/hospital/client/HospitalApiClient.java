package com.xftxyz.doctorarrival.hospital.client;

import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-hospital", path = "/api/hospital/find", contextId = "hospitalFind")
@Repository
public interface HospitalApiClient {

    @GetMapping("/rule/{hospitalCode}")
    BookingRule getBookingRuleInner(@PathVariable("hospitalCode") String hospitalCode);
}
