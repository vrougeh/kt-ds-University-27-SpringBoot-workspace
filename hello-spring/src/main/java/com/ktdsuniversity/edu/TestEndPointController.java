package com.ktdsuniversity.edu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * End Point 를 생성하는 역활
 */
@Controller // 해당 클래스가 엔드포인트를 만들 수 있도록 지원해준다
public class TestEndPointController {
	
	//spring application 이 시작될때
	//@controller 가 적용된 모든 클래스를 찾아 해당클래스들을 인스턴스로 생성한다.
	//해당 인스턴스들은 Bean Container에 저장한다
	public TestEndPointController() {
		System.out.println("TestEndPointController 인스턴스 만들어짐");
		System.out.println(this);
	}
	
	/*
	 * "/jsp" 엔드포인드
	 *  - hellojsp.jsp 파일을 읽어서 html로 변환시킨 후 결과를 반환
	 */
	@GetMapping("/jsp")
	public String viewHelloJspPage(Model model) {
		// Model model parameter --> Template Engine(jsp_에게 데이터를 전송시키는 객체
		System.out.println(model);
		//myname이라는 키(변수명)로 "이름"을 할당해서 탬플릿에게 전달
		model.addAttribute("myname","이름");
		model.addAttribute("age","나이");
		System.out.println(model);
		// spring.mvc.view.prefix + hellojsp + spring.mvc.view.seffix
		// /WEB-INF/views/ + hellojsp + .jsp
		// controller 에서 String 타입을 반환시켰을 경우
		// prefix + 반환값 + suffix의 경로를 /src/main/webapp 아래에서 탐색한다.
		// 탐색한 jsp 파일을 HTML로 변환시켜 브라우저에게 전달
		return "hellojsp";
	}
	
	
	/*
	 * 사용자가 "/root" 엔드포인트에접근하면
	 * "첫 페이지입니다. 환영합니다"를 브라우저에 보내주는 코드 작성
	 */
	@GetMapping("/root")
	public ResponseEntity<String> viewHomeHtml(){
		return new ResponseEntity<>("첫 페이지입니다. 환영합니다",HttpStatus.OK);
	}
	
	//"/hello"엔드 포인트 생성
	@GetMapping("/hello")
	//사용자가 "/hello" 엔드포인트를 요청할 경우 사용자에게 보여줄 html 페이지 생성
	public ResponseEntity<String> viewHelloHtml(){
		//사용자에게 보여줄 html 반환
		return new ResponseEntity<>("Hello", HttpStatus.OK);
		
	}

}
