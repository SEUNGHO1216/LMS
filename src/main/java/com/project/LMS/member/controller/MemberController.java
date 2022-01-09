package com.project.LMS.member.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.LMS.member.model.MemberInput;
import com.project.LMS.member.model.ResetPasswordInput;
import com.project.LMS.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //@autowired가 아니라 생성자로 주입하는 방법임! 근데 Required~어노테이션을 통해 생성자 주입도 알아서해줌
@Controller
public class MemberController {
	
	
	private final MemberService memberService;
	@RequestMapping("/member/login")
	public String login() {
		return "member/login";
	}
	

	@GetMapping("/member/register")
	public String register() {
		
		return "member/register";
	}
	@PostMapping("/member/register")
	public String registerSubmit(Model model,HttpServletRequest request, HttpServletResponse response, MemberInput parameter) {
					//model은 클라이언트에게 정보를 내리기 위해 사용하는 인터페이스!
	
		System.out.println(parameter.toString());//데이터 클라이언트에서 서버로 잘 넘어갔는지 확인용
		
		//회원가입시 중복되는 primarykey들어오면 update시켜버림. 그거 중복 방지 처리!->service에서!
		
		boolean result=memberService.register(parameter);
		model.addAttribute("result",result);//result 결과에 따라 클라이언트에게 결과 보여줌
		
		return "member/register_complete";
	}
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String uuid=request.getParameter("id");
		//System.out.println(uuid);
		
		boolean result=memberService.emailAuth(uuid);
		model.addAttribute("result",result);
		
		return "member/email_auth";
	}
	
	@GetMapping("/member/info")
	public String memberInfo() {
		
		return "member/info";
	}
	@GetMapping("/member/logout")
	public String logout() {
		
		return "member/info";
	}
	@GetMapping("/member/find-password")
	public String findPassword() {
		
		return "member/find_password";
	}
	@PostMapping("/member/find-password")
	public String findPasswordSubmit(Model model,ResetPasswordInput parameter) {
		
		boolean result= false;
		try {
			result=memberService.sendResetPassword(parameter);
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		
		return "member/find_password_result";//redirect 방식> 주소와 view가 함께 이동
	}
	
	@GetMapping("/member/reset-password")
	public String resetPassword(Model model, HttpServletRequest request){
		
		String uuid=request.getParameter("id");
		
		boolean result=memberService.checkResetPassword(uuid);
		model.addAttribute("result",result);
		
		return "member/reset_password";
	}
	@PostMapping("/member/reset-password")
	public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
		
		boolean result=false;
		try {
			
			result=memberService.resetPassword(parameter.getId(), parameter.getPassword());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result",result);//자동으로 매핑
		
		
		return "member/reset_password_result";
	}
}
