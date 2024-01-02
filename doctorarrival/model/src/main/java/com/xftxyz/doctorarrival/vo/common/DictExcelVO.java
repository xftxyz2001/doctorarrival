package com.xftxyz.doctorarrival.vo.common;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DictExcelVO {
    /**
     * id 手动输入
     */
    @ExcelProperty(value = "id", index = 0)
    private Long id;

    /**
     * 上级id
     */
    @ExcelProperty(value = "上级id", index = 1)
    private Long parentId;

    /**
     * 值
     */
    @ExcelProperty(value = "值", index = 2)
    private String value;

    /**
     * 编码
     */
    @ExcelProperty(value = "编码", index = 3)
    private String dictCode;
}
