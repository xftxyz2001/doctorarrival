package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.domain.hospital.HospitalSet;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.hospital.mapper.HospitalSetMapper;
import com.xftxyz.doctorarrival.hospital.service.HospitalSetService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.vo.hospital.HospitalSetQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

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
        // 根据id查询医院设置信息
        HospitalSet hospitalSet = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(hospitalSet)) {
            throw new BusinessException(ResultEnum.HOSPITAL_NOT_EXIST);
        }
        hospitalSet.setStatus(status);
        if (baseMapper.updateById(hospitalSet) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean saveWarp(HospitalSet hospitalSet) {
        // 根据医院编码查询医院设置信息
        LambdaQueryWrapper<HospitalSet> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HospitalSet::getHospitalCode, hospitalSet.getHospitalCode());
        HospitalSet existHospital = baseMapper.selectOne(lambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(existHospital)) {
            throw new BusinessException(ResultEnum.HOSPITAL_ALREADY_EXIST);
        }
        if (baseMapper.insert(hospitalSet) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_SAVE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean updateByIdWarp(HospitalSet hospitalSet) {
        // 根据id查询医院设置信息
        HospitalSet existHospital = baseMapper.selectById(hospitalSet.getId());
        if (ObjectUtils.isEmpty(existHospital)) {
            throw new BusinessException(ResultEnum.HOSPITAL_NOT_EXIST);
        }
        if (baseMapper.updateById(hospitalSet) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_UPDATE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdWarp(Long id) {
        if (baseMapper.deleteById(id) <= 0) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public Boolean removeByIdsWarp(List<Long> idList) {
        int num = baseMapper.deleteBatchIds(idList);
        if (num < idList.size()) {
            throw new BusinessException(ResultEnum.HOSPITAL_SET_DELETE_FAILED);
        }
        return true;
    }

    @Override
    public HospitalSet getByIdWarp(Long id) {
        HospitalSet hospitalSet = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(hospitalSet)) {
            throw new BusinessException(ResultEnum.HOSPITAL_NOT_EXIST);
        }
        return hospitalSet;
    }
}
