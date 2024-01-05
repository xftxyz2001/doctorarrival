package com.xftxyz.doctorarrival.sdk.vo.api;

import lombok.Data;

import java.util.List;

/**
 * 更新医院请求
 */
@Data
public class UpdateHospitalRequest {

    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 医院类型
     */
    private String hospitalType;
    /**
     * 省code
     */
    private String provinceCode;
    /**
     * 市code
     */
    private String cityCode;

    /**
     * 区code
     */
    private String districtCode;

    /**
     * 详情地址
     */
    private String address;
    /**
     * 医院logo
     */
    private String logoData;

    /**
     * 医院简介
     */
    private String intro;

    /**
     * 坐车路线
     */
    private String route;
    /**
     * 预约规则
     */
    private BookingRule bookingRule;

    public static class BookingRule {

        /**
         * 预约周期
         */
        private Integer cycle;

        /**
         * 放号时间
         */
        private String releaseTime;

        /**
         * 停挂时间
         */
        private String stopTime;

        /**
         * 退号截止天数（如：就诊前一天为-1，当天为0）
         */
        private Integer quitDay;

        /**
         * 退号时间
         */
        private String quitTime;

        /**
         * 预约规则
         */
        private List<String> rule;

    }

}
