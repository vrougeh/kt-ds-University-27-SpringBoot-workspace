package com.ktdsuniversity.edu.config.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * EndPoint에 접근하기전 세션이 존재할 경우 컨트롤러 실행되지 않고 게시글 목록조회 페이지로 이동시킨다.
 */
public class IllegalAccessInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("__LOGIN_DATA__")  != null) {
			response.sendRedirect("/");
			return false;
		}
		return true;
	}
	
	

}
