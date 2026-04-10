package com.ktdsuniversity.edu.replies.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.common.utils.ObjectUtils;
import com.ktdsuniversity.edu.common.utils.SessionUtils;
import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.utils.MultipartFileHandler;
import com.ktdsuniversity.edu.files.vo.request.SearchFilesGroupVO;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.request.CreateVO;
import com.ktdsuniversity.edu.replies.vo.request.UpdateVO;
import com.ktdsuniversity.edu.replies.vo.response.RecommendResultVO;
import com.ktdsuniversity.edu.replies.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.replies.vo.response.UpdateResultVO;

@Service
public class RepliesServiceImpl implements RepliesService {
	private static final Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);

	@Autowired
	private RepliesDao repliesDao;

	@Autowired
	private FilesDao filesDao;

	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Transactional
	@Override
	public RepliesVO createNewReply(CreateVO createVO) {

		String fileGroupId = this.multipartFileHandler.upload(createVO.getAttachFile());
		createVO.setFileGroupId(fileGroupId);

		int insertCount = this.repliesDao.insertNewReply(createVO);
		if (insertCount == 1) {
			RepliesVO insertResult = this.repliesDao.selectReplyByReplyId(createVO.getId());
			return insertResult;
		}
		return null;
	}

	@Override
	public SearchResultVO findRepliesByArticleId(String articleId) {

		SearchResultVO searchResultVO = new SearchResultVO();
		int count = this.repliesDao.selectRepliesCountByArticleId(articleId);
		searchResultVO.setCount(count);
		if (count > 0) {
			List<RepliesVO> searchList = this.repliesDao.selectRepliesByArticleId(articleId);
			searchResultVO.setResult(searchList);
		}

		return searchResultVO;
	}

	@Transactional
	@Override
	public RecommendResultVO updateRecommendReplyByReplyId(String replyId) {
		
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(replyId);
		if (ObjectUtils.isNotNull(reply)) {
			if (SessionUtils.isMineResource(reply.getEmail())) {
				throw new HelloSpringApiException(
						"권한이 부족합니다.", 
						HttpStatus.BAD_REQUEST.value(), 
						"자신의 댓글은 추천할 수 없습니다.");
			}
		}
		
		int updateCount = this.repliesDao.updateReplyRecommendByReplyId(replyId);
		if (updateCount == 1) {
			reply = this.repliesDao.selectReplyByReplyId(replyId);
			
			RecommendResultVO result = new RecommendResultVO();
			result.setReplyId(replyId);
			result.setRecommendCount(reply.getRecommendCnt());
			return result;
		}
		return null;
	}

	@Transactional
	@Override
	public RepliesVO deleteReplyByReplyId(String replyId) {

		RepliesVO result = this.repliesDao.selectReplyByReplyId(replyId);

		if (result != null) {
			int deleteResult = this.repliesDao.deleteReplyByReplyId(replyId);
			logger.debug("deleteResult : {}", deleteResult);
			if (deleteResult == 1) {
				return result;
			}
		}
		return null;
	}

	@Transactional
	@Override
	public UpdateResultVO updateReply(UpdateVO updateVO) {
		RepliesVO reply = this.repliesDao.selectReplyByReplyId(updateVO.getReplyId());
		if (ObjectUtils.isNotNull(reply)) {
			if (!SessionUtils.isMineResource(reply.getEmail())) {
				throw new HelloSpringApiException("파라미터가 충분하지 않습니다.", HttpStatus.BAD_REQUEST.value(), "자신의 댓글이 아닙니다.");
			}
		}
		
		updateVO.setFileGroupId(reply.getFileGroupId());
		
		// 선택한 파일들만 삭제한다.
		if (updateVO.getDelFileNum() != null && updateVO.getDelFileNum().size() > 0) {
			// 선택한 파일들의 정보를 조회 --> 파일경로 > 실제파일 제거
			SearchFilesGroupVO searchFilesGroupVO = new SearchFilesGroupVO();
			searchFilesGroupVO.setDeleteFileNum(updateVO.getDelFileNum());
			searchFilesGroupVO.setFileGroupId(updateVO.getFileGroupId());
			List<String> deleteTargets = this.filesDao.selectFilePathByFileGroupIdAndFileNums(searchFilesGroupVO);
			for (String target : deleteTargets) {
				new File(target).delete();
			}
			// 선택한 파일들을 FILES 테이블에서 제거
			int deleteCount = this.filesDao.deleteFilesByFileGroupIdAndFileNums(searchFilesGroupVO);
			logger.debug("삭제한 파일 데디터의 수 : {}", deleteCount);
		
		}
		
		List<MultipartFile> attachFiles = updateVO.getNewAttachFiles();
		String fileGroupId = updateVO.getFileGroupId();
		if (fileGroupId == null || fileGroupId.length() == 0) {
			fileGroupId = this.multipartFileHandler.upload(attachFiles);
			updateVO.setFileGroupId(fileGroupId);
		} else {
			this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
		}
		
		int updateCount = this.repliesDao.updateReplyByReplyId(updateVO);
		
		UpdateResultVO result = new UpdateResultVO();
		result.setReplyId(updateVO.getReplyId());
		result.setUpdate(updateCount == 1);
		
		return result;
	}
}
