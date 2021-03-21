package com.gulimall.auth.feign;

import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author aqiang9  2020-09-02
 */
@FeignClient("gulimall-third-party")
public interface ThreadPartFeignService {

    @GetMapping("/sms/sendCode")
    CommonResult<Object> sendSmsCode(@RequestParam("phone") String phone, @RequestParam("phone") String code);
}
