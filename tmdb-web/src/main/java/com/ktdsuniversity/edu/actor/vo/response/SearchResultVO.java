package com.ktdsuniversity.edu.actor.vo.response;

import java.util.List;

import com.ktdsuniversity.edu.actor.vo.ActorVO;

public class SearchResultVO {
	
	private List<ActorVO> actorList;
	private int actorCount;
	
	public List<ActorVO> getActorList() {
		return this.actorList;
	}
	public void setActorList(List<ActorVO> actorList) {
		this.actorList = actorList;
	}
	public int getActorCount() {
		return this.actorCount;
	}
	public void setActorCount(int actorCount) {
		this.actorCount = actorCount;
	}
	
	

}
