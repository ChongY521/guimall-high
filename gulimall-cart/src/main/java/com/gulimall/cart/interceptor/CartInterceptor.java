package com.gulimall.cart.interceptor;

import com.gulimall.cart.to.UserInfoTo;
import com.gulimall.common.constant.SessionConstant;
import com.gulimall.common.vo.UserInfoVo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author aqiang9  2020-09-08
 */
public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute(SessionConstant.USER_INFO_KEY);
        if (userInfo instanceof UserInfoVo) {
            UserInfoVo info = (UserInfoVo) userInfo;

            userInfoTo.setUserId(info.getId());
            // 创建一个临时用户 放到cookie
        }

        boolean flag = true;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (SessionConstant.COOKIE_NAME.equals(name)) {
                    userInfoTo.setUserKey(cookie.getValue());
                    flag = false;
                    userInfoTo.setTemp(true);
                    break;
                }
            }
        }
        if (flag) {
            String userKeyValue = UUID.randomUUID().toString();
            userInfoTo.setUserKey(userKeyValue);
            userInfoTo.setTemp(true);
        }
        threadLocal.set(userInfoTo);
        return true;
    }

    /**
     * 分配临时用户
     * @throws Exception
     */

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = threadLocal.get();
        if (userInfoTo.getTemp()) {
            Cookie cookie = new Cookie(SessionConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain(SessionConstant.DOMAIN_NAME);
            cookie.setMaxAge(SessionConstant.TEMP_USER_COOKIE_TIME);
            cookie.setPath(SessionConstant.COOKIE_PATH );
            response.addCookie(cookie);
        }
    }
}
