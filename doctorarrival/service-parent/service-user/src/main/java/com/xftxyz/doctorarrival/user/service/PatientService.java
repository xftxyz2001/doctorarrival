package com.xftxyz.doctorarrival.user.service;

import com.xftxyz.doctorarrival.domain.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 25810
* @description 针对表【patient(病人信息表)】的数据库操作Service
* @createDate 2024-01-03 23:44:16
*/
public interface PatientService extends IService<Patient> {

    List<Patient> getPatientList(String userId);

    Patient getPatientDetail(String userId, String patientId);

    Boolean removePatient(String userId, String patientId);
}
