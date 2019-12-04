package com.yxw.platform.message.service;

import java.util.List;

import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.framework.mvc.service.BaseService;

public interface MixedMeterialService extends BaseService<MixedMeterial, String> {
	/**
	 * 根据id数组查询图文
	 * */
	public List<MixedMeterial> findEntityByIds(String[] ids);

	/**
	 * 根据id数组查询图文
	 * */
	public List<MixedMeterial> findMixedMeterialsByIds(String[] ids);

	/**
	 * 根据回复ID获取相应素材
	 * */
	public MixedMeterial getMeterialByReplyId(String replyId);
}
