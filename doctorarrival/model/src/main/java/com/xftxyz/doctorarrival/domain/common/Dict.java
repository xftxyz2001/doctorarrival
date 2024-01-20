package com.xftxyz.doctorarrival.domain.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典表
 *
 * @TableName dict
 */
@TableName(value = "dict")
@Data
public class Dict implements Serializable {
    /**
     * id 手动输入
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 上级id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 编码
     */
    @TableField(value = "dict_code")
    private String dictCode;

    /**
     * 是否包含子节点
     */
    @TableField(exist = false)
    private boolean hasChildren;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}