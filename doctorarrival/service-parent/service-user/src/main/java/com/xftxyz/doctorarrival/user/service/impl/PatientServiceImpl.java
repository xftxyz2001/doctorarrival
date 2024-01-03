package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.domain.user.Patient;
import com.xftxyz.doctorarrival.user.service.PatientService;
import com.xftxyz.doctorarrival.user.mapper.PatientMapper;
import org.springframework.stereotype.Service;

/**
* @author 25810
* @description 针对表【patient(病人信息表)】的数据库操作Service实现
* @createDate 2024-01-03 23:44:16
*/
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient>
    implements PatientService{

}




