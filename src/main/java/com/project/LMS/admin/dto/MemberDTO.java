package com.project.LMS.admin.dto;

import java.time.LocalDateTime;

import com.project.LMS.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDTO {

	String email;
	String password;
	String name;
	String phone;
	LocalDateTime logDt;
	
	boolean emailAuthYn; 
	String emailAuthKey; 
	LocalDateTime emailAuthDt;
	
	String resetpasswordKey; 
	LocalDateTime resetPasswordLimitDt;
	
	boolean adminYn;
	String userStatus;
	
	//일단 추가
	long totalCount;
	long seq;
	
	//빌더패턴, 바로 return해도 됨
	public static MemberDTO of(Member member) {
		
		return MemberDTO.builder()
				.email(member.getEmail())
				.password(member.getPassword())
				.name(member.getName())
				.phone(member.getPhone())
				.logDt(member.getLogDt())
				.emailAuthYn(member.isEmailAuthYn())
				.emailAuthKey(member.getEmailAuthKey())
				.emailAuthDt(member.getEmailAuthDt())
				.resetpasswordKey(member.getResetpasswordKey())
				.resetPasswordLimitDt(member.getResetPasswordLimitDt())
				.adminYn(member.isAdminYn())
				.userStatus(member.getUserStatus())
				.build();
	}
}
