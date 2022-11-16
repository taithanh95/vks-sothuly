package com.bitsco.vks.sothuly.feign;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.sothuly.model.Accused;
import com.bitsco.vks.sothuly.model.Case;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(Constant.FEIGN_CLIENT.MANAGE)
public interface ManageServiceFeignAPI {
    @GetMapping(value = "ping")
    String ping();

    @PostMapping("/case/findById/")
    ResponseBody findById(@RequestBody Case c) throws Exception;

    @PostMapping("/accused/findById/")
    ResponseBody findById(@RequestBody Accused accused) throws Exception;
}
