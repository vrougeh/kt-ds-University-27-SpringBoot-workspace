<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원가입 성공</title>
    <link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
  </head>
  <body>
<h1>회원 목록</h1>
    <div>총 ${searchCount}개의 회원이 검색되었습니다.</div>
    <div>이름 : ${searchResult[0].name}</div>
    <div>이메일 : ${searchResult[0].email}</div>
    <div>비밀번호 : ${searchResult[0].password}</div>
    </body>
</html>
