package com.ktdsuniversity.edu.members.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegistVO {

	@Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "이메일 형태가 아닙니다.")
	@NotBlank(message = "이메일 형태가 아닙니다.")
	private String email;
	@NotBlank(message = "이름을 반드시 입력해주세요")
	private String name;
	@NotBlank(message = "비밀번호를 반드시 입력해주세요")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$"
	       , message = "비밀번호는 영소문자, 영대문자, 숫자 최소 1개를 포함하여 8글자 이상으로 입력하세요")
	private String password;

	private String salt;

	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
