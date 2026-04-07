<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
<jsp:param value="게시글 조회 ${user.email}"  name="title"/>
</jsp:include>
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
                <a href="/delete-me">탈퇴하기</a>
            </div>
      </div>
<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>