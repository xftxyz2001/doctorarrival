package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.repository.ScheduleRepository;
import com.xftxyz.doctorarrival.hospital.service.ScheduleService;
import com.xftxyz.doctorarrival.vo.hospital.ScheduleDateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.xftxyz.doctorarrival.common.constant.Constants.DAY_IN_MILLIS;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final HospitalRepository hospitalRepository;

    @Override
    public List<Schedule> findScheduleByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode) {
        return scheduleRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
    }

    @Override
    public IPage<ScheduleDateVO> findSchedulePage(String hospitalCode, String departmentCode, Long current, Long size) {
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
        List<ScheduleDateVO> scheduleDateVOList = groupedSchedule.entrySet().stream().filter(entry -> {
            Date workDate = entry.getKey();
            return workDate.after(startDate) && workDate.before(endDate);
        }).map(entry -> {
            Date workDate = entry.getKey();
            String dayOfWeek = DateTimeHelper.getDayOfWeek(workDate);

            List<Schedule> scheduleList = entry.getValue();
            Integer reservedNumber = scheduleList.stream().mapToInt(Schedule::getReservedNumber).sum();
            Integer availableNumber = scheduleList.stream().mapToInt(Schedule::getAvailableNumber).sum();
            Integer status = scheduleList.stream().mapToInt(Schedule::getStatus).max().getAsInt();

            ScheduleDateVO scheduleDateVO = new ScheduleDateVO();
            scheduleDateVO.setHospitalCode(hospitalCode);
            scheduleDateVO.setDepartmentCode(departmentCode);
            scheduleDateVO.setWorkDate(workDate);
            scheduleDateVO.setDayOfWeek(dayOfWeek);
            scheduleDateVO.setReservedNumber(reservedNumber);
            scheduleDateVO.setAvailableNumber(availableNumber);
            scheduleDateVO.setStatus(status);

            return scheduleDateVO;
        }).sorted(Comparator.comparing(ScheduleDateVO::getWorkDate)).toList();

        Page<ScheduleDateVO> scheduleVOPage = new Page<>(current, size, groupedSchedule.size());
        return scheduleVOPage.setRecords(scheduleDateVOList);
    }

    @Override
    public List<Schedule> findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(String hospitalCode, String departmentCode, String workDate) {
        Date workDateDate = DateTimeHelper.parseDateByDash(workDate);
        Date workDateDateEnd = new Date(workDateDate.getTime() + DAY_IN_MILLIS);

        return scheduleRepository.findByHospitalCodeAndDepartmentCodeAndWorkDateBetween(hospitalCode, departmentCode, workDateDate, workDateDateEnd);
    }

    @Override
    public Schedule findScheduleById(String id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty()) {
            throw new BusinessException(ResultEnum.SCHEDULE_NOT_FOUND);
        }
        return scheduleOptional.get();
    }

}
