package com.ktdsuniversity.edu.actor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.actor.dao.ActorDao;
import com.ktdsuniversity.edu.actor.vo.ActorVO;
import com.ktdsuniversity.edu.actor.vo.response.SearchResultVO;

@Service
public class ActorServiceImpl implements ActorService {
	
	@Autowired
	private ActorDao actorDao;

	@Override
	public SearchResultVO findAllActor() {
		
		int count = this.actorDao.selectActorCount();
		
		List<ActorVO> list = this.actorDao.selectActorList();
		
		SearchResultVO result = new SearchResultVO();
		result.setActorList(list);
		result.setActorCount(count);
		
		
		return result;
	}

}
