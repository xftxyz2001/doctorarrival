package com.xftxyz.doctorarrival.sdk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xftxyz.doctorarrival.common.helper.Base64Helper;
import com.xftxyz.doctorarrival.common.helper.CipherHelper;
import com.xftxyz.doctorarrival.common.helper.KeyHelper;
import com.xftxyz.doctorarrival.common.result.Result;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.sdk.constant.ApiUrls;
import com.xftxyz.doctorarrival.sdk.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.PrivateKey;

@SuppressWarnings("rawtypes")
public class DoctorarrivalService {

    // 医院编码
    private final String hospitalCode;

    // 医院私钥
    private final PrivateKey privateKey;

    public DoctorarrivalService(String hospitalCode, PrivateKey privateKey) {
        this.hospitalCode = hospitalCode;
        this.privateKey = privateKey;

        // 初始化AES密钥
        updateSecretKey();
    }

    private EncryptionRequestProcessor encryptionRequestProcessor;

    private RestTemplate restTemplate = new RestTemplate();

    // 获取AES密钥
    private void updateSecretKey() {
        String url = ApiUrls.UPDATE_SECRET_KEY + hospitalCode;
        Result result = restTemplate.getForObject(url, Result.class);
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

    private <T> T sendPost(String url, Object request, Class<T> clazz) {
        EncryptionRequest encryptionRequest = new EncryptionRequest();
        encryptionRequest.setHospitalCode(hospitalCode);
        String encryptedRequestData = null;
        try {
            encryptedRequestData = encryptionRequestProcessor.encrypt(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("请求加密失败");
        }
        encryptionRequest.setData(encryptedRequestData);
        Result result = restTemplate.postForObject(url, encryptionRequest, Result.class);
        if (ObjectUtils.isEmpty(result)) {
            throw new RuntimeException("请求发送失败");
        } else if (!ResultEnum.SUCCESS.getCode().equals(result.getCode())) {
            throw new RuntimeException(result.getMessage());
        }
        String encryptedResponseData = result.getData().toString();
        try {
            return encryptionRequestProcessor.decrypt(encryptedResponseData, clazz);
        } catch (IOException e) {
            throw new RuntimeException("响应解密失败");
        }
    }

    // 更新医院信息
    public Boolean updateHospital(UpdateHospitalRequest updateHospitalRequest) {
        return sendPost(ApiUrls.UPDATE_HOSPITAL, updateHospitalRequest, Boolean.class);
    }
}
