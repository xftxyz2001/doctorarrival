package com.xftxyz.doctorarrival.vo.hospital;

import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 医院
 */
@Data
public class HospitalVO {

    /**
     * 医院编号
     */
    private String hospitalCode;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 医院类型
     */
    private String hospitalType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

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
     * 状态 0：未上线 1：已上线
     */
    // private Integer status;

    /**
     * 预约规则
     */
    private BookingRule bookingRule;

    // public void setBookingRule(String bookingRule) {
    // this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule.class);
    // }

}
