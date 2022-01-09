package com.project.LMS.member.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.member.entity.Member;
import com.project.LMS.member.model.MemberInput;
import com.project.LMS.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService{//서비스는 db의 트랜잭션 처리에 대해 작용하는 단위
	
	boolean register(MemberInput parameter);
	
	/* uuid에 해당하는 계정을 활성화 함. */
	boolean emailAuth(String uuid);
	
	/*입력한 이메일로 비밀번호 초기화 정보를 전송*/
	boolean sendResetPassword(ResetPasswordInput parameter);

	/*입력받은 uuid로 password초기화 함*/
	boolean resetPassword(String id, String password);

	/*입력받은 uuid값이 유효한지 확인*/
	boolean checkResetPassword(String uuid);
	
	/*회원목록관리 (관리자에서만 사용가능)*/
	List<MemberDTO> list();
}
