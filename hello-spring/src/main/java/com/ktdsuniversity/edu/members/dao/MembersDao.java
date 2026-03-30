package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;

@Mapper
public interface MembersDao {

	int insertNewMembers(RegistVO writeVO);

	int selectMembersCount();

	List<MembersVO> selectMembersList();

	MembersVO selectMembersByEmail(String email);

	int deleteMembersByEmail(String email);

	int updateMembersByEmail(RegistVO writeVO);

}
