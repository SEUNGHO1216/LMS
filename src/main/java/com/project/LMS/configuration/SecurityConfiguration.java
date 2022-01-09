package com.project.LMS.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.project.LMS.member.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * spring secruity 사용하기 위해
 * 1. annotation -confguration, enableWebSecurity 추가
 * 2. WebSecurityConfigurerAdapter 상속
 * 3
 */

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private MemberService memberService;
	
	@Bean
	PasswordEncoder getPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}
	@Bean //bean이 뭔지 공부
	UserAuthenticationFailureHandler getFailureHandler() {//오류 발생시 처리 메소드
		return new UserAuthenticationFailureHandler();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();//내 페이지에서 로그인한게 맞는지?확인하는 걸 disable시킴
		// 이 메소드를 통해 주소에 대한 권한 설정 가능
		
		http.authorizeRequests().antMatchers(
				"/",
				"/member/register",
				"/member/email-auth",
				"/member/find-password",
				"/member/reset-password"
				).permitAll(); //이 주소들은 로그인 생략가능
		
		http.formLogin().loginPage("/member/login")//이 주소에서 로그인 하겠다!
		.failureHandler(getFailureHandler()) //로그인 실패시 처리
		.permitAll();
		
		http.authorizeRequests().antMatchers("/admin/**")// 위조소로 시작되는 url들은
		.hasAuthority("ROLE_ADMIN");//이 권한이 있어야 접근 가능하다.
		
		http.exceptionHandling().accessDeniedPage("/error/denied");//일반유저가 관리자 페이지 접근시 오류나는거 예외처리
		//접근 불가 페이지의 경우 위 경로로 데리고 간다.
		
		super.configure(http);
		
		//로그아웃처리
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);
	}
	
	@Override //비밀번호도 bcrypt 방식으로 인코딩해줘야함
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService)
		.passwordEncoder(getPasswordEncoder());
		
		super.configure(auth);
	}
	
}
