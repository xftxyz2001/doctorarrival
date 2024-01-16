package com.xftxyz.doctorarrival.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xftxyz.doctorarrival.vo.user.*;

import java.util.List;

/**
* @author 25810
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2024-01-03 23:44:16
*/
public interface UserInfoService extends IService<UserInfo> {

    Boolean saveWarp(UserInfo userInfo);

    Boolean removeByIdWarp(Long id);

    Boolean removeByIdsWarp(List<Long> idList);

    Boolean updateByIdWarp(UserInfo userInfo);

    Boolean setStatus(Long id, Integer status);

    UserInfo getByIdWarp(Long id);

    IPage<UserInfo> find(UserInfoQueryVO userInfoQueryVO, Long current, Long size);

    LoginResponse login(LoginParam loginParam);

    UserInfoBasic getUserInfoBasic(String userId);

    UserInfo getUserInfoDetail(String userId);

    Boolean saveRealName(String userId, RealNameParam realNameParam);

    Boolean updatePhone(String userId, LoginParam loginParam);

    Boolean updateNickname(String userId, String nickName);
}
