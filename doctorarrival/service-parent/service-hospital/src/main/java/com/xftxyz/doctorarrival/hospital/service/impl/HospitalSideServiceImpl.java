package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.domain.hospital.*;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.Base64Helper;
import com.xftxyz.doctorarrival.helper.CipherHelper;
import com.xftxyz.doctorarrival.helper.KeyHelper;
import com.xftxyz.doctorarrival.hospital.mapper.HospitalSetMapper;
import com.xftxyz.doctorarrival.hospital.repository.DepartmentRepository;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.repository.ScheduleRepository;
import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.processor.EncryptionRequestProcessor;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.sdk.api.*;
import com.xftxyz.doctorarrival.sdk.vo.EncryptionRequest;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import static com.xftxyz.doctorarrival.constant.Constants.HOSPITAL_API_AES_KEY_REDIS_KEY_PREFIX;
import static com.xftxyz.doctorarrival.constant.Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX;

@Service
@RequiredArgsConstructor
public class HospitalSideServiceImpl implements HospitalSideService {

    // RedisTemplate
    private final StringRedisTemplate stringRedisTemplate;

    // MyBatisPlusMapper
    private final HospitalSetMapper hospitalSetMapper;

    // MongoDBRepository
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Resource join(HospitalJoinVO hospitalJoinVO) {
        // 验证码校验
        // String redisKey = SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + hospitalJoinVO.getContactsPhone();
        // if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(redisKey))) {
        //     throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_EXPIRED);
        // }
        // String code = stringRedisTemplate.opsForValue().get(redisKey);
        // if (!hospitalJoinVO.getVerificationCode().equals(code)) {
        //     throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_ERROR);
        // }

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

        // 处理附加信息
        Hospital hospital = new Hospital();
        hospital.setHospitalCode(hospitalJoinVO.getHospitalCode());
        hospital.setHospitalName(hospitalJoinVO.getHospitalName());
        hospital.setHospitalType(hospitalJoinVO.getHospitalType());
        hospital.setProvinceCode(hospitalJoinVO.getProvinceCode());
        hospital.setCityCode(hospitalJoinVO.getCityCode());
        hospital.setDistrictCode(hospitalJoinVO.getDistrictCode());
        hospital.setAddress(hospitalJoinVO.getAddress());
        hospital.setLogoData(hospitalJoinVO.getLogoData());
        hospital.setIntro(hospitalJoinVO.getIntro());
        hospital.setRoute(hospitalJoinVO.getRoute());
        hospitalRepository.save(hospital);

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
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            UpdateHospitalRequest request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateHospitalRequest.class);
            updateHospitalInternal(hospitalCode, request);
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    // 更新医院信息
    private void updateHospitalInternal(String hospitalCode, UpdateHospitalRequest request) {
        Hospital hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        if (ObjectUtils.isEmpty(hospital)) {
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
        hospital.setBookingRule(bookingRule);

        hospitalRepository.save(hospital);
    }

    @Override
    public String updateDepartment(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            UpdateDepartmentRequest request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateDepartmentRequest.class);
            updateDepartmentInternal(hospitalCode, request);
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    // 更新科室信息
    private void updateDepartmentInternal(String hospitalCode, UpdateDepartmentRequest request) {
        Department department = departmentRepository.findByHospitalCodeAndDepartmentCode(hospitalCode, request.getDepartmentCode());
        if (ObjectUtils.isEmpty(department)) {
            department = new Department();
            department.setHospitalCode(hospitalCode);
            department.setDepartmentCode(request.getDepartmentCode());
        }
        department.setDepartmentName(request.getDepartmentName());
        department.setIntro(request.getIntro());
        department.setPrimaryDepartmentCode(request.getPrimaryDepartmentCode());
        department.setPrimaryDepartmentName(request.getPrimaryDepartmentName());

        departmentRepository.save(department);
    }

    @Override
    public String updateSchedule(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            UpdateScheduleRequest request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateScheduleRequest.class);
            updateScheduleInternal(hospitalCode, request);
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    // 更新排班信息
    private void updateScheduleInternal(String hospitalCode, UpdateScheduleRequest request) {

        // 更新排班信息
        Schedule schedule = scheduleRepository.findByHospitalCodeAndDepartmentCodeAndHospitalScheduleId(hospitalCode,
                request.getDepartmentCode(), request.getHospitalScheduleId());
        if (ObjectUtils.isEmpty(schedule)) {
            schedule = new Schedule();
            schedule.setHospitalCode(hospitalCode);
            schedule.setDepartmentCode(request.getDepartmentCode());
            schedule.setHospitalScheduleId(request.getHospitalScheduleId());
        }
        schedule.setDoctorTitle(request.getDoctorTitle());
        schedule.setDoctorName(request.getDoctorName());
        schedule.setSkill(request.getSkill());
        schedule.setWorkDate(request.getWorkDate());
        schedule.setWorkTime(request.getWorkTime());
        schedule.setReservedNumber(request.getReservedNumber());
        schedule.setAvailableNumber(request.getAvailableNumber());
        schedule.setAmount(request.getAmount());
        schedule.setStatus(request.getStatus());

        scheduleRepository.save(schedule);
    }

    @Override
    public String deleteDepartment(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            UpdateDepartmentRequest request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateDepartmentRequest.class);
            departmentRepository.deleteByHospitalCodeAndDepartmentCode(hospitalCode, request.getDepartmentCode());
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    @Override
    public String deleteSchedule(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            UpdateScheduleRequest request = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), UpdateScheduleRequest.class);
            scheduleRepository.deleteByHospitalCodeAndDepartmentCodeAndHospitalScheduleId(hospitalCode, request.getDepartmentCode(), request.getHospitalScheduleId());
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    @Override
    public String updateDepartments(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            BatchUpdateDepartmentRequest requests = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), BatchUpdateDepartmentRequest.class);
            // 更新科室信息
            for (UpdateDepartmentRequest request : requests) {
                updateDepartmentInternal(hospitalCode, request);
            }
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    @Override
    public String updateSchedules(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            BatchUpdateScheduleRequest requests = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), BatchUpdateScheduleRequest.class);
            // 更新排班信息
            for (UpdateScheduleRequest request : requests) {
                updateScheduleInternal(hospitalCode, request);
            }
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    @Override
    public String deleteDepartments(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            BatchUpdateDepartmentRequest requests = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), BatchUpdateDepartmentRequest.class);
            // 删除科室信息
            for (UpdateDepartmentRequest request : requests) {
                departmentRepository.deleteByHospitalCodeAndDepartmentCode(hospitalCode, request.getDepartmentCode());
            }
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

    @Override
    public String deleteSchedules(EncryptionRequest encryptionRequest) {
        // 获取医院编码，解密请求数据
        String hospitalCode = encryptionRequest.getHospitalCode();
        EncryptionRequestProcessor encryptionRequestProcessor = getEncryptionRequestProcessor(hospitalCode);

        try {
            BatchUpdateScheduleRequest requests = encryptionRequestProcessor.decrypt(encryptionRequest.getData(), BatchUpdateScheduleRequest.class);
            // 删除排班信息
            for (UpdateScheduleRequest request : requests) {
                scheduleRepository.deleteByHospitalCodeAndDepartmentCodeAndHospitalScheduleId(hospitalCode, request.getDepartmentCode(), request.getHospitalScheduleId());
            }
            return encryptionRequestProcessor.encrypt(true);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.REQUEST_ERROR);
        }
    }

}
