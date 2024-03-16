package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.common.client.DictApiClient;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.enumeration.DictCodeEnum;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.user.mapper.PatientMapper;
import com.xftxyz.doctorarrival.user.service.PatientService;
import com.xftxyz.doctorarrival.vo.user.PatientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * @author 25810
 * @description 针对表【patient(病人信息表)】的数据库操作Service实现
 * @createDate 2024-01-03 23:44:16
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient>
        implements PatientService {

    private final DictApiClient dictApiClient;

    @Override
    public List<PatientVO> getPatientList(String userId) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId, userId);
        List<Patient> patients = baseMapper.selectList(lambdaQueryWrapper);
        return warptoPatientVO(patients);
    }

    private List<PatientVO> warptoPatientVO(List<Patient> patients) {
        Map<String, String> certificatesTypeMap = dictApiClient.getDictMapByDictCodeInner(DictCodeEnum.CERTIFICATES_TYPE.getCode());
        return patients.stream().map(patient -> {
            PatientVO patientVO = new PatientVO();
            patientVO.setId(patient.getId());
            patientVO.setUserId(patient.getUserId());
            patientVO.setName(patient.getName());
            patientVO.setPhone(patient.getPhone());
            patientVO.setCertificatesType(patient.getCertificatesType());
            patientVO.setCertificatesTypeName(certificatesTypeMap.get(patient.getCertificatesType().toString()));
            patientVO.setCertificatesNo(patient.getCertificatesNo());
            patientVO.setGender(patient.getGender());
            patientVO.setMarry(patient.getMarry());
            patientVO.setBirthday(patient.getBirthday());
            patientVO.setInsured(patient.getInsured());
            patientVO.setCardNo(patient.getCardNo());
            patientVO.setContactsName(patient.getContactsName());
            patientVO.setContactsPhone(patient.getContactsPhone());
            patientVO.setStatus(patient.getStatus());

            return patientVO;
        }).toList();
    }

    private PatientVO warptoPatientVO(Patient patient) {
        Map<String, String> certificatesTypeMap = dictApiClient.getDictMapByDictCodeInner(DictCodeEnum.CERTIFICATES_TYPE.getCode());
        PatientVO patientVO = new PatientVO();
        patientVO.setId(patient.getId());
        patientVO.setUserId(patient.getUserId());
        patientVO.setName(patient.getName());
        patientVO.setPhone(patient.getPhone());
        patientVO.setCertificatesType(patient.getCertificatesType());
        patientVO.setCertificatesTypeName(certificatesTypeMap.get(patient.getCertificatesType().toString()));
        patientVO.setCertificatesNo(patient.getCertificatesNo());
        patientVO.setGender(patient.getGender());
        patientVO.setMarry(patient.getMarry());
        patientVO.setBirthday(patient.getBirthday());
        patientVO.setInsured(patient.getInsured());
        patientVO.setCardNo(patient.getCardNo());
        patientVO.setContactsName(patient.getContactsName());
        patientVO.setContactsPhone(patient.getContactsPhone());
        patientVO.setStatus(patient.getStatus());

        return patientVO;
    }

    @Override
    public PatientVO getPatientDetail(String userId, String patientId) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId, userId);
        lambdaQueryWrapper.eq(Patient::getId, patientId);
        Patient patient = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BusinessException(ResultEnum.PATIENT_NOT_EXIST);
        }
        return warptoPatientVO(patient);
    }

    @Override
    public Boolean removePatient(String userId, String patientId) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId, userId);
        lambdaQueryWrapper.eq(Patient::getId, patientId);
        Patient patient = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(patient)) {
            throw new BusinessException(ResultEnum.PATIENT_NOT_EXIST);
        }
        if (baseMapper.deleteById(patient.getId()) <= 0) {
            throw new BusinessException(ResultEnum.PATIENT_REMOVE_FAIL);
        }
        return true;
    }

    @Override
    public Boolean addPatient(Long userId, Patient patient) {
        patient.setUserId(userId);
        if (baseMapper.insert(patient) <= 0) {
            throw new BusinessException(ResultEnum.PATIENT_ADD_FAIL);
        }
        return true;
    }

    @Override
    public Boolean updatePatient(Long userId, Patient patient) {
        LambdaQueryWrapper<Patient> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Patient::getUserId, userId);
        lambdaQueryWrapper.eq(Patient::getId, patient.getId());
        Patient existPatient = baseMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtils.isEmpty(existPatient)) {
            throw new BusinessException(ResultEnum.PATIENT_NOT_EXIST);
        }

        existPatient.setName(patient.getName());
        existPatient.setPhone(patient.getPhone());
        existPatient.setCertificatesType(patient.getCertificatesType());
        existPatient.setCertificatesNo(patient.getCertificatesNo());
        existPatient.setGender(patient.getGender());
        existPatient.setMarry(patient.getMarry());
        existPatient.setBirthday(patient.getBirthday());
        existPatient.setInsured(patient.getInsured());
        existPatient.setCardNo(patient.getCardNo());
        existPatient.setContactsName(patient.getContactsName());
        existPatient.setContactsPhone(patient.getContactsPhone());

        if (baseMapper.updateById(patient) <= 0) {
            throw new BusinessException(ResultEnum.PATIENT_UPDATE_FAIL);
        }
        return true;
    }

    @Override
    public Patient getPatientDetailNoWarp(Long patientId) {
        return baseMapper.selectById(patientId);
    }
}
