package com.xftxyz.doctorarrival.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.UserInfoQueryVO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/user/info")
public class UserInfoAdminController {

    private final UserInfoService userInfoService;

    // 新增用户信息
    @PostMapping("/save")
    public Boolean save(@RequestBody UserInfo userInfo) {
        return userInfoService.saveWarp(userInfo);
    }

    // 删除用户信息
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.removeByIdWarp(id);
    }

    // 批量删除用户信息
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return userInfoService.removeByIdsWarp(idList);
    }

    // 修改用户信息
    @PutMapping("/update")
    public Boolean update(@RequestBody UserInfo userInfo) {
        return userInfoService.updateByIdWarp(userInfo);
    }

    // 设置用户状态
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(0) @Max(1) Integer status) {
        return userInfoService.setStatus(id, status);
    }

    // 根据id查询用户信息
    @GetMapping("/id/{id}")
    public UserInfo getById(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.getByIdWarp(id);
    }

    // 条件查询带分页
    @PostMapping("/find")
    public IPage<UserInfo> find(@RequestBody UserInfoQueryVO userInfoQueryVO,
                                @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return userInfoService.find(userInfoQueryVO, current, size);
    }

}
