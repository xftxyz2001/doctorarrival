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

    // ----------

    // 下单
    String ORDER = BASE_URL + "/order";

    // 更新订单状态
    String UPDATE_ORDER_STATUS = BASE_URL + "/order/status";

}
