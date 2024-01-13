package com.xftxyz.doctorarrival.sdk;

import java.util.ArrayList;
import java.util.List;

import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest.BookingRule;

import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalServiceBuilder;

public class DoctorarrivalServiceTest {

    public static void main(String[] args) {
        DoctorarrivalService doctorarrivalService = DoctorarrivalServiceBuilder.build("2",
                "C:\\Users\\25810\\Downloads\\private.key");

        // 创建UpdateHospitalRequest对象并设置值
        UpdateHospitalRequest updateHospitalRequest = new UpdateHospitalRequest();
        updateHospitalRequest.setHospitalName("北京协和医院");
        updateHospitalRequest.setHospitalType("3000021");
        updateHospitalRequest.setProvinceCode("11");
        updateHospitalRequest.setCityCode("1101");
        updateHospitalRequest.setDistrictCode("110101");
        updateHospitalRequest.setAddress("大望路");
        updateHospitalRequest.setLogoData("data");
        updateHospitalRequest.setIntro("北京协和医院");

        // 创建BookingRule对象并设置值
        BookingRule bookingRule = new BookingRule();
        bookingRule.setCycle(10);
        bookingRule.setReleaseTime("08:30");
        bookingRule.setStopTime("11:30");
        bookingRule.setQuitDay(-1);
        bookingRule.setQuitTime("15:30");

        // 设置规则列表
        List<String> ruleList = new ArrayList<>();
        ruleList.add("西院区预约号取号地点：西院区门诊楼一层大厅挂号窗口取号");
        ruleList.add("东院区预约号取号地点：东院区老门诊楼一层大厅挂号窗口或新门诊楼各楼层挂号/收费窗口取号");
        bookingRule.setRule(ruleList);

        // 将BookingRule对象设置到UpdateHospitalRequest对象中
        updateHospitalRequest.setBookingRule(bookingRule);

        Boolean success = doctorarrivalService.updateHospital(updateHospitalRequest);
        System.out.println("success = " + success);

    }
}
