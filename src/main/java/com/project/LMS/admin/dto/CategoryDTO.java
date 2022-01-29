package com.project.LMS.admin.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.LMS.admin.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {

	Long id;
	
	String categoryName;
	int sortValue;
	boolean usingYn;
	
	public static List<CategoryDTO> of(List<Category> categories){
		
		if(categories!=null) {
			List<CategoryDTO> categoryList =new ArrayList<CategoryDTO>();
			for(Category x:categories) {
				categoryList.add(of(x));
			}
			return categoryList;
		}
		return null;
	}
	public static CategoryDTO of(Category category) {
		
		
		return CategoryDTO.builder()
				.id(category.getId())
				.categoryName(category.getCategoryName())
				.sortValue(category.getSortValue())
				.usingYn(category.isUsingYn())
				.build();
		
	}
}
