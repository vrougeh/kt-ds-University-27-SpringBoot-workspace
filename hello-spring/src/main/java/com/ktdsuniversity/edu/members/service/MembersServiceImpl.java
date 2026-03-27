package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMembers(WriteVO writeVO) {
		int insertCount = this.membersDao.insertNewMembers(writeVO);
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

}
