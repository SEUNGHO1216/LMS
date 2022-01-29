package com.project.LMS.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {
	

	@GetMapping("/admin/main.do") //관리자 페이지는 확장자로 구분지어주기도 한다.
	public String main() {
		return "admin/main";
	}
}
