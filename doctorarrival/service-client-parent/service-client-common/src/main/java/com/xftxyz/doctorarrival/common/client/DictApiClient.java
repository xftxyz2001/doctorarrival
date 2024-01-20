package com.xftxyz.doctorarrival.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "service-common", path = "/api/common/dict")
@Repository
public interface DictApiClient {

    @GetMapping("/inner/map/code/{dictCode}")
    Map<String, String> getDictMapByDictCodeInner(@PathVariable("dictCode") String dictCode);

    @GetMapping("/inner/map/administrative/divisions")
    Map<String, String> getAdministrativeDivisionsMapInner();
}
