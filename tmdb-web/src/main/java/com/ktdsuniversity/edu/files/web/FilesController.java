package com.ktdsuniversity.edu.files.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.files.service.FilesService;
import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.response.DownLoadVO;

@Controller
public class FilesController {

	@Autowired
	private FilesService filesService;
	
	
private Map<String,String> mimeTypeMap;
	
	public FilesController() {
		this.mimeTypeMap = new HashMap<>();
		// 1. Text & Web
		this.mimeTypeMap.put("css", "text/css");
		this.mimeTypeMap.put("csv", "text/csv");
		this.mimeTypeMap.put("htm", "text/html");
		this.mimeTypeMap.put("html", "text/html");
		this.mimeTypeMap.put("js", "text/javascript");
		this.mimeTypeMap.put("txt", "text/plain");

		// 2. Images
		this.mimeTypeMap.put("gif", "image/gif");
		this.mimeTypeMap.put("jpeg", "image/jpeg");
		this.mimeTypeMap.put("jpg", "image/jpeg");
		this.mimeTypeMap.put("png", "image/png");
		this.mimeTypeMap.put("svg", "image/svg+xml");
		this.mimeTypeMap.put("webp", "image/webp");

		// 3. Audio & Video
		this.mimeTypeMap.put("mp3", "audio/mpeg");
		this.mimeTypeMap.put("mp4", "video/mp4");
		this.mimeTypeMap.put("oga", "audio/ogg");
		this.mimeTypeMap.put("webm", "video/webm");

		// 4. Documents (Office & PDF)
		this.mimeTypeMap.put("doc", "application/msword");
		this.mimeTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		this.mimeTypeMap.put("hwp", "application/x-hwp");
		this.mimeTypeMap.put("hwpx", "application/hpwx"); // 주의: 필요시 application/x-hwp-v8 등으로 수정
		this.mimeTypeMap.put("pdf", "application/pdf");
		this.mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		this.mimeTypeMap.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		this.mimeTypeMap.put("xls", "application/vnd.ms-excel");
		this.mimeTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		// 5. Binaries & Data
		this.mimeTypeMap.put("apk", "application/vnd.android.package-archive");
		this.mimeTypeMap.put("bin", "application/octet-stream");
		this.mimeTypeMap.put("exe", "application/octet-stream");
		this.mimeTypeMap.put("json", "application/json");
		this.mimeTypeMap.put("xml", "application/xml");
		this.mimeTypeMap.put("zip", "application/zip");
	}
	
	@GetMapping("/file/{fileGroupId}/{fileNum}")
	public ResponseEntity<Resource> doDownLoadAction(@PathVariable String fileGroupId, @PathVariable int fileNum){
		
		SearchFileVO searchFileVO = new SearchFileVO();
		searchFileVO.setFileGroupId(fileGroupId);
		searchFileVO.setFileNum(fileNum);
		
		//다운로드를 위한 정보와 파일 찾아오기
		DownLoadVO downLoadVO = this.filesService.findAttachFile(searchFileVO);
		if (downLoadVO == null || downLoadVO.getResource() == null) {
		    return ResponseEntity.notFound().build(); 
		}
		
		//다운로드 시작
		//HTTP Response 세팅
		//HTTP Response Header 세팅
		HttpHeaders headers = new HttpHeaders();
//		content-disposition = 파일이름
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+downLoadVO.getDisplayName());
//		content-length = 파일크기(bytes)
		headers.set(HttpHeaders.CONTENT_LENGTH, downLoadVO.getFileLength()+"");
//		content-type = 마임타입
		headers.set(HttpHeaders.CONTENT_TYPE, mimeTypeMap.getOrDefault(downLoadVO.getExtendName().toLowerCase(),"application/octet-stream"));
		
		//브라우저에게 http resoponse 전송
		return ResponseEntity.ok().headers(headers).body(downLoadVO.getResource());
	}
}
