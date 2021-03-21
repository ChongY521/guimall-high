package com.gulimall.member.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aqiang9  2020-09-03
 */
@Getter
@Setter
public class SocialUser {
    private String access_token;
    private String expires_in;
    private String remind_in;
    private String uid;
}
