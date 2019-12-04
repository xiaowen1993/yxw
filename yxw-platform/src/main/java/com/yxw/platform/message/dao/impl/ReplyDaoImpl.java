package com.yxw.platform.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.message.dao.ReplyDao;
import com.yxw.platform.vo.MsgHospitalVO;

@Repository
public class ReplyDaoImpl extends BaseDaoImpl<Reply, String> implements ReplyDao {

	private static Logger logger = LoggerFactory.getLogger(ReplyDaoImpl.class);
	private final static String ADD_REPLY_METERIAL = "addReplyMeterial";
	private final static String ADD_REPLY_METERIALS = "addReplyMeterials";
	private final static String ADD_REPLY_RULE = "addReplyRule";
	private final static String FIND_HOSP_LIST_BY_PAGE = "findHospListByPage";
	private final static String FIND_LIST_BY_RULEID = "findListByRuleId";
	private final static String DELETE = "delete";
	private final static String GET_FOCUSED_REPLY = "getFocusedReply";
	private final static String GET_KEYWORD_REPLY = "getKeywordReply";
	private final static String DELETE_REPLY_MIXED = "deleteReplyMixed";

	/**
	 * 插入回复素材关联关系
	 * */
	@Override
	public Long addReplyMeterial(Map<String, Object> params) {
		try {
			return (long) sqlSession.insert(getSqlName(ADD_REPLY_METERIAL), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_METERIAL)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_METERIAL)), e);
		}
	}

	/**
	 * 插入回复素材关联关系
	 * */
	@Override
	public Long addReplyMeterials(List<Map<String, Object>> list) {
		try {
			return (long) sqlSession.insert(getSqlName(ADD_REPLY_METERIALS), list);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_METERIALS)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_METERIALS)), e);
		}
	}

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
	 * 删除该医院之前设置的自动回复
	 * */
	@Override
	public void deleteByHospId(Map<String, Object> replyParams) {
		try {
			Assert.notNull(replyParams);
			sqlSession.delete(getSqlName(DELETE), replyParams);
		} catch (Exception e) {
			logger.error(String.format("更新对象出错！语句：%s", getSqlName(DELETE)), e);
			throw new SystemException(String.format("更新对象出错！语句：%s", getSqlName(DELETE)), e);
		}
	}

	/**
	 * 保存回复与规则的关系
	 * */
	@Override
	public Long addReplyRule(List<Map<String, Object>> list) {
		try {
			Assert.notNull(list);
			return (long) sqlSession.insert(getSqlName(ADD_REPLY_RULE), list);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_RULE)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(ADD_REPLY_RULE)), e);
		}
	}

	/**
	 * 获取某一规则的下的回复
	 * */
	@Override
	public List<Reply> findByRuleId(String RuleId) {
		try {
			Assert.notNull(RuleId);
			return sqlSession.selectList(getSqlName(FIND_LIST_BY_RULEID), RuleId);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_LIST_BY_RULEID)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(FIND_LIST_BY_RULEID)), e);
		}
	}

	/**
	 * 获取被关注的回复
	 * */
	@Override
	public Reply getFocusedReply(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectOne(getSqlName(GET_FOCUSED_REPLY), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_FOCUSED_REPLY)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_FOCUSED_REPLY)), e);
		}
	}

	@Override
	public List<Reply> getKeywordReply(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectOne(getSqlName(GET_KEYWORD_REPLY), params);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_KEYWORD_REPLY)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(GET_KEYWORD_REPLY)), e);
		}
	}

	@Override
	public void deleteReplyMixed(String replyId) {
		try {
			Assert.notNull(replyId);
			sqlSession.delete(getSqlName(DELETE_REPLY_MIXED), replyId);
		} catch (Exception e) {
			logger.error(String.format("更新对象出错！语句：%s", getSqlName(DELETE_REPLY_MIXED)), e);
			throw new SystemException(String.format("更新对象出错！语句：%s", getSqlName(DELETE_REPLY_MIXED)), e);
		}
	}
}
