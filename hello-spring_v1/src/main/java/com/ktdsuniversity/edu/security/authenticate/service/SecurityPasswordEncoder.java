package com.ktdsuniversity.edu.security.authenticate.service;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktdsuniversity.edu.members.helpers.SHA256Util;

/**
 * 데이터 베이스에 있는 비밀번호와 로그인 요청 정보의 비밀번호가 일치하는지 검사
 *
 * 필요한데이터 1.데이터베이스의 회원 비밀번호(암호화가 되어있는 비밀번호) 2.로그인 요청 정보 중 비밀번호(암호화가 되어있지 않은
 * 비밀번호) 3.로그인 요청 정보 중 비밀번호를 암호화 하기 위한 salt 정보
 */
public class SecurityPasswordEncoder implements PasswordEncoder {

	/**
	 * 현재 사용안함
	 * 로그인 요청 정보 중 비밀번호를 암호화 하는 코드 SHA 암호화 코드에서는 사용 불가!
	 *
	 * @param rawPassword : 암호화 되어 있지 않은 평문 비밀번호
	 * @return 암호화된 비밀번호
	 */
	@Override
	public @Nullable String encode(@Nullable CharSequence rawPassword) {
		return null;
	}

	/**
	 * 현재 사용안함
	 * 로그인 요청 정보 중 평문 비밀번호와 데이터베이스에 있는 암호화된 비밀번호가 일치하는지 검사 평문 비밀번호 > 암호화 > 데이터베이스의
	 * 암호와 비교
	 */
	@Override
	public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
		return false;
	}

	/**
	 * 평문 비밀번호를 사용자 고유의 Salt와 결합하여 SHA-256 방식으로 암호화함.
	 * * @param rawPassword 사용자가 입력한 평문 비밀번호
	 * @param salt DB에 저장된 해당 사용자의 고유 Salt 값
	 * @return SHA-256으로 암호화된 비밀번호 문자열
	 */
	public String encode(String rawPassword, String salt) {
		return SHA256Util.getEncrypt(rawPassword, salt);
	}

	/**
	 * 로그인 시 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호를 비교함.
	 * * @param rawPassword 사용자가 로그인 페이지에서 입력한 평문 비밀번호
	 * @param salt DB에서 조회한 해당 사용자의 Salt
	 * @param encodedPassword DB에 저장되어 있는 암호화된 비밀번호
	 * @return 일치 여부 (true: 일치, false: 불일치)
	 */
	public boolean matches(String rawPassword, String salt, String encodedPassword) {
		return this.encode(rawPassword, salt).equals(encodedPassword);
	}

}
