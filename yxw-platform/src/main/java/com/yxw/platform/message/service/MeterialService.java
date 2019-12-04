package com.yxw.platform.message.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Meterial;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.vo.MsgHospitalVO;

public interface MeterialService extends BaseService<Meterial, String> {

	/**
	 * 获取素材管理医院列表
	 * */
	public PageInfo<MsgHospitalVO> findHospListByPage(Map<String, Object> params, Page<MsgHospitalVO> page);

	/**
	 * 根据图片路径查询该图片是否存在
	 * */
	public boolean finByPicPath(String picPath);
}
