/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.vo.MsgPushHospitalVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.msgpush.dao.MsgTemplateDao;

/**
 * 模板消息dao实现类
 * 
 * @Package: com.yxw.platform.msgpush.dao.impl
 * @ClassName: MsgTemplateDaoImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class MsgTemplateDaoImpl extends BaseDaoImpl<MsgTemplate, String> implements MsgTemplateDao {

	private final static String FIND_HOSP_LIST_BY_PAGE = "findHospListByPage";

	private static Logger logger = LoggerFactory.getLogger(MsgTemplateDaoImpl.class);

	/**
	 * 获取消息管理医院列表
	 * */
	@Override
	public PageInfo<MsgPushHospitalVo> findHospListByPage(Map<String, Object> params, Page<MsgPushHospitalVo> page) {
		try {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<MsgPushHospitalVo> list = sqlSession.selectList(getSqlName(FIND_HOSP_LIST_BY_PAGE), params);
			return new PageInfo<MsgPushHospitalVo>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(FIND_HOSP_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(FIND_HOSP_LIST_BY_PAGE)), e);
		}
	}
}
