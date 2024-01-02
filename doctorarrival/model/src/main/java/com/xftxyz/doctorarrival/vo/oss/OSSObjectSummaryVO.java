package com.xftxyz.doctorarrival.vo.oss;

import lombok.Data;

import java.util.Date;

@Data
public class OSSObjectSummaryVO {

    /**
     * 文件名 key
     */
    private String name;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 最后修改时间
     */
    private Date lastModified;

    /**
     * 文件类型
     */
    private String type;
}
