package com.ktdsuniversity.edu.board.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.common.utils.SessionUtils;
import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.utils.MultipartFileHandler;
import com.ktdsuniversity.edu.files.vo.request.SearchFilesGroupVO;

@Service
public class BoardServiceImpl implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);

	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다
	 */
	@Autowired
	private BoardDao boardDao;

	@Autowired
	private FilesDao filesDao;

	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Override
	public SearchResultVO findAllBoard() {
		// 게시글 개수조회
		int count = this.boardDao.selectBoardCount();

		// 게시글 목록조회
		List<BoardVO> list = this.boardDao.selectBoardList();

		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);

		return result;
	}

	@Transactional
	@Override
	public boolean createNewBoard(WriteVO writeVO) {

		// 첨부 파일 업로드
		List<MultipartFile> attachFiles = writeVO.getAttachFile();
		String fileGroupId = multipartFileHandler.upload(attachFiles);
		writeVO.setFileGroupId(fileGroupId);

		// dao => insert 요청
		// mybatis 는 insert, update, delete를 수행했을때
		// 영향을 받은 row의 수를 반환시킨다.
		// 예> insert ==> insert된 row의 개수 반환

		int insertCount = this.boardDao.insertNewBoard(writeVO);

		logger.debug("생성된 게시글의 개수? {}", insertCount);
//		System.out.println("생성된 게시글의 개수? "+ insertCount);
		return insertCount == 1;
	}

	@Transactional
	@Override
	public BoardVO findBoardByArticleId(String articleId, ReadType readType) {

		BoardVO board = this.boardDao.selectBoardById(articleId);
		logger.debug("email : {}", board.getEmail());
		if (SessionUtils.isMineResource(board.getEmail())) {
			if (readType == ReadType.VIEW) {
				// 1. 조회수 증가.
				int updateCount = this.boardDao.updateViewCntIncreaseById(articleId);
				logger.debug("조회수가 증가된 게시글의 수: {} ", updateCount);
//				System.out.println("조회수가 증가된 게시글의 수: " + updateCount);

				if (updateCount == 0) {
					// 존재하지 않는 게시글을 조회하려 했다.
					throw new HelloSpringException("존재하지 않는 게시글입니다.", "errors/404");
				}
			}
		} else {
			throw new HelloSpringException("잘못된 접근입니다.", "errors/403");
		}

		// 2. 게시글 조회.
		BoardVO newboard = this.boardDao.selectBoardById(articleId);

		// 조회한 게시글을 반환.
		return newboard;
	}

	@Transactional
	@Override
	public boolean deleteBoardByArticleId(String id) {
		int deleteCount = this.boardDao.deleteBoardById(id);

		// 삭제하려는 게시글에 첨부파일 목록을 가져온다
		List<String> deleteTargets = this.filesDao.selectFilesByGroupId(id);
		// 파일목록이 존재하면 모든파일들을 제거한다
		if (deleteTargets != null) {
			for (String target : deleteTargets) {
				new File(target).delete();
			}
			// 파일목록을 제거한 이후에 "FILES" 테이블에서 해당 파일 정보를 모두 삭제한다
			// TODO 수정필요 id > filegroupid
			int deleteFileCount = this.filesDao.deleteFilesByBoardId(id);
			logger.debug("삭제한 파일개수 {}", deleteFileCount);
//			System.out.println("삭제한 파일개수"+deleteFileCount);
		}
		return deleteCount == 1;
	}

	@Transactional
	@Override
	public boolean updateBoardByArticleId(UpdateVO updateVO) {

		// 선택한 파일들만 삭제한다.
		if (updateVO.getDeleteFileNum() != null && updateVO.getDeleteFileNum().size() > 0) {
			// 선택한 파일들의 정보를 조회 --> 파일경로 > 실제파일 제거
			SearchFilesGroupVO searchFilesGroupVO = new SearchFilesGroupVO();
			searchFilesGroupVO.setDeleteFileNum(updateVO.getDeleteFileNum());
			searchFilesGroupVO.setFileGroupId(updateVO.getFileGroupId());
			List<String> deleteTargets = this.filesDao.selectFilePathByFileGroupIdAndFileNums(searchFilesGroupVO);
			for (String target : deleteTargets) {
				new File(target).delete();
			}
			// 선택한 파일들을 FILES 테이블에서 제거
			int deleteCount = this.filesDao.deleteFilesByFileGroupIdAndFileNums(searchFilesGroupVO);
			logger.debug("삭제한 파일 데디터의 수 : {}", deleteCount);
//			System.out.println("삭제한 파일 데디터의 수 : " + deleteCount);

		}

		List<MultipartFile> attachFiles = updateVO.getAttachFile();
		String fileGroupId = updateVO.getFileGroupId();
		if (fileGroupId == null || fileGroupId.length() == 0) {
			fileGroupId = this.multipartFileHandler.upload(attachFiles);
			updateVO.setFileGroupId(fileGroupId);
		} else {
			this.multipartFileHandler.upload(attachFiles, updateVO.getFileGroupId());
		}

		updateVO.getEmail();

		int updateCount = this.boardDao.updateBoardById(updateVO);

		// 첨부 파일 업로드
//		List<MultipartFile> attachFiles = updateVO.getAttachFile();
//		multipartFileHandler.upload(attachFiles,updateVO.getFileGroupId());
		return updateCount == 1;
	}

}
