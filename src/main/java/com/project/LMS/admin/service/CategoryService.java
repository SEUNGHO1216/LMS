package com.project.LMS.admin.service;

import java.util.List;


import com.project.LMS.admin.dto.CategoryDTO;
import com.project.LMS.admin.model.CategoryInput;

public interface CategoryService {
	
	/*화면에 표현하기 위한 기능*/
	List<CategoryDTO> list();
	
	/*카테고리 신규 추가*/
	boolean add(String categoryName);
	
	/*카테고리 수정*/
	boolean update(CategoryInput parameter);
	
	/*카테고리 삭제*/
	boolean del(long id);

	
}