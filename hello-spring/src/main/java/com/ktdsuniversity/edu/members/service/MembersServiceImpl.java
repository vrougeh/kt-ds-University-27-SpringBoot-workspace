package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.helpers.SHA256Util;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;

	@Transactional
	@Override
	public boolean createNewMembers(RegistVO registVO) {

		MembersVO membersVO = this.membersDao.selectMembersByEmail(registVO.getEmail());
		if (membersVO != null) {
			throw new HelloSpringException("이미 사용중인 이메일 입니다.", "members/regist", registVO);
		}
		// 암호화를 위한 비밀키 생성
		String newSalt = SHA256Util.generateSalt();
		String userPassword = registVO.getPassword();
		// 사용자가 입력한 비밀번호를 newSalt를 이용해 암호화
		// 비밀번호화 newSalt의 값이 일치하면, 항상 같은 값의 암호화 결과가 생성된다.
		userPassword = SHA256Util.getEncrypt(userPassword, newSalt);
		registVO.setSalt(newSalt);
		registVO.setPassword(userPassword);
		int insertCount = this.membersDao.insertNewMembers(registVO);
		System.out.println("생성된 멤버의 개수? " + insertCount);
		return insertCount == 1;
	}

	@Override
	public SearchResultVO findMembers() {
		int count = this.membersDao.selectMembersCount();

		List<MembersVO> list = this.membersDao.selectMembersList();

		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);

		return result;
	}

	@Override
	public MembersVO findMembersByEmail(String email) {
		MembersVO user = this.membersDao.selectMembersByEmail(email);
		return user;
	}

	@Transactional
	@Override
	public boolean deleteMembersByEmail(String email) {
		int deleteCount = this.membersDao.deleteMembersByEmail(email);
		return deleteCount == 1;
	}

	@Transactional
	@Override
	public boolean updateMembersByEmail(UpdateVO updateVO) {
		String newSalt = SHA256Util.generateSalt();
		String userPassword = updateVO.getPassword();
		// 사용자가 입력한 비밀번호를 newSalt를 이용해 암호화
		// 비밀번호화 newSalt의 값이 일치하면, 항상 같은 값의 암호화 결과가 생성된다.
		userPassword = SHA256Util.getEncrypt(userPassword, newSalt);
		updateVO.setSalt(newSalt);
		updateVO.setPassword(userPassword);
		int updateCount = this.membersDao.updateMembersByEmail(updateVO);
		return updateCount == 1;
	}

	@Transactional(noRollbackFor = HelloSpringException.class)
	@Override
	public MembersVO findMemberByEmailAndPassword(LoginVO loginVO) {

		// email을 이용해 회원 정보 조회하기(selectMemberByEmail)
		MembersVO searchResult = this.membersDao.selectMembersByEmail(loginVO.getEmail());
		// 조회된 결과가 없다면 이메일 또는 비밀번호가 잘못되었습니다 예외 던지기 - IllegalArgumentsException
		if (searchResult == null) {
			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다", "members/login", loginVO);
		}

		if (searchResult.getBlockYn().equals("Y")) {

			// 로그인 BLOCK 된 시간으로부터 120분이 지나면 다시 로그인 가능한 상태로 변경한다
			// 이경우엔 예외를 던지지 않도록 한다.
			String latestLoginFailDate = searchResult.getLatestLoginFailDate();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime latestLoginBlockDate = LocalDateTime.parse(latestLoginFailDate, formatter);

			if (latestLoginBlockDate.isAfter(LocalDateTime.now().minusMinutes(120))) {
				throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다", "members/login", loginVO);
			}

		}
		// 조회된 결과가 있다면 사용자가 전송한 비밀번호와 조회된 회원의 salt를 이용해 sha 암호화하기
		String userpassword = loginVO.getPassword();
		String usersalt = searchResult.getSalt();

		System.out.println("비밀번호 : " + userpassword + ", salt : " + usersalt);
		// 암호화한 비밀번호와 조회한 비밀번호가 일치하는지 확인하기
		String encryptedPassword = SHA256Util.getEncrypt(userpassword, usersalt);
		System.out.println("암호화된 : " + encryptedPassword);
		// 비밀번확 일치하지 않는다면 이메일 또는 비밀번확 잘못되었습니다 예외 던지기 - IllegalArgumentsException
		if (!encryptedPassword.equals(searchResult.getPassword())) {
			// 해당 이메일의 로그인 실패 횟수를 1증가시키고
			// 최근 로그인 실패 날짜와 시간을 현재로 변경한다.
			this.membersDao.updateIncreaseLoginFailCount(loginVO.getEmail());

			// 최근 로그인 실패 횟수가 5 이상이라면 block-yn을 y로 변경한다.
			this.membersDao.updateBlock(loginVO.getEmail());

			throw new HelloSpringException("이메일 또는 비밀번호가 잘못되었습니다", "members/login", loginVO);
		}
		// 로그인 성공 처리
		// 1. login_fail_count를 0으로 초기화
		// 2. latest_login_ip를 현제 아이피로 변경
		// 3. login_date를 현재 시간으로 변경
		// 4. block_yn을 N으로 변경
		this.membersDao.updateSuccessLogin(loginVO);
		// 비밀번호가 일치하면 조회한 결과를 반환
		return searchResult;
	}

}
