package com.yxw.statistics.biz.hospitalmanager.service;

import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.statistics.biz.hospitalmanager.entity.Area;
import com.yxw.statistics.biz.vo.ProvinceVo;

public interface AreaService extends BaseService<Area, String> {
	public Map<String, Object> findAllByLevel(Integer level);
	
	public Map<String, ProvinceVo> findAllHiperLevel(Integer level);
}
