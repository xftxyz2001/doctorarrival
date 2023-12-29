package com.xftxyz.doctorarrival.domain.hospital;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 预约规则
 */
@Data
@Document("BookingRule")
public class BookingRule {

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

    // public void setRule(String rule) {
    // if (!StringUtils.isEmpty(rule)) {
    // this.rule = JSONArray.parseArray(rule, String.class);
    // }
    // }

}
