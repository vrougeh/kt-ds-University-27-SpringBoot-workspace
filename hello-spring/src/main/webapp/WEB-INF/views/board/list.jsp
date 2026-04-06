<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>게시글 목록</title>
		<link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
	</head>
<body>
	<div class="grid list">
		<h1>게시글 목록</h1>
		<div>총 ${searchCount}개의 게시글이 검색되었습니다.</div>
		<div class="login-button"><a href="/login">로그인</a></div>
		<div class="loout"><a href="/logout">로그아웃</a></div>
		<div class="regist"><a href="/regist">회원가입</a></div>
		<table class="grid">
			<thead>
				<tr>
					<th>아이디</th>
					<th>제목</th>
					<th>이메일</th>
					<th>조회수</th>
					<th>작성 날자 및 시간</th>
					<th>수정날짜 및 시간</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty searchResult }">
						<c:forEach items="${searchResult}" var="board">
							<!-- 반복 대상 -->
							<tr>
								<td>${board.id}</td>
								<td>
									<a href="/view/${board.id}">${board.subject}</a> 
								</td>
								<td>${board.email}</td>
								<td>${board.viewCnt}</td>
								<td>${board.crtDt}</td>
								<td>${board.mdfyDt}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<!-- searchResult가 존재하지 않으면 "검색된 데디터가 없습니다"를 보여준다 -->
						<tr>
							<td colspan="6">검색된 데이터가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="btn-group">
			<div class="right-align">
				<a href="/write">새로운 게시글 작성</a>
			</div>
		</div>
	</div>
</body>
</html>
