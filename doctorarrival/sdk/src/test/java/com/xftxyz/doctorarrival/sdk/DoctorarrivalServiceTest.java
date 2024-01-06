package com.xftxyz.doctorarrival.sdk;

import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.common.helper.KeyPairHelper;
import com.xftxyz.doctorarrival.sdk.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.sdk.service.DoctorarrivalService;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

public class DoctorarrivalServiceTest {

    public static void main(String[] args) throws IOException, InvalidKeySpecException {

        FileInputStream inputStream = new FileInputStream("C:\\Users\\25810\\Downloads\\privat.key");
        byte[] privateKeyb = new byte[inputStream.available()];
        inputStream.read(privateKeyb);
        inputStream.close();
        PrivateKey privateKey = KeyPairHelper.getPrivateKey(privateKeyb);
        DoctorarrivalService doctorarrivalService = new DoctorarrivalService();
        doctorarrivalService.setHospitalCode("1");
        doctorarrivalService.setEncryptionRequestProcessor(new EncryptionRequestProcessor(privateKey));

        UpdateHospitalRequest updateHospitalRequest = new UpdateHospitalRequest();
        updateHospitalRequest.setHospitalName("测试医院");
        Boolean b = doctorarrivalService.updateHospital(updateHospitalRequest);
        System.out.println(b);
    }
}
