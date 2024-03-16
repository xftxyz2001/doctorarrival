package com.xftxyz.doctorarrival.user.controller;


import com.xftxyz.doctorarrival.helper.JwtHelper;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "用户相关")
@RequestMapping("/api/user/info")
public class UserInfoApiController {

    private final UserInfoService userInfoService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.login(loginParam);
    }

    @Operation(summary = "获取用户基本信息")
    @GetMapping("/auth/basic")
    public UserInfoBasic getUserInfoBasic(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoBasic(userId);
    }

    @Operation(summary = "获取用户详细信息")
    @GetMapping("/auth/detail")
    public UserInfoVO getUserInfoDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoDetail(userId);
    }

    @Operation(summary = "保存实名认证信息")
    @PostMapping("/auth/realname")
    public Boolean saveRealName(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull RealNameParam realNameParam) {
        return userInfoService.saveRealName(userId, realNameParam);
    }

    @Operation(summary = "更新手机号")
    @PostMapping("/auth/phone")
    public Boolean updatePhone(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.updatePhone(userId, loginParam);
    }

    @Operation(summary = "更新昵称")
    @PostMapping("/auth/nickname")
    public Boolean updateNickname(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestParam("nickName") @NotBlank String nickName) {
        return userInfoService.updateNickname(userId, nickName);
    }

}
