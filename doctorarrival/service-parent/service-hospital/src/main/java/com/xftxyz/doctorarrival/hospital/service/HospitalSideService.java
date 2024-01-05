package com.xftxyz.doctorarrival.hospital.service;

import org.springframework.core.io.Resource;

import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

public interface HospitalSideService {
    Resource join(HospitalJoinVO hospitalJoinVO);
}
