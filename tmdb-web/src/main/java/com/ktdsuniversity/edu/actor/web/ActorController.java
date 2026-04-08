package com.ktdsuniversity.edu.actor.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktdsuniversity.edu.actor.service.ActorService;
import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.response.SearchResultVO;

@Controller
public class ActorController {
	
	@Autowired
	ActorService actorService;
	
	
	@GetMapping("/actor/list")
	public String viewListPage(Model model) {
		
		SearchResultVO searchResult = this.actorService.findAllActor();
		
		List<ActorVO> list = searchResult.getActorList();
		
		int searchCount = searchResult.getActorCount();
		
		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount",searchCount);
		
		return "actor/list";
	}

}
