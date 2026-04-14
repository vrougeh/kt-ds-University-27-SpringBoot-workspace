package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.SearchListVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.members.vo.MembersVO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;




@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다
	 */
	@Autowired
	private BoardService boardService;
	
	//192.168.211.28:8080/?pageNo=&listSize=&searchType=&searchKeyword=
	@GetMapping("/")
	public String viewListPage(Model model,@ModelAttribute SearchListVO searchListVO ) {
		
		SearchResultVO searchResult = this.boardService.findAllBoard(searchListVO);
		
		//게시글의 목록을 조회
		List<BoardVO> list = searchResult.getResult();
		//게시글의 개수 조회
		int searchCount = searchResult.getCount();
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		model.addAttribute("pagenation",searchListVO);
		
		return "board/list";
	}
	
	// 게시글 등록화면 보여주는 EndPoint
	@GetMapping("/write")
	public String viewWritePage() {
		return "board/write";
	}
	
	@PostMapping("/write")  /*@Valid의 결과를 받아오는 파라미터 반드시 @Valid 파라미터 이후에 작성*/
	public String doWriteAction(@Valid WriteVO writeVO, BindingResult bindingResult, Model model, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) { //@ModelAttribute 생략
		//사용자의 입력값을 검증 했을 때 에러가 있다면 
		if(bindingResult.hasErrors()) {
			logger.debug("{}",bindingResult.getAllErrors());
//			System.out.println(bindingResult.getAllErrors());
			//브라우저에게 "board/write"페이지를 보여주도록하고 해당페이지는 사용자가 입력한 값을 전달한다
			model.addAttribute("inputData",writeVO);
			return "board/write";
		}
		writeVO.setEmail(loginMember.getEmail());
		
		//create update delete => 성공 or 실패 여부 반환
		boolean createResult = this.boardService.createNewBoard(writeVO);
		logger.debug("게시글 생성 성공? {}", createResult);
//		System.out.println("게시글 생성 성공?" + createResult);
		
		if (!createResult) {
	        // 시간이 초과되었거나 등록에 실패한 경우
			logger.debug("시간이 초과되었습니다.");
//			System.out.println("시간이 초과되었습니다.");
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
		logger.debug("삭제 결과? {}", deleteResult);
//		System.out.println("삭제 결과?"+deleteResult);
		return "redirect:/";
		
	}
	
	@GetMapping("/update/{articleId}")
	public String viewUpdatePage(@PathVariable String articleId, Model model, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) {
		BoardVO data = this.boardService.findBoardByArticleId(articleId, ReadType.UPDATE);

		model.addAttribute("article",data);
		if (!loginMember.getEmail().equals(data.getEmail())) {
			throw new HelloSpringException("잘못된 접근입니다.", "errors/403");
		}
		return "board/update";
		
	}
	
	@PostMapping("/update/{articleId}")
	public String doUpdateAction(@PathVariable String articleId, UpdateVO updateVO, @SessionAttribute("__LOGIN_DATA__") MembersVO loginMember) {
		logger.debug("id : {} / email : {}", articleId, loginMember.getEmail());
//		System.out.println("id : "+ articleId+"/ email : "+ loginMember.getEmail());
		updateVO.setId(articleId);
		updateVO.setEmail(loginMember.getEmail());
		
		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		logger.debug("수정 성공: {}", updateResult);
//		System.out.println("수정 성공:" + updateResult);
		
		return "redirect:/view/"+articleId;
	}
	
	
	

}
