package com.xftxyz.doctorarrival.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {


    FAILED(-1, "失败"), // 失败
    SUCCESS(0, "成功"), // 成功

    // 参数错误
    PARAM_ERROR(400, "非法参数"),
    PARAM_VALID_ERROR(401, "参数校验失败"),

    // 用户相关
    USER_NOT_LOGIN(3000, "用户未登录"),
    TOKEN_EXPIRED(3001, "token已过期"),
    PERMISSION_DENIED(3100, "权限不足"),

    // 文件相关
    FILE_UPLOAD_FAILED(6000, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(6001, "文件下载失败"),
    FILE_NOT_EXIST(6002, "文件不存在"),
    FILE_DELETE_FAILED(6003, "文件删除失败"),
    ;

    private final Integer code;
    private final String message;

}
