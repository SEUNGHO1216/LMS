package com.project.LMS.member.entity;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //전체 생성자
@NoArgsConstructor //디폴트 생성자
@Builder //전체 생성자 필요로한다.
@Data
@Entity
public class Member {//보통 entity가 데이터 테이블 의미한다. 값 저장은 repository통해서!
	//여기에 멤버변수 추가하면 jpa와 hibernate에 의해 자동으로 생성 및 수정된다. 개꿀
	
	@Id
	private String email;
	
	private String password;
	private String name;
	private String phone;
	private LocalDateTime logDt;
	private boolean emailAuthYn; //메일 인증 됐는지 확인
	private String emailAuthKey; //메일 인증 됐는지 확인하기 위한 키
	private LocalDateTime emailAuthDt;
	private String resetpasswordKey; //이 키 일치하는 사용 비밀번호 초기화
	private LocalDateTime resetPasswordLimitDt; //비밀번호 초기화키의 유효기간
	
	private boolean adminYn;//(관리자인지 회원인지 판별) (문제는 이걸 어떻게 true, false 처리할 것인가)
	
	
	//관리자 여부를 지정할꺼임?
	//회원에 따른 ROLE을 지정할꺼냐??
	//준회원/정회원/특별회원/관리자 롤 지정
	//ROLE_SEMI_USER, ROLE_USER, ROLE_SPECIAL_USER, ROLE_ADMIN
	//준회원/정회원/특별회원 만 있고 
	//관리자는 따로둘 것인지??(아예 클래스를 따로)
	
}
