package com.yxw.platform.message.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.message.dao.ReplyDao;
import com.yxw.platform.message.dao.RuleDao;
import com.yxw.platform.message.service.ReplyService;
import com.yxw.platform.vo.MsgHospitalVO;

@Service
public class ReplyServiceImpl extends BaseServiceImpl<Reply, String> implements ReplyService {

	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private RuleDao ruleDao;

	@Autowired
	private HospitalService hospitalService;

	@Override
	protected BaseDao<Reply, String> getDao() {
		return replyDao;
	}

	/**
	 * 保存回复图文关系
	 * */
	@Override
	public Long addReplyMeterial(Map<String, Object> params) {
		return replyDao.addReplyMeterial(params);
	}

	/**
	 * 保存回复图文关系
	 * */
	@Override
	public Long addReplyMeterials(List<Map<String, Object>> list) {
		return replyDao.addReplyMeterials(list);
	}

	/**
	 * 获取消息管理医院列表
	 * */
	@Override
	public PageInfo<MsgHospitalVO> findHospListByPage(Map<String, Object> params, Page<MsgHospitalVO> page) {
		return replyDao.findHospListByPage(params, page);
	}

	/**
	 * 删除该医院之前设置的自动回复
	 * */
	@Override
	public void deleteByHospId(Map<String, Object> replyParams) {
		replyDao.deleteByHospId(replyParams);
	}

	/**
	 * 保存回复与规则的关系
	 * */
	@Override
	public Long addReplyRule(List<Map<String, Object>> list) {
		return replyDao.addReplyRule(list);
	}

	/**
	 * 获取某一规则的下的回复
	 * */
	@Override
	public List<Reply> findByRuleId(String RuleId) {
		return replyDao.findByRuleId(RuleId);
	}

	/**
	 * 获取被关注的回复
	 * */
	@Override
	public Reply getFocusedReply(Map<String, Object> params) {
		return replyDao.getFocusedReply(params);
	}

	/**
	 * 获取关键字回复的规则
	 * */
	@Override
	public List<Rule> getKeywordRule(Map<String, Object> params) {
		List<Rule> ruleList = null;
		ruleList = ruleDao.getRuleByKeyword(params);
		// 获取完全匹配的规则
		if (ruleList == null || ruleList.size() == 0) {
			// 如果没有则获取未完全匹配
			ruleList = ruleDao.getRuleByHalfKeyword(params);
		}
		return ruleList;
	}

	/**
	 * 删除回复图文关联关系
	 * */
	@Override
	public void deleteReplyMixed(String replyId) {
		replyDao.deleteReplyMixed(replyId);
	}
}
