package com.xftxyz.doctorarrival.hospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;
import com.xftxyz.doctorarrival.vo.hospital.HospitalVO;

import java.util.List;

public interface HospitalService {
    List<HospitalVO> findHospitalByHospitalName(String hospitalName);

    HospitalVO findHospitalByHospitalCode(String hospitalCode);

    IPage<HospitalVO> findHospitalPage(HospitalQueryVO hospitalQueryVO, Long current, Long size);
}
