package com.xftxyz.mock.mockhospital.listener;
import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest.BookingRule;

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
        try {
            log.info("init...");
            long startTime = System.nanoTime();

            UpdateHospitalRequest updateHospitalRequest = objectMapper.readValue(new File("hospital.json"), UpdateHospitalRequest.class);
            log.info("update hospital");
            doctorarrivalService.updateHospital(updateHospitalRequest);

            List<Department> departments = objectMapper.readValue(new File("departments.json"), new TypeReference<>() {
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

            List<Schedule> schedules = objectMapper.readValue(new File("schedules.json"), new TypeReference<>() {
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
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
            log.info("done in {}ms", timeTakenToStartup.toMillis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
