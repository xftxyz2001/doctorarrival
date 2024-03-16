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

    @Operation(summary = "登录",
               description = "此接口接收一个非空的LoginParam对象作为登录凭证，其中包括用户名和密码等信息。接口验证登录信息有效后，返回一个包含用户身份标识和可能的访问令牌的LoginResponse对象。")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.login(loginParam);
    }

    @Operation(summary = "获取用户基本信息",
               description = "通过JWT鉴权传递用户ID，接口返回该用户的简要基本信息，如用户ID、昵称等，封装在UserInfoBasic对象中。")
    @GetMapping("/auth/basic")
    public UserInfoBasic getUserInfoBasic(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoBasic(userId);
    }

    @Operation(summary = "获取用户详细信息",
               description = "通过JWT鉴权传递用户ID，接口返回该用户的详细信息，包括但不限于基本信息、实名认证状态、联系方式等，封装在UserInfoVO对象中。")
    @GetMapping("/auth/detail")
    public UserInfoVO getUserInfoDetail(@RequestHeader(JwtHelper.X_USER_ID) String userId) {
        return userInfoService.getUserInfoDetail(userId);
    }

    @Operation(summary = "保存实名认证信息",
               description = "通过JWT鉴权传递用户ID，并接收一个非空的RealNameParam对象作为实名认证数据，接口将实名信息保存至用户账户中，成功保存后返回true。")
    @PostMapping("/auth/realname")
    public Boolean saveRealName(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull RealNameParam realNameParam) {
        return userInfoService.saveRealName(userId, realNameParam);
    }

    @Operation(summary = "更新手机号",
               description = "通过JWT鉴权传递用户ID，并接收一个非空的LoginParam对象作为新的手机号信息，接口将更新用户的手机号码，成功更新后返回true。注意，这里的LoginParam应包含新的手机号码。")
    @PostMapping("/auth/phone")
    public Boolean updatePhone(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestBody @NotNull LoginParam loginParam) {
        return userInfoService.updatePhone(userId, loginParam);
    }

    @Operation(summary = "更新昵称",
               description = "通过JWT鉴权传递用户ID，并通过请求参数指定新的昵称，接口将更新用户的昵称信息，成功更新后返回true。昵称不能为空。")
    @PostMapping("/auth/nickname")
    public Boolean updateNickname(@RequestHeader(JwtHelper.X_USER_ID) String userId, @RequestParam("nickName") @NotBlank String nickName) {
        return userInfoService.updateNickname(userId, nickName);
    }

}
