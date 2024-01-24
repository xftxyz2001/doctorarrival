package com.xftxyz.doctorarrival.hospital.client;

import com.xftxyz.doctorarrival.vo.hospital.HospitalStatisticVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-hospital", path = "/admin/hospital/set", contextId = "hospitalSet")
@Repository
public interface HospitalSetAdminClient {

    @PostMapping("/inner/statistic")
    HospitalStatisticVO statistic(@RequestBody HospitalStatisticVO hospitalStatisticVO);
}
