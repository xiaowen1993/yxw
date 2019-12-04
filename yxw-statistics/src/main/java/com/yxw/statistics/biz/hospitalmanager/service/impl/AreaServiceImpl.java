package com.yxw.statistics.biz.hospitalmanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.statistics.biz.constants.CommonConstant;
import com.yxw.statistics.biz.hospitalmanager.dao.AreaDao;
import com.yxw.statistics.biz.hospitalmanager.entity.Area;
import com.yxw.statistics.biz.hospitalmanager.service.AreaService;
import com.yxw.statistics.biz.vo.CityVo;
import com.yxw.statistics.biz.vo.ProvinceVo;

@Service(value = "areaService")
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {

	private AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	
	private Logger logger = LoggerFactory.getLogger(AreaService.class);

	@Override
	public Map<String, Object> findAllByLevel(Integer level) {
		List<Area> areas = areaDao.findAllByLevel(level);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		for (Area area : areas) {
			resultMap.put(area.getId(), area);
		}

		return resultMap;
	}

	@Override
	public Map<String, ProvinceVo> findAllHiperLevel(Integer level) {
		if (level == CommonConstant.LEVEL_COUNTY) {
			logger.error("暂时不支持三级吧");
		}
		List<Area> areas = areaDao.findAllHiperLevel(level);

		Map<String, ProvinceVo> resultMap = new HashMap<>();

		for (Area area : areas) {
			switch (area.getLevel()) {
			case CommonConstant.LEVEL_PROVINCE:
				ProvinceVo provinceVo = new ProvinceVo();
				provinceVo.setId(area.getId());
				provinceVo.setName(area.getName());
				resultMap.put(area.getId(), provinceVo);
				break;
			case CommonConstant.LEVEL_CITY:
				String parentId = area.getParentId();
				if (resultMap.containsKey(parentId)) {
					CityVo cityVo = new CityVo();
					cityVo.setId(area.getId());
					cityVo.setName(area.getName());
					cityVo.setParentId(parentId);
					resultMap.get(parentId).getCityMap().put(cityVo.getId(), cityVo);
				} else {
					logger.error("无效的数据，找不到该parentId 对应的省份: " + JSON.toJSONString(area));
				}
				break;
			case CommonConstant.LEVEL_COUNTY:
				// 暂时不支持3级吧。
				break;
			}
		}

		return resultMap;
	}

	@Override
	protected BaseDao<Area, String> getDao() {
		return areaDao;
	}

}
