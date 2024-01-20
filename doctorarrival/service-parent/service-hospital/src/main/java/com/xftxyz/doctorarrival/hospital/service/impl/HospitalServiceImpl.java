package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.common.client.DictApiClient;
import com.xftxyz.doctorarrival.domain.common.Dict;
import com.xftxyz.doctorarrival.domain.hospital.Hospital;
import com.xftxyz.doctorarrival.enumeration.DictCodeEnum;
import com.xftxyz.doctorarrival.hospital.repository.HospitalRepository;
import com.xftxyz.doctorarrival.hospital.service.HospitalService;
import com.xftxyz.doctorarrival.vo.hospital.HospitalQueryVO;
import com.xftxyz.doctorarrival.vo.hospital.HospitalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    private final DictApiClient dictApiClient;

    @Override
    public List<HospitalVO> findHospitalByHospitalName(String hospitalName) {
        List<Hospital> hospitalList = hospitalRepository.findByHospitalNameLike(hospitalName);
        return warptoHospitalVO(hospitalList);
    }

    @Override
    public HospitalVO findHospitalByHospitalCode(String hospitalCode) {
        Hospital hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        return warptoHospitalVO(hospital);
    }

    @Override
    public IPage<HospitalVO> findHospitalPage(HospitalQueryVO hospitalQueryVO, Long current, Long size) {
        List<Hospital> hospitalList = hospitalRepository.findByHospitalTypeAndProvinceCodeAndCityCodeAndDistrictCode(
                hospitalQueryVO.getHospitalType(), hospitalQueryVO.getProvinceCode(), hospitalQueryVO.getCityCode(),
                hospitalQueryVO.getDistrictCode());
        Page<HospitalVO> hospitalPage = new Page<>(current, size, hospitalList.size());
        int start = (int) ((current - 1) * size);
        List<Hospital> hospitalListPart = hospitalList.stream().skip(start).limit(size).toList();
        List<HospitalVO> hospitalVOList = warptoHospitalVO(hospitalListPart);
        return hospitalPage.setRecords(hospitalVOList);
    }

    private List<HospitalVO> warptoHospitalVO(List<Hospital> hospitalList) {
        Map<String, String> hospitalTypeMap = dictApiClient.getDictMapByDictCodeInner(DictCodeEnum.HOSPITAL_TYPE.getCode());
        Map<String, String> administrativeDivisionsMap = dictApiClient.getAdministrativeDivisionsMapInner();
        return hospitalList.stream().map(hospital -> {
            HospitalVO hospitalVO = new HospitalVO();
            hospitalVO.setHospitalCode(hospital.getHospitalCode());
            hospitalVO.setHospitalName(hospital.getHospitalName());
            hospitalVO.setHospitalType(hospitalTypeMap.get(hospital.getHospitalType()));
            hospitalVO.setProvince(administrativeDivisionsMap.get(hospital.getProvinceCode()));
            hospitalVO.setCity(administrativeDivisionsMap.get(hospital.getCityCode()));
            hospitalVO.setDistrict(administrativeDivisionsMap.get(hospital.getDistrictCode()));
            hospitalVO.setAddress(hospital.getAddress());
            hospitalVO.setLogoData(hospital.getLogoData());
            hospitalVO.setIntro(hospital.getIntro());
            hospitalVO.setRoute(hospital.getRoute());
            hospitalVO.setBookingRule(hospital.getBookingRule());

            return hospitalVO;
        }).toList();
    }

    private HospitalVO warptoHospitalVO(Hospital hospital) {
        Map<String, String> hospitalTypeMap = dictApiClient.getDictMapByDictCodeInner(DictCodeEnum.HOSPITAL_TYPE.getCode());
        Map<String, String> administrativeDivisionsMap = dictApiClient.getAdministrativeDivisionsMapInner();
        HospitalVO hospitalVO = new HospitalVO();
        hospitalVO.setHospitalCode(hospital.getHospitalCode());
        hospitalVO.setHospitalName(hospital.getHospitalName());
        hospitalVO.setHospitalType(hospitalTypeMap.get(hospital.getHospitalType()));
        hospitalVO.setProvince(administrativeDivisionsMap.get(hospital.getProvinceCode()));
        hospitalVO.setCity(administrativeDivisionsMap.get(hospital.getCityCode()));
        hospitalVO.setDistrict(administrativeDivisionsMap.get(hospital.getDistrictCode()));
        hospitalVO.setAddress(hospital.getAddress());
        hospitalVO.setLogoData(hospital.getLogoData());
        hospitalVO.setIntro(hospital.getIntro());
        hospitalVO.setRoute(hospital.getRoute());
        hospitalVO.setBookingRule(hospital.getBookingRule());

        return hospitalVO;
    }
}
