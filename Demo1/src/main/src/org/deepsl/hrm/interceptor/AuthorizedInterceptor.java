package org.deepsl.hrm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.util.common.HrmConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 判断用户权限的Spring MVC的拦截器
 */
public class AuthorizedInterceptor  implements HandlerInterceptor {

	/** 定义不需要拦截的请求 */
	private static final String[] IGNORE_URI = {"/loginForm", "/login","/404.html"};

	/**
	 * 该方法需要preHandle方法的返回值为true时才会执行。
	 * 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		return true;
	}


	/**
	 * 这个方法在preHandle方法返回值为true的时候才会执行。
	 * 执行时间是在处理器进行处理之 后，也就是在Controller的方法调用之后执行。
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//		User user = (User) httpServletRequest.getSession(false).getAttribute("user_session");
//		if(user != null){
//			// 客户端跳转到main页面
//			modelAndView.setViewName("redirect:/main");
//		}else{
//			// 设置登录失败提示信息
//			modelAndView.addObject("message", "您还未登录!请先登录");
//			// 服务器内部跳转到登录页面
//			modelAndView.setViewName("forward:/loginForm");
//		}
	}

	/**
	 * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
	 * 当preHandle的返回值为false的时候整个请求就结束了。
	 * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}








}
