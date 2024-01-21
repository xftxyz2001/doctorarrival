package com.xftxyz.mock.mockhospital.repository;

import com.xftxyz.mock.mockhospital.domain.Patient;

import java.util.List;
import java.util.function.Predicate;

public interface PatientRepository {

    // 添加
    void save(Patient patient);

    // 删除
    void delete(Long id);

    // 修改
    void update(Patient patient);

    // 查询（传入一个过滤器）
    List<Patient> query(Predicate<Patient> filter);
}
