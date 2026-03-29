<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>list</title>
  </head>
  <body>
    <h1>영화 목록</h1>
    <div>총 ${searchCount}개의 영화가 검색되었습니다.</div>
    <div>영화 아이디: ${searchResult[0].movieId}</div>
    <div>포스트 URL: ${searchResult[0].posterUrl}</div>
    <div>영화 타이틀: ${searchResult[0].title}</div>
    <div>관람 등급: ${searchResult[0].movieRating}</div>
    <div>오픈 날짜: ${searchResult[0].openDate}</div>
    <div>개봉 국가: ${searchResult[0].openCountry}</div>
    <div>러닝타임: ${searchResult[0].runningTime}</div>
    <div>영화 설명: ${searchResult[0].introduce}</div>
    <div>시놉시스: ${searchResult[0].synopsis}</div>
    <div>오리지널타이틀: ${searchResult[0].originalTitle}</div>
    <div>개봉상태: ${searchResult[0].movieState}</div>
    <div>언어: ${searchResult[0].language}</div>
    <div>쓴돈: ${searchResult[0].budget}</div>
    <div>번돈: ${searchResult[0].profit}</div>
  </body>
</html>
