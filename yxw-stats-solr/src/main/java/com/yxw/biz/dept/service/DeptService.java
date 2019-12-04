package com.yxw.biz.dept.service;

import com.yxw.vo.dept.DeptStatsRequest;

public interface DeptService {
	/**
	 * 按医院进行多月统计
	 * @param request
	 * @return
	 */
	public String statsInfos(DeptStatsRequest request);
	
}
