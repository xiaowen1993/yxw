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
package com.yxw.mobileapp.biz.vote.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.mobileapp.biz.vote.dao.VoteDao;
import com.yxw.mobileapp.biz.vote.service.VoteService;

/**
 * @Package: com.yxw.mobileapp.biz.vote.service.impl
 * @ClassName: VoteServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "voteService")
public class VoteServiceImpl implements VoteService {
	private VoteDao voteDao = SpringContextHolder.getBean(VoteDao.class);

	@Override
	public Boolean checkHadVote(String orderNo, String openId, String hospitalId, String raterCode) {
		// TODO Auto-generated method stub
		return voteDao.checkHadVote(orderNo, openId, hospitalId, raterCode);
	}

	@Override
	public Map<String, Object> saveVoteInfo(VoteInfo entity) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (entity.getOrderNoHashVal() == null) {
			if (StringUtils.isNotBlank(entity.getOrderNo())) {
				entity.setOrderNoHashVal(Math.abs(entity.getOrderNo().hashCode()));
			}
		}

		if (StringUtils.isNotBlank(entity.getId())) {
			voteDao.update(entity);
		} else {
			voteDao.add(entity);
		}

		resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
		resMap.put(BizConstant.METHOD_INVOKE_RES_DATA_KEY, entity);
		return resMap;
	}

	@Override
	public VoteInfo findVoteInfo(String entityId, String orderNo, String openId, String hospitalId, String raterCode) {
		// TODO Auto-generated method stub
		return voteDao.findVoteInfo(entityId, orderNo, openId, hospitalId, raterCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<VoteInfo> findRaterAllVoteInfos(String hospitalId, String raterCode) {
		// TODO Auto-generated method stub
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.VOTE_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { hospitalId, raterCode, null, hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class, String.class, String.class, String.class };
			QueryHashTableCallable collectCall = new QueryHashTableCallable(VoteDao.class, "findRaterAllVoteInfos", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		List<VoteInfo> voteInfos = new ArrayList<VoteInfo>();
		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Object obj = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (obj != null && obj instanceof List) {
					voteInfos.addAll((List) obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return voteInfos;
	}

	@Override
	public VoteInfo generateVoteInfo(String appId, String appCode, String bizCode, String orderNo, String cardNo, String openId, String raterCode,
			String fowardUrl, String voteTitle, String patientName) {
		// TODO Auto-generated method stub
		// HospitalCache hospiatlCache = SpringContextHolder.getBean(HospitalCache.class);
		VoteInfo voteInfo = new VoteInfo();
		voteInfo.setIsHadVote(BizConstant.VOTE_IS_HAD_NO);
		voteInfo.setAppId(appId);
		voteInfo.setAppCode(appCode);

		if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appCode)) {
			// HospIdAndAppSecretVo vo = hospiatlCache.findAppSecretByAppId(appId, appCode);
			HospIdAndAppSecretVo vo = null;
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> params = new ArrayList<Object>();
			params.add(appId);
			params.add(appCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);
			if (CollectionUtils.isNotEmpty(results)) {
				vo = (HospIdAndAppSecretVo) results.get(0);

				voteInfo.setHospitalId(vo.getHospId());
				voteInfo.setHospitalCode(vo.getHospCode());
				voteInfo.setHospitalName(vo.getHospName());
			}
		}

		voteInfo.setOrderNo(orderNo);
		voteInfo.setOrderNoHashVal(Math.abs(orderNo.hashCode()));
		voteInfo.setOpenId(openId);
		voteInfo.setBizCode(bizCode);
		voteInfo.setCardNo(cardNo);
		voteInfo.setRaterCode(raterCode);
		voteInfo.setFowardUrl(fowardUrl);
		voteInfo.setCreateTime(System.currentTimeMillis());
		voteInfo.setVoteTitle(voteTitle);
		voteInfo.setPatientName(patientName);
		voteDao.add(voteInfo);
		return voteInfo;
	}

	@Override
	public List<VoteInfo> findListByOpenId(String openId, String viewType) {
		// TODO Auto-generated method stub
		return voteDao.findListByOpenId(openId, viewType);
	}
}
