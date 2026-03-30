<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원 조회 : 이메일</title>
            <link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
    </head>
    <body>
        <h1>회원 조회</h1>
        <div class="grid view">
            <span>이메일</span>
            <div>${user.email}</div>
            <span>이름</span>
            <div>${user.name}</div>
            <span>비밀번호</span>
            <div>${user.password}</div>
        </div>
        <div class="btn-group">
            <div class="right-align">
                <a href="/member/update/${user.email}">수정</a>
                <a href="/member/delete?id=${user.email}">삭제</a>
            </div>
      </div>
    </body>
</html>