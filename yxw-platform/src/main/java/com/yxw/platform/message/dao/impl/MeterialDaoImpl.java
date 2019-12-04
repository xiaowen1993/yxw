package com.yxw.platform.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Meterial;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.MeterialDao;
import com.yxw.platform.vo.MsgHospitalVO;

@Repository
public class MeterialDaoImpl extends BaseDaoImpl<Meterial, String> implements MeterialDao {

	private static Logger logger = LoggerFactory.getLogger(MeterialDaoImpl.class);

	private final static String FIND_HOSP_LIST_BY_PAGE = "findHospListByPage";

	private final static String FIND_BY_PICPATH = "findByPicPath";

	/**
	 * 获取消息管理医院列表
	 * */
	@Override
	public PageInfo<MsgHospitalVO> findHospListByPage(Map<String, Object> params, Page<MsgHospitalVO> page) {
		try {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<MsgHospitalVO> list = sqlSession.selectList(getSqlName(FIND_HOSP_LIST_BY_PAGE), params);
			return new PageInfo<MsgHospitalVO>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(FIND_HOSP_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(FIND_HOSP_LIST_BY_PAGE)), e);
		}
	}

	/**
	 * 根据图片路径查询该图片是否存在
	 * */
	@Override
	public Meterial finByPicPath(String picPath) {
		try {
			return sqlSession.selectOne(FIND_BY_PICPATH, picPath);
		} catch (Exception e) {
			logger.error(String.format("根据图片路径查询该图片是否存在出错！语句:%s", getSqlName(FIND_BY_PICPATH)), e);
			throw new SystemException(String.format("根据图片路径查询该图片是否存在出错！语句:%s", getSqlName(FIND_BY_PICPATH)), e);
		}
	}
}
