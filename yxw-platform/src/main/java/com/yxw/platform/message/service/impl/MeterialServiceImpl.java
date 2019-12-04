package com.yxw.platform.message.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Meterial;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.message.dao.MeterialDao;
import com.yxw.platform.message.service.MeterialService;
import com.yxw.platform.vo.MsgHospitalVO;

@Service
public class MeterialServiceImpl extends BaseServiceImpl<Meterial, String> implements MeterialService {

	@Autowired
	private MeterialDao meterialDao;

	@Override
	protected BaseDao<Meterial, String> getDao() {
		return meterialDao;
	}

	/**
	 * 获取素材管理医院列表
	 * */
	@Override
	public PageInfo<MsgHospitalVO> findHospListByPage(Map<String, Object> params, Page<MsgHospitalVO> page) {
		return meterialDao.findHospListByPage(params, page);
	}

	/**
	 * 根据图片路径查询该图片是否存在
	 * */
	public boolean finByPicPath(String picPath) {
		Meterial entity = meterialDao.finByPicPath(picPath);
		if (entity != null) {
			return true;
		} else {
			return false;
		}
	}
}
