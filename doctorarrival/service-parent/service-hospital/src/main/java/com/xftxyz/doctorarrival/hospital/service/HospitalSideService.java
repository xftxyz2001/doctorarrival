package com.xftxyz.doctorarrival.hospital.service;

import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import org.springframework.core.io.Resource;

public interface HospitalSideService {
    Resource join(HospitalJoinVO hospitalJoinVO);

    String updateSecretKey(String hospitalCode);

    String updateHospital(EncryptionRequest encryptionRequest);

    String updateDepartment(EncryptionRequest encryptionRequest);

    String updateSchedule(EncryptionRequest encryptionRequest);

    String deleteDepartment(EncryptionRequest encryptionRequest);

    String deleteSchedule(EncryptionRequest encryptionRequest);

    String updateDepartments(EncryptionRequest encryptionRequest);

    String updateSchedules(EncryptionRequest encryptionRequest);

    String deleteDepartments(EncryptionRequest encryptionRequest);

    String deleteSchedules(EncryptionRequest encryptionRequest);
}
