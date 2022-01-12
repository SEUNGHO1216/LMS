package com.project.LMS.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.admin.model.MemberParam;
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
		
		
		return "admin/member/list";
	}
}
