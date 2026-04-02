package com.ktdsuniversity.edu.members.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MembersController {
	
	@Autowired	
	private MembersService membersService;
	
	//회원가입 페이지 엔드포인트
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
	public String viewWritePage() {
		return "members/regist";
	}
	
	
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
	
	@GetMapping("/member/view/{email}")
	public String viewUserPage(Model model, @PathVariable String email) {
		
		MembersVO findResult = this.membersService.findMembersByEmail(email);
		model.addAttribute("user",findResult);
		return "members/view";
	}
	
	@GetMapping("member/update/{email}")
	public String viewUpdatePage(@PathVariable String email, Model model) {
		MembersVO data = this.membersService.findMembersByEmail(email);
		model.addAttribute("user",data);
		return "members/update";
	}
	
	@PostMapping("member/update/{email}")
	public String doUpdateAction(@PathVariable String email, UpdateVO updateVO) {
		updateVO.setEmail(email);
		boolean updateResult = this.membersService.updateMembersByEmail(updateVO);
		System.out.println("수정 성공:" + updateResult);
		return "redirect:/member/view/"+email;
	}
	
	@GetMapping("/member/delete")
	public String doDeleteAction(@RequestParam String email) {
		boolean deleteResult = this.membersService.deleteMembersByEmail(email);
		return "redirect:/member/list";
	}
	/*
	 * /member/view/사용자아이디 ==> 회원정보 조회 하기
	 * /member/update/사용자아이디 ==> 회원정보 수정페이지 보기
	 * /member/update/사용자아이디 ==> 회원정보 수정 하기
	 * /member/delete?id=사용자아이디 ==> 회원정보 삭제하기
	 */


}
