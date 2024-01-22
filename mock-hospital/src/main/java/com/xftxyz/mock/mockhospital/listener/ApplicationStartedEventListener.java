package com.xftxyz.mock.mockhospital.listener;

import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.sdk.api.BatchUpdateDepartmentRequest;
import com.xftxyz.doctorarrival.sdk.api.BatchUpdateScheduleRequest;
import com.xftxyz.doctorarrival.sdk.api.UpdateDepartmentRequest;
import com.xftxyz.doctorarrival.sdk.api.UpdateScheduleRequest;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.mock.mockhospital.domain.Department;
import com.xftxyz.mock.mockhospital.domain.Schedule;
import com.xftxyz.mock.mockhospital.repository.DepartmentRepository;
import com.xftxyz.mock.mockhospital.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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

    private final Random random = new Random();

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        int primaryDepartmentCount = 5;
        int departmentCount = 10;
        int scheduleCount = 20;

        log.info("init...");
        // 大科室
        for (int i = 0; i < primaryDepartmentCount; i++) {
            String primaryDepartmentCode = UUID.randomUUID().toString();
            String primaryDepartmentName = "科室-" + i;
            // 小科室
            BatchUpdateDepartmentRequest batchUpdateDepartmentRequest = new BatchUpdateDepartmentRequest();
            // BatchUpdateScheduleRequest batchUpdateScheduleRequest = new BatchUpdateScheduleRequest();
            for (int j = 0; j < departmentCount; j++) {
                String departmentCode = UUID.randomUUID().toString();
                String departmentName = primaryDepartmentName + "-" + j;
                String intro = "科室介绍-" + departmentName;

                Department department = new Department();
                department.setDepartmentCode(departmentCode);
                department.setDepartmentName(departmentName);
                department.setIntro(intro);
                department.setPrimaryDepartmentCode(primaryDepartmentCode);
                department.setPrimaryDepartmentName(primaryDepartmentName);
                departmentRepository.save(department);

                UpdateDepartmentRequest updateDepartmentRequest = new UpdateDepartmentRequest();
                updateDepartmentRequest.setDepartmentCode(departmentCode);
                updateDepartmentRequest.setDepartmentName(departmentName);
                updateDepartmentRequest.setIntro(intro);
                updateDepartmentRequest.setPrimaryDepartmentCode(primaryDepartmentCode);
                updateDepartmentRequest.setPrimaryDepartmentName(primaryDepartmentName);
                batchUpdateDepartmentRequest.add(updateDepartmentRequest);

                // 排班
                Date workDate = DateTimeHelper.getTodayStartDate();
                Integer workTime = 0;
                for (int k = 0; k < scheduleCount; k++) {
                    String doctorTitle = "医生职称-" + k;
                    String doctorName = "医生姓名-" + k;
                    String skill = "医生擅长-" + k;

                    Schedule schedule = new Schedule();
                    schedule.setDepartmentCode(departmentCode);
                    schedule.setDoctorTitle(doctorTitle);
                    schedule.setDoctorName(doctorName);
                    schedule.setSkill(skill);
                    schedule.setWorkDate(workDate);
                    schedule.setWorkTime(workTime.toString());
                    schedule.setReservedNumber(random.nextInt(1, 100));
                    schedule.setAvailableNumber(random.nextInt(0, schedule.getReservedNumber()));
                    schedule.setAmount(BigDecimal.valueOf(random.nextInt(0, 1000)).divide(BigDecimal.valueOf(100), 2,
                            RoundingMode.HALF_UP));
                    schedule.setStatus(1);
                    schedule.setId(UUID.randomUUID().toString());
                    scheduleRepository.save(schedule);

                    UpdateScheduleRequest updateScheduleRequest = new UpdateScheduleRequest();
                    updateScheduleRequest.setDepartmentCode(departmentCode);
                    updateScheduleRequest.setDoctorTitle(doctorTitle);
                    updateScheduleRequest.setDoctorName(doctorName);
                    updateScheduleRequest.setSkill(skill);
                    updateScheduleRequest.setWorkDate(schedule.getWorkDate());
                    updateScheduleRequest.setWorkTime(schedule.getWorkTime());
                    updateScheduleRequest.setReservedNumber(schedule.getReservedNumber());
                    updateScheduleRequest.setAvailableNumber(schedule.getAvailableNumber());
                    updateScheduleRequest.setAmount(schedule.getAmount());
                    updateScheduleRequest.setStatus(schedule.getStatus());
                    updateScheduleRequest.setHospitalScheduleId(schedule.getId());
                    // batchUpdateScheduleRequest.add(updateScheduleRequest);

                    if (workTime.compareTo(1) == 0) {
                        workTime = 0;
                        workDate = new Date(workDate.getTime() + Constants.DAY_IN_MILLIS);
                    } else {
                        workTime = 1;
                    }
                }
            }
            doctorarrivalService.updateDepartments(batchUpdateDepartmentRequest);
            // doctorarrivalService.updateSchedules(batchUpdateScheduleRequest);
            log.info("progress: {}%", (i + 1) * 100.0 / primaryDepartmentCount);
        }
        log.info("done.");
    }
}
