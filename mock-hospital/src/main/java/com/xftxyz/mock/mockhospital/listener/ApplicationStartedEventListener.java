package com.xftxyz.mock.mockhospital.listener;

import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.mock.mockhospital.domain.Department;
import com.xftxyz.mock.mockhospital.domain.Schedule;
import com.xftxyz.mock.mockhospital.repository.DepartmentRepository;
import com.xftxyz.mock.mockhospital.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final DepartmentRepository departmentRepository;
    private final ScheduleRepository scheduleRepository;

    private final Random random = new Random();

    private final int primaryDepartmentMax = 20;
    private final int primaryDepartmentMin = 10;
    private final int departmentMax = 50;
    private final int departmentMin = 10;

    private final int scheduleCount = 20;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        int primaryDepartmentCount = random.nextInt(primaryDepartmentMin, primaryDepartmentMax);
        // 大科室
        for (int i = 0; i < primaryDepartmentCount; i++) {
            String primaryDepartmentCode = UUID.randomUUID().toString();
            String primaryDepartmentName = "科室-" + i;
            // 小科室
            int departmentCount = random.nextInt(departmentMin, departmentMax);
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

                    if (workTime.compareTo(1) == 0) {
                        workTime = 0;
                        workDate = new Date(workDate.getTime() + Constants.DAY_IN_MILLIS);
                    } else {
                        workTime = 1;
                    }
                }
            }

        }
    }
}
