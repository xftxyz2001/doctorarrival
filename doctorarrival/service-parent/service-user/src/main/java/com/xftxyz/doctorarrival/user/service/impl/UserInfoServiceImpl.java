package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.constant.Constants;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.user.mapper.UserInfoMapper;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 25810
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2024-01-03 23:44:16
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean saveWarp(UserInfo userInfo) {
        // 根据手机号查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getPhone, userInfo.getPhone());
        UserInfo existUser = baseMapper.selectOne(lambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(existUser)) {
            throw new BusinessException(ResultEnum.USER_ALREADY_EXIST);
        }
        if (baseMapper.insert(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_SAVE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdWarp(Long id) {
        if (baseMapper.deleteById(id) <= 0) {
            throw new BusinessException(ResultEnum.USER_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdsWarp(List<Long> idList) {
        int num = baseMapper.deleteBatchIds(idList);
        if (num < idList.size()) {
            throw new BusinessException(ResultEnum.USER_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean updateByIdWarp(UserInfo userInfo) {
        // 根据id查询用户信息
        UserInfo existUser = baseMapper.selectById(userInfo.getId());
        if (ObjectUtils.isEmpty(existUser)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        if (baseMapper.updateById(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean setStatus(Long id, Integer status) {
        // 根据id查询用户信息
        UserInfo userInfo = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        userInfo.setStatus(status);
        if (baseMapper.updateById(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public UserInfo getByIdWarp(Long id) {
        UserInfo userInfo = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        return userInfo;
    }

    @Override
    public IPage<UserInfo> find(UserInfoQueryVO userInfoQueryVO, Long current, Long size) {
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(userInfoQueryVO.getPhone()), UserInfo::getPhone, userInfoQueryVO.getPhone());
        lambdaQueryWrapper.like(StringUtils.hasText(userInfoQueryVO.getNickName()), UserInfo::getNickName, userInfoQueryVO.getNickName());
        lambdaQueryWrapper.between(!ObjectUtils.isEmpty(userInfoQueryVO.getCreateTimeFrom()) && !ObjectUtils.isEmpty(userInfoQueryVO.getCreateTimeTo()),
                UserInfo::getCreateTime, userInfoQueryVO.getCreateTimeFrom(), userInfoQueryVO.getCreateTimeTo());
        return baseMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);
    }

    private void checkVerificationCode(String phoneNumber, String verificationCode) {
        // 检查验证码
        // Redis对应的key
        String redisKey = Constants.SMS_VERIFICATION_CODE_REDIS_KEY_PREFIX + phoneNumber;
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(redisKey))) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_EXPIRED);
        }
        String redisCode = stringRedisTemplate.opsForValue().get(redisKey);
        if (!verificationCode.equals(redisCode)) {
            throw new BusinessException(ResultEnum.SMS_VERIFICATION_CODE_ERROR);
        }
        // 删除验证码
        stringRedisTemplate.delete(redisKey);
    }

    @Override
    public LoginResponse login(LoginParam loginParam) {
        String phoneNumber = loginParam.getPhoneNumber();
        String verificationCode = loginParam.getVerificationCode();

        checkVerificationCode(phoneNumber, verificationCode);

        // 根据手机号查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getPhone, phoneNumber);
        UserInfo userInfo = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(userInfo)) {
            // 用户不存在，创建用户
            userInfo = new UserInfo();
            userInfo.setPhone(phoneNumber);
            userInfo.setNickName(phoneNumber);
            baseMapper.insert(userInfo);
        }

        // 生成token
        String token = JwtHelper.generateToken(userInfo.getId());
        // 响应
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setNickName(userInfo.getNickName());
        loginResponse.setToken(token);
        return loginResponse;
    }

    @Override
    public UserInfoBasic getUserInfoBasic(String userId) {
        UserInfo userInfo = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        UserInfoBasic userInfoBasic = new UserInfoBasic();
        userInfoBasic.setPhone(userInfo.getPhone());
        userInfoBasic.setOpenid(userInfo.getOpenid());
        userInfoBasic.setNickName(userInfo.getNickName());
        return userInfoBasic;
    }

    @Override
    public UserInfo getUserInfoDetail(String userId) {
        UserInfo userInfo = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        return userInfo;
    }

    @Override
    public Boolean saveRealName(String userId, RealNameParam realNameParam) {
        // 根据id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        userInfo.setName(realNameParam.getName());
        userInfo.setCertificatesType(realNameParam.getCertificatesType());
        userInfo.setCertificatesNo(realNameParam.getCertificatesNo());
        userInfo.setCertificatesUrl(realNameParam.getCertificatesUrl());
        userInfo.setAuthStatus(UserInfo.AUTH_STATUS_AUTHING); // 认证中
        if (baseMapper.updateById(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean updatePhone(String userId, LoginParam loginParam) {
        String phoneNumber = loginParam.getPhoneNumber();
        // 根据手机号查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getPhone, phoneNumber);
        UserInfo existUser = baseMapper.selectOne(lambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(existUser)) {
            throw new BusinessException(ResultEnum.PHONE_ALREADY_EXIST);
        }

        String verificationCode = loginParam.getVerificationCode();
        checkVerificationCode(phoneNumber, verificationCode);

        // 根据id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        userInfo.setPhone(loginParam.getPhoneNumber());
        if (baseMapper.updateById(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean updateNickname(String userId, String nickName) {
        // 根据id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXIST);
        }
        userInfo.setNickName(nickName);
        if (baseMapper.updateById(userInfo) <= 0) {
            throw new BusinessException(ResultEnum.USER_UPDATE_FAILED);
        }
        return true;
    }
}
