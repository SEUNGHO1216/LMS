package com.project.LMS.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.LMS.admin.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
}