package com.yxw.biz.common.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.vo.AreaHospitalInfosVo;
import com.yxw.vo.HospitalInfosVo;
import com.yxw.vo.StatsHospitalInfosVo;

public interface HospitalInfoDao extends BaseDao<HospitalInfosVo, String> {
	
	public List<HospitalInfosVo> findAllByAppId(String appId);
	
	public List<StatsHospitalInfosVo> findAllInfos();
	
	public List<StatsHospitalInfosVo> getHospitalInfos();
	
	public List<AreaHospitalInfosVo> getAreaHospitalInfos();
	
	public List<StatsHospitalInfosVo> getAllHospitalInfos();
	
}
