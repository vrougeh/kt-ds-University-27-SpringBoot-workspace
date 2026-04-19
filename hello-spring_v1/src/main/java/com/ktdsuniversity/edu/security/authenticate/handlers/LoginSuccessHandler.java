package com.ktdsuniversity.edu.security.authenticate.handlers;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ktdsuniversity.edu.common.utils.StringUtils;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그인 인증이 성공했을 때 실행되는 핸들러.
 * 로그인 성공 로그 기록 및 페이지 이동 처리를 담당함.
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private MembersDao membersDao;

	public LoginSuccessHandler(MembersDao membersDao) {
		this.membersDao = membersDao;
	}

	/**
	 * 인증 성공 시 실행되는 로직
	 * @param authentication 인증된 사용자의 정보(Principal)를 담고 있는 객체
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		// 1. 로그인 이력 업데이트를 위한 정보 수집
		LoginVO loginVO = new LoginVO();
		loginVO.setIp(request.getRemoteAddr());
		loginVO.setEmail(authentication.getName());
		// 2. DB에 로그인 성공 정보(최근 로그인 시간, 접속 IP, 실패 카운트 초기화 등) 업데이트
		this.membersDao.updateSuccessLogin(loginVO);

		//HttpServletRequest에서 파라미터를 가져오는방법
		// 3. 로그인 성공 후 이동할 페이지 결정
		// 로그인 페이지 진입 전, 사용자가 가려고 했던 URL 정보를 'go' 파라미터에서 추출
		String go = request.getParameter("go");


		// 4. 리다이렉트 처리
		// go 파라미터가 비어있다면 메인 페이지("/")로 이동, 있다면 해당 경로로 이동
		response.sendRedirect(StringUtils.emptyTo(go, "/"));

	}

}
