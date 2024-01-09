package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.service.HospitalService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> findHospitalByHospitalName(String hospitalName) {
        return hospitalRepository.findByHospitalNameLike(hospitalName);
    }

    @Override
    public Hospital findHospitalByHospitalCode(String hospitalCode) {
        return hospitalRepository.findByHospitalCode(hospitalCode);
    }

    @Override
    public IPage<Hospital> findHospitalPage(HospitalQueryVO hospitalQueryVO, Long current, Long size) {
        List<Hospital> hospitalList = hospitalRepository.findByHospitalTypeAndProvinceCodeAndCityCodeAndDistrictCode(
                hospitalQueryVO.getHospitalType(), hospitalQueryVO.getProvinceCode(), hospitalQueryVO.getCityCode(),
                hospitalQueryVO.getDistrictCode());
        Page<Hospital> hospitalPage = new Page<>(current, size, hospitalList.size());
        int start = (int) ((current - 1) * size);
        List<Hospital> list = hospitalList.stream().skip(start).limit(size).toList();
        return hospitalPage.setRecords(list);
    }
}
