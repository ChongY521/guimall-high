package com.gulimall.thirdparty.controlller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aqiang9  2020-09-02
 */
@RestController
@RequestMapping("/sms")
public class SmsSendController {
    @Autowired
    SmsComponent smsComponent ;


    @GetMapping("/sendCode")
    public CommonResult sendSmsCode(@RequestParam("phone") String  phone , @RequestParam("phone") String code ){
        smsComponent.sendSms(phone , code );
        return CommonResult.ok() ;
    }


}
