package com.ktdsuniversity.edu.movie.vo.response;

import java.util.List;

import com.ktdsuniversity.edu.movie.vo.MovieVO;

public class SearchResultVO {
	
	private List<MovieVO> movieList;
	private int movieCount;
	
	public List<MovieVO> getMovieList() {
		return this.movieList;
	}
	public void setMovieList(List<MovieVO> movieList) {
		this.movieList = movieList;
	}
	public int getMovieCount() {
		return this.movieCount;
	}
	public void setMovieCount(int movieCount) {
		this.movieCount = movieCount;
	}
	
	

}
