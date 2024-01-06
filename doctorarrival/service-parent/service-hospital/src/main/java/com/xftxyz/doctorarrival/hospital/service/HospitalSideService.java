package com.xftxyz.doctorarrival.hospital.service;

import org.springframework.core.io.Resource;

import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

public interface HospitalSideService {
    Resource join(HospitalJoinVO hospitalJoinVO);

    String updateHospital(EncryptionRequest encryptionRequest);

    String updateSecretKey(String hospitalCode);
}
