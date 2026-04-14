<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>${param.title}</title>
<link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
<script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
${param.script}
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<c:choose>
				<c:when test="${ empty sessionScope.__LOGIN_DATA__}">
					<a href="/">홈</a>
					<a href="/regist">회원가입</a>
					<a href="/login">로그인</a>
					<!-- 로그인 안했을 때 링크 -->
				</c:when>
				<c:otherwise>
					<!-- 로그인 했을 때 링크 -->
					<div class="member-info"
						data-email="${sessionScope.__LOGIN_DATA__.email}">
						${sessionScope.__LOGIN_DATA__.name}
						${sessionScope.__LOGIN_DATA__.email}</div>
					<a href="/">홈</a>
					<a href="/member/view/${sessionScope.__LOGIN_DATA__.email}">마이페이지</a>
					<a href="/logout">로그아웃</a>
				</c:otherwise>
			</c:choose>
		</div>