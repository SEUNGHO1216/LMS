package com.project.LMS.member.exception;

public class MemberEmailNotAuthenticatedException extends RuntimeException {

	public MemberEmailNotAuthenticatedException(String error) {
		super(error);
	}
}
