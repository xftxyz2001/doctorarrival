package com.xftxyz.doctorarrival.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xftxyz.doctorarrival.domain.user.UserLoginRecord;
import com.xftxyz.doctorarrival.user.mapper.UserLoginRecordMapper;
import com.xftxyz.doctorarrival.user.service.UserLoginRecordService;
import org.springframework.stereotype.Service;

/**
 * @author 25810
 * @description 针对表【user_login_record(登录记录表)】的数据库操作Service实现
 * @createDate 2024-01-03 23:44:16
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord>
        implements UserLoginRecordService {

}
