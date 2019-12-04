package com.yxw.platform.message.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.vo.MsgHospitalVO;

public interface ReplyService extends BaseService<Reply, String> {

	/**
	 * 保存回复图文关系
	 * */
	public Long addReplyMeterial(Map<String, Object> params);

	/**
	 * 获取消息管理医院列表
	 * */
	public PageInfo<MsgHospitalVO> findHospListByPage(Map<String, Object> params, Page<MsgHospitalVO> page);

	/**
	 * 删除该医院之前设置的自动回复
	 * */
	public void deleteByHospId(Map<String, Object> replyParams);

	/**
	 * 保存回复与规则的关系
	 * */
	public Long addReplyRule(List<Map<String, Object>> list);

	/**
	 * 获取某一规则的下的回复
	 * */
	public List<Reply> findByRuleId(String RuleId);

	/**
	 * 保存回复图文关系
	 * */
	public Long addReplyMeterials(List<Map<String, Object>> list);

	/**
	 * 获取被关注的回复
	 * */
	public Reply getFocusedReply(Map<String, Object> params);

	/**
	 * 获取关键字回复的规则
	 * */
	public List<Rule> getKeywordRule(Map<String, Object> params);

	/**
	 * 删除回复图文关联关系
	 * */
	public void deleteReplyMixed(String replyId);

}
