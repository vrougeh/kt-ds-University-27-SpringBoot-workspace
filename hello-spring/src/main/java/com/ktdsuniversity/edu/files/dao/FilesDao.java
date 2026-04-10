package com.ktdsuniversity.edu.files.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.request.SearchFilesGroupVO;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;

@Mapper
public interface FilesDao {

	int insertAttachFile(UploadVO uploadVO);

	DownLoadVO selectFilesByGroupIdAndFileNum(SearchFileVO searchFileVO);

	int deleteFilesByBoardId(String id);

	List<String> selectFilePathByFileGroupIdAndFileNums(SearchFilesGroupVO searchFilesGroupVO);

	int deleteFilesByFileGroupIdAndFileNums(SearchFilesGroupVO searchFilesGroupVO);

	List<String> selectFilesByGroupId(String id);

	String selectNewFileGroupId();

	int insertFileGroupId(String fileGroupId);


}
