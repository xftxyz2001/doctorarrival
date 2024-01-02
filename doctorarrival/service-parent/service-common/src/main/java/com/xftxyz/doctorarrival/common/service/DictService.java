package com.xftxyz.doctorarrival.common.service;

import com.xftxyz.doctorarrival.domain.common.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

/**
 * @author 25810
 * @description 针对表【dict(数据字典表)】的数据库操作Service
 * @createDate 2023-12-30 00:51:50
 */
public interface DictService extends IService<Dict> {

    /**
     * 导入数据字典
     *
     * @param file 文件
     * @return 是否成功
     */
    Boolean importDict(MultipartFile file);

    /**
     * 导出数据字典
     */
    Resource exportDict();

    List<Dict> getDictChildrenByParentId(Integer parentId);

    List<Dict> getDictChildrenByDictCode(String dictCode);
}
