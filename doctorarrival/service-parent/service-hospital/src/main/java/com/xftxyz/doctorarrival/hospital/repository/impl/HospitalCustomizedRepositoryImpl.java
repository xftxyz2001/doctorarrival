package com.xftxyz.doctorarrival.hospital.repository.impl;

import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.hospital.repository.HospitalCustomizedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalCustomizedRepositoryImpl implements HospitalCustomizedRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Hospital> findByHospitalTypeAndProvinceCodeAndCityCodeAndDistrictCode(String hospitalType, String provinceCode, String cityCode, String districtCode) {
        Query query = new Query();
        // 通过医院类型、省市区查询医院（null表示不限制）
        if (StringUtils.hasText(hospitalType)) {
            query.addCriteria(Criteria.where("hospitalType").is(hospitalType));
        }
        if (StringUtils.hasText(provinceCode)) {
            query.addCriteria(Criteria.where("provinceCode").is(provinceCode));
        }
        if (StringUtils.hasText(cityCode)) {
            query.addCriteria(Criteria.where("cityCode").is(cityCode));
        }
        if (StringUtils.hasText(districtCode)) {
            query.addCriteria(Criteria.where("districtCode").is(districtCode));
        }
        return mongoTemplate.find(query, Hospital.class);
    }
}
