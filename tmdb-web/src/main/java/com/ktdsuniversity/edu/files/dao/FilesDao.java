package com.ktdsuniversity.edu.files.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;

@Mapper
public interface FilesDao {

	int insertAttachFile(UploadVO uploadVO);

	DownLoadVO selectFilesByGroupIdAndFileNum(SearchFileVO searchFileVO);

}
