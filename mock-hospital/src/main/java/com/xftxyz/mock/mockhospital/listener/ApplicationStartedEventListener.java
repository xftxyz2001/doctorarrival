package com.xftxyz.mock.mockhospital.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.sdk.api.*;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.mock.mockhospital.domain.Department;
import com.xftxyz.mock.mockhospital.domain.Schedule;
import com.xftxyz.mock.mockhospital.repository.DepartmentRepository;
import com.xftxyz.mock.mockhospital.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * 初始化Mock数据
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final DoctorarrivalService doctorarrivalService;

    private final DepartmentRepository departmentRepository;
    private final ScheduleRepository scheduleRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("init...");
        long startTime = System.nanoTime();

        File hospitalConfigFile = new File("config/hospital.json");
        if (!hospitalConfigFile.exists()) {
            log.info("config/hospital.json not found, skip init");
            return;
        }
        try {
            UpdateHospitalRequest updateHospitalRequest = objectMapper.readValue(hospitalConfigFile, UpdateHospitalRequest.class);
            log.info("update hospital");
            doctorarrivalService.updateHospital(updateHospitalRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File departmentsConfigFile = new File("config/departments.json");
        if (!departmentsConfigFile.exists()) {
            log.info("config/departments.json not found, skip init");
            return;
        }
        try {
            List<Department> departments = objectMapper.readValue(departmentsConfigFile, new TypeReference<>() {
            });
            BatchUpdateDepartmentRequest batchUpdateDepartmentRequest = new BatchUpdateDepartmentRequest();
            for (Department department : departments) {
                UpdateDepartmentRequest updateDepartmentRequest = new UpdateDepartmentRequest();
                updateDepartmentRequest.setDepartmentCode(department.getDepartmentCode());
                updateDepartmentRequest.setDepartmentName(department.getDepartmentName());
                updateDepartmentRequest.setIntro(department.getIntro());
                updateDepartmentRequest.setPrimaryDepartmentCode(department.getPrimaryDepartmentCode());
                updateDepartmentRequest.setPrimaryDepartmentName(department.getPrimaryDepartmentName());
                batchUpdateDepartmentRequest.add(updateDepartmentRequest);

                departmentRepository.save(department);
            }
            log.info("update departments");
            doctorarrivalService.updateDepartments(batchUpdateDepartmentRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File schedulesConfigFile = new File("config/schedules.json");
        if (!schedulesConfigFile.exists()) {
            log.info("config/schedules.json not found, skip init");
            return;
        }
        try {
            List<Schedule> schedules = objectMapper.readValue(schedulesConfigFile, new TypeReference<>() {
            });
            BatchUpdateScheduleRequest batchUpdateScheduleRequest = new BatchUpdateScheduleRequest();
            for (Schedule schedule : schedules) {
                UpdateScheduleRequest updateScheduleRequest = new UpdateScheduleRequest();
                updateScheduleRequest.setDepartmentCode(schedule.getDepartmentCode());
                updateScheduleRequest.setDoctorTitle(schedule.getDoctorTitle());
                updateScheduleRequest.setDoctorName(schedule.getDoctorName());
                updateScheduleRequest.setSkill(schedule.getSkill());
                updateScheduleRequest.setWorkDate(schedule.getWorkDate());
                updateScheduleRequest.setWorkTime(schedule.getWorkTime());
                updateScheduleRequest.setReservedNumber(schedule.getReservedNumber());
                updateScheduleRequest.setAvailableNumber(schedule.getAvailableNumber());
                updateScheduleRequest.setAmount(schedule.getAmount());
                updateScheduleRequest.setStatus(schedule.getStatus());
                updateScheduleRequest.setHospitalScheduleId(schedule.getId());
                batchUpdateScheduleRequest.add(updateScheduleRequest);

                scheduleRepository.save(schedule);
            }
            log.info("update schedules");
            doctorarrivalService.updateSchedules(batchUpdateScheduleRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
        log.info("done in {}ms", timeTakenToStartup.toMillis());
    }
}
