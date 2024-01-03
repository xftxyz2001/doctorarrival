package com.xftxyz.doctorarrival.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {

    // 通用
    FAILED(-1, "失败"), // 失败
    SUCCESS(0, "成功"), // 成功

    // 公共模块
    DATA_IMPORT_FAILED(111, "数据导入失败"),

    // 医院相关
    HOSPITAL_NOT_EXIST(200, "医院设置不存在"),

    // 用户相关
    USER_NOT_LOGIN(300, "用户未登录"),
    TOKEN_EXPIRED(301, "token已过期"),
    PERMISSION_DENIED(310, "权限不足"),

    // 参数错误
    PARAM_ERROR(400, "非法参数"),
    PARAM_VALID_ERROR(401, "参数校验失败"),

    // 文件相关
    FILE_UPLOAD_FAILED(600, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(601, "文件下载失败"),
    FILE_NOT_EXIST(602, "文件不存在"),
    FILE_DELETE_FAILED(603, "文件删除失败"),
    ;

    private final Integer code;
    private final String message;

}
