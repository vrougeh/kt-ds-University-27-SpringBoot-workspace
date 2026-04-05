package com.ktdsuniversity.edu.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.utils.MultipartFileHandler;
import com.ktdsuniversity.edu.movie.dao.MovieDao;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.WriteVO;
import com.ktdsuniversity.edu.movie.vo.response.SearchResultVO;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private FilesDao filesDao;

	@Autowired
	private MultipartFileHandler multipartFileHandler;

	@Override
	public SearchResultVO findAllMovie() {

		int count = this.movieDao.selectMovieCount();

		//게시글 목록조회
		List<MovieVO> list = this.movieDao.selectMovieList();

		SearchResultVO result = new SearchResultVO();
		result.setMovieList(list);
		result.setMovieCount(count);

		return result;
	}

	@Override
	public boolean createNewMovie(WriteVO writeVO) {
		int insertCount = this.movieDao.insertNewMovie(writeVO);

		//첨부파일 업로드 코드 추가
		MultipartFile attachFiles = writeVO.getAttachFile();
		multipartFileHandler.upload(attachFiles, writeVO.getMovieId());

		return insertCount == 1;
	}

	@Override
	public MovieVO findMovieByMovieId(String movieId) {
		MovieVO movie = this.movieDao.selectMovieById(movieId);
		return movie;
	}

}
