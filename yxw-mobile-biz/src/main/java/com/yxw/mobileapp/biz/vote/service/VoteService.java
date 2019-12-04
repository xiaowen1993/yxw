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
package com.yxw.mobileapp.biz.vote.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;

/**
 * @Package: com.yxw.mobileapp.biz.vote.service
 * @ClassName: VoteService
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface VoteService {
	/**
	 * 是否已经评价过
	 * @param orderNo
	 * @param openId
	 * @return
	 */
	public Boolean checkHadVote(String orderNo, String openId, String hospitalId, String raterCode);

	/**
	 * 保存评价信息
	 * @param entity
	 * @return
	 */
	public Map<String, Object> saveVoteInfo(VoteInfo entity);

	/**
	 * 生成待评价信息 并保存
	 * @param appId
	 * @param appCode
	 * @param bizCode 业务编码 
	 *        业务编号(2位  不足2位的补0)  挂号:01    门诊:02    住院:03
	 * @param orderNo
	 * @param cardNo       就诊卡号
	 * @param openId     
	 * @param raterCode    就诊医生code
	 * @param fowardUrl    评价结束后是否跳转,不需要跳转传null
	 * @return
	 */
	public VoteInfo generateVoteInfo(String appId, String appCode, String bizCode, String orderNo, String cardNo, String openId, String raterCode,
			String fowardUrl, String voteTitle, String patientName);

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
	 * @return
	 */
	public List<VoteInfo> findRaterAllVoteInfos(String hospitalId, String raterCode);

	/**
	 * 获取openId所有的待评价信息
	 * @param openId
	 * @viewType 
	 *      BizConstant.VOTE_VIEW_LIST_NO_VOTE ="noVote"     待评价列表
	 *      BizConstant.VOTE_VIEW_LIST_HAD_VOTE="hadVote"    已评价列表
	 * @return
	 */
	public List<VoteInfo> findListByOpenId(String openId, String viewType);
}
