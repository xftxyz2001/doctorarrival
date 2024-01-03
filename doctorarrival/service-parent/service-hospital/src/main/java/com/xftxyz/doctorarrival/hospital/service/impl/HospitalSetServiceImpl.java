package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.hospital.mapper.HospitalSetMapper;
import com.xftxyz.doctorarrival.hospital.service.HospitalSetService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalSetQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author 25810
 * @description 针对表【hospital_set(医院设置表)】的数据库操作Service实现
 * @createDate 2024-01-03 16:16:36
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet>
        implements HospitalSetService {

    @Override
    public IPage<HospitalSet> find(HospitalSetQueryVO hospitalSetQueryVO, Long current, Long size) {

        LambdaQueryWrapper<HospitalSet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(hospitalSetQueryVO.getHospitalCode()), HospitalSet::getHospitalCode,
                hospitalSetQueryVO.getHospitalCode());
        lambdaQueryWrapper.like(StringUtils.hasText(hospitalSetQueryVO.getHospitalName()), HospitalSet::getHospitalName,
                hospitalSetQueryVO.getHospitalName());

        lambdaQueryWrapper.between(!ObjectUtils.isEmpty(hospitalSetQueryVO.getCreateTimeFrom()) && !ObjectUtils.isEmpty(hospitalSetQueryVO.getCreateTimeTo()),
                HospitalSet::getCreateTime, hospitalSetQueryVO.getCreateTimeFrom(), hospitalSetQueryVO.getCreateTimeTo());
        return baseMapper.selectPage(new Page<>(current, size), lambdaQueryWrapper);
    }

    @Override
    public Boolean setStatus(Long id, Integer status) {
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setId(id);
        hospitalSet.setStatus(status);
        return baseMapper.updateById(hospitalSet) > 0;
    }
}
