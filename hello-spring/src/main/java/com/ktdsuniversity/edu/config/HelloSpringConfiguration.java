package com.ktdsuniversity.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.config.interceptors.IllegalAccessInterceptor;
import com.ktdsuniversity.edu.config.interceptors.SessionInterceptor;

//application.yml에서 작성 할 수 없는 설정들을 적용하기 위한 annotation
//@component의 자식 annotation
@Configuration

//spring-boot-starter-validation 동작 활성화 시키기
//application.yml의 mvc관련 설정들이 모두 무시됨
@EnableWebMvc
public class HelloSpringConfiguration implements WebMvcConfigurer{
	
	
	//Interceptor 등록 및 대상 url 지정
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SessionInterceptor sessionInterceptor = new SessionInterceptor();
		
		registry.addInterceptor(sessionInterceptor)
		        .addPathPatterns("/**")// 모든 URL 대상으로 sessionInterceptor를 수행해라
		        .excludePathPatterns("/regist/check/duplicate/**", //회원가입 이메일 중복 검사
		        		             "/regist", //회원가입 페이지 & 처
		        		             "/login", //로그인 페이지 & 처리
		        		             "/js/**", //static resources
		        		             "/css/**", //static resources
		        		             "/image/**", //static resources
		        		             "/", //게시글 목록 조회
		        		             "/view/**", //게시글 내용조회
		        		             "/file/**", //첨부파일 다운로드
		        		             "/error" //에러 내용이 보여지는 페이지 추후 삭제
		        		             ) //sessionInterceptor가 적용되지 않을 URL 명시
		        ;
		IllegalAccessInterceptor illegalAccessInterceptor = new IllegalAccessInterceptor();
		registry.addInterceptor(illegalAccessInterceptor)
								.addPathPatterns("/regist/check/duplicate/**"
												 , "/regist"
												 , "/login")
								;
		
	}
	
	
	
	
	//configureViewResolvers 설정
	//spring.mvc.view.prefix, spring.mvc.view.suffix 재설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	//addResourceHandlers
	//src/main/resources/static 경로의 endpoint 재설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /static/css/ 폴더에 있는 파일들에 대한 endpoint 설정
		registry.addResourceHandler("/css/**")
		        .addResourceLocations("classpath:/static/css/");
		// /static/img/ 폴더에 있는 파일들에 대한 endpoint 설정
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		// /static/js/ 폴더에 있는 파일들에 대한 endpoint 설정
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
	}
}
