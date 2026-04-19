package com.ktdsuniversity.edu.members.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.response.DuplicateResultVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

import jakarta.validation.Valid;


@Controller
public class MembersController {

	private static final Logger logger = LoggerFactory.getLogger(MembersController.class);

	@Autowired
	private MembersService membersService;

	//회원가입 페이지 엔드포인트
	@PreAuthorize(value = "hasRole('RL-20260418-000002')")
//	/member ==> 회원들의 목록이 조회되도록 코드를 작성
	@GetMapping("/member")
	public String viewListPage(Model model) {
		SearchResultVO searchResult = this.membersService.findMembers();

		List<MembersVO> list = searchResult.getResult();

		int searchCount = searchResult.getCount();

		model.addAttribute("searchResult",list);
		model.addAttribute("searchCount",searchCount);

		return "members/list";
	}

	@GetMapping("/regist")
	public String viewWritePage(Authentication authentication) {
		if(authentication != null) {
			return "redirect:/";
		}
		return "members/regist";
	}

	@PreAuthorize(value = "isAnonymous()")
	@PostMapping("/regist")
	public String doRegistAction(@Valid @ModelAttribute RegistVO registVO, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("inputData", registVO);
			return "/members/regist";
		}

		boolean createResult = this.membersService.createNewMembers(registVO);
		System.out.println("회원가입 성공?" + createResult);
		return"redirect:/member";
	}

	@ResponseBody
	@GetMapping("/regist/check/duplicate/{email}")
	public DuplicateResultVO doCheckDuplicateEmailAction(@PathVariable String email) {
		//email이 이미 사용중인지 확이한다.
		MembersVO membersVO = this.membersService.findMembersByEmail(email);
//		System.out.println("이메일이 있는지 확인? "+membersVO);
		//확인된 결과를 브라우저에게 json으로 전송한다.
		// 사용중 > {email: test@test, duplicate:true}
		// 사용중 X > {email: test@test, duplicate:false}
		DuplicateResultVO result = new DuplicateResultVO();
		result.setEmail(email);
		result.setDuplicate(membersVO != null);

		return result;
	}

	//본인의 정보만 조회
	@PreAuthorize(value = "isAuthenticated() and #id == authentication.principal.email")
	@GetMapping("/member/view/{email}")
	public String viewUserPage(Model model, @PathVariable String email) {

		MembersVO findResult = this.membersService.findMembersByEmail(email);
		model.addAttribute("user",findResult);
		return "members/view";
	}
	//본인의 정보만 조회
	@PreAuthorize(value = "isAuthenticated() and #id == authentication.principal.email")
	@GetMapping("member/update/{email}")
	public String viewUpdatePage(@PathVariable String email, Model model) {
		MembersVO data = this.membersService.findMembersByEmail(email);
		model.addAttribute("user",data);
		return "members/update";
	}

	//본인의 정보만 조회
	@PreAuthorize(value = "isAuthenticated() and #id == authentication.principal.email")
	@PostMapping("member/update/{email}")
	public String doUpdateAction(@PathVariable String email, UpdateVO updateVO) {
		updateVO.setEmail(email);
		boolean updateResult = this.membersService.updateMembersByEmail(updateVO);
		System.out.println("수정 성공:" + updateResult);
		return "redirect:/member/view/"+email;
	}
	//본인의 정보만 조회
	@PreAuthorize(value = "isAuthenticated() and #id == authentication.principal.email")
	@GetMapping("/member/delete")
	public String doDeleteAction(@RequestParam String email) {
		boolean deleteResult = this.membersService.deleteMembersByEmail(email);
		return "redirect:/member/list";
	}

	@GetMapping("/login")
	public String viewLoginPage(Authentication authentication) {
		if(authentication != null) {
			return "redirect:/";
		}
		return "members/login";
	}
	//spring security token 방식으로 변경
//	@PostMapping("/login")
//	public String doLoginAction(@Valid @ModelAttribute LoginVO loginVO,
//			BindingResult bindingResult,
//			Model model,
//			@RequestParam(required = false, defaultValue = "/") String go,
//			HttpServletRequest request
//			) {
//		if(bindingResult.hasErrors()) {
//			model.addAttribute("loginData", loginVO);
//			return "members/login";
//		}
//
//		String userIp = request.getRemoteAddr();
//		System.out.println(userIp);
//		loginVO.setIp(userIp);
//
//		MembersVO member = this.membersService.findMemberByEmailAndPassword(loginVO);
//
//		//서버의 세션을 삭제한다
//		//로그아웃
//		request.getSession().invalidate();
//
//		//request.getSession() < httpRequestHeader 로 전달된 JSESSIONID의 객체를 반환
//		//request.getSession(true ) < 기존 JSESSIONID로 발급된 세션객체는 버리고 새로운id의 세션객체를 생성 후 반환
//		HttpSession session = request.getSession(true);
//		System.out.println("session : "+session.getId());
//		session.setAttribute("__LOGIN_DATA__", member);
////		System.out.println("go??????????????????????????"+go);
//		return "redirect:" + go;
//	}

	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping("/logout")
	public String viewLogoutPage() {
		return "members/login";
	}

	@PreAuthorize(value = "isAuthenticated() and #id == authentication.principal.email")
	@GetMapping("/delete-me")
	public String doDeleteAction(Authentication authentication) {

		if(authentication.getAuthorities() == null) {
			return "redirect:/";
		}

		//로그인 세션에서 회원의 이메일을 가져온다
		String memberEmail = ((MembersVO) authentication.getPrincipal()).getEmail();
		//members 테이블에서 회원의 정보를 이메일을 이용해 삭제한다
		boolean delete = this.membersService.deleteMembersByEmail(memberEmail);
		if(delete) {
			//현재로그인된 사용자를 로그아웃 시킨다
			//"members/deletesuccess" 페이지를 보여준다.
			return "members/deletesuccess";
			//"탈퇴가 완료됐습니다."
		}

		return "members/deletefail";
	}

}
