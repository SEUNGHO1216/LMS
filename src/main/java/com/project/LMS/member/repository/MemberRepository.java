package com.project.LMS.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.LMS.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
											//Member클래스->entity
	Optional<Member> findByEmailAuthKey(String emailAuthKey);
	//spring boot가 자동으로 implement해준다..뭐지 대체 나보다 낫다.
	
	Optional<Member> findByEmailAndName(String email, String name);
	
	Optional<Member> findByResetpasswordKey(String resetpasswordKey);
}