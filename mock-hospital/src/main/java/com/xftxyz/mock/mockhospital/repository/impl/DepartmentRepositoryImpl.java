package com.xftxyz.mock.mockhospital.repository.impl;

import com.xftxyz.mock.mockhospital.domain.Department;
import com.xftxyz.mock.mockhospital.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private List<Department> departments = new ArrayList<>();

    @Override
    public void save(Department department) {
        departments.add(department);
    }

    @Override
    public void delete(String departmentCode) {
        departments.removeIf(department -> department.getDepartmentCode().equals(departmentCode));
    }

    @Override
    public void update(Department department) {
        delete(department.getDepartmentCode());
        save(department);
    }

    @Override
    public List<Department> query(Predicate<Department> filter) {
        return departments.stream().filter(filter).collect(Collectors.toList());
    }
}