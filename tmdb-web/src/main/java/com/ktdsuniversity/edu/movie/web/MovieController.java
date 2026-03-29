package com.ktdsuniversity.edu.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.movie.service.MovieService;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.WriteVO;
import com.ktdsuniversity.edu.movie.vo.response.SearchResultVO;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/")
	public String getMethodName() {
		return "/";
	}
	
	
	@GetMapping("/list")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.movieService.findAllMovie();
		
		List<MovieVO> list = searchResult.getMovieList();
		
		int searchCount = searchResult.getMovieCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		return "movie//list";
	}
	
	
	@GetMapping("/write")
	public String viewWritePage() {
		return "movie/write";
	}
	
	@PostMapping("/write")
	public String doWriteAction(WriteVO writeVO) {
		boolean createResult = this.movieService.createNewMovie(writeVO);
		
		return"redirect:/list";
	}
	
	

}
