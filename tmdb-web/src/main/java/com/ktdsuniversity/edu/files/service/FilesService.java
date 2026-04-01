package com.ktdsuniversity.edu.files.service;

import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;

public interface FilesService {

	DownLoadVO findAttachFile(SearchFileVO searchFileVO);

}
