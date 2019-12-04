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
package com.yxw.mobileapp.biz.vote.dao;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.vote.dao
 * @ClassName: VoteDao
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface VoteDao extends BaseDao<VoteInfo, String> {
	/**
	 * 是否已经评价过
	 * @param orderNo
	 * @param openId
	 * @return
	 */
	public Boolean checkHadVote(String orderNo, String openId, String hospitalId, String raterCode);

	/**
	 * 根据订单号,openid ,被评价的事物的code hospitalId,查询评价信息
	 * @param entityId
	 * @param orderNo
	 * @param openId
	 * @param raterCode
	 * @return
	 */
	public VoteInfo findVoteInfo(String entityId, String orderNo, String openId, String hospitalId, String raterCode);

	/**
	 * 查询被评价事物的所有评价信息
	 * @param hospitalId
	 * @param raterCode
	 * @param openId
	 * @param isHadVote   是否已评价
	 * @param hashTableName
	 * @return
	 */
	public List<VoteInfo> findRaterAllVoteInfos(String hospitalId, String raterCode, String openId, Integer isHadVote, String hashTableName);

	/**
	 * 获取openId所有的待评价信息
	 * @param openId
	 * @viewType 
	 *      BizConstant.VOTE_VIEW_LIST_NO_VOTE ="noVote"     待评价列表
	 *      BizConstant.VOTE_VIEW_LIST_HAD_VOTE="hadVote"    已评价列表
	 *      BizConstant.VOTE_VIEW_LIST_ALL_VOTE="allVote"    所有评价列表
	 * @return
	 */
	public List<VoteInfo> findListByOpenId(String openId, String viewType);

}
