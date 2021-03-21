package com.gulimall.auth.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author aqiang9  2020-09-02
 */
@Getter
@Setter
@ToString
public class UserRegisterVo {

    @NotEmpty(message = "用户名必须提交")
    @Length(min = 6 , max = 18 , message = "用户名必须是6-18位")
    private String username ;

    @NotEmpty(message = "密码必须提交")
    @Pattern(regexp = "^[a-z0-9_-]{6,18}$" ,message = "密码必须是6-18位,数字、字母、_、- 组成" )
    private String password;

    @NotEmpty(message = "手机号必须填写")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$" ,message = "电话号码不合法")
    private String mobile;


    @NotEmpty(message = "验证码必须填写")
    private String code;


}
