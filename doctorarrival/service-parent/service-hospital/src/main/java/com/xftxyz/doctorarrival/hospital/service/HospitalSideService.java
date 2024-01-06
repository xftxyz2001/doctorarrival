package com.xftxyz.doctorarrival.hospital.service;

import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import org.springframework.core.io.Resource;

import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

public interface HospitalSideService {
    Resource join(HospitalJoinVO hospitalJoinVO);

    Boolean updateHospital(EncryptionRequest encryptionRequest);
}
