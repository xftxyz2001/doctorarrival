package com.xftxyz.doctorarrival.user.controller;


import com.xftxyz.doctorarrival.common.helper.JwtHelper;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.LoginParam;
import com.xftxyz.doctorarrival.vo.user.LoginResponse;
import com.xftxyz.doctorarrival.vo.user.RealNameParam;
import com.xftxyz.doctorarrival.vo.user.UserInfoBasic;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/info")
public class UserInfoApiController {

    private final UserInfoService userInfoService;

    // 登录
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.login(loginParam);
    }

    // 获取用户信息（基本）
    @GetMapping("/auth/basic")
    public UserInfoBasic getUserInfoBasic(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoBasic(userId);
    }

    // 获取用户信息（详细）
    @GetMapping("/auth/detail")
    public UserInfo getUserInfoDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoDetail(userId);
    }

    // 保存实名认证信息
    @PostMapping("/auth/realname")
    public Boolean saveRealName(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull RealNameParam realNameParam) {
        return userInfoService.saveRealName(userId, realNameParam);
    }
}
