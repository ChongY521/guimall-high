package com.gulimall.auth.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author aqiang9  2020-09-02
 */
@Getter
@Setter
public class UserLoginVo {
    @NotEmpty(message = "账号不能为空")
    private String loginAccount ;
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 来源地址
     */
    private String originUrl ;
}

