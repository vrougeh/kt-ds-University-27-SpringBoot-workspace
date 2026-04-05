package com.ktdsuniversity.edu.files.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;

@Service
public class FilesServiceImpl implements FilesService {

	@Autowired
	private FilesDao filesDao;

	@Override
	public DownLoadVO findAttachFile(SearchFileVO searchFileVO) {
		DownLoadVO result = this.filesDao.selectFilesByGroupIdAndFileNum(searchFileVO);
		return result;
	}

}
