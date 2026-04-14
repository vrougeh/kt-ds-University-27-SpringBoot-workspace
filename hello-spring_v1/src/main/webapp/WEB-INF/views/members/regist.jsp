<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
	<jsp:param value="회원가입" name="title" />
	<jsp:param
		value="<script type='text/javascript' src='/js/members.js'></script>"
		name="script" />
</jsp:include>
<h1>회원가입</h1>
<div>
	<a href="/">홈</a>
</div>
<form:form modelAttribute="registVO" method="post" action="/regist">
	<div class="grid regist">
		<label for="email">이메일</label>
		<div class="input-div">
			<input type="email" name="email" id="email" placeholder="이메일을 입력하세요"
				value="${inputData.email } ${errorData.email}" />
			<form:errors path="email" cssClass="validation-error" element="div" />
			<c:if test="${not empty errorMessage}">
				<div class="validation-error">${errorMessage}</div>
			</c:if>
		</div>

		<label for="name">이름</label>
		<div class="input-div">
			<input type="text" name="name" id="name" placeholder="이름을 입력하세요"
				value="${inputData.name }${errorData.name}" />
			<form:errors path="name" cssClass="validation-error" element="div" />
		</div>

		<label for="password">비밀번호</label>
		<div class="input-div">
			<input type="password" name="password" id="password"
				placeholder="비밀번호를 입력하세요" />
			<form:errors path="password" cssClass="validation-error"
				element="div" />
		</div>

		<label for="confirm-password">비밀번호 확인</label>
		<div class="input-div">
			<input type="password" id="confirm-password" name="confirm-password" />
		</div>

		<label for="show-password">비밀번호 확인하기</label> <input type="checkbox"
			id="show-password" />

		<div class="btn-group">
			<div class="right-align">
				<input type="submit" value="저장" />
			</div>
		</div>
	</div>
</form:form>
<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>
