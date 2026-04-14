package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

import jakarta.validation.Valid;

public interface MembersService {

	boolean createNewMembers(RegistVO writeVO);

	SearchResultVO findMembers();

	MembersVO findMembersByEmail(String email);

	boolean deleteMembersByEmail(String email);

	boolean updateMembersByEmail(UpdateVO updateVO);

	MembersVO findMemberByEmailAndPassword(@Valid LoginVO loginVO);



}
