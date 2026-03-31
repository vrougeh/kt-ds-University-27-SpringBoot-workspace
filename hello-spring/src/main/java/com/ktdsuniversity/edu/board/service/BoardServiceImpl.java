package com.ktdsuniversity.edu.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Service
public class BoardServiceImpl implements BoardService{
	
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다
	 */
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private FilesDao filesDao;

	@Override
	public SearchResultVO findAllBoard() {
		//게시글 개수조회
		int count = this.boardDao.selectBoardCount();
		
		//게시글 목록조회
		List<BoardVO> list = this.boardDao.selectBoardList();
		
		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);
		
		return result;
	}

	@Override
	public boolean createNewBoard(WriteVO writeVO) {
		// dao => insert 요청
		// mybatis 는 insert, update, delete를 수행했을때
		// 영향을 받은 row의 수를 반환시킨다.
		// 예> insert ==> insert된 row의 개수 반환
		
		int insertCount = this.boardDao.insertNewBoard(writeVO);
		
		//첨부 파일 업로드
		List<MultipartFile> attachFiles = writeVO.getAttachFile();
		if(attachFiles != null && attachFiles.size() > 0) {
			System.out.println("파일 개수" + attachFiles.size());
			for(int i = 0 ; i < attachFiles.size() ; i++) {
//			for(MultipartFile uploadedFile : attachFiles) {
				//업로드한 파일이 서버컴퓨터의 파일시스템에 저장되도록 한다.
				File storeFile = new File("C:\\uploadFiles",attachFiles.get(i).getOriginalFilename());
				//C:\\uploadFiles 폴더가 없으면 생성해라
				if(!storeFile.getParentFile().exists()) {
					storeFile.getParentFile().mkdirs();
				}
				try {
					attachFiles.get(i).transferTo(storeFile);
					//files 테이블에 첨부파일 데이터 insert
					UploadVO uploadVO = new UploadVO();
					String filename = attachFiles.get(i).getOriginalFilename();
					String ext = filename.substring(filename.lastIndexOf(".")+1);
					uploadVO.setFileNum(i+1);
					uploadVO.setFileGroupId(writeVO.getId());
					uploadVO.setObfuscateName(filename); // 추후 난독화
					uploadVO.setDisplayName(filename);
					uploadVO.setExtendName(ext);
					uploadVO.setFileLength(storeFile.length());
					uploadVO.setFilePath(storeFile.getAbsolutePath());
					this.filesDao.insertAttachFile(uploadVO);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("생성된 게시글의 개수? "+ insertCount);
		return insertCount == 1;
	}
	
	@Override
	public BoardVO findBoardByArticleId(String articleId, ReadType readType) {
		
		if(readType == ReadType.VIEW) {
			// 1. 조회수 증가.
			int updateCount = this.boardDao.updateViewCntIncreaseById(articleId);
			System.out.println("조회수가 증가된 게시글의 수: " + updateCount);
			
			if (updateCount == 0) {
				// 존재하지 않는 게시글을 조회하려 했다.
				return null;
//				throw new RuntimeException("존재하지 않는 게시글입니다.");
			}
		}
		
		// 2. 게시글 조회.
		BoardVO board = this.boardDao.selectBoardById(articleId);
		
		// 조회한 게시글을 반환.
		return board;
	}

	@Override
	public boolean deleteBoardByArticleId(String id) {
		int deleteCount = this.boardDao.deleteBoardById(id);
		return deleteCount == 1;
	}

	@Override
	public boolean updateBoardByArticleId(UpdateVO updateVO) {
		int updateCount = this.boardDao.updateBoardById(updateVO);
		return updateCount == 1;
	}


}
