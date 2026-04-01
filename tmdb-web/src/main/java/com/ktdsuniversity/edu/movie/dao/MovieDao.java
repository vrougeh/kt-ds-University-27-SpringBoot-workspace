package com.ktdsuniversity.edu.movie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.WriteVO;

@Mapper
public interface MovieDao {

	int selectMovieCount();

	List<MovieVO> selectMovieList();

	int insertNewMovie(WriteVO writeVO);

	MovieVO selectMovieById(String movieId);

	int insertMovieToPosterUrl(String filePath);

}
