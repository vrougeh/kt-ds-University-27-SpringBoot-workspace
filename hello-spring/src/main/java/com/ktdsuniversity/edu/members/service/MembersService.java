package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.request.WriteVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

public interface MembersService {

	boolean createNewMembers(WriteVO writeVO);

	SearchResultVO findMembers();

}
