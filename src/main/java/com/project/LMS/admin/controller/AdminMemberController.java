package com.project.LMS.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.admin.model.MemberParam;
import com.project.LMS.admin.model.MemberPasswordChange;
import com.project.LMS.admin.model.MemberStatusInput;
import com.project.LMS.member.entity.Member;
import com.project.LMS.member.service.MemberService;
import com.project.LMS.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

	private final MemberService memberService;
	
	@GetMapping("/admin/member/list.do")
	public String list(Model model, MemberParam parameter) {
		
		parameter.init();
		List<MemberDTO> list=memberService.list(parameter);
		
		
		long totalCount=0;
		if(list !=null && list.size()>0) {
			totalCount=list.get(0).getTotalCount();//전체 페이지 개수처리
		}
		long pageIndex=parameter.getPageIndex();
		long pageSize=parameter.getPageSize();
		String queryString=parameter.getQueryString();
		
		PageUtil pageUtil =new PageUtil(totalCount, pageSize, pageIndex, queryString);

		
		model.addAttribute("list",list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pager",pageUtil.pager());
//		model.addAttribute("pageIndex", pageIndex);
		
		
		return "admin/member/list";
	}
	
	@GetMapping("/admin/member/detail.do")
	public String detail(Model model, MemberParam parameter) {

		
		parameter.init();
		MemberDTO memberDTO=memberService.detail(parameter.getEmail());
		System.out.println("★★★★★★★★");
		System.out.println(memberDTO.getEmail());
		System.out.println("★★★★★★★★");

		
		
		model.addAttribute("member",memberDTO);
		model.addAttribute("pageIndex",parameter.getPageStart());
		
		return "admin/member/detail";
	}
	
	@PostMapping("/admin/member/status.do")
	public String status(Model model, MemberStatusInput parameter) {
		
		boolean result=memberService.updateStatus(parameter.getEmail(),parameter.getUserStatus());
		
		return "redirect:/admin/member/detail.do?email="+parameter.getEmail();
	}
	@PostMapping("/admin/member/password.do")
	public String password(Model model, MemberPasswordChange parameter) {
		
		boolean result=memberService.changePassword(parameter.getEmail(), parameter.getPassword());
		return "redirect:/admin/member/detail.do?email="+parameter.getEmail();
	}
}
