package com.project.LMS.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.x500.X500Principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.admin.mapper.MemberMapper;
import com.project.LMS.admin.model.MemberParam;
import com.project.LMS.components.MailComponents;
import com.project.LMS.member.entity.Member;
import com.project.LMS.member.exception.MemberEmailNotAuthenticatedException;
import com.project.LMS.member.model.MemberInput;
import com.project.LMS.member.model.ResetPasswordInput;
import com.project.LMS.member.repository.MemberRepository;
import com.project.LMS.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //이 어노테이션을 통해 bean으로 만들어줄 수 있다.
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final MailComponents mailComponent;
	private final MemberMapper memberMapper; 
	
	@Override
	public boolean register(MemberInput parameter) {
		
		Optional<Member> optionalMember=memberRepository.findById(parameter.getEmail());
		if(optionalMember.isPresent()) {
			
			return false;
		}
		String uuid=UUID.randomUUID().toString();
		String encPassword=BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		
		
		Member member=Member.builder()
				.email(parameter.getEmail())
				.password(encPassword)
				.name(parameter.getName())
				.phone(parameter.getPhone())
				.logDt(LocalDateTime.now())
				.emailAuthKey(uuid)
				.emailAuthYn(false)
				.emailAuthDt(LocalDateTime.now())
				.build();
		memberRepository.save(member);//서버로 넘어간 데이터를 데이터베이스로 가져가는 과정!(
		
		String mail=parameter.getEmail();
		String subject="가입을 축하드립니다!";
		String text="<p>환영합니다!</p><p>아래 링크를 클릭하여 가입을 완료하세요!</p>"
				+"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>클릭하고 가입완료</a></div>";
		
		mailComponent.sendMail(mail, subject, text);
		return true;
	}
	
	@Override
	public boolean emailAuth(String uuid) {//계정활성화 메서드

		Optional<Member> optionalMember=memberRepository.findByEmailAuthKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		Member member = optionalMember.get();//repository에서 찾아온 정보를 member에 반환
		
		if(member.isEmailAuthYn()) {
			return false; //한번 인증처리된 이메일 다시 사용못하도록 조치
		}
		
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);

		return true;
	}

	@Override  //spring security에서 원하는 회원 정보 (아이디,패스워드,권한)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optionalMember=memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			System.out.println("회원 정보 없음!");
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		Member member=optionalMember.get();
		
		if(member.isEmailAuthYn()==false) {
			throw new MemberEmailNotAuthenticatedException("이메일을 활성화 이후 로그인 해주세요.");
		}
		
		List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();//권한은 개발자가 주면된다.
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));//롤 추가
		
		if(member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));		}
		
		return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
	}
	
	@Override 
	public boolean sendResetPassword(ResetPasswordInput parameter) {


		Optional<Member> optionalMember=memberRepository.findByEmailAndName(parameter.getEmail(),parameter.getName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		
		Member member= optionalMember.get();
		
		String uuid=UUID.randomUUID().toString();
		member.setResetpasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));//유효기간 현재날짜 시간+하루
		memberRepository.save(member);
		
		String mail=parameter.getEmail();
		System.out.println(mail);
		String subject="[TEST]비밀번호 초기화 정보입니다.";
		String text="<h1>안녕하세요!</h1>"
				+ "<p>아래 링크를 클릭하여 비밀번호 초기화 정보를 전달 받으세요!</p>"
				+"<div><a target='_blank' href='http://localhost:8080/member/reset-password?id="+uuid+"'>비밀번호 초기화 링크</a></div>";
		
		mailComponent.sendMail(mail, subject, text);
		return true;
	}
	
	@Override
	public boolean resetPassword(String id, String password) {

		Optional<Member> optionalMember= memberRepository.findByResetpasswordKey(id);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member =optionalMember.get();
		
		//초기화 날짜 설정했는데, 이 유호기간에 맞는지도 설정해야돼
		if(member.getResetPasswordLimitDt()==null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
			//유효날짜가 오늘보다 전이면 유효기간 지난거니 안되지..
		}
		
		String encPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		//비밀번호는 암호화 필요!
		member.setPassword(encPassword);
		member.setResetpasswordKey("");
		member.setResetPasswordLimitDt(null);
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public boolean checkResetPassword(String uuid) {
		
		Optional<Member> optionalMember= memberRepository.findByResetpasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member =optionalMember.get();
		
		//초기화 날짜 설정했는데, 이 유호기간에 맞는지도 설정해야돼
		if(member.getResetPasswordLimitDt()==null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
			//유효날짜가 오늘보다 전이면 유효기간 지난거니 안되지..
		}
		
		return true;
	}

	@Override
	public List<MemberDTO> list(MemberParam parameter) {
		
		long totalCount=memberMapper.selectListCount(parameter);//adminDTO에 칼럼추가
		List<MemberDTO> list=memberMapper.selectList(parameter);
		int i=0;
		if(!CollectionUtils.isEmpty(list)) {
			for(MemberDTO x:list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount-parameter.getPageStart()-i); 
				i++;
				
			}
		}
		

		return list;
	}
	
}
