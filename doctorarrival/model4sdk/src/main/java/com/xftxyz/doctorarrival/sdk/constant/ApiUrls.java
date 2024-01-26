package com.xftxyz.doctorarrival.sdk.constant;

public interface ApiUrls {

    // 获取密钥
    String UPDATE_SECRET_KEY = "/key/"; // + hospitalCode

    // 更新医院信息
    String UPDATE_HOSPITAL = "/hospital";

    // 更新科室信息
    String UPDATE_DEPARTMENT = "/department";

    // 更新排班信息
    String UPDATE_SCHEDULE = "/schedule";

    // 删除科室信息
    String DELETE_DEPARTMENT = "/remove/department";

    // 删除排班信息
    String DELETE_SCHEDULE = "/remove/schedule";

    // 批量更新科室信息
    String UPDATE_DEPARTMENTS = "/departments";

    // 批量更新排班信息
    String UPDATE_SCHEDULES = "/schedules";

    // 批量删除科室信息
    String DELETE_DEPARTMENTS = "/remove/departments";

    // 批量删除排班信息
    String DELETE_SCHEDULES = "/remove/schedules";

    // ----------

    // 下单
    String ORDER = "/order";

    // 更新订单状态
    String UPDATE_ORDER_STATUS = "/order/status";

}
