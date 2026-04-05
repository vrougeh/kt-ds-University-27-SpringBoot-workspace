package com.ktdsuniversity.edu.files.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Component
public class MultipartFileHandler {

	@Autowired
	private FilesDao filesDao;

	public void upload(MultipartFile attachFiles, String fileGroupId) {

		if(attachFiles != null) {
			String obfuscateName = UUID.randomUUID().toString();
			//업로드한 파일이 서버컴퓨터의 파일시스템에 저장되도록 한다.
			File storeFile = new File("C:\\uploadFiles",obfuscateName);
			//C:\\uploadFiles 폴더가 없으면 생성해라
			if(!storeFile.getParentFile().exists()) {
				storeFile.getParentFile().mkdirs();
			}
			try {
				attachFiles.transferTo(storeFile);
				//files 테이블에 첨부파일 데이터 insert
				UploadVO uploadVO = new UploadVO();
				String filename = attachFiles.getOriginalFilename();
				String ext = filename.substring(filename.lastIndexOf(".")+1);
				uploadVO.setFileGroupId(fileGroupId);
				uploadVO.setObfuscateName(obfuscateName);
				uploadVO.setDisplayName(filename);
				uploadVO.setExtendName(ext);
				uploadVO.setFileLength(storeFile.length());
				uploadVO.setFilePath(storeFile.getAbsolutePath());
				this.filesDao.insertAttachFile(uploadVO);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
