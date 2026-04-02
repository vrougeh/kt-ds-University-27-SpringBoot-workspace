package com.ktdsuniversity.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//application.yml에서 작성 할 수 없는 설정들을 적용하기 위한 annotation
//@component의 자식 annotation
@Configuration

//spring-boot-starter-validation 동작 활성화 시키기
//application.yml의 mvc관련 설정들이 모두 무시됨
@EnableWebMvc
public class HelloSpringConfiguration implements WebMvcConfigurer{
	
	
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
