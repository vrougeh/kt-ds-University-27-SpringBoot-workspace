package com.ktdsuniversity.edu.movie.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;
import com.ktdsuniversity.edu.movie.dao.MovieDao;
import com.ktdsuniversity.edu.movie.vo.MovieVO;
import com.ktdsuniversity.edu.movie.vo.request.WriteVO;
import com.ktdsuniversity.edu.movie.vo.response.SearchResultVO;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private FilesDao filesDao;

	@Override
	public SearchResultVO findAllMovie() {
		
		int count = this.movieDao.selectMovieCount();
		
		//게시글 목록조회
		List<MovieVO> list = this.movieDao.selectMovieList();
		
		SearchResultVO result = new SearchResultVO();
		result.setMovieList(list);
		result.setMovieCount(count);
		
		return result;
	}

	@Override
	public boolean createNewMovie(WriteVO writeVO) {
		int insertCount = this.movieDao.insertNewMovie(writeVO);
		
		//첨부파일 업로드 코드 추가
		MultipartFile attachFiles = writeVO.getAttachFile();
		if(attachFiles != null && attachFiles.getSize() > 0) {
			System.out.println("파일 개수" + attachFiles.getSize());
			//업로드한 파일이 서버컴퓨터의 파일시스템에 저장되도록 한다.
			File storeFile = new File("C:\\uploadFiles",attachFiles.getOriginalFilename());
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
				uploadVO.setFileNum(1);
				uploadVO.setFileGroupId(writeVO.getMovieId());
				uploadVO.setObfuscateName(filename); // 추후 난독화
				uploadVO.setDisplayName(filename);
				uploadVO.setExtendName(ext);
				uploadVO.setFileLength(storeFile.length());
				uploadVO.setFilePath(storeFile.getAbsolutePath());
				this.filesDao.insertAttachFile(uploadVO);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return insertCount == 1;
	}

}
