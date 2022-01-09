package com.project.LMS.components;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailComponents {

	private final JavaMailSender javaMailSender;
	
	public void sendMailTest() {
		
		SimpleMailMessage message =new SimpleMailMessage();
		message.setTo("seungho1216@naver.com"); //setFrom은 이미 환경설정에서 username으로 기입해놨다.
		message.setSubject("테스트 메일입니다."); //제목
		message.setText("안녕하세요. 자바의 신이 되기 위한 몸부림 중에 심플메일센더 테스트 중입니다.");
		
		javaMailSender.send(message);
		
	}
	
	public boolean sendMail(String mail,String subject, String text) {//html양식으로 보내기 위함
		
		boolean result=false;
		
		MimeMessagePreparator msg=new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(mail);
				mimeMessageHelper.setSubject(subject);
				mimeMessageHelper.setText(text, true);
			}
		};
		//rollback 못하니 예외처리 정도만 해서 상황파악하자
		try {
			javaMailSender.send(msg);
			result=true;
			System.out.println("전송 완료(테스트)");
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("전송 실패(테스트)");
		}
		
		return result;
	}
}
