package com.ktdsuniversity.edu.movie.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.movie.service.MovieService;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.UpdateVO;
import com.ktdsuniversity.edu.movie.vo.request.WriteVO;
import com.ktdsuniversity.edu.movie.vo.response.SearchResultVO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/")
	public String getMethodName() {
		return "/main";
	}
	
	
	@GetMapping("/list")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.movieService.findAllMovie();
		
		List<MovieVO> list = searchResult.getMovieList();
		
		int searchCount = searchResult.getMovieCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		return "movie/list";
	}
	
	
	@GetMapping("/write")
	public String viewWritePage() {
		return "movie/write";
	}
	
	@PostMapping("/write")
	public String doWriteAction(@Valid WriteVO writeVO, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			model.addAttribute("inputData",writeVO);
			return "movie/write";
		}
//		String title = writeVO.getTitle();
//		title = title.replace("<", "&lt;")
//				     .replace(">", "&gt;");
//		writeVO.setTitle(title);
		
		boolean createResult = this.movieService.createNewMovie(writeVO);
		System.out.println(createResult);
		
		return"redirect:/list";
	}
	
	@GetMapping("/view/{movieId}")
	public String viewDetailPage(Model model, @PathVariable String movieId) {
		MovieVO findResult = this.movieService.findMovieByMovieId(movieId);
		model.addAttribute("movie",findResult);
		return "movie/view";
	}
	
	@GetMapping("path")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
	
	
	
	
	@GetMapping("/update/{movieId}")
	public String viewUpdatePage(@PathVariable String movieId, Model model) {
		MovieVO data = this.movieService.findMovieByMovieId(movieId);
		model.addAttribute("movie",data);
		return "movie/update";
	}
	
	@PostMapping("/update/{movieId}")
	public String doUpdateAction(@RequestParam String movieId, UpdateVO updateVO ) {
		//TODO: process POST request
		
		return null;
	}
	

}
