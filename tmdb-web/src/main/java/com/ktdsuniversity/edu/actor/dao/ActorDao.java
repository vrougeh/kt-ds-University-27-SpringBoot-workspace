package com.ktdsuniversity.edu.actor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.actor.vo.ActorVO;

@Mapper
public interface ActorDao {

	int selectActorCount();

	List<ActorVO> selectActorList();

}
