package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

public interface MembersService {

	boolean createNewMembers(WriteVO writeVO);

	SearchResultVO findMembers();

	MembersVO findMembersByEmail(String email);

	boolean deleteMembersByEmail(String email);

	boolean updateMembersByEmail(UpdateVO updateVO);



}
