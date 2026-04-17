<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!-- /templates/header.jsp  import -->
<jsp:include page="/WEB-INF/views/templates/header.jsp">
	<jsp:param value="게시글 목록" name="title" />
	<jsp:param
		value="<script type='text/javascript' src='/js/board.js'></script>"
		name="script" />
</jsp:include>
<div class="grid list">
	<h1>게시글 목록</h1>
	<div>총 ${searchCount}개의 게시글이 검색되었습니다.</div>
	<table class="grid">
		<thead>
			<tr>
				<th>아이디</th>
				<th>제목</th>
				<th>이름</th>
				<th>조회수</th>
				<th>작성 날자 및 시간</th>
				<th>수정날짜 및 시간</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty searchResult}">
					<c:forEach items="${searchResult}" var="board">
						<!-- 반복 대상 -->
						<tr>
							<td>${board.id}</td>
							<td><a href="/view/${board.id}">${board.subject}</a></td>
							<td>${board.membersVO.name}</td>
							<td>${board.viewCnt}</td>
							<td>${board.crtDt}</td>
							<td>${board.mdfyDt}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<!-- searchResult가 존재하지 않으면 "검색된 데디터가 없습니다"를 보여준다 -->
					<tr>
						<td colspan="6">검색된 데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="btn-group">
		<div class="right-align">
			<c:if test="${not empty sessionScope.__LOGIN_DATA__}">
				<a href="/write">새로운 게시글 작성</a>
			</c:if>
		</div>
	</div>

	<div class="search-box">
		<select id="list-size">
			<option value="10" ${pagination.listSize eq '10' ? 'selected':'' }>10개씩</option>
			<option value="20" ${pagination.listSize eq '20' ? 'selected':'' }>20개씩</option>
			<option value="50" ${pagination.listSize eq '50' ? 'selected':'' }>50개씩</option>
			<option value="100" ${pagination.listSize eq '100' ? 'selected':'' }>100개씩</option>
		</select>
		<div>
			<select id="search-type">
				<option value="email"
					${pagination.searchType eq "email" ? "selected" : "" }>이메일로 검색</option>
				<option value="name"
					${pagination.searchType eq "name" ? "selected" : "" }>작성자 이름으로 검색</option>
				<option value="subject"
					${pagination.searchType eq "subject" ? "selected" : "" }>제목으로 검색</option>
				<option value="content"
					${pagination.searchType eq "content" ? "selected" : "" }>내용으로 검색</option>
				<option value="subject+content"
					${pagination.searchType eq "subject content" ? "selected" : "" }>제목 + 내용으로 검색</option>
			</select> <input type="text" id="search-keyword" placeholder="검색어를 입력하세요"
				value="${pagination.searchKeyword}" />
			<button type="button" class="search-button">검색!</button>
		</div>
	</div>
	<c:if test="${pagination.pageCount > 0 }">
		<ul class="page-navigator">
			<c:if test="${pagination.hasPrevPageGroup }">
				<li><a data-page-no="0" href="javascript:void(-1)"> 처음</a></li>
				<li><a data-page-no="${pagination.prevPageGroupStartPageNo }"
					href="javascript:void(-1)">이전</a></li>
			</c:if>
			<c:forEach begin="${pagination.groupStartPageNo }"
				end="${pagination.groupEndPageNo -1}" step="1" var="page">
				<li class="${page eq pagination.pageNo ? 'active': '' }"><a
					data-page-no="${page}" href="javascript:void(-1)">${page + 1}</a></li>
			</c:forEach>
			<c:if test="${pagination.hasNextPageGroup }">
				<li><a data-page-no="${pagination.nextPageGroupStartPageNo }"
					href="javascript:void(-1)"> 다음</a></li>
				<li><a data-page-no="${pagination.pageCount - 1 }"
					href="javascript:void(-1)"> 마지막</a></li>
			</c:if>
		</ul>
	</c:if>
</div>
<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>
