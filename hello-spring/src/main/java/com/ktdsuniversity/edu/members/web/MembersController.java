package com.ktdsuniversity.edu.members.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MembersController {
	
	@Autowired	
	private MembersService membersService;
	
	//회원가입 페이지 엔드포인트
	@GetMapping("/members/list")
	public String viewListPage(Model model) {
		SearchResultVO searchResult = this.membersService.findMembers();
		
		List<MembersVO> list = searchResult.getResult();
		
		int searchCount = searchResult.getCount();
		
		model.addAttribute("searchResult",list);
		model.addAttribute("searchCount",searchCount);
		
		return "members/list";
	}
	
	@GetMapping("/members/write")
	public String viewWritePage() {
		return "members/write";
	}
	
	
	@PostMapping("/members/write")
	public String doWriteAction(WriteVO writeVO) {
		
		boolean createResult = this.membersService.createNewMembers(writeVO);
		System.out.println("게시글 생성 성공?" + createResult);
		return"redirect:/members/list";
	}

}
