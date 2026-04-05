package com.ktdsuniversity.edu.files.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;
import com.ktdsuniversity.edu.movie.vo.request.UpdateVO;

@Mapper
public interface FilesDao {

	int insertAttachFile(UploadVO uploadVO);

	DownLoadVO selectFilesByGroupIdAndFileNum(SearchFileVO searchFileVO);

	int deleteFilesByBoardId(String id);

	List<String> selectFilePathByFileGroupIdAndFileNums(UpdateVO updateVO);

	int deleteFilesByFileGroupIdAndFileNums(UpdateVO updateVO);

	List<String> selectFilesByGroupId(String id);

}
