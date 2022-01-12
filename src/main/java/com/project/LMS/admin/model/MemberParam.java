package com.project.LMS.admin.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberParam {

	
	
	private String searchType;
	private String searchValue;
	
	private long pageIndex;
	private long pageSize;
	
	/*
    limit 0, 10   |pageIndex :1
    limit 10, 10  |pageIndex :2
    limit 20, 10  |pageIndex :3
    limit 30, 10  |pageIndex :4
	*/
	public long getPageStart() {
		init();
		
		
		return (pageIndex-1)*pageSize;
	}
	public long getPageEnd() {
		init();
		
		return pageSize; 
	}
	public void init() {
		if(pageIndex <1) {
			pageIndex=1;
		}
		
		if(pageSize<10) {
			pageSize=10;
		}
		
	}
	public String getQueryString() {
		init();
		StringBuilder sb=new StringBuilder();
		if(searchType!=null &&searchType.length()>0) {
			sb.append(String.format("searchType=%s",searchType));
		}
		if(searchValue!=null &&searchValue.length()>0) {
			if(sb.length()>0) {
				sb.append("&");
			}
			sb.append(String.format("searchValue=%s", searchValue));
		}
		return sb.toString();
		
	}
}
