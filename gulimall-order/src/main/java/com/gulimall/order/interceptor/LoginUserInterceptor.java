package com.gulimall.order.interceptor;

import com.gulimall.common.constant.SessionConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author aqiang9  2020-09-24
 */
public class LoginUserInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{

		HttpSession session = request.getSession();

		Object attribute = session.getAttribute(SessionConstant.USER_INFO_KEY);

		if (attribute == null ){
			// 去登录
			request.getSession().setAttribute("msg" , "请先登录");
			response.sendRedirect( "http://auth.gulimall.com/login.html?orginUrl=http://order.gulimall.com/confirm.html");
			return false ;

		}
		return true ;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{

	}
}
