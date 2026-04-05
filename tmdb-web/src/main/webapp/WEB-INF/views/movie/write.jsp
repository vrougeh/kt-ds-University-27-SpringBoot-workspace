<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>write</title>
    <link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
    <script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
    <script type="text/javascript" src="/js/movie.js"></script>
  </head>
  <body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <h1>영화 등록</h1>
    <form:form modelAttribute="writeVO" method="post" action="/write" enctype="multipart/form-data">
     <div class="grid write">
        
        <label for="attach-files">포스터 이미지 첨부파일</label>
        <div id="attach-files" class="attach-files">
          <input type="file" name="attachFile" />
        </div>

        <label for="title">영화 타이틀</label>
        <div class="input-div">
          <input
            id="title"
            type="text"
            name="title"
            placeholder="영화 제목을 입력하세요."
            value="${inputData.title }"
          />
          <form:errors path="title" cssClass="validation-error" element="div"/>
        </div>

        <label for="movieRating">관람 등급</label>
        <input
          id="movieRating"
          type="text"
          name="movieRating"
          placeholder="관람 등급을 입력하세요. 3자 제한"
          value="${inputData.movieRating }"
        />

        <label for="openDate">개봉 날짜</label>
        <input id="openDate" type="date" name="openDate" />

        <label for="openCountry">개봉 국가</label>
        <input
          id="openCountry"
          type="text"
          name="openCountry"
          placeholder="개봉 국가를 입력하세요. 2자 제한"
          value="${inputData.openDate }"
        />

        <label for="runningTime">러닝타임</label>
        <input
          id="runningTime"
          type="number"
          name="runningTime"
          placeholder="러닝타입을 입력하세요."
          value="${inputData.runningTime }"
        />

        <label for="introduce">영화 설명</label>
        <textarea
          name="introduce"
          id="introduce"
          placeholder="내용을 입력하세요."
        ></textarea>

        <label for="synopsis">시놉시스</label>
        <div class="input-div">
          <textarea
            name="synopsis"
            id="synopsis"
            placeholder="내용을 입력하세요."
          > ${inputData.synopsis } </textarea>
        <form:errors path="synopsis" cssClass="validation-error" element="div"/>
        </div>

        <label for="originalTitle">오리지널타이틀</label>
        <input
          id="originalTitle"
          type="text"
          name="originalTitle"
          placeholder="오리지널타이틀을 입력하세요."
          value="${inputData.originalTitle }"
        />

        <label for="state">개봉상태</label>
        <div class="input-div">
          <input
            id="state"
            type="text"
            name="state"
            placeholder="개봉상태를 입력하세요. 5자 제한"
            value="${inputData.state }"
          />
          <form:errors path="state" cssClass="validation-error" element="div"/>
        </div>

        <label for="language">언어</label>
         <div class="input-div">
          <input
            id="language"
            type="text"
            name="language"
            placeholder="언어을 입력하세요. 6자 제한"
            value="${inputData.language }"
          />
          <form:errors path="language" cssClass="validation-error" element="div"/>
        </div>

        <label for="budget">제작비</label>
        <input
          id="budget"
          type="number"
          name="budget"
          placeholder="제작비를 입력하세요."
          value="${inputData.budget }"
        />

        <label for="profit">수익</label>
        <input
          id="profit"
          type="number"
          name="profit"
          placeholder="수익을 입력하세요."
          value="${inputData.profit }"
        />

        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="저장" />
          </div>
        </div>
      </div>
    </form:form>
  </body>
</html>
