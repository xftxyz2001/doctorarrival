package com.xftxyz.doctorarrival.domain.hospital;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 科室
 */
@Data
@Document("Department")
public class Department {

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
    @Indexed // 普通索引
    private String hospitalCode;

    /**
     * 科室编号
     */
    @Indexed(unique = true) // 唯一索引
    private String departmentCode;

    /**
     * 科室名称
     */
    private String departmentName;

    /**
     * 科室描述
     */
    private String intro;

    /**
     * 大科室编号
     */
    private String primaryDepartmentCode;

    /**
     * 大科室名称
     */
    private String primaryDepartmentName;

}
