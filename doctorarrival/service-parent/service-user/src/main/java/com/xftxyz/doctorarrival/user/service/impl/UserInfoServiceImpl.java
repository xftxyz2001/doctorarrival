package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.user.mapper.UserInfoMapper;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.UserInfoQueryVO;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

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
}




