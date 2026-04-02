package com.ktdsuniversity.edu.movie.vo.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class WriteVO {
	@NotBlank(message = "ID를 반드시 입력해주세요")
	private String movieId;
	@NotBlank(message = "URL을 반드시 입력해주세요")
	private String posterUrl;
	@NotBlank(message = "타이틀을 반드시 입력해주세요")
	private String title;
	@Size(max=3, message="최대 3자 입력가능")
	private String movieRating;
	private String openDate;
	@Size(max=2, message="최대 2자 입력가능")
	private String openCountry;
	private int runningTime;
	private String introduce;
	@NotBlank(message = "시놉시스를 반드시 입력해주세요")
	private String synopsis;
	private String originalTitle;
	@Size(max=5, message="최대 5자 입력가능")
	@NotBlank(message = "상태를 반드시 입력해주세요")
	private String state;
	@Size(max=6, message="최대 6자 입력가능")
	@NotBlank(message = "언어를 반드시 입력해주세요")
	private String language;
	private long budget;
	private long profit;
	
	private MultipartFile attachFile;
	
	public MultipartFile getAttachFile() {
		return this.attachFile;
	}
	public void setAttachFile(MultipartFile attachFile) {
		this.attachFile = attachFile;
	}
	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getPosterUrl() {
		return this.posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMovieRating() {
		return this.movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}
	public String getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getOpenCountry() {
		return this.openCountry;
	}
	public void setOpenCountry(String openCountry) {
		this.openCountry = openCountry;
	}
	public int getRunningTime() {
		return this.runningTime;
	}
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	public String getIntroduce() {
		return this.introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getSynopsis() {
		return this.synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getOriginalTitle() {
		return this.originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public long getBudget() {
		return this.budget;
	}
	public void setBudget(long budget) {
		this.budget = budget;
	}
	public long getProfit() {
		return this.profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	
	
	

}
