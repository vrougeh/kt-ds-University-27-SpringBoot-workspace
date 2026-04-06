package com.ktdsuniversity.edu.board.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class BoardController {
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다
	 */
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.boardService.findAllBoard();
		
		//게시글의 목록을 조회
		List<BoardVO> list = searchResult.getResult();
		//게시글의 개수 조회
		int searchCount = searchResult.getCount();
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		
		return "board/list";
	}
	
	// 게시글 등록화면 보여주는 EndPoint
	@GetMapping("/write")
	public String viewWritePage(HttpServletRequest request) {
		
		HttpSession session =  request.getSession();
		System.out.println(session);
		
		if(session.getAttribute("__LOGIN_DATA__")== null) {
			System.out.println("로그인 데이터가 없습니다.");
			return "redirect:/";
		}
		
		return "board/write";
	}
	
	@PostMapping("/write")  /*@Valid의 결과를 받아오는 파라미터 반드시 @Valid 파라미터 이후에 작성*/
	public String doWriteAction(@Valid WriteVO writeVO, BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session) { //@ModelAttribute 생략
		//사용자의 입력값을 검증 했을 때 에러가 있다면 
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			//브라우저에게 "board/write"페이지를 보여주도록하고 해당페이지는 사용자가 입력한 값을 전달한다
			model.addAttribute("inputData",writeVO);
			return "board/write";
		}
		
		
		MembersVO loginMember = (MembersVO) session.getAttribute("__LOGIN_DATA__");
		if(loginMember == null) {
			System.out.println("로그인 데이터가 없습니다.");
			return "redirect:/";
		}
		
		System.out.println(writeVO.getSubject());
		System.out.println(writeVO.getEmail());
		System.out.println(writeVO.getContent());
		
		//create update delete => 성공 or 실패 여부 반환
		boolean createResult = this.boardService.createNewBoard(writeVO, loginMember);
		
		System.out.println("게시글 생성 성공?" + createResult);
		
		if (!createResult) {
	        // 시간이 초과되었거나 등록에 실패한 경우
			System.out.println("시간이 초과되었습니다.");
	        return "redirect:/";
	    }
		
		// redirect: 브라우저에게 다음 End Point를 요청하도록 지시
		return "redirect:/";
	}
	
	// 게시글 내용 조회.
	// endpoint ==> /view/게시글아이디 예> /view/BO-20260327-000001
	// 해야 하는 역할
	//  1. 게시글 내용을 조회해서 브라우저에게 노출.
	//  2. 조회수 1증가.
	@GetMapping("/view/{articleId}")
	public String viewDetailPage(Model model, @PathVariable String articleId) {
		
		// articleId로 데이터베이스에서 게시글을 조회한다.
		// 조회할 때 조회수가 하나 증가해야 한다.
		BoardVO findResult = this.boardService.findBoardByArticleId(articleId, ReadType.VIEW);
		
		model.addAttribute("article", findResult);
		
		return "board/view";
	}
	
	@GetMapping("/delete")
	public String doDeleteAction(@RequestParam String id) {
		
		boolean deleteResult = this.boardService.deleteBoardByArticleId(id);
		System.out.println("삭제 결과?"+deleteResult);
		return "redirect:/";
		
	}
	
	@GetMapping("/update/{articleId}")
	public String viewUpdatePage(@PathVariable String articleId, Model model) {
		BoardVO data = this.boardService.findBoardByArticleId(articleId, ReadType.UPDATE);
		model.addAttribute("article",data);
		return "board/update";
		
	}
	
	@PostMapping("/update/{articleId}")
	public String doUpdateAction(@PathVariable String articleId, UpdateVO updateVO) {
		
		updateVO.setId(articleId);
		
		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		System.out.println("수정 성공:" + updateResult);
		
		return "redirect:/view/"+articleId;
	}
	
	
	

}
