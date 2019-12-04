/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-9-8</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.vote.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.vote.dao.VoteDao;

/**
 * @Package: com.yxw.mobileapp.biz.vote.dao.impl
 * @ClassName: VoteDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "voteDao")
public class VoteDaoImpl extends BaseDaoImpl<VoteInfo, String> implements VoteDao {
	private Logger logger = LoggerFactory.getLogger(VoteDaoImpl.class);

	private static String SQLNAME_COUNT_VOTE_INFO = "countVoteInfo";

	private static String SQLNAME_FIND_VOTE_INFO = "findVoteInfo";

	private static String SQLNAME_FIND_RATER_ALL_VOTEINFO = "findRaterAllVoteInfos";

	@Override
	public Boolean checkHadVote(String orderNo, String openId, String hospitalId, String raterCode) {
		// TODO Auto-generated method stub
		boolean isHadVote = false;
		try {
			Assert.notNull(orderNo);
			Assert.notNull(openId);
			Assert.notNull(raterCode);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			paramMap.put("openId", openId);
			paramMap.put("raterCode", raterCode);
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getVoteHashTable(openId, true));
			Integer count = sqlSession.selectOne(getSqlName(SQLNAME_COUNT_VOTE_INFO), paramMap);
			if (count > 0) {
				isHadVote = true;
			}
		} catch (Exception e) {
			logger.error(String.format("查询是否已评价过出错!语句：%s", getSqlName(SQLNAME_COUNT_VOTE_INFO)), e);
			throw new SystemException(String.format("查询是否已评价过出错!语句：%s", getSqlName(SQLNAME_COUNT_VOTE_INFO)), e);
		}

		return isHadVote;
	}

	@Override
	public VoteInfo findVoteInfo(String entityId, String orderNo, String openId, String hospitalId, String raterCode) {
		VoteInfo voteInfo = null;
		try {
			Assert.notNull(orderNo);
			Assert.notNull(openId);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", entityId);
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			paramMap.put("openId", openId);
			paramMap.put("raterCode", raterCode);
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getVoteHashTable(openId, true));
			voteInfo = sqlSession.selectOne(getSqlName(SQLNAME_FIND_VOTE_INFO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_VOTE_INFO)), e);
			throw new SystemException(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_VOTE_INFO)), e);
		}
		return voteInfo;
	}

	@Override
	public List<VoteInfo>
			findRaterAllVoteInfos(String hospitalId, String raterCode, String openId, Integer isHadVote, String hashTableName) {
		List<VoteInfo> voteInfos = null;
		try {
			Assert.notNull(hospitalId);
			Assert.notNull(raterCode);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hospitalId", hospitalId);
			paramMap.put("raterCode", raterCode);
			paramMap.put("openId", openId);
			paramMap.put("hashTableName", hashTableName);
			voteInfos = sqlSession.selectList(getSqlName(SQLNAME_FIND_RATER_ALL_VOTEINFO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_VOTE_INFO)), e);
			throw new SystemException(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_VOTE_INFO)), e);
		}

		if (voteInfos == null) {
			voteInfos = new ArrayList<VoteInfo>();
		}

		return voteInfos;
	}

	@Override
	public List<VoteInfo> findListByOpenId(String openId, String viewType) {
		// TODO Auto-generated method stub
		List<VoteInfo> voteInfos = null;
		try {
			Assert.notNull(openId);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openId", openId);
			if (BizConstant.VOTE_VIEW_LIST_NO_VOTE.equalsIgnoreCase(viewType)) {
				paramMap.put("isHadVote", BizConstant.VOTE_IS_HAD_NO);
			} else if (BizConstant.VOTE_VIEW_LIST_HAD_VOTE.equalsIgnoreCase(viewType)) {
				paramMap.put("isHadVote", BizConstant.VOTE_IS_HAD_YES);
			}
			paramMap.put("hashTableName", SimpleHashTableNameGenerator.getVoteHashTable(openId, true));
			voteInfos = sqlSession.selectList(getSqlName(SQLNAME_FIND_RATER_ALL_VOTEINFO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_RATER_ALL_VOTEINFO)), e);
			throw new SystemException(String.format("查找评价信息出错!语句：%s", getSqlName(SQLNAME_FIND_RATER_ALL_VOTEINFO)), e);
		}

		if (voteInfos == null) {
			voteInfos = new ArrayList<VoteInfo>();
		}

		return voteInfos;
	}

}
