<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>write</title>
    <link rel="stylesheet" type="text/css" href="/css/tmdb.css" />
  </head>
  <body>
    <h1>영화 등록</h1>
    <form method="post" action="/update/${movie.movieId }" enctype="multipart/form-data">
      <div class="grid write">
        <label for="posterUrl">포스터 URL</label>
        <input
          id="posterUrl"
          type="text"
          name="posterUrl"
          placeholder="포스터URL을 입력하세요."
          value="${movie.posterUrl }"
        />
        
        <label for="attach-files">포스터 이미지 첨부파일</label>
        <div id="attach-files" class="attach-files">
          <input type="file" name="attachFile"/>
        </div>

        <label for="title">영화 타이틀</label>
        <input
          id="title"
          type="text"
          name="title"
          placeholder="영화 제목을 입력하세요."
          value="${movie.title }"
        />

        <label for="movieRating">관람 등급</label>
        <input
          id="movieRating"
          type="text"
          name="movieRating"
          placeholder="관람 등급을 입력하세요. 3자 제한"
          value="${movie.movieRating }"
        />

        <label for="openDate">개봉 날짜</label>
        <input id="openDate" type="date" name="openDate" value="${movie.openDate }" />

        <label for="openCountry">개봉 국가</label>
        <input
          id="openCountry"
          type="text"
          name="openCountry"
          placeholder="개봉 국가를 입력하세요. 2자 제한"
          value="${movie.openCountry }"
        />

        <label for="runningTime">러닝타임</label>
        <input
          id="runningTime"
          type="number"
          name="runningTime"
          placeholder="러닝타입을 입력하세요."
          value="${movie.runningTime }"
        />

        <label for="introduce">영화 설명</label>
        <textarea
          name="introduce"
          id="introduce"
          placeholder="내용을 입력하세요."
        >${movie.introduce}</textarea>

        <label for="synopsis">시놉시스</label>
        <textarea
          name="synopsis"
          id="synopsis"
          placeholder="내용을 입력하세요."
        >${movie.synopsis}</textarea>

        <label for="originalTitle">오리지널타이틀</label>
        <input
          id="originalTitle"
          type="text"
          name="originalTitle"
          placeholder="오리지널타이틀을 입력하세요."
          value="${movie.originalTitle }"
        />

        <label for="state">개봉상태</label>
        <input
          id="state"
          type="text"
          name="state"
          placeholder="개봉상태를 입력하세요. 5자 제한"
          value="${movie.state }"
        />

        <label for="language">언어</label>
        <input
          id="language"
          type="text"
          name="language"
          placeholder="언어을 입력하세요. 6자 제한"
          value="${movie.language }"
        />

        <label for="budget">제작비</label>
        <input
          id="budget"
          type="number"
          name="budget"
          placeholder="제작비를 입력하세요."
          value="${movie.budget }"
        />

        <label for="profit">수익</label>
        <input
          id="profit"
          type="number"
          name="profit"
          placeholder="수익을 입력하세요."
          value="${movie.profit }"
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
