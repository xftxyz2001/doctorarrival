package com.xftxyz.doctorarrival.hospital.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.common.client.DictApiClient;
import com.xftxyz.doctorarrival.domain.hospital.BookingRule;
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

    @Override
    public BookingRule getBookingRule(String hospitalCode) {
        Hospital hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        return hospital.getBookingRule();
    }

    private List<HospitalVO> warptoHospitalVO(List<Hospital> hospitalList) {
        Map<String, String> hospitalTypeMap = dictApiClient.getDictMapByDictCodeInner(DictCodeEnum.HOSPITAL_TYPE.getCode());
        return hospitalList.stream().map(hospital -> {
            HospitalVO hospitalVO = new HospitalVO();
            hospitalVO.setHospitalCode(hospital.getHospitalCode());
            hospitalVO.setHospitalName(hospital.getHospitalName());
            hospitalVO.setHospitalType(hospitalTypeMap.get(hospital.getHospitalType()));

            List<String> administrativeDivisionsList = dictApiClient.getAdministrativeDivisionsListInner(hospital.getProvinceCode(), hospital.getCityCode(), hospital.getDistrictCode());
            hospitalVO.setProvince(administrativeDivisionsList.get(0));
            hospitalVO.setCity(administrativeDivisionsList.get(1));
            hospitalVO.setDistrict(administrativeDivisionsList.get(2));

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
        HospitalVO hospitalVO = new HospitalVO();
        hospitalVO.setHospitalCode(hospital.getHospitalCode());
        hospitalVO.setHospitalName(hospital.getHospitalName());
        hospitalVO.setHospitalType(hospitalTypeMap.get(hospital.getHospitalType()));

        List<String> administrativeDivisionsList = dictApiClient.getAdministrativeDivisionsListInner(hospital.getProvinceCode(), hospital.getCityCode(), hospital.getDistrictCode());
        hospitalVO.setProvince(administrativeDivisionsList.get(0));
        hospitalVO.setCity(administrativeDivisionsList.get(1));
        hospitalVO.setDistrict(administrativeDivisionsList.get(2));

        hospitalVO.setAddress(hospital.getAddress());
        hospitalVO.setLogoData(hospital.getLogoData());
        hospitalVO.setIntro(hospital.getIntro());
        hospitalVO.setRoute(hospital.getRoute());
        hospitalVO.setBookingRule(hospital.getBookingRule());

        return hospitalVO;

    }
}
