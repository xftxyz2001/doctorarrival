package com.xftxyz.mock.mockhospital.repository;

import com.xftxyz.mock.mockhospital.domain.Department;

import java.util.List;
import java.util.function.Predicate;

public interface DepartmentRepository {

    // 添加
    void save(Department department);

    // 删除
    void delete(String departmentCode);

    // 修改
    void update(Department department);

    // 查询（传入一个过滤器）
    List<Department> query(Predicate<Department> filter);
}
