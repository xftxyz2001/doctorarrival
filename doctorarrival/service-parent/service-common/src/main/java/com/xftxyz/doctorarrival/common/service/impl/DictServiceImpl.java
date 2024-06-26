package com.xftxyz.doctorarrival.common.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.common.mapper.DictMapper;
import com.xftxyz.doctorarrival.common.service.DictService;
import com.xftxyz.doctorarrival.domain.common.Dict;
import com.xftxyz.doctorarrival.enumeration.DictCodeEnum;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.vo.common.DictExcelVO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 25810
 * @description 针对表【dict(数据字典表)】的数据库操作Service实现
 * @createDate 2023-12-30 00:51:50
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
        implements DictService {

    @Override
    @Transactional
    public Boolean importDict(MultipartFile file) {
        try {
            // 清空数据
            remove(null);
            // 读取数据
            List<DictExcelVO> dictExcelVOList = EasyExcel.read(file.getInputStream()).head(DictExcelVO.class).sheet().doReadSync();

            // 转为Dict
            List<Dict> dictList = dictExcelVOList.stream().map(dictExcelVO -> {
                Dict dict = new Dict();
                dict.setId(dictExcelVO.getId());
                dict.setParentId(dictExcelVO.getParentId());
                dict.setName(dictExcelVO.getName());
                dict.setValue(dictExcelVO.getValue());
                dict.setDictCode(dictExcelVO.getDictCode());
                return dict;
            }).toList();
            // 保存
            return saveBatch(dictList);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.DATA_IMPORT_FAILED);
        }
    }

    @Override
    public Resource exportDict() {
        try {
            // 查询数据
            List<Dict> dictList = baseMapper.selectList(null);

            // 转为DictExcelVO
            List<DictExcelVO> dictExcelVOList = dictList.stream().map(dict -> {
                DictExcelVO dictExcelVO = new DictExcelVO();
                dictExcelVO.setId(dict.getId());
                dictExcelVO.setParentId(dict.getParentId());
                dictExcelVO.setName(dict.getName());
                dictExcelVO.setValue(dict.getValue());
                dictExcelVO.setDictCode(dict.getDictCode());
                return dictExcelVO;
            }).toList();

            // 写出
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            EasyExcel.write(outputStream, DictExcelVO.class).sheet("dict").doWrite(dictExcelVOList);
            return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.DATA_EXPORT_FAILED);
        }
    }

    @Override
    public List<Dict> getDictChildrenByParentId(Long parentId) {
        LambdaQueryWrapper<Dict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, parentId);
        List<Dict> dictList = baseMapper.selectList(lambdaQueryWrapper);
        dictList.forEach(this::fillHasChildren);
        return dictList;
    }

    @Override
    public List<Dict> getDictChildrenByDictCode(String dictCode) {
        LambdaQueryWrapper<Dict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getDictCode, dictCode);
        Dict dict = baseMapper.selectOne(lambdaQueryWrapper);
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, dict.getId());
        List<Dict> dictList = baseMapper.selectList(lambdaQueryWrapper);
        dictList.forEach(this::fillHasChildren);
        return dictList;
    }

    @Override
    public Map<String, String> getDictMapByDictCodeInner(String dictCode) {
        LambdaQueryWrapper<Dict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getDictCode, dictCode);
        Dict dict = baseMapper.selectOne(lambdaQueryWrapper);
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, dict.getId());
        List<Dict> dictList = baseMapper.selectList(lambdaQueryWrapper);
        return dictList.stream().collect(Collectors.toMap(Dict::getValue, Dict::getName));
    }

    @Override
    public List<String> getAdministrativeDivisionsListInner(String provinceCode, String cityCode, String districtCode) {
        LambdaQueryWrapper<Dict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getDictCode, DictCodeEnum.ADMINISTRATIVE_DIVISIONS.getCode());
        Dict dict = baseMapper.selectOne(lambdaQueryWrapper);

        List<String> administrativeDivisionsNameList = new ArrayList<>();
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, dict.getId());
        lambdaQueryWrapper.eq(Dict::getValue, provinceCode);
        Dict province = baseMapper.selectOne(lambdaQueryWrapper);
        administrativeDivisionsNameList.add(province.getName());

        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, province.getId());
        lambdaQueryWrapper.eq(Dict::getValue, cityCode);
        Dict city = baseMapper.selectOne(lambdaQueryWrapper);
        administrativeDivisionsNameList.add(city.getName());

        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, city.getId());
        lambdaQueryWrapper.eq(Dict::getValue, districtCode);
        Dict district = baseMapper.selectOne(lambdaQueryWrapper);
        administrativeDivisionsNameList.add(district.getName());
        return administrativeDivisionsNameList;
    }

    private void fillHasChildren(Dict dict) {
        LambdaQueryWrapper<Dict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dict::getParentId, dict.getId());
        dict.setHasChildren(baseMapper.selectCount(lambdaQueryWrapper) > 0);
    }
}
