package com.project.LMS.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.member.entity.Member;
import com.project.LMS.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

	private final MemberService memberService;
	
	@GetMapping("/admin/member/list.do")
	public String list(Model model) {
		
		List<MemberDTO> list=memberService.list();
		
		model.addAttribute("list",list);
		
		return "admin/member/list";
	}
}
