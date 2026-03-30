package com.ktdsuniversity.edu.board.vo.request;

public class UpdateVO extends WriteVO {
	private String id; //UPDATE - where 에 사용할 변수

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
