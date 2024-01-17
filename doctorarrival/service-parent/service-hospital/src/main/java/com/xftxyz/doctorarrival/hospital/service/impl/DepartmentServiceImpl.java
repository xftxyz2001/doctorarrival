package com.xftxyz.doctorarrival.hospital.service.impl;

import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.hospital.repository.DepartmentRepository;
import com.xftxyz.doctorarrival.hospital.service.DepartmentService;
import com.xftxyz.doctorarrival.vo.hospital.DepartmentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentVO> findDepartmentByHospitalCode(String hospitalCode) {
        List<Department> departmentList = departmentRepository.findByHospitalCode(hospitalCode);
        return wrapDepartmentList(departmentList);
    }

    @Override
    public Department findDepartmentByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode) {
        return departmentRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }

    private List<DepartmentVO> wrapDepartmentList(List<Department> departmentList) {
        // 分组
        Map<String, List<DepartmentVO>> groupedDepartments = departmentList.stream()
                .map(department -> {
                    DepartmentVO departmentVO = new DepartmentVO();
                    departmentVO.setHospitalCode(department.getHospitalCode());
                    departmentVO.setDepartmentCode(department.getDepartmentCode());
                    departmentVO.setDepartmentName(department.getDepartmentName());
                    departmentVO.setIntro(department.getIntro());
                    departmentVO.setPrimaryDepartmentCode(department.getPrimaryDepartmentCode());
                    departmentVO.setPrimaryDepartmentName(department.getPrimaryDepartmentName());
                    // departmentVO.setChildren();
                    return departmentVO;
                })
                .collect(Collectors.groupingBy(DepartmentVO::getPrimaryDepartmentCode));

        // 封装
        List<DepartmentVO> result = new ArrayList<>();
        for (Map.Entry<String, List<DepartmentVO>> entry : groupedDepartments.entrySet()) {
            String primaryDepartmentCode = entry.getKey();
            List<DepartmentVO> children = entry.getValue();
            String primaryDepartmentName = children.get(0).getPrimaryDepartmentName();

            DepartmentVO parent = new DepartmentVO();
            parent.setDepartmentCode(primaryDepartmentCode);
            parent.setDepartmentName(primaryDepartmentName);
            parent.setChildren(children);
            result.add(parent);
        }

        return result;
    }
}
