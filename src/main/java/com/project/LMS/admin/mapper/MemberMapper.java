package com.project.LMS.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.LMS.admin.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	List<MemberDTO> selectList(MemberDTO parameter);

}
