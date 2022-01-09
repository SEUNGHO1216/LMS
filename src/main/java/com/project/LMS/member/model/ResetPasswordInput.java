package com.project.LMS.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {
	private String email;
	private String name;
	
	private String id; //초기화를 위한 uuid parameter값
	private String password;
}
