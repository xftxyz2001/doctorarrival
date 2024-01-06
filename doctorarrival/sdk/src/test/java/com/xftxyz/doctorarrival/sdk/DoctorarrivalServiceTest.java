package com.xftxyz.doctorarrival.sdk;

import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalServiceBuilder;

public class DoctorarrivalServiceTest {

    public static void main(String[] args){
        DoctorarrivalService doctorarrivalService = DoctorarrivalServiceBuilder.build("2", "C:\\Users\\25810\\Downloads\\private.key");
        UpdateHospitalRequest updateHospitalRequest = new UpdateHospitalRequest();
        updateHospitalRequest.setHospitalName("测试医院");
        Boolean success = doctorarrivalService.updateHospital(updateHospitalRequest);
        System.out.println("success = " + success);

    }
}
