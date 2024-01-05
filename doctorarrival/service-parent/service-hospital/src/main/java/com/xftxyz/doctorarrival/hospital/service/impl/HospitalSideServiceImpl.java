package com.xftxyz.doctorarrival.hospital.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xftxyz.doctorarrival.common.constant.Constants;
import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.hospital.mapper.HospitalSetMapper;
import com.xftxyz.doctorarrival.hospital.service.HospitalSideService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalJoinVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalSideServiceImpl implements HospitalSideService {

    private final StringRedisTemplate stringRedisTemplate;

    private final HospitalSetMapper hospitalSetMapper;

    @Override
    public Resource join(HospitalJoinVO hospitalJoinVO) {
        // 验证码校验
        String redisKey = Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + hospitalJoinVO.getContactsPhone();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(redisKey))) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_EXPIRED);
        }
        String code = stringRedisTemplate.opsForValue().get(redisKey);
        if (!hospitalJoinVO.getVerificationCode().equals(code)) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_ERROR);
        }
        // 检查医院是否已经加入
        LambdaQueryWrapper<HospitalSet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HospitalSet::getHospitalName, hospitalJoinVO.getHospitalName());
        if (hospitalSetMapper.selectCount(lambdaQueryWrapper) > 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_ALREADY_EXIST);
        }
        // TODO 生成签名秘钥

        // 添加
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setHospitalCode(hospitalJoinVO.getHospitalCode());
        hospitalSet.setHospitalName(hospitalJoinVO.getHospitalName());
        hospitalSet.setApiUrl(hospitalJoinVO.getApiUrl());
        hospitalSet.setContactsName(hospitalJoinVO.getContactsName());
        hospitalSet.setContactsPhone(hospitalJoinVO.getContactsPhone());
        hospitalSet.setStatus(HospitalSet.STATUS_DISABLE);

        if (hospitalSetMapper.insert(hospitalSet) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_SAVE_FAILED);
        }
        return null;
    }
}
