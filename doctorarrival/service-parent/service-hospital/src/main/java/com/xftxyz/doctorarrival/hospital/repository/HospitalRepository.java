package com.xftxyz.doctorarrival.hospital.repository;

import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HospitalRepository extends MongoRepository<Hospital, String>, HospitalCustomizedRepository {
    // 根据医院编号查询医院
    Hospital findByHospitalCode(String hospitalCode);

    // 根据医院名称查询医院（模糊查询）
    List<Hospital> findByHospitalNameLike(String hospitalName);

}
