package com.xftxyz.doctorarrival.hospital.service;

import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface HospitalSideService {
    Resource join(HospitalJoinVO hospitalJoinVO);
}
