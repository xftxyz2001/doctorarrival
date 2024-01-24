package com.xftxyz.doctorarrival.user.client;

import com.xftxyz.doctorarrival.vo.user.UserStatisticVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-user", path = "/admin/user/info", contextId = "userInfoAdmin")
@Repository
public interface UserInfoAdminClient {
    @PostMapping("/inner/statistic")
    UserStatisticVO statistic(@RequestBody UserStatisticVO userStatisticVO);
}
