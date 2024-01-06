package com.xftxyz.doctorarrival.sdk.service;

import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.sdk.constant.ApiUrls;
import com.xftxyz.doctorarrival.sdk.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.common.result.Result;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
public class DoctorarrivalService {

    private String hospitalCode;

    private EncryptionRequestProcessor encryptionRequestProcessor;

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T sendPost(String url, Object request, Class<T> clazz) {
        EncryptionRequest encryptionRequest = new EncryptionRequest();
        encryptionRequest.setHospitalCode(hospitalCode);
        try {
            encryptionRequest.setData(encryptionRequestProcessor.encrypt(request));
            Result result = restTemplate.postForObject(url, encryptionRequest, Result.class);
            return encryptionRequestProcessor.decrypt(result.getData().toString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    // 更新医院信息
    public Boolean updateHospital(UpdateHospitalRequest updateHospitalRequest) {
        return sendPost(ApiUrls.UPDATE_HOSPITAL, updateHospitalRequest, Boolean.class);
    }
}
