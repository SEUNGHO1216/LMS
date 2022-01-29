package com.project.LMS.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.project.LMS.admin.dto.CategoryDTO;
import com.project.LMS.admin.entity.Category;
import com.project.LMS.admin.model.CategoryInput;
import com.project.LMS.admin.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	
	private Sort getSortBySortValueDesc() {
		
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}
	
	
	@Override
	public boolean add(String categoryName) {
		// 추가
		Category category=Category.builder()
				.categoryName(categoryName)
				.usingYn(true)
				.sortValue(0) //값이 많은걸 앞으로 배치함..내림차순 말하나?
				.build();
		categoryRepository.save(category);
		
		// 카테고리명이 중복인거 체크
		return true;
	}

	@Override
	public boolean update(CategoryInput parameter) {
		Optional<Category> optionalCategory=categoryRepository.findById(parameter.getId());
		if(optionalCategory.isPresent()) {
			Category category=optionalCategory.get();
			
			category.setCategoryName(parameter.getCategoryName());
			category.setSortValue(parameter.getSortValue());
			category.setUsingYn(parameter.isUsingYn());
			categoryRepository.save(category);
		}
		return true;
	}

	@Override
	public boolean del(long id) {
		categoryRepository.deleteById(id);
		return true;
	}

	@Override
	public List<CategoryDTO> list() {
		
		//List<CategoryDTO> categoryDTOList=new ArrayList<CategoryDTO>();
		List<Category> categories= categoryRepository.findAll(getSortBySortValueDesc());
		
			
		return CategoryDTO.of(categories);
		 
	}
	
	
	
}