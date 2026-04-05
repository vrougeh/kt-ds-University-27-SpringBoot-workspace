<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>회원 리스트</title>
        <link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
    </head>
    <body>
      <jsp:include page="/WEB-INF/views/common/header.jsp" />
	    <div class="grid list">
	        <h1>회원 목록</h1>
	        <div>총 ${searchCount}개의 회원이 검색되었습니다.</div>
	        <table class="grid">
		        <thead>
		            <tr>
		                <th>이메일</th>
		                <th>이름</th>
		                <th>비밀번호</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:choose>
		                <c:when test="${not empty searchResult }">
		                    <c:forEach items="${searchResult}" var="user">
			                    <tr>
					                <td>${user.email}</td>
					                <td>${user.name}</td>
					                <td>${user.password}</td>
					            </tr>
		                    </c:forEach>
		                </c:when>
		                <c:otherwise>
		                    <tr>
		                        <td colspan="3">검색된 데이터가 없습니다.</td>
		                    </tr>
		                </c:otherwise>
		            </c:choose>
		        </tbody>
		    </table>
	        <div class="btn-group">
	            <div class="right-align">
	                <a href="/regist">회원가입</a>
	            </div>
	        </div>
	    </div>
    </body>
</html>
