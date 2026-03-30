package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMembers(RegistVO registVO) {
		int insertCount = this.membersDao.insertNewMembers(registVO);
		System.out.println("생성된 멤버의 개수? "+ insertCount);
		return insertCount == 1;
	}

	@Override
	public SearchResultVO findMembers() {
		int count= this.membersDao.selectMembersCount();
		
		List<MembersVO> list = this.membersDao.selectMembersList();
		
		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);
		
		return result;
	}

	@Override
	public MembersVO findMembersByEmail(String email) {
		MembersVO user = this.membersDao.selectMembersByEmail(email);
		return user;
	}

	@Override
	public boolean deleteMembersByEmail(String email) {
		int deleteCount = this.membersDao.deleteMembersByEmail(email);
		return deleteCount == 1;
	}

	@Override
	public boolean updateMembersByEmail(UpdateVO updateVO) {
		int updateCount = this.membersDao.updateMembersByEmail(updateVO);
		return updateCount==1;
	}

}
