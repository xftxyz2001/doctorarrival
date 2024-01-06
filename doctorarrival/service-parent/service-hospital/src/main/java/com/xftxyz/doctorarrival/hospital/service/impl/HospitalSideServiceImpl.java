package com.xftxyz.doctorarrival.hospital.service.impl;

import static com.xftxyz.doctorarrival.common.constant.Constants.HOSPITAL_API_AES_KEY_REDIS_KEY_PREFIX;
import static com.xftxyz.doctorarrival.common.constant.Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX;

import java.io.ByteArrayInputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.helper.Base64Helper;
import com.xftxyz.doctorarrival.common.helper.CipherHelper;
import com.xftxyz.doctorarrival.common.helper.KeyHelper;
import com.xftxyz.doctorarrival.common.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.hospital.mapper.HospitalSetMapper;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.sdk.api.UpdateHospitalRequest;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalSideServiceImpl implements HospitalSideService {

    private final StringRedisTemplate stringRedisTemplate;

    private final HospitalSetMapper hospitalSetMapper;

    private final HospitalRepository hospitalRepository;

    @Override
    public Resource join(HospitalJoinVO hospitalJoinVO) {
        // 验证码校验
        String redisKey = SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + hospitalJoinVO.getContactsPhone();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(redisKey))) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_EXPIRED);
        }
        String code = stringRedisTemplate.opsForValue().get(redisKey);
        if (!hospitalJoinVO.getVerificationCode().equals(code)) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_ERROR);
        }
        // 检查医院是否已经加入
        LambdaQueryWrapper<HospitalSet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HospitalSet::getHospitalCode, hospitalJoinVO.getHospitalCode());
        if (hospitalSetMapper.selectCount(lambdaQueryWrapper) > 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_ALREADY_EXIST);
        }
        // 生成签名秘钥
        KeyPair keyPair = KeyHelper.generateKeyPair();
        String base64PublicKey = Base64Helper.encodeToString(keyPair.getPublic().getEncoded());

        // 添加
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setHospitalCode(hospitalJoinVO.getHospitalCode());
        hospitalSet.setHospitalName(hospitalJoinVO.getHospitalName());
        hospitalSet.setApiUrl(hospitalJoinVO.getApiUrl());
        hospitalSet.setSignKey(base64PublicKey);
        hospitalSet.setContactsName(hospitalJoinVO.getContactsName());
        hospitalSet.setContactsPhone(hospitalJoinVO.getContactsPhone());
        hospitalSet.setStatus(HospitalSet.STATUS_DISABLE);

        if (hospitalSetMapper.insert(hospitalSet) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_SAVE_FAILED);
        }
        return new InputStreamResource(new ByteArrayInputStream(keyPair.getPrivate().getEncoded()));
    }

    @Override
    public String updateSecretKey(String hospitalCode) {
        // 根据hospitalCode查询医院设置
        LambdaQueryWrapper<HospitalSet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HospitalSet::getHospitalCode, hospitalCode);
        HospitalSet hospitalSet = hospitalSetMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(hospitalSet)) {
            throw new BusinessException(ResultEnum.HOSPITAL_NOT_EXIST);
        }
        // 获取公钥，得到加密解密器
        String base64PublicKey = hospitalSet.getSignKey();
        PublicKey publicKey;
        try {
            publicKey = KeyHelper.getPublicKey(Base64Helper.decode(base64PublicKey));
        } catch (InvalidKeySpecException e) {
            throw new BusinessException(ResultEnum.HOSPITAL_SIGN_ERROR);
        }
        CipherHelper cipherHelper = new CipherHelper(publicKey);
        // 生成AES密钥
        byte[] bytesAES = KeyHelper.generateKey().getEncoded();
        // base64编码存入redis
        String base64AES = Base64Helper.encodeToString(bytesAES);
        stringRedisTemplate.opsForValue().set(HOSPITAL_API_AES_KEY_REDIS_KEY_PREFIX + hospitalCode, base64AES);

        // 使用公钥加密AES密钥
        byte[] encryptedAES = cipherHelper.encrypt(bytesAES);
        // base64编码返回
        return Base64Helper.encodeToString(encryptedAES);
    }

    private EncryptionRequestProcessor getEncryptionRequestProcessor(String hospitalCode) {
        String base64AES = stringRedisTemplate.opsForValue().get(HOSPITAL_API_AES_KEY_REDIS_KEY_PREFIX + hospitalCode);
        if (ObjectUtils.isEmpty(base64AES)) {
            throw new BusinessException(ResultEnum.HOSPITAL_SIGN_ERROR);
        }
        return new EncryptionRequestProcessor(base64AES);
    }

    @Override
    public String updateHospital(EncryptionRequest encryptionRequest) {
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);
        UpdateHospitalRequest request = null;
        try {
            request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateHospitalRequest.class);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
        Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalCode);
        Hospital hospital = null;
        if (hospitalOptional.isPresent()) {
            hospital = hospitalOptional.get();
        } else {
            hospital = new Hospital();
            hospital.setHospitalCode(hospitalCode);
        }
        hospital.setHospitalName(request.getHospitalName());
        hospital.setHospitalType(request.getHospitalType());
        hospital.setProvinceCode(request.getProvinceCode());
        hospital.setCityCode(request.getCityCode());
        hospital.setDistrictCode(request.getDistrictCode());
        hospital.setAddress(request.getAddress());
        hospital.setLogoData(request.getLogoData());
        hospital.setIntro(request.getIntro());
        hospital.setRoute(request.getRoute());
        UpdateHospitalRequest.BookingRule requestBookingRule = request.getBookingRule();
        BookingRule bookingRule = ObjectUtils.isEmpty(hospital.getBookingRule()) ? new BookingRule()
                : hospital.getBookingRule();
        if (!ObjectUtils.isEmpty(requestBookingRule)) {
            bookingRule.setCycle(requestBookingRule.getCycle());
            bookingRule.setReleaseTime(requestBookingRule.getReleaseTime());
            bookingRule.setStopTime(requestBookingRule.getStopTime());
            bookingRule.setQuitDay(requestBookingRule.getQuitDay());
            bookingRule.setQuitTime(requestBookingRule.getQuitTime());
            bookingRule.setRule(requestBookingRule.getRule());
        }
        try {
            hospitalRepository.save(hospital);
            return encryptionRequestProcessor.encrypt(true);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
