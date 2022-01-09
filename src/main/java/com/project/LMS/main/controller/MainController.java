package com.project.LMS.main.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.LMS.components.MailComponents;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {
	
	//private final MailComponents mailComponents;
	
	@RequestMapping("/")
	
	public String index() {
		/*
		String mail="seungho1216@naver.com";
		String subject="안녕하세요 백승호님!";
		String text="<h1>자바를 잡아</h1>"
				+ "<h2>지금 새벽 1시 48분~</h2>";
		mailComponents.sendMail(mail, subject, text);
		*/
		return "index";
	}
	
	@RequestMapping("/error/denied")
	public String errorDenied() {
		
		return "error/denied";
	}

	
	@RequestMapping("/hello")
	public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("Hello again!\r\nit's already 1:47 a.m... \r\nwhy shoud I sleep late everyday?\r\n한글도 잘 나오나 모르겠네요");
		writer.close();
	}

}
