package com.ktdsuniversity.edu.board.vo.request;

import java.util.List;

public class UpdateVO extends WriteVO {

	private List<Integer> deleteFileNum;

	public List<Integer> getDeleteFileNum() {
		return this.deleteFileNum;
	}

	public void setDeleteFileNum(List<Integer> deleteFileNum) {
		this.deleteFileNum = deleteFileNum;
	}


}
