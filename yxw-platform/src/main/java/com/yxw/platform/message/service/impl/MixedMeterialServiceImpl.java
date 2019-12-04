package com.yxw.platform.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.message.dao.MixedMeterialDao;
import com.yxw.platform.message.service.MixedMeterialService;

@Service
public class MixedMeterialServiceImpl extends BaseServiceImpl<MixedMeterial, String> implements MixedMeterialService {

	@Autowired
	private MixedMeterialDao MixedMeterialDao;

	@Override
	protected BaseDao<MixedMeterial, String> getDao() {
		return MixedMeterialDao;
	}

	/**
	 * 根据id数组查询图文
	 * */
	@Override
	public List<MixedMeterial> findEntityByIds(String[] ids) {
		return MixedMeterialDao.findEntityByIds(ids);
	}

	/**
	 * 根据id数组查询图文
	 * */
	@Override
	public List<MixedMeterial> findMixedMeterialsByIds(String[] ids) {
		return MixedMeterialDao.findMixedMeterialsByIds(ids);
	}

	/**
	 * 根据回复ID获取相应素材
	 * */
	@Override
	public MixedMeterial getMeterialByReplyId(String replyId) {
		return MixedMeterialDao.getMeterialByReplyId(replyId);
	}

}
