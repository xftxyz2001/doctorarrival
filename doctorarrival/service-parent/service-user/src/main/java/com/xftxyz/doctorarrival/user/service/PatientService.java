package com.xftxyz.doctorarrival.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.vo.user.PatientVO;

import java.util.List;

/**
 * @author 25810
 * @description 针对表【patient(病人信息表)】的数据库操作Service
 * @createDate 2024-01-03 23:44:16
 */
public interface PatientService extends IService<Patient> {

    List<PatientVO> getPatientList(String userId);

    PatientVO getPatientDetail(String userId, String patientId);

    Boolean removePatient(String userId, String patientId);

    Boolean addPatient(Long userId, Patient patient);

    Boolean updatePatient(Long userId, Patient patient);

    Patient getPatientDetailNoWarp(Long patientId);
}
