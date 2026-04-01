<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 조회</title>
<link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
</head>
<body>
    <h1>영화 내용 조회</h1>
    <div class="grid view">
        <span>영화 아이디</span>
        <div>${movie.movieId }</div>
        
        <span>영화 포스터 URL</span>
        <div>${movie.posterUrl }</div>
        
        <span>영화 포스터 이미지</span>
        <div>
        <img alt="이미지가 없습니다." src="${movie.files.filePath}/">
        </div>
        
        <span>영화 타이틀</span>
        <div>${movie.title }</div>
        
        <span>관람 등급</span>
        <div>${movie.movieRating }</div>
        
        <span>오픈 날짜</span>
        <div>${movie.openDate }</div>
        
        <span>개봉 국가</span>
        <div>${movie.openCountry }</div>
        
        <span>러닝타임</span>
        <div>${movie.runningTime }</div>
        
        <span>영화 설명</span>
        <div>${movie.introduce }</div>
        
        <span>시놉시스</span>
        <div>${movie.synopsis }</div>
        
        <span>오리지널타이틀</span>
        <div>${movie.originalTitle }</div>
        
        <span>개봉상태</span>
        <div>${movie.state }</div>
        
        <span>언어</span>
        <div>${movie.language }</div>
        
        <span>제작비</span>
        <div>${movie.budget }</div>
        
        <span>수익</span>
        <div>${movie.profit }</div>
        
        <div class="btn-group">
          <div class="right-align">
              <a href="/update/${movie.movieId}">수정</a>
              <a href="/delete?id=${movie.movieId}">삭제</a>
          </div>
        </div>
    </div>
</body>
</html>