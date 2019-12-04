package com.yxw.hospitalmanager.service;

import java.util.Map;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.hospitalmanager.entity.Area;
import com.yxw.hospitalmanager.vo.ProvinceVo;

public interface AreaService extends BaseService<Area, String> {
	public Map<String, Object> findAllByLevel(Integer level);
	
	public Map<String, ProvinceVo> findAllHiperLevel(Integer level);
}
