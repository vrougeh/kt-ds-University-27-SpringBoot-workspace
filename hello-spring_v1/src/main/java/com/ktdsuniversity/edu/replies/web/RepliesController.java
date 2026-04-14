package com.ktdsuniversity.edu.replies.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

import jakarta.validation.Valid;

@Controller
public class RepliesController {

	private static final Logger logger = LoggerFactory.getLogger(RepliesController.class);

	@Autowired
	private RepliesService repliesService;

	@ResponseBody
	@PostMapping("/api/replies/update/{replyId}")
	public UpdateResultVO doUpdateReplyByReplyId(@Valid UpdateVO updateVO, @PathVariable String replyId,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
		}
		updateVO.setReplyId(replyId);
		UpdateResultVO updateResult = this.repliesService.updateReply(updateVO);
		return updateResult;
	}

	@ResponseBody
	@GetMapping("/api/replies/delete/{id}")
	public RepliesVO doDeleteReplyByReplyId(@PathVariable String id) {
		RepliesVO deleteResult = this.repliesService.deleteReplyByReplyId(id);
		return deleteResult;
	}

	@ResponseBody
	@GetMapping("/api/replies/recommend/{id}")
	public RecommendResultVO  doRecommendReplyAction(@PathVariable String replyId) {
		RecommendResultVO  recommandResult = this.repliesService.updateRecommendReplyByReplyId(replyId);
		logger.debug("recommandResult : {}", recommandResult);
		return recommandResult;
	}

	@ResponseBody
	@GetMapping("/api/replies/{articleId}")
	public SearchResultVO getRepliesList(@PathVariable String articleId) {
		SearchResultVO searchResult = this.repliesService.findRepliesByArticleId(articleId);
		return searchResult;
	}

	@ResponseBody
	@PostMapping("/api/replies-with-file")
	public RepliesVO doCreateNewReplyWithFileAction(@Valid CreateVO createVO, BindingResult bindingResult,
			@SessionAttribute("__LOGIN_DATA__") MembersVO loginMembersVO) {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
		}

		createVO.setEmail(loginMembersVO.getEmail());

		logger.debug("reply : {}", createVO.getReply());
		logger.debug("reply : {}", createVO.getEmail());
		logger.debug("reply : {}", createVO.getArticleId());
		logger.debug("reply : {}", createVO.getParentReplyId());

		RepliesVO createResult = this.repliesService.createNewReply(createVO);

		return createResult;
	}

	// AJAX(API) 요청/반환
	// 요청데이터 + 바노한 데이터 ==> JSON
	@ResponseBody
	@PostMapping("/api/replies")
	public RepliesVO doCreateNewReplyAction(@RequestBody @Valid CreateVO createVO, BindingResult bindingResult,
			@SessionAttribute("__LOGIN_DATA__") MembersVO loginMembersVO) {

		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), errors);
		}

		createVO.setEmail(loginMembersVO.getEmail());

		logger.debug("reply : {}", createVO.getReply());
		logger.debug("reply : {}", createVO.getEmail());
		logger.debug("reply : {}", createVO.getArticleId());
		logger.debug("reply : {}", createVO.getParentReplyId());

		RepliesVO createResult = this.repliesService.createNewReply(createVO);

		return createResult;
	}

}
