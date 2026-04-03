<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원가입</title>
    <script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
    <script type="text/javascript" src="/js/members.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
  </head>
  <body>
    <h1>회원가입</h1>
    <form:form modelAttribute="registVO" method="post" action="/regist">
      <div class="grid regist">
        <label for="email">이메일</label>
        <div class="input-div">
          <input
            type="email"
            name="email"
            id="email"
            placeholder="이메일을 입력하세요"
            value="${inputData.email }"
          />
          <form:errors path="email" cssClass="validation-error" element="div" />
        </div>

        <label for="name">이름</label>
        <div class="input-div">
          <input
            type="text"
            name="name"
            id="name"
            placeholder="이름을 입력하세요"
            value="${inputData.name }"
          />
          <form:errors path="name" cssClass="validation-error" element="div" />
        </div>

        <label for="password">비밀번호</label>
        <div class="input-div">
          <input
            type="password"
            name="password"
            id="password"
            placeholder="비밀번호를 입력하세요"
          />
          <form:errors
            path="password"
            cssClass="validation-error"
            element="div"
          />
        </div>

        <label for="confirm-password">비밀번호 확인</label>
        <div class="input-div">
          <input
            type="password"
            id="confirm-password"
            name="confirm-password"
          />
        </div>

        <label for="show-password">비밀번호 확인하기</label>
        <input type="checkbox" id="show-password" />

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </form:form>
  </body>
</html>
