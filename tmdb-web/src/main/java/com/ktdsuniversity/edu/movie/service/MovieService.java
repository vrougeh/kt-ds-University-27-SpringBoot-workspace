package com.ktdsuniversity.edu.movie.service;

import com.ktdsuniversity.edu.movie.vo.request.WriteVO;
import com.ktdsuniversity.edu.movie.vo.response.SearchResultVO;

public interface MovieService {

	SearchResultVO findAllMovie();

	boolean createNewMovie(WriteVO writeVO);

}
