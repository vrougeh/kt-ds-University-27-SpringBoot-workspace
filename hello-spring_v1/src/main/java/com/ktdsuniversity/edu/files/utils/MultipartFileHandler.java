package com.ktdsuniversity.edu.files.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Component
public class MultipartFileHandler {

	private static final Logger logger = LoggerFactory.getLogger(MultipartFileHandler.class);

	@Autowired
	private FilesDao filesDao;

	public String upload(List<MultipartFile> attachFiles, String fileGroupId) {
		if(attachFiles != null && attachFiles.size() > 0) {
			logger.debug("파일 개수 {}", attachFiles.size());
//			System.out.println("파일 개수" + attachFiles.size());

//			for(MultipartFile uploadedFile : attachFiles) {
			for (MultipartFile attachFile : attachFiles) {

				//업로드를 하지 않았는데 했다고 판단한 경우에는 다음 반복으로 넘어가라
				if(attachFile.isEmpty()) {
					continue;
				}


				String obfuscateName = UUID.randomUUID().toString();
				//업로드한 파일이 서버컴퓨터의 파일시스템에 저장되도록 한다.
				File storeFile = new File("C:\\uploadFiles",obfuscateName);
				//C:\\uploadFiles 폴더가 없으면 생성해라
				if(!storeFile.getParentFile().exists()) {
					storeFile.getParentFile().mkdirs();
				}
				try {
					attachFile.transferTo(storeFile);
					//files 테이블에 첨부파일 데이터 insert
					UploadVO uploadVO = new UploadVO();
					String filename = attachFile.getOriginalFilename();
					String ext = filename.substring(filename.lastIndexOf(".")+1);
					uploadVO.setFileGroupId(fileGroupId);
					uploadVO.setObfuscateName(obfuscateName);
					uploadVO.setDisplayName(filename);
					uploadVO.setExtendName(ext);
					uploadVO.setFileLength(storeFile.length());
					uploadVO.setFilePath(storeFile.getAbsolutePath());
					this.filesDao.insertAttachFile(uploadVO);
				} catch (IllegalStateException | IOException e) {
//					e.printStackTrace();
					logger.error("파일 업로드중 에러 발생!",e);
				}
			}
			return fileGroupId;
		}
		return null;
	}

	/**
	 * @param attachFiles
	 * @return 첨부파일의 그룹 아이디
	 */
	public String upload(List<MultipartFile> attachFiles) {

		if(attachFiles != null && attachFiles.size() > 0) {
			logger.debug("파일 개수 {}", attachFiles.size());
//			System.out.println("파일 개수" + attachFiles.size());

			String fileGroupId = this.filesDao.selectNewFileGroupId();
			this.filesDao.insertFileGroupId(fileGroupId);

			this.upload(attachFiles, fileGroupId);

			return fileGroupId;
		}
		return null;
	}



}
