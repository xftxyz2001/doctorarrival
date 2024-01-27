package com.xftxyz.doctorarrival.sdk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xftxyz.doctorarrival.helper.Base64Helper;
import com.xftxyz.doctorarrival.helper.CipherHelper;
import com.xftxyz.doctorarrival.helper.KeyHelper;
import com.xftxyz.doctorarrival.result.Result;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.sdk.api.*;
import com.xftxyz.doctorarrival.sdk.callback.UpdateOrderRequest;
import com.xftxyz.doctorarrival.sdk.callback.UpdateOrderResponse;
import com.xftxyz.doctorarrival.sdk.constant.ApiUrls;
import com.xftxyz.doctorarrival.sdk.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Objects;
import java.util.function.BiConsumer;

// @SuppressWarnings("rawtypes")
public class DoctorarrivalService {

    private final String serverUrl;

    // 医院编码
    private final String hospitalCode;

    // 医院私钥
    private final PrivateKey privateKey;

    public DoctorarrivalService(String serverUrl, String hospitalCode, PrivateKey privateKey) {
        this.serverUrl = serverUrl;
        this.hospitalCode = hospitalCode;
        this.privateKey = privateKey;

        // 初始化AES密钥
        updateSecretKey();
    }

    private EncryptionRequestProcessor encryptionRequestProcessor;

    private RestTemplate restTemplate = new RestTemplate();

    // 获取AES密钥
    private void updateSecretKey() {
        String url = this.serverUrl + ApiUrls.UPDATE_SECRET_KEY + hospitalCode;
        Result<?> result = restTemplate.getForObject(url, Result.class);
        if (ObjectUtils.isEmpty(result)) {
            throw new RuntimeException("获取AES密钥失败");
        } else if (!ResultEnum.SUCCESS.getCode().equals(result.getCode())) {
            throw new RuntimeException(result.getMessage());
        }
        // 获取base64编码的加密后的AES密钥
        String base64encryptedAES = result.getData().toString();
        // base64解码
        byte[] encryptedAES = Base64Helper.decode(base64encryptedAES);
        // 私钥解密得到AES密钥
        CipherHelper cipherHelper = new CipherHelper(privateKey);
        byte[] aesKey = cipherHelper.decrypt(encryptedAES);
        SecretKey secretKey = KeyHelper.getSecretKey(aesKey);
        // 更新加密处理器
        encryptionRequestProcessor = new EncryptionRequestProcessor(secretKey);
    }

    private EncryptionRequest beforeRequest(Object request) {
        EncryptionRequest encryptionRequest = new EncryptionRequest();
        encryptionRequest.setHospitalCode(hospitalCode);
        String encryptedRequestData = null;
        try {
            encryptedRequestData = encryptionRequestProcessor.encrypt(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("请求加密失败");
        }
        encryptionRequest.setData(encryptedRequestData);
        return encryptionRequest;
    }

    private <T> T afterResponse(Result<?> result, Class<T> clazz) {
        if (ObjectUtils.isEmpty(result)) {
            throw new RuntimeException("响应接受失败");
        } else if (!ResultEnum.SUCCESS.getCode().equals(result.getCode())) {
            throw new RuntimeException(result.getMessage());
        }
        Object data = result.getData();
        if (ObjectUtils.isEmpty(data)) {
            return null;
        }
        String encryptedResponseData = data.toString();
        try {
            return encryptionRequestProcessor.decrypt(encryptedResponseData, clazz);
        } catch (IOException e) {
            throw new RuntimeException("响应解密失败");
        }
    }

    private <T> T sendPost(String url, Object request, Class<T> clazz) {
        EncryptionRequest encryptionRequest = beforeRequest(request);
        Result<?> result = restTemplate.postForObject(url, encryptionRequest, Result.class);
        return afterResponse(result, clazz);
    }

    // 更新医院信息
    public Boolean updateHospital(UpdateHospitalRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.UPDATE_HOSPITAL, updateHospitalRequest, Boolean.class);
    }

    // 更新科室信息
    public Boolean updateDepartment(UpdateDepartmentRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.UPDATE_DEPARTMENT, updateHospitalRequest, Boolean.class);
    }

    // 更新排班信息
    public Boolean updateSchedule(UpdateScheduleRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.UPDATE_SCHEDULE, updateHospitalRequest, Boolean.class);
    }

    // 删除科室信息
    public Boolean deleteDepartment(UpdateDepartmentRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.DELETE_DEPARTMENT, updateHospitalRequest, Boolean.class);
    }

    // 删除排班信息
    public Boolean deleteSchedule(UpdateScheduleRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.DELETE_SCHEDULE, updateHospitalRequest, Boolean.class);
    }

    // 批量更新科室信息
    public Boolean updateDepartments(BatchUpdateDepartmentRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.UPDATE_DEPARTMENTS, updateHospitalRequest, Boolean.class);
    }

    // 批量更新排班信息
    public Boolean updateSchedules(BatchUpdateScheduleRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.UPDATE_SCHEDULES, updateHospitalRequest, Boolean.class);
    }

    // 批量删除科室信息
    public Boolean deleteDepartments(BatchUpdateDepartmentRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.DELETE_DEPARTMENTS, updateHospitalRequest, Boolean.class);
    }

    // 批量删除排班信息
    public Boolean deleteSchedules(BatchUpdateScheduleRequest updateHospitalRequest) {
        return sendPost(this.serverUrl + ApiUrls.DELETE_SCHEDULES, updateHospitalRequest, Boolean.class);
    }

    // ----------
    // 解密请求
    public <T> T decryptRequest(EncryptionRequest encryptedRequest, Class<T> clazz) {
        if (!Objects.equals(hospitalCode, encryptedRequest.getHospitalCode())) {
            throw new RuntimeException("医院编码不匹配");
        }
        try {
            return encryptionRequestProcessor.decrypt(encryptedRequest.getData(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("请求解密失败");
        }
    }

    // 加密响应
    public String encryptResponse(Object response) {
        try {
            return encryptionRequestProcessor.encrypt(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("响应加密失败");
        }
    }

    public String processSubmitOrder(EncryptionRequest encryptionRequest, BiConsumer<UpdateOrderRequest, UpdateOrderResponse> consumer) {
        UpdateOrderRequest updateOrderRequest = decryptRequest(encryptionRequest, UpdateOrderRequest.class);
        UpdateOrderResponse updateOrderResponse = new UpdateOrderResponse();
        consumer.accept(updateOrderRequest, updateOrderResponse);
        return encryptResponse(updateOrderResponse);
    }

    public String processUpdateOrderStatus(EncryptionRequest encryptionRequest, BiConsumer<UpdateOrderRequest, UpdateOrderResponse> consumer) {
        UpdateOrderRequest updateOrderRequest = decryptRequest(encryptionRequest, UpdateOrderRequest.class);
        UpdateOrderResponse updateOrderResponse = new UpdateOrderResponse();
        consumer.accept(updateOrderRequest, updateOrderResponse);
        return encryptResponse(updateOrderResponse);
    }

}
