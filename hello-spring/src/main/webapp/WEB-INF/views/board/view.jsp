<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp">
    <jsp:param value="게시글 조회 : ${article.id}"  name="title"/>
</jsp:include>
    <h1>게시글 내용 조회</h1>
    <div class="grid view">
      <span>아이디</span>
      <div>${article.id}</div>

      <span>제목</span>
      <div>${article.subject}</div>
      
      <span>이름</span>
      <div>${article.membersVO.name} (가입일 ${article.membersVO.registDate} )</div>

      <span>조회수</span>
      <div>${article.viewCnt}</div>

      <span>작성일</span>
      <div>${article.crtDt}</div>

      <span>마지막 수정일</span>
      <div>${article.mdfyDt}</div>

      <span>첨부파일</span>
      <div>
        <ul class="vertical-list">
          <c:forEach items="${article.files}" var="file">
            <li>
              <a href="/file/${file.fileGroupId}/${file.fileNum}">
              ${file.displayName}
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>

      <span>내용</span>
      <pre>${article.content}</pre>

      <div class="btn-group">
        <div class="right-align">
            <c:if test="${article.email eq sessionScope.__LOGIN_DATA__.email }">
	            <a href="/update/${article.id}">수정</a>
	            <a href="/delete?id=${article.id}">삭제</a>
            </c:if>
        </div>
      </div>
<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>