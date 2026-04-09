package com.ktdsuniversity.edu.config.interceptors;

import java.io.PrintWriter;

import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 로그인이 필요한(세션데이터가 필요한) URL
 * 1. 글 작성 페이지
 * 2. 글 작성 처리 URL
 * 3. 글 수정 페이지
 * 4. 글 수정 처리 URL
 * 5. 글 삭제 처리 URL
 * 6. 회원 정보 수정 페이지
 * 7. 회원 정보 수정 처리 URL
 * 8. 회원 삭제 처리 URL
 * 9. 회원 탈퇴 처리 URL
 * 10. 회원 로그아웃 URL
 * 위 URL 10개에  세션없이 접근할 경우(컨트롤러가 실행되기 전) 로그인 페이지로 이동시키기
 * 로그인이 필요없는(세션데이터가 필요없는) URL
 * 1. 회원가입 페이지
 * 2. 회원가입 처리 URL
 * 3. 로그인 페이지
 * 4. 로그인 처리 URL
 * 5. static resource(JS, CSS, Image)
 * 6. 게시글 목록페이지
 * 7. 게시글 내용 조회 페이지
 * 위 URL 5개중 1~4까지는 세션이 있는상태에서 접근이 불가해야한다.
 */
//											인터셉터의 기능 정의해둔 인터페이스
public class SessionInterceptor implements HandlerInterceptor{ 
	
	
	/**
	 * 컨트롤러가 실행되기 이전에 수행할 공통코드를 작성하는 영역
	 * 
	 * @param request  브라우저(클라이언트)가 요청한 내용 - header parameter file session ..
	 * @param response 컨트롤러가 실행되기 이전에 브라우저(클라이언트)로 응답을 보내는 역할(필요시 시용)
	 * @param handler  실행할 컨트롤러
	 * @return boolean 컨트롤러의 실행 여부 반환 true = 컨트롤러 실행 false = 컨트롤러 실행 x
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		
		//세션을 가져온다
		HttpSession session = request.getSession();
		//세션이 있는지 검사한다
		if(session.getAttribute("__LOGIN_DATA__")== null) {
			
			String pathName = request.getRequestURI();
			if(pathName.startsWith("/api/")) {
				String jsonResult = "{"
									+ "	\"status\": 403,"
									+ "	\"error\": \"권한이 부족합니다\""
									+ "}";
				//response data의 encoding을 UTF-8로 변경
				response.setCharacterEncoding("UTF-8");
				
				//response 되는 데이터의 타입을 "application/json"으로 세팅
				response.setContentType("application/json");
				//클라이언트에게 데이터 전송하는 객체
				PrintWriter writer =response.getWriter();
				writer.write(jsonResult);
				writer.flush();
				return false;
			}
			//세션이 없으면 컨트롤러는 실행시키지 않는다
			//대신 사용자에게 로그인 페이지를 보여준다 URL은 변동죄지 않아야 한다
			String loginPagePath = "/WEB-INF/views/members/login.jsp";
			//사용자에게 보여질 화면 설정
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPagePath);
			//브라우저에게 화면을 보내준다 url은 바뀌지않는다.
			dispatcher.forward(request, response);
			
			return false;
		}
		
		//세션이 있으면 컨트롤러 실행
		return true;
		
	}
	
	/**
	 * 컨트롤러가 실행된 이후에 수행할 공통코드를 작성하는 영역
	 * 예> 모델 데이터 추가 삭제 수정
	 * 
	 * @param request  브라우저(클라이언트)가 요청한 내용 - header parameter file session ..
	 * @param response 컨트롤러가 실행된 후 브라우저(클라이언트)로 응답을 보내는 역할(필요시 시용)
	 * @param handler  실행된 컨트롤러
	 * @param modelAndView 컨트롤러가 반환시킨 View(Template)와 Model
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		@Nullable ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * JSP를 HTML로 변환시켜 브라우저(클라이언트)로 반환하기 직전에 수행되는 영역
	 * 예> response 내용 첨부/제거.(파일, 텍스트, 이미지, 암호화), 처리되지 않고 던져진 예외 핸들링
	 * 
	 * @param request  브라우저(클라이언트)가 요청한 내용 - header parameter file session ..
	 * @param response 컨트롤러가 실행된 후 브라우저(클라이언트)로 응답을 보내는 역할(필요시 시용)
	 * @param handler  실행된 컨트롤러
	 * @param ex       catch 되지 않은 예외 객체 > 예외의 종류를 통해 알맞은 예외 처리등을 수행
	 */
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		@Nullable Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
