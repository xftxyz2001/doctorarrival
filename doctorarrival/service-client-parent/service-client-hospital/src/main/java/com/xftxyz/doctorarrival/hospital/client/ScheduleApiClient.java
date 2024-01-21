package com.xftxyz.doctorarrival.hospital.client;

import com.xftxyz.doctorarrival.vo.hospital.ScheduleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-hospital", path = "/api/hospital/schedule")
@Repository
public interface ScheduleApiClient {

    @GetMapping("/inner/id/{id}")
    ScheduleVO getScheduleByIdInner(@PathVariable("id") String id);
}
