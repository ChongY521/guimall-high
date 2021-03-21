package com.gulimall.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基本信息  放入 session
 * @author aqiang9  2020-09-03
 */
@Getter
@Setter
@ToString
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = -5601507033335315457L;
    private Long id;
    /**
     * 会员等级id
     */
    private Long levelId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
}
