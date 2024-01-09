package com.xftxyz.doctorarrival.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;

import java.util.List;

public interface HospitalService {
    List<Hospital> findHospitalByHospitalName(String hospitalName);

    Hospital findHospitalByHospitalCode(String hospitalCode);

    IPage<Hospital> findHospitalPage(HospitalQueryVO hospitalQueryVO, Long current, Long size);
}
