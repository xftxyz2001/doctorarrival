package com.xftxyz.doctorarrival.user.controller;


import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.*;
import jakarta.validation.constraints.NotBlank;
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
    public UserInfoVO getUserInfoDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoDetail(userId);
    }

    // 保存实名认证信息
    @PostMapping("/auth/realname")
    public Boolean saveRealName(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull RealNameParam realNameParam) {
        return userInfoService.saveRealName(userId, realNameParam);
    }

    // 更新手机号
    @PostMapping("/auth/phone")
    public Boolean updatePhone(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.updatePhone(userId, loginParam);
    }

    // 更新昵称
    @PostMapping("/auth/nickname")
    public Boolean updateNickname(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestParam("nickName") @NotBlank String nickName) {
        return userInfoService.updateNickname(userId, nickName);
    }

}
