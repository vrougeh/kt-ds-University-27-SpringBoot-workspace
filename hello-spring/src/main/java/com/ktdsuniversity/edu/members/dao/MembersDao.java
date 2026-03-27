package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;

@Mapper
public interface MembersDao {

	int insertNewMembers(WriteVO writeVO);

	int selectMembersCount();

	List<MembersVO> selectMembersList();

}
