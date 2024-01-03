package com.xftxyz.doctorarrival.hospital.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.vo.hospital.HospitalSetQueryVO;

/**
 * @author 25810
 * @description 针对表【hospital_set(医院设置表)】的数据库操作Service
 * @createDate 2024-01-03 16:16:36
 */
public interface HospitalSetService extends IService<HospitalSet> {

    IPage<HospitalSet> find(HospitalSetQueryVO hospitalSetQueryVO, Long current, Long size);

    Boolean setStatus(Long id, Integer status);
}
