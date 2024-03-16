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

    @Operation(summary = "新增用户",
               description = "此接口用于接收并保存一个非空的UserInfo对象作为新的用户数据，成功保存到数据库后返回true。")
    @PostMapping("/save")
    public Boolean save(@RequestBody UserInfo userInfo) {
        return userInfoService.saveWarp(userInfo);
    }

    @Operation(summary = "删除用户",
               description = "根据传入的用户ID（最小值为1）从数据库中删除对应用户，删除成功后返回true。")
    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.removeByIdWarp(id);
    }

    @Operation(summary = "批量删除用户",
               description = "接收一个非空的用户ID列表，批量从数据库中删除指定的用户，删除成功后返回true。")
    @DeleteMapping("/remove/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<Long> idList) {
        return userInfoService.removeByIdsWarp(idList);
    }

    @Operation(summary = "修改用户",
               description = "接收一个非空的UserInfo对象作为更新用户数据，根据其ID进行更新操作，成功更新到数据库后返回true。")
    @PutMapping("/update")
    public Boolean update(@RequestBody UserInfo userInfo) {
        return userInfoService.updateByIdWarp(userInfo);
    }

    @Operation(summary = "设置用户状态",
               description = "根据传入的用户ID（最小值为1）和状态（0或1）更新用户的启用禁用状态，成功更新到数据库后返回true。")
    @PutMapping("/status/{id}/{status}")
    public Boolean setStatus(@PathVariable("id") @Min(1) Long id,
                             @PathVariable("status") @Min(0) @Max(1) Integer status) {
        return userInfoService.setStatus(id, status);
    }

    @Operation(summary = "通过ID获取用户",
               description = "根据传入的用户ID（最小值为1）从数据库中获取对应的用户信息。")
    @GetMapping("/id/{id}")
    public UserInfo getById(@PathVariable("id") @Min(1) Long id) {
        return userInfoService.getByIdWarp(id);
    }

    @Operation(summary = "条件查询用户带分页",
               description = "接收一个非空的UserInfoQueryVO对象作为查询条件，并指定当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合条件的用户信息分页列表。")
    @PostMapping("/find")
    public IPage<UserInfo> find(@RequestBody UserInfoQueryVO userInfoQueryVO,
                                @RequestParam(value = "current", defaultValue = "1") @Min(1) Long current,
                                @RequestParam(value = "size", defaultValue = "20") @Min(1) Long size) {
        return userInfoService.find(userInfoQueryVO, current, size);
    }

    @NoWrap
    @Operation(summary = "用户数据统计",
               description = "接收一个非空的UserStatisticVO对象作为统计条件，根据其中参数对用户数据进行统计计算，返回统计结果。")
    @PostMapping("/inner/statistic")
    public UserStatisticVO statistic(@RequestBody UserStatisticVO userStatisticVO) {
        return userInfoService.statistic(userStatisticVO);
    }

}
