package com.xftxyz.doctorarrival.user.client;

import com.xftxyz.doctorarrival.domain.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user", path = "/api/user/patient", contextId = "userPatient")
@Repository
public interface PatientApiClient {
    @GetMapping("/inner/detail/{patientId}")
    Patient getPatientDetailInner(@PathVariable("patientId") Long patientId);
}
