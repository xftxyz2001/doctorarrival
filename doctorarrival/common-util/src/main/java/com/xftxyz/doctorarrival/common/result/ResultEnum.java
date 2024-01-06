package com.xftxyz.doctorarrival.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {

    // 通用
    FAILED(-1, "失败"), // 失败
    SUCCESS(0, "成功"), // 成功

    // 1公共模块
    DATA_IMPORT_FAILED(111, "数据导入失败"),
    DATA_EXPORT_FAILED(112, "数据导出失败"),

    // 2医院相关
    HOSPITAL_NOT_EXIST(200, "医院不存在"),
    HOSPITAL_ALREADY_EXIST(201, "医院已存在"),
    HOSPITAL_SET_SAVE_FAILED(202, "医院设置保存失败"),
    HOSPITAL_SET_UPDATE_FAILED(203, "医院设置更新失败"),
    HOSPITAL_SET_DELETE_FAILED(204, "医院设置删除失败"),
    HOSPITAL_SIGN_ERROR(205, "签名错误"),
    REQUEST_ERROR(206, "请求错误"),
    RESPONSE_ERROR(207, "响应错误"),

    // 3用户相关
    USER_NOT_LOGIN(300, "用户未登录"),
    TOKEN_EXPIRED(301, "token已过期"),
    PERMISSION_DENIED(310, "权限不足"),
    USER_ALREADY_EXIST(311, "用户已存在"),
    USER_SAVE_FAILED(312, "用户保存失败"),
    USER_DELETE_FAILED(313, "用户删除失败"),
    USER_NOT_EXIST(314, "用户不存在"),
    USER_UPDATE_FAILED(315, "用户更新失败"),

    // 4参数错误
    PARAM_ERROR(400, "非法参数"),
    PARAM_VALID_ERROR(401, "参数校验失败"),

    // 5短信相关
    SMS_VERIFICATION_CODE_SEND_FAILED(501, "短信验证码发送失败"),
    SMS_VERIFICATION_CODE_REQUEST_TOO_FREQUENT(502, "请求验证码过于频繁"),
    SMS_VERIFICATION_CODE_EXPIRED(503, "验证码失效"),
    SMS_VERIFICATION_CODE_ERROR(504, "验证码错误"),

    // 6文件相关
    FILE_UPLOAD_FAILED(600, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(601, "文件下载失败"),
    FILE_DELETE_FAILED(602, "文件删除失败"),
    FILE_LIST_FAILED(603, "文件列表获取失败"),

    // 7订单相关
    ORDER_NOT_EXIST(700, "订单不存在"),
    ORDER_ALREADY_EXIST(701, "订单已存在"), //
    ORDER_SAVE_FAILED(702, "订单保存失败"),
    ORDER_DELETE_FAILED(703, "订单删除失败"),
    ORDER_UPDATE_FAILED(704, "订单更新失败"),
    ORDER_STATUS_UPDATE_FAILED(705, "订单状态更新失败"),
    ORDER_STATUS_NOT_ALLOW(706, "订单状态不允许"),
    ;

    private final Integer code;
    private final String message;

    }
