package com.xftxyz.doctorarrival.domain.hospital;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 医院
 */
@Data
@Document("Hospital")
public class Hospital {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除（0：未删除，1：已删除）
     */
    // private Integer isDeleted;

    /**
     * 医院编号
     */
    @Indexed(unique = true) // 唯一索引
    private String hospitalCode;

    /**
     * 医院名称
     */
    @Indexed // 普通索引
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
     * 状态 0：未上线 1：已上线
     */
    private Integer status;

    /**
     * 预约规则
     */
    private BookingRule bookingRule;

    // public void setBookingRule(String bookingRule) {
    // this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule.class);
    // }

}
