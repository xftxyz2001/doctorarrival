package com.xftxyz.doctorarrival.hospital;

import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.domain.hospital.Schedule;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.hospital.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class MongoTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void testDate() {
        // Schedule schedule = new Schedule();
        // schedule.setHospitalCode("1");
        // schedule.setDepartmentCode("1");
        // schedule.setDoctorTitle("1");
        // schedule.setDoctorName("1");
        // schedule.setSkill("1");
        // schedule.setWorkDate(DateTimeHelper.getTodayStartDate());
        // schedule.setWorkTime("0");
        // schedule.setReservedNumber(10);
        // schedule.setAvailableNumber(7);
        // schedule.setAmount(new BigDecimal("1"));
        // schedule.setStatus(1);
        // schedule.setHospitalScheduleId("yjdgfnfjttshtyshgy");
        //
        // Schedule save = scheduleRepository.save(schedule);
        Date d1 = DateTimeHelper.getTodayStartDate();
        Date d2 = new Date(d1.getTime() + Constants.DAY_IN_MILLIS);
        List<Schedule> scheduleList = scheduleRepository.findByHospitalCodeAndDepartmentCodeAndWorkDateBetween("1", "1", d1, d2);
        System.out.println(scheduleList);
    }
}
