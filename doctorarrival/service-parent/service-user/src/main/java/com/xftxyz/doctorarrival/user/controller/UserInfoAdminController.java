package com.xftxyz.doctorarrival.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xftxyz.doctorarrival.annotation.NoWrap;
import com.xftxyz.doctorarrival.domain.user.UserInfo;
import com.xftxyz.doctorarrival.user.service.UserInfoService;
import com.xftxyz.doctorarrival.vo.user.UserInfoQueryVO;
import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "用户管理")
@RequestMapping("/admin/user/info")
public class UserInfoAdminController {

    private final UserInfoService userInfoService;

    @Operation(summary = "新增用户")
    @PostMapping("/save")
    public Boolean save(@RequestBody UserInfo userInfo) {
        return userInfoService.saveWarp(userInfo);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除用户")
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return userInfoService.removeByIdsWarp(idList);
    }

    @Operation(summary = "修改用户")
    @PutMapping("/update")
    public Boolean update(@RequestBody UserInfo userInfo) {
        return userInfoService.updateByIdWarp(userInfo);
    }

    @Operation(summary = "设置用户状态")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(0) @Max(1) Integer status) {
        return userInfoService.setStatus(id, status);
    }

    @Operation(summary = "通过ID获取用户")
    @GetMapping("/id/{id}")
    public UserInfo getById(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询用户带分页")
    @PostMapping("/find")
    public IPage<UserInfo> find(@RequestBody UserInfoQueryVO userInfoQueryVO,
                                @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return userInfoService.find(userInfoQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "用户数据统计")
    @PostMapping("/inner/statistic")
    public UserStatisticVO statistic(@RequestBody UserStatisticVO userStatisticVO) {
        return userInfoService.statistic(userStatisticVO);
    }

}
