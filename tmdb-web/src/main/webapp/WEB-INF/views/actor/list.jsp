<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class = "grid list">
			<h1>배우 목록</h1>
			<div>총 ${searchCount}명의 배우가 검색되었습니다.</div>
			<table>
				<thead>
				<tr>
					<th>배우 아이디</th>
					<th>배우 이름</th>
					<th>배우 포스터 URL</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${not empty searchResult }">
						<c:forEach items="${searchResult}" var="actor">
							<tr>
								<td>${actor.actorId}</td>
								<td>${actor.actorName}</td>
								<td>${actor.actorProfileUrl}</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
				</tbody>
			</table>
			
		</div>
	</body>
</html>