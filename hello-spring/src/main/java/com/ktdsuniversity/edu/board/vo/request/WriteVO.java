package com.ktdsuniversity.edu.board.vo.request;


/**
 * 게시글 등록을 위해
 * 브라우저에서 컨트롤서(엔드포인트)로 전송되는
 * 파라미터를 받아오기 위한 클래스
 * Spring이 파라미터를 WriteVO의 멤버면수로 할당할 때
 * setter를 이용
 */
public class WriteVO {
	private String subject;
	private String content;
	private String email;
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


}
