package com.gulimall.member.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author aqiang9  2020-09-03
 */
@Getter
@Setter
public class WeiboSocialUserInfo {
    private long id;
    private String screen_name;
    private String name;
    private String province;
    private String city;
    private String location;
    private String description;
    private String url;
    private String profile_image_url;
    private String domain;
    private String gender;
    private int followers_count;
    private int friends_count;
    private int statuses_count;
    private int favourites_count;
//    private Date created_at;
    private boolean following;
    private boolean allow_all_act_msg;
    private boolean geo_enabled;
    private boolean verified;
    private boolean allow_all_comment;
    private String avatar_large;
    private String verified_reason;
    private boolean follow_me;
    private int online_status;
    private int bi_followers_count;
}
