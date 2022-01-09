package com.project.LMS.admin.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemberDTO {

	private String email;
	private String password;
	private String name;
	private String phone;
	private LocalDateTime logDt;
	
	private boolean emailAuthYn; 
	private String emailAuthKey; 
	private LocalDateTime emailAuthDt;
	
	private String resetpasswordKey; 
	private LocalDateTime resetPasswordLimitDt;
	
	private boolean adminYn;
}
