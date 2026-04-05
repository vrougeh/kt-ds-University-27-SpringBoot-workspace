<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원정보 수정</title>
    <link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <h1>회원정보 수정</h1>
    <form method="post" action="/member/update/${user.email}">
      <div class="grid write">
        <label for="name">이름</label>
        <input
          type="text"
          name="name"
          id="name"
          placeholder="이름을 입력하세요"
          value= "${user.name}"
        />

        <label for="password">비밀번호</label>
        <input
          type="password"
          name="password"
          id="password"
          placeholder="비밀번호를 입력하세요"
          value="${user.password}"
        />

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </form>
  </body>
</html>
