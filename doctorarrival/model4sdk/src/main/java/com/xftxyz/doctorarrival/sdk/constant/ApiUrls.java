package com.xftxyz.doctorarrival.sdk.constant;

public interface ApiUrls {

    // 基础URL
    String BASE_URL = "http://localhost/api/hospital/side";

    // 获取密钥
    String UPDATE_SECRET_KEY = BASE_URL + "/key/"; // + hospitalCode

    // 更新医院信息
    String UPDATE_HOSPITAL = BASE_URL + "/hospital";

    // 更新科室信息
    String UPDATE_DEPARTMENT = BASE_URL + "/department";

    // 更新排班信息
    String UPDATE_SCHEDULE = BASE_URL + "/schedule";

    // 删除科室信息
    String DELETE_DEPARTMENT = BASE_URL + "/remove/department";

    // 删除排班信息
    String DELETE_SCHEDULE = BASE_URL + "/remove/schedule";

    // 批量更新科室信息
    String UPDATE_DEPARTMENTS = BASE_URL + "/departments";

    // 批量更新排班信息
    String UPDATE_SCHEDULES = BASE_URL + "/schedules";

    // 批量删除科室信息
    String DELETE_DEPARTMENTS = BASE_URL + "/remove/departments";

    // 批量删除排班信息
    String DELETE_SCHEDULES = BASE_URL + "/remove/schedules";

    // ----------

    // 下单
    String ORDER = "/order";

    // 更新订单状态
    String UPDATE_ORDER_STATUS = "/order/status";

}
