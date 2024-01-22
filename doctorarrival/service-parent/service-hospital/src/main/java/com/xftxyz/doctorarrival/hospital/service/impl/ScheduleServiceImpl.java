package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.domain.hospital.Department;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.hospital.repository.DepartmentRepository;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.repository.ScheduleRepository;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.xftxyz.doctorarrival.constant.Constants.DAY_IN_MILLIS;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final DepartmentRepository departmentRepository;

    private final HospitalRepository hospitalRepository;

    @Override
    public List<Schedule> findScheduleByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode) {
        return scheduleRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }

    @Override
    public IPage<ScheduleVO> findSchedulePage(String hospitalCode, String departmentCode, Long current, Long size) {
        // 创建今天开始的Date（不包含时分秒）
        Date today = DateTimeHelper.getTodayStartDate();
        // 查询预约周期
        Integer cycle = hospitalRepository.findByHospitalCode(hospitalCode).getBookingRule().getCycle();
        // 创建cycle天后的Date
        Date cycleEndDate = new Date(today.getTime() + cycle * DAY_IN_MILLIS);

        // 分页用的开始和结束日期
        Date startDate = new Date(today.getTime() + (current - 1) * size * DAY_IN_MILLIS);
        Date endDate = new Date(startDate.getTime() + size * DAY_IN_MILLIS);

        // 查询排班
        List<Schedule> scheduleListAll = scheduleRepository.findByHospitalCodeAndDepartmentCodeAndWorkDateBetween(hospitalCode, departmentCode, today, cycleEndDate);
        // 按照日期分组
        Map<Date, List<Schedule>> groupedSchedule = scheduleListAll.stream().collect(Collectors.groupingBy(Schedule::getWorkDate));
        // 过滤出在分页范围内的排班
        List<ScheduleVO> scheduleDateVOList = groupedSchedule.entrySet().stream().filter(entry -> {
            Date workDate = entry.getKey();
            return workDate.compareTo(startDate) >= 0 && workDate.compareTo(endDate) < 0;
        }).map(entry -> {
            Date workDate = entry.getKey();
            String dayOfWeek = DateTimeHelper.getDayOfWeek(workDate);

            List<Schedule> scheduleList = entry.getValue();
            Integer reservedNumber = scheduleList.stream().mapToInt(Schedule::getReservedNumber).sum();
            Integer availableNumber = scheduleList.stream().mapToInt(Schedule::getAvailableNumber).sum();
            Integer status = scheduleList.stream().mapToInt(Schedule::getStatus).max().getAsInt();

            ScheduleVO scheduleDateVO = new ScheduleVO();
            scheduleDateVO.setHospitalCode(hospitalCode);
            scheduleDateVO.setDepartmentCode(departmentCode);
            scheduleDateVO.setWorkDate(workDate);
            scheduleDateVO.setDayOfWeek(dayOfWeek);
            scheduleDateVO.setReservedNumber(reservedNumber);
            scheduleDateVO.setAvailableNumber(availableNumber);
            scheduleDateVO.setStatus(status);

            return scheduleDateVO;
        }).sorted(Comparator.comparing(ScheduleVO::getWorkDate)).toList();

        Page<ScheduleVO> scheduleVOPage = new Page<>(current, size, groupedSchedule.size());
        return scheduleVOPage.setRecords(scheduleDateVOList);
    }

    @Override
    public List<Schedule> findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(String hospitalCode, String departmentCode, String workDate) {
        Date workDateDate = DateTimeHelper.parseDateByDash(workDate);
        Date workDateDateEnd = new Date(workDateDate.getTime() + DAY_IN_MILLIS);

        return scheduleRepository.findByHospitalCodeAndDepartmentCodeAndWorkDateBetween(hospitalCode, departmentCode, workDateDate, workDateDateEnd);
    }

    @Override
    public ScheduleVO findScheduleById(String id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty()) {
            throw new BusinessException(ResultEnum.SCHEDULE_NOT_FOUND);
        }
        return warptoScheduleVO(scheduleOptional.get());
    }

    private ScheduleVO warptoScheduleVO(Schedule schedule) {
        ScheduleVO scheduleVO = new ScheduleVO();
        String hospitalCode = schedule.getHospitalCode();
        String departmentCode = schedule.getDepartmentCode();
        scheduleVO.setHospitalCode(hospitalCode);
        scheduleVO.setDepartmentCode(departmentCode);
        Hospital hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        scheduleVO.setHospitalName(hospital.getHospitalName());
        Department department = departmentRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
        scheduleVO.setDepartmentName(department.getDepartmentName());

        scheduleVO.setDoctorTitle(schedule.getDoctorTitle());
        scheduleVO.setDoctorName(schedule.getDoctorName());
        scheduleVO.setSkill(schedule.getSkill());
        scheduleVO.setWorkDate(schedule.getWorkDate());
        scheduleVO.setDayOfWeek(DateTimeHelper.getDayOfWeek(schedule.getWorkDate()));
        scheduleVO.setWorkTime(schedule.getWorkTime());
        scheduleVO.setReservedNumber(schedule.getReservedNumber());
        scheduleVO.setAvailableNumber(schedule.getAvailableNumber());
        scheduleVO.setAmount(schedule.getAmount());
        scheduleVO.setStatus(schedule.getStatus());
        scheduleVO.setHospitalScheduleId(schedule.getId());

        return scheduleVO;
    }

    @Override
    public ScheduleVO findScheduleByIdNoWarp(String id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        return scheduleOptional.map(this::warptoScheduleVO).orElse(null);
    }

}
