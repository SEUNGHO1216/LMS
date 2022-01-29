package com.project.LMS.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.LMS.admin.dto.CategoryDTO;
import com.project.LMS.admin.entity.Category;
import com.project.LMS.admin.model.CategoryInput;
import com.project.LMS.admin.model.MemberParam;
import com.project.LMS.admin.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {

	private final CategoryService categoryService;
	
	@GetMapping("/admin/category/list.do")
	public String list(Model model, MemberParam parameter) {
		
		List<CategoryDTO> category=categoryService.list();

		model.addAttribute("category",category);
		
		return "admin/category/list";
	}
	
	@PostMapping("/admin/category/add.do")
	public String add(Model model, CategoryInput parameter) {
		
		boolean result=categoryService.add(parameter.getCategoryName());
		
		return "redirect:/admin/category/list.do"; 
		
	}
	@PostMapping("/admin/category/delete.do")
	public String delete(Model model, CategoryInput parameter) {
		
		boolean result=categoryService.del(parameter.getId());
		
		if (result) {
			
			return "redirect:/admin/category/list.do";
		}else {
			return null;
		}
	}
	@PostMapping("/admin/category/update.do")
	public String update(Model model, CategoryInput parameter) {
		
		boolean result=categoryService.update(parameter);
			
			return "redirect:/admin/category/list.do";
	
	}
}
