package com.yxw.solr.biz.common.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.solr.vo.HospitalInfosVo;
import com.yxw.solr.vo.StatsHospitalInfosVo;

public interface HospitalInfoDao extends BaseDao<HospitalInfosVo, String> {
	
	public List<HospitalInfosVo> findAllByAppId(String appId);
	
	public List<StatsHospitalInfosVo> findAllInfos();
	
	public List<StatsHospitalInfosVo> getStdInfos();
	
}
