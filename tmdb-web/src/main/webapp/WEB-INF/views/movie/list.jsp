<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>list</title>
    <link rel="stylesheet" type="text/css" href="/css/tmdb" />
  </head>
  <body>
  <div class="grid list">
    <h1>영화 목록</h1>
    <div>총 ${searchCount}개의 영화가 검색되었습니다.</div>
    <table class="grid">
      <thead>
      <tr>
        <th>영화 아이디</th>
        <th>포스트 URL</th>
        <th>영화 타이틀</th>
        <th>관람 등급</th>
        <th>오픈 날짜</th>
        <th>개봉 국가</th>
        <th>러닝타임</th>
        <th>영화 설명</th>
        <th>시놉시스</th>
        <th>오리지널타이틀</th>
        <th>개봉상태</th>
        <th>언어</th>
        <th>제작비</th>
        <th>수익</th>
      </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${not empty searchResult }">
            <c:forEach items="${searchResult}" var="movie">
              <tr>
                <td>${movie.movieId}</td>
                <td>${movie.posterUrl}</td>
                <td>${movie.title}</td>
                <td>${movie.movieRating}</td>
                <td>${movie.openDate}</td>
                <td>${movie.openCountry}</td>
                <td>${movie.runningTime}</td>
                <td>${movie.introduce}</td>
                <td>${movie.synopsis}</td>
                <td>${movie.originalTitle}</td>
                <td>${movie.state}</td>
                <td>${movie.language}</td>
                <td>${movie.budget}</td>
                <td>${movie.profit}</td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
          <tr>
            <td colspan="13">검색된 데이터가 없습니다.</td>
          </tr>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
    </div>
  </body>
</html>
