package com.project.LMS.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberInput {//입력된 데이터 받아주는 곳
	
	private String email;
	private String password;
	private String name;
	private String phone;
	
	
}
