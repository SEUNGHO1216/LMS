package com.project.LMS.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override  //에러 발생시 호출되는 메소드
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String msg="로그인에 실패하였습니다.";
		if(exception instanceof InternalAuthenticationServiceException) {
			msg=exception.getMessage();
		}
		setUseForward(true); //forward방식으로 이동하겠다.
		setDefaultFailureUrl("/member/login?error=true");
		request.setAttribute("errorMessage", msg);
		
		System.out.println("로그인에 실패했습니다.");
		super.onAuthenticationFailure(request, response, exception);
	
	}

}
//InternalAuthenticationServiceException