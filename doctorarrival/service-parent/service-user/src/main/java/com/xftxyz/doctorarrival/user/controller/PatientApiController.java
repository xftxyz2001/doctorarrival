package com.xftxyz.doctorarrival.user.controller;

import com.xftxyz.doctorarrival.common.helper.JwtHelper;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.user.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {

    private final PatientService patientService;

    @GetMapping("/auth/list")
    public List<Patient> getPatientList(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return patientService.getPatientList(userId);
    }

    @GetMapping("/auth/detail/{patientId}")
    public Patient getPatientDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                    @PathVariable("patientId") String patientId) {
        return patientService.getPatientDetail(userId, patientId);
    }

    @DeleteMapping("/auth/remove/{patientId}")
    public Boolean removePatient(@RequestHeader(JwtHelper.X_USER_ID) String userId,
                                 @PathVariable("patientId") String patientId) {
        return patientService.removePatient(userId, patientId);
    }
}
