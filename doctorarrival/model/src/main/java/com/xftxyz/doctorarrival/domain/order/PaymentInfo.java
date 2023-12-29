package com.xftxyz.doctorarrival.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款记录表
 *
 * @TableName payment_info
 */
@TableName(value = "payment_info")
@Data
public class PaymentInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单交易号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * 商品描述
     */
    @TableField(value = "subject")
    private String subject;

    /**
     * 支付类型（1：微信，2：支付宝）
     */
    @TableField(value = "payment_type")
    private Integer paymentType;

    /**
     * 交易流水号（第三方平台订单号）
     */
    @TableField(value = "trade_no")
    private String tradeNo;

    /**
     * 支付金额（分）
     */
    @TableField(value = "total_amount")
    private Integer totalAmount;

    /**
     * 退款金额（分）
     */
    @TableField(value = "refund_amount")
    private Integer refundAmount;

    /**
     * 订单状态（0：未支付，1：已支付，2：已取消，3：已完成，4：退款中，5：已退款）
     */
    @TableField(value = "payment_status")
    private Integer paymentStatus;

    /**
     * 回调时间
     */
    @TableField(value = "callback_time")
    private Date callbackTime;

    /**
     * 回调内容
     */
    @TableField(value = "callback_content")
    private String callbackContent;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除（0:未删除，1:已删除）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}