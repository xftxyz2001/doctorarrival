package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalRepository extends MongoRepository<Hospital, String> {
    // 根据医院编号查询医院
    Hospital findByHospitalCode(String hospitalCode);

    // 根据医院名称查询医院（模糊查询）
    List<Hospital> findByHospitalNameLike(String hospitalName);

    // 根据医院类型和省市区查询医院
    @Query("select h from Hospital h where "
            + "(?1 is null or h.hospitalType = ?1) and "
            + "(?2 is null or h.provinceCode = ?2) and "
            + "(?3 is null or h.cityCode = ?3) and "
            + "(?4 is null or h.districtCode = ?4)")
    List<Hospital> findByHospitalTypeAndProvinceCodeAndCityCodeAndDistrictCode(String hospitalType, String provinceCode,
                                                                               String cityCode, String districtCode);
}
