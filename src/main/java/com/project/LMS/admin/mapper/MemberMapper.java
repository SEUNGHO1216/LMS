package com.project.LMS.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.LMS.admin.dto.MemberDTO;
import com.project.LMS.admin.model.MemberParam;

@Mapper
public interface MemberMapper {
	
	long selectListCount(MemberParam parameter);
	List<MemberDTO> selectList(MemberParam parameter);

}
