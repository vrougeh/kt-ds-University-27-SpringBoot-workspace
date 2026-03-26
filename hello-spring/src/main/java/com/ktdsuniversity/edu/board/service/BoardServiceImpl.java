package com.ktdsuniversity.edu.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;

@Service
public class BoardServiceImpl implements BoardService{
	
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다
	 */
	@Autowired
	private BoardDao boardDao;

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

}
