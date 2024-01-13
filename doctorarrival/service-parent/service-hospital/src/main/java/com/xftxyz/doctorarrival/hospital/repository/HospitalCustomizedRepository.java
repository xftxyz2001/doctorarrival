package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Hospital;

import java.util.List;

public interface HospitalCustomizedRepository {
    List<Hospital> findByHospitalTypeAndProvinceCodeAndCityCodeAndDistrictCode(String hospitalType, String provinceCode, String cityCode, String districtCode);
}
