package com.yxw.mobileapp.biz.usercenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MedicalCardConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.interfaces.constants.CardType;
import com.yxw.interfaces.constants.IdType;
import com.yxw.interfaces.vo.mycenter.Card;
import com.yxw.interfaces.vo.mycenter.MZPatient;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.usercenter.dao.MedicalCardDao;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.biz.usercenter.thread.CardCacheRunnable;
import com.yxw.mobileapp.biz.usercenter.thread.QueryCardForParamsCallable;
import com.yxw.mobileapp.datas.manager.MedicalcardBizManager;

/**
 * 调用返回说明： resultCode:0, 执行成功 resultCode:1, 执行失败, 信息输出给用户看 resultCode:-1, 执行失败，信息不输出给用户看
 * 
 * @Package: com.yxw.mobileapp.biz.medicalcard.service.impl
 * @ClassName: MedicalCardServiceImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-6-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0	
 */
@Repository(value = "medicalCardService")
public class MedicalCardServiceImpl extends BaseServiceImpl<MedicalCard, String> implements MedicalCardService {

	private Logger logger = LoggerFactory.getLogger(MedicalCardServiceImpl.class);

	private MedicalcardBizManager medicalcardBizManager = SpringContextHolder.getBean(MedicalcardBizManager.class);

	private MedicalCardDao medicalCardDao = SpringContextHolder.getBean(MedicalCardDao.class);

	@Override
	public List<MedicalCard> findCardsByOpenIdAndHospitalCode(MedicalCard medicalCard) {
		return medicalCardDao.findCardsByOpenIdAndHospitalCode(medicalCard);
	}

	@Override
	public int getCardCountByOpenIdAndHospitalCode(MedicalCard medicalCard) {
		return medicalCardDao.getCardCountByOpenIdAndHospitalCode(medicalCard);
	}

	@Override
	public boolean checkSelfCardExist(MedicalCard medicalCard) {
		return medicalCardDao.findCardsByOpenIdAndHospitalCodeAndOwnership(medicalCard).size() > 0;
	}

	@Override
	public Map<String, Object> unBindCard(MedicalCard medicalCard) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// this.medicalCardDao.unbindCard(medicalCard.getId());
			medicalCard.setState(MedicalCardConstant.MEDICALCARD_UNBIND);
			medicalCard.setUpdateTime(new Date().getTime());
			this.medicalCardDao.update(medicalCard);
			// Thread thread = new Thread(new CardCacheRunnable(medicalCard,
			// CacheConstant.UPDATE_OP_TYPE_UPDATE), "unbindCard");
			Thread thread = new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_DEL), "unbindCard");
			thread.start();
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			resultMap.put("card", medicalCard.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("解绑就诊卡失败，卡Id=" + medicalCard.getId());
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "解绑就诊卡失败");
		}

		return resultMap;
	}

	@Override
	protected BaseDao<MedicalCard, String> getDao() {
		// TODO Auto-generated method stub
		return this.medicalCardDao;
	}

	@Override
	public List<MedicalCard> findCardByCardNoAndHospitalCode(MedicalCard medicalCard) {
		List<MedicalCard> cards = new ArrayList<MedicalCard>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();
		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			// 操作同一个参数中的medicalCard 会造成多线程并发 故未每个线程生成一个新的副本对象
			MedicalCard queryCard = new MedicalCard();
			BeanUtils.copyProperties(medicalCard, queryCard);
			queryCard.setHashTableName(hashTableName);
			Object[] parameters = new Object[] { queryCard };
			Class<?>[] parameterTypes = new Class[] { MedicalCard.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(MedicalCardDao.class, "findCardByCardNoAndHospitalCode", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				@SuppressWarnings("unchecked")
				List<MedicalCard> resCards = (List<MedicalCard>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(resCards)) {
					cards.addAll(resCards);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("query medicalCard thread task is excption:{}", e);
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return cards;

	}

	@Override
	public MedicalCard findCardByIdNoAndHospitalCodeAndOpenId(MedicalCard medicalCard) {
		return medicalCardDao.findCardByIdNoAndHospitalCodeAndOpenId(medicalCard);
	}

	@Override
	public Map<String, String> autoGetCardNo(MedicalCard medicalCard) {
		Map<String, String> result = new HashMap<String, String>();

		Map<String, Object> resMap =
				medicalcardBizManager.queryMZPatient(medicalCard.getHospitalCode(), medicalCard.getBranchCode(), medicalCard.getIdNo(),
						medicalCard.getCardType() + "", medicalCard.getName());
		String code = (String) resMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
		if (code.equals(BizConstant.INTERFACE_RES_SUCCESS_CODE)) {
			MZPatient patient = (MZPatient) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
			if (patient != null) {
				result.put("cardNo", patient.getPatCardNo());
				result.put("cardType", patient.getPatCardType());
			}
		}

		return result;
	}

	/**
	 * cardType 1本人，2他人
	 * 
	 * @param cards
	 * @param cardType
	 * @return
	 */
	protected int getCardCount(List<MedicalCard> cards, int cardType) {
		int result = 0;
		int type = ( cardType == 1 ? 1 : 2 );
		for (MedicalCard medicalCard : cards) {
			if (medicalCard.getOwnership() == type) {
				result += 1;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> createCard(MedicalCard medicalCard) {
		logger.info("begin create card....");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> resMap =
				medicalcardBizManager.createMZPatient(medicalCard.getHospitalCode(), medicalCard.getBranchCode(), medicalCard);
		String code = (String) resMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
		if (code.equals(BizConstant.INTERFACE_RES_SUCCESS_CODE)) {
			Card card = (Card) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
			// 调用接口如果成功
			if (StringUtils.isNotBlank(card.getPatCardNo()) && StringUtils.isNotBlank(card.getPatCardType())) {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
				resultMap.put("cardType", card.getPatCardType());
				resultMap.put("cardNo", card.getPatCardNo());
				resultMap.put("patientId", card.getPatId());
			} else {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "建档失败");
			}
		} else {
			// 失败
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
		}
		logger.info("end create card....");
		return resultMap;
	}

	@Override
	public List<MedicalCard> findCardsByOwnership(MedicalCard medicalCard) {
		return medicalCardDao.findCardsByOpenIdAndHospitalCodeAndOwnership(medicalCard);
	}

	@Override
	public List<MedicalCard> findCardsByOpenId(Map<String, Object> params) {
		String openId = params.get(BizConstant.OPENID) == null ? null : params.get(BizConstant.OPENID).toString();
		if (StringUtils.isNotBlank(openId)) {
			String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
			params.put("hashTableName", hashTableName);
		} else {
			Assert.isNull(openId, "openId is not null");
		}
		return medicalCardDao.findCardsByOpenId(params);
	}

	public MedicalCard findById(String medicalCardId) {
		long start = System.currentTimeMillis();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();
		MedicalCard medicalCard = null;
		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { medicalCardId, hashTableName };
			Class<?>[] parameterTypes = new Class[] { String.class, String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(MedicalCardDao.class, "findByIdFromHashTable", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				medicalCard = (MedicalCard) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (medicalCard != null) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			if (medicalCard != null) {
				logger.debug("findByIdFromHashTable cast time :{}Millis . res:",
						new Object[] { System.currentTimeMillis() - start, JSON.toJSONString(medicalCard) });
			} else {
				logger.debug("findByIdFromHashTable cast time :{}Millis . res:", new Object[] { System.currentTimeMillis() - start, 0 });
			}
		}

		return medicalCard;

	}

	@Override
	public List<MedicalCard> findCardForParams(Map<String, Object> params) {
		long start = System.currentTimeMillis();
		List<MedicalCard> medicalCards = new ArrayList<MedicalCard>();
		// 设置线程池的数量
		int cpuNums = Runtime.getRuntime().availableProcessors();
		ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
		List<FutureTask<List<MedicalCard>>> taskList = new ArrayList<FutureTask<List<MedicalCard>>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			for (Object key : params.keySet()) {
				newMap.put(key.toString(), params.get(key).toString());
			}
			newMap.put("hashTableName", tableName);
			QueryCardForParamsCallable collectCall = new QueryCardForParamsCallable(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<MedicalCard>> collectTask = new FutureTask<List<MedicalCard>>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<List<MedicalCard>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<MedicalCard> threadRes = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(threadRes)) {
					medicalCards.addAll(threadRes);
					logger.info("date:{} findCardForParams query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("根据参数查询绑卡结束，耗时:{} millis.", System.currentTimeMillis() - start);
		}
		return medicalCards;
	}

	@Override
	public MedicalCard findByIdAndOpenId(String id, String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return medicalCardDao.findByIdAndHashTableName(id, hashTableName);
	}

	@Override
	public Map<String, Object> updateAdmissionNo(MedicalCard medicalCard) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			this.medicalCardDao.updateAdmissionNo(medicalCard);
			Thread thread = new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "updateAdmissionNo");
			thread.start();
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			resultMap.put("card", medicalCard.getId());
		} catch (Exception e) {
			logger.error("更新住院信息失败，卡Id=" + medicalCard.getId() + ", errorMsg: {}, cause: {}", new Object[] { e.getMessage(), e.getCause() });
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "解绑就诊卡失败");
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> whetherCanBindCard(MedicalCard medicalCard, int maxNum) {
		List<MedicalCard> list = medicalCardDao.findCardsByOpenIdAndHospitalCode(medicalCard);

		int selfCount = 0;
		int otherCount = 0;
		for (MedicalCard card : list) {
			if (card.getOwnership() == MedicalCardConstant.OWNER_TYPE_SELF) {
				selfCount++;
			} else {
				otherCount++;
			}
		}

		logger.info("可以去绑卡?? :" + ( otherCount + selfCount < maxNum + 1 ));
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put(MedicalCardConstant.CAN_BIND_CARD, ( otherCount + selfCount < maxNum + 1 )
				? MedicalCardConstant.WHETHER_CAN_BIND_CARD_YES : MedicalCardConstant.WHETHER_CAN_BIND_CARD_NO);
		respMap.put(MedicalCardConstant.MAX_CARD_COUNT, maxNum);
		respMap.put(MedicalCardConstant.SELF_CARDS, selfCount);
		respMap.put(MedicalCardConstant.OTHER_CARDS, otherCount);
		// 下面返回的参数，暂时供挂号部分的绑卡使用。判断可以绑卡的类型
		int bindCardType = 0;
		if (selfCount == 0 && otherCount == maxNum) {
			bindCardType = 1;
		} else if (selfCount == 1 && otherCount < maxNum) {
			bindCardType = 2;
		} else {
			bindCardType = 0;
		}
		respMap.put(MedicalCardConstant.BIND_CARD_TYPE, bindCardType);
		return respMap;
	}

	@Override
	public String autoBindCardForEasyHealth(MedicalCard medicalCard) {
		logger.info("[健康易]自动绑卡...idNo: " + medicalCard.getIdNo());
		String cardId = "";

		try {
			// 这个不走配置.只验证姓名。用身份证去带卡。手机号码使用接口返回的。
			medicalCard.setCardType(Integer.valueOf(CardType.ID_CARD));
			medicalCard.setCardNo(medicalCard.getIdNo());
			Map<String, Object> resMap =
					medicalcardBizManager.queryMZPatient(medicalCard.getHospitalCode(), medicalCard.getBranchCode(),
							medicalCard.getCardNo(), medicalCard.getCardType() + "", medicalCard.getName());
			String code = (String) resMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
			if (code.equals(BizConstant.INTERFACE_RES_SUCCESS_CODE)) {
				MZPatient patient = (MZPatient) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (patient != null) {
					logger.info("[健康易]自动绑卡，接口返回的用户信息: {}. 身份证号码： {}.", new Object[] { JSON.toJSONString(patient), medicalCard.getIdNo() });
					/*
					 * 健康易的自动绑卡，卡号、卡类型是医院带出来的。
					 */
					String name = patient.getPatName();

					if (name.equals(medicalCard.getName()) && StringUtils.isNotBlank(patient.getPatCardType())
							&& StringUtils.isNotBlank(patient.getPatCardNo())) {

						medicalCard.setCardType(Integer.valueOf(patient.getPatCardType()));
						medicalCard.setCardNo(patient.getPatCardNo());

						String mobile = patient.getPatMobile();
						if (StringUtils.isNotBlank(mobile)) {
							// 手机号码优先拿接口返回的
							medicalCard.setMobile(mobile);
						}

						List<MedicalCard> cards = null;
						MedicalCard tempCard = new MedicalCard();

						/** 至此，接口绑卡是成功的，下面是入库的操作 **/
						tempCard.setName(medicalCard.getName());
						tempCard.setCardNo(medicalCard.getCardNo());
						tempCard.setHospitalCode(medicalCard.getHospitalCode());
						tempCard.setCardType(medicalCard.getCardType());
						tempCard.setState(MedicalCardConstant.MEDICALCARD_UNBIND);
						tempCard.setPlatformMode(medicalCard.getPlatformMode());
						tempCard.setOpenId(medicalCard.getOpenId());
						// 查本人有没有绑过这张卡
						cards = medicalCardDao.findCardByCardNoAndHospitalCodeAndOpenId(tempCard);

						if (cards.size() == 0) {
							// 新增绑卡信息
							try {
								long createTime = new Date().getTime();
								medicalCard.setCreateTime(createTime);
								medicalCard.setUpdateTime(createTime);
								medicalCard.setPatientId(patient.getPatId());
								medicalCard.setBindWay(MedicalCardConstant.BIND_TYPE_AUTO);
								String id = medicalCardDao.add(medicalCard);
								medicalCard.setId(id);
								// 新增，插入缓存(需要Id)
								new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.add").start();

								// 发送成功绑卡的消息
								// medicalCard.setAppId(appId); // 发模板消息需要用到
								CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
								commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);

								// 返回新卡的主键
								cardId = id;
							} catch (Exception e) {
								logger.info("[健康易]自动绑卡入库出错(新增).. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
							}
						} else if (cards.size() == 1) {
							try {
								tempCard = cards.get(0);
								medicalCard.setId(tempCard.getId());
								medicalCard.setUpdateTime(new Date().getTime());
								medicalCard.setPatientId(patient.getPatId());
								medicalCard.setBindWay(MedicalCardConstant.BIND_TYPE_AUTO);
								medicalCardDao.update(medicalCard);
								// 更新，修改缓存
								medicalCard.setCreateTime(tempCard.getCreateTime() == null ? 0L : tempCard.getCreateTime());
								new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.update")
										.start();

								// 发送成功绑卡的消息
								CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
								commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);

								// 返回原卡的主键
								cardId = tempCard.getId();
							} catch (Exception e) {
								logger.info("[健康易]自动绑卡入库出错(更新).. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
							}

						} else {
							// 出错了，这个要提示用户，不能保存了。
							logger.info("[健康易]自动绑卡出错。数据库里面还有多个这张卡的信息");
						}
					} else {
						logger.error("[健康易]账号姓名与接口返回姓名不一致，或就诊卡信息为空，自动绑卡失败...");
					}
				} else {
					logger.error("[健康易]获取不到就诊人信息。resultCode = 0, 但是没有病人信息");
				}
			} else {
				logger.error("[健康易]获取不到就诊人信息。resultCode != 0");
			}
		} catch (Exception e) {
			logger.error("调用接口自动绑卡失败! errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return cardId;
	}

	@Override
	public List<MedicalCard> findCardByIdNoAndHospitalCode(MedicalCard medicalCard) {
		List<MedicalCard> cards = new ArrayList<MedicalCard>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();
		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			// 操作同一个参数中的medicalCard 会造成多线程并发 故未每个线程生成一个新的副本对象
			MedicalCard queryCard = new MedicalCard();
			BeanUtils.copyProperties(medicalCard, queryCard);
			queryCard.setHashTableName(hashTableName);
			Object[] parameters = new Object[] { queryCard };
			Class<?>[] parameterTypes = new Class[] { MedicalCard.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(MedicalCardDao.class, "findCardsByIdNoAndHospitalCode", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				@SuppressWarnings("unchecked")
				List<MedicalCard> resCards = (List<MedicalCard>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(resCards)) {
					cards.addAll(resCards);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return cards;
	}

	@Override
	public String autoBindSelfCardForEasyHealth(CommonParamsVo vo, String userName, String userCardNo, String userModbile) {
		String cardId = "";
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userCardNo) && StringUtils.isNotBlank(userModbile)) {
			if (StringUtils.isBlank(vo.getAppId()) || StringUtils.isBlank(vo.getAppCode()) || StringUtils.isBlank(vo.getHospitalCode())
					|| StringUtils.isBlank(vo.getHospitalId())) {
				logger.error("参数,CommonParamsVo 的appId, appCode, hospitalCode, hospitalId 存在空值！");

				if (logger.isDebugEnabled()) {
					logger.debug("异常参数打印：" + JSON.toJSONString(vo));
				}
			} else {
				// 检测是否存在身份证同登陆号码的诊疗卡（本人）
				MedicalCard tempCard = new MedicalCard();
				tempCard.setAppId(vo.getAppId());
				tempCard.setOpenId(vo.getAppCode());
				tempCard.setOpenId(vo.getOpenId());
				tempCard.setHospitalCode(vo.getHospitalCode());
				tempCard.setHospitalId(vo.getHospitalId());
				tempCard.setHospitalName(vo.getHospitalName());
				tempCard.setPlatformMode(ModeTypeUtil.getPlatformModeType(vo.getAppCode()));
				tempCard.setIdType(Integer.valueOf(IdType.CHINA_ID_CARD));
				tempCard.setIdNo(userCardNo);
				tempCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);
				tempCard.setBindWay(MedicalCardConstant.BIND_TYPE_AUTO);
				tempCard.setMobile(userModbile); // 无卡号，卡类型
				tempCard.setBirth(MedicalCardUtil.getBirth(userCardNo));
				tempCard.setSex(MedicalCardUtil.getSex(userCardNo));
				tempCard.setName(userName);
				// 这里没有添加分院进行，sql语句是支持的。
				List<MedicalCard> medicalCards = findCardByIdNoAndHospitalCode(tempCard);
				if (CollectionUtils.isEmpty(medicalCards)) {
					// 判断能不能执行绑卡
					RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
					int maxNum = ruleConfigManager.getRuleEditByHospitalCode(vo.getHospitalCode()).getAddVpNum();
					Map<String, Object> resMap = whetherCanBindCard(tempCard, maxNum);
					int canBindCard = (Integer) resMap.get(MedicalCardConstant.CAN_BIND_CARD);
					if (canBindCard == MedicalCardConstant.WHETHER_CAN_BIND_CARD_YES) {
						// 进行绑卡操作
						int selfCardNums = (Integer) resMap.get(MedicalCardConstant.SELF_CARDS);
						if (selfCardNums == 0) {
							// 帮本人的卡
							tempCard.setOwnership(MedicalCardConstant.OWNER_TYPE_SELF);
						} else {
							// 本人的卡被绑定，只能绑他人的卡
							tempCard.setOwnership(MedicalCardConstant.OWNER_TYPE_OTHER);
						}

						if (StringUtils.isBlank(vo.getBranchHospitalCode()) || StringUtils.isBlank(vo.getBranchHospitalId())
								|| StringUtils.isBlank(vo.getBranchHospitalName())) {
							BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
							List<BranchHospital> branches = baseDatasManager.queryBranchsByHospitalCode(vo.getHospitalCode());
							if (branches.size() > 1) {
								logger.error("多家分院，不知道要选择哪一家分院。无法自动绑卡.");
							} else if (branches.size() == 1) {
								// 假定没有分院。如果存在分院，则会有问题。
								BranchHospital branch = branches.get(0);
								tempCard.setBranchCode(branch.getCode());
								tempCard.setBranchId(branch.getId());
								tempCard.setBranchName(branch.getName());

								// 执行自动绑卡操作
								cardId = autoBindCardForEasyHealth(tempCard);
							} else {
								logger.error("配置错误，没有分院信息.");
							}
						} else {
							tempCard.setBranchCode(vo.getBranchHospitalCode());
							tempCard.setBranchId(vo.getBranchHospitalId());
							tempCard.setBranchName(vo.getBranchHospitalName());

							// 执行自动绑卡操作
							cardId = autoBindCardForEasyHealth(tempCard);
						}
					} else {
						logger.info("绑卡超过了上限，不能进行绑卡操作... hospitalCode: {}, openId: {}",
								new Object[] { vo.getHospitalCode(), vo.getOpenId() });
					}
				}
			}
		} else {
			logger.error("请先登陆..." + vo.getOpenId());
		}

		return cardId;
	}

	@Override
	public List<MedicalCard> findAllCardsByOpenId(String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return medicalCardDao.findAllCardsByOpenId(openId, hashTableName);
	}

	@Override
	public MedicalCard findCardByHospitalIdAndOpenIdAndCardNo(String hospitalId, String openId, String cardNo) {
		MedicalCard medicalCard = null;
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		List<MedicalCard> list = medicalCardDao.findCardByHospitalIdAndOpenIdAndCardNo(hospitalId, openId, cardNo, hashTableName);
		if (!CollectionUtils.isEmpty(list)) {
			medicalCard = list.get(0);
		}
		return medicalCard;
	}

	@Override
	public MedicalCard findCardByOpenIdAndHospitalCodeAndIdNo(String openId, String hospitalCode, String idType, String idNo) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return medicalCardDao.findCardByOpenIdAndHospitalCodeAndIdNo(openId, hashTableName, hospitalCode, idType, idNo);
	}

	@Override
	public Map<String, Object> syncBindCardForEasyHealth(MedicalCard medicalCard) {
		logger.info("begin bind card.....bindway:[{}]", medicalCard.getBindWay());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查卡是否存在了（state = 1）
		medicalCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);

		// 成功，保存信息
		MedicalCard tempCard = new MedicalCard();
		tempCard.setName(medicalCard.getName());
		tempCard.setCardNo(medicalCard.getCardNo());
		tempCard.setHospitalCode(medicalCard.getHospitalCode());
		tempCard.setCardType(medicalCard.getCardType());
		tempCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);
		tempCard.setPlatformMode(medicalCard.getPlatformMode());
		tempCard.setOpenId(medicalCard.getOpenId());
		tempCard.setFamilyId(medicalCard.getFamilyId());
		List<MedicalCard> cards = medicalCardDao.findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId(tempCard);

		if (CollectionUtils.isEmpty(cards)) {
			tempCard.setState(MedicalCardConstant.MEDICALCARD_UNBIND);

			// 查本人有没有绑过这张卡
			cards = medicalCardDao.findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId(tempCard);

			if (cards.size() == 0) {
				// 新增绑卡信息
				logger.info("绑卡-新增诊疗卡...");
				try {
					long createTime = new Date().getTime();
					medicalCard.setCreateTime(createTime);
					medicalCard.setUpdateTime(createTime);
					String id = medicalCardDao.add(medicalCard);
					medicalCard.setId(id);
					// 新增，插入缓存(需要Id)
					new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.add").start();
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
					resultMap.put("card", medicalCard.getId());
					resultMap.put(BizConstant.COMMON_ENTITY_KEY, medicalCard);

					// 发送成功绑卡的消息
					// medicalCard.setAppId(appId); // 发模板消息需要用到
					try {
						CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
						commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);
					} catch (Exception e) {
						logger.error("绑卡成功，发送绑卡消息异常...medicalcardId={}.", id);
						resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
						resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
					}
				} catch (Exception e) {
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
					logger.info("绑卡-新增诊疗卡出错");
				}
			} else if (cards.size() == 1) {
				// 更新这条信息
				logger.info("绑卡-更新旧诊疗卡...");
				try {
					tempCard = cards.get(0);
					medicalCard.setId(tempCard.getId());
					medicalCard.setUpdateTime(new Date().getTime());
					medicalCardDao.update(medicalCard);
					// 更新，修改缓存
					medicalCard.setCreateTime(tempCard.getCreateTime() == null ? 0L : tempCard.getCreateTime());
					new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.update").start();
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
					resultMap.put("card", medicalCard.getId());
					resultMap.put(BizConstant.COMMON_ENTITY_KEY, medicalCard);

					// 发送成功绑卡的消息
					// medicalCard.setAppId(appId); // 发模板消息需要用到
					try {
						CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
						commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);
					} catch (Exception e) {
						resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
						resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
						logger.error("绑卡成功，发送绑卡消息异常...medicalcardId={}.", medicalCard.getId());
					}
				} catch (Exception e) {
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
					logger.info("绑卡-更新旧诊疗卡出错");
				}

			} else {
				// 出错了，这个要提示用户，不能保存了。
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
				logger.info("数据库里面还有多个这张卡的信息");
			}
		} else {
			if (cards.size() > 1) {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
				logger.info("数据库里面还有多个这张卡的信息");
			} else {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
				resultMap.put("card", cards.get(0).getId());
				resultMap.put(BizConstant.COMMON_ENTITY_KEY, cards.get(0));
			}
		}

		logger.info("end bind card.....bindway[{}]", medicalCard.getBindWay());
		return resultMap;
	}

	@Override
	public void removeCards(String openId, String familyId) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		medicalCardDao.removeCardsByFamilyId(openId, hashTableName, familyId);
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdAndFamilyId(String openId, String familyId) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return medicalCardDao.findCardsByOpenIdAndFamilyId(openId, hashTableName, familyId);
	}

	@Override
	public Map<String, Object> bindCard(MedicalCard medicalCard, String verifyConditionType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 看这个人有没有绑定了卡
		List<MedicalCard> medicalCards = null;

		// medicalCards = findCardsByOpenIdAndFamilyIdAndHospitalCode(medicalCard.getOpenId(), medicalCard.getFamilyId(), medicalCard.getHospitalCode());
		medicalCards =
				findCardsByCardNoAndAppCodeAndHospitalCode(medicalCard.getCardNo(), medicalCard.getAppCode(), medicalCard.getHospitalCode());
		if (CollectionUtils.isEmpty(medicalCards)) {
			Map<String, Object> resMap =
					medicalcardBizManager.queryMZPatient(medicalCard.getHospitalCode(), medicalCard.getBranchCode(), medicalCard);
			String code = (String) resMap.get(BizConstant.INTERFACE_MAP_CODE_KEY);
			if (code.equals(BizConstant.INTERFACE_RES_SUCCESS_CODE)) {
				MZPatient patient = (MZPatient) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (patient != null) {
					String name = patient.getPatName();
					String idNo = patient.getPatIdNo();
					String mobile = patient.getPatMobile();
					String cardNo = patient.getPatCardNo();
					String msg = "";

					// 对于儿童的监护人手机号码，暂时不进行校验。
					if (medicalCard.getOwnership().intValue() != MedicalCardConstant.OWNER_TYPE_CHILD) {
						String[] condtions = verifyConditionType.split(",");
						/**
						 * 添加就诊人时需要验证内容的类型 1：姓名 2 ：身份证 3：手机号 4：卡号 多个值用,号隔开
						 */
						for (int i = 0; i < condtions.length; i++) {
							if (condtions[i].equals("1")) {
								if (!name.equals(medicalCard.getName())) {
									msg = "姓名";
									break;
								}
							} else if (condtions[i].equals("2")) {
								if (!idNo.equals(medicalCard.getIdNo())) {
									msg = "身份证";
									break;
								}
							} else if (condtions[i].equals("3")) {
								if (!mobile.equals(medicalCard.getMobile())) {
									msg = "手机号码";
									break;
								}
							} else if (condtions[i].equals("4")) {
								if (!cardNo.equals(medicalCard.getCardNo())) {
									msg = "就诊卡号";
									break;
								}
							}
						}
					} else {
						if (!name.equals(medicalCard.getName())) {
							msg = "姓名";
						}
					}

					boolean needCheckAgain = false;
					needCheckAgain =
							!medicalCard.getCardNo().equals(patient.getPatCardNo())
									|| ! ( medicalCard.getCardType().toString().equals(patient.getPatCardType()) );
					if (StringUtils.isNotBlank(msg)) {
						// 失败了
						logger.info("绑卡-验证=[" + msg + "], 不通过");
						resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
						resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "您输入的" + msg + "与医院所留信息不一致，请修改后再进行绑卡");
					} else {
						// 绑卡的时候，使用接口返回的卡类型，卡号
						String srcCardNo = medicalCard.getCardNo();
						medicalCard.setCardType(Integer.valueOf(patient.getPatCardType()));
						medicalCard.setCardNo(patient.getPatCardNo());

						if (needCheckAgain) {
							medicalCard.setState(MedicalCardConstant.MEDICALCARD_BOUND);
							medicalCards = findCardByCardNoAndHospitalCode(medicalCard);
							if (medicalCards.size() > 0) {
								logger.error("输入卡号: {}, 返回卡号: {}. 就诊卡被绑定，不能绑卡.", new Object[] { srcCardNo, medicalCard.getCardNo() });
								resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
								resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "该就诊卡已经被绑定。");
								return resultMap;
							}
						}

						// 成功，保存信息
						MedicalCard tempCard = new MedicalCard();
						tempCard.setName(medicalCard.getName());
						tempCard.setCardNo(medicalCard.getCardNo());
						tempCard.setHospitalCode(medicalCard.getHospitalCode());
						tempCard.setCardType(medicalCard.getCardType());
						tempCard.setState(MedicalCardConstant.MEDICALCARD_UNBIND);
						tempCard.setPlatformMode(medicalCard.getPlatformMode());
						tempCard.setOpenId(medicalCard.getOpenId());
						tempCard.setFamilyId(medicalCard.getFamilyId());
						// 查本人有没有绑过这张卡
						medicalCards = medicalCardDao.findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId(tempCard);

						if (medicalCards.size() == 0) {
							// 新增绑卡信息
							logger.info("绑卡-新增诊疗卡...");
							try {
								long createTime = new Date().getTime();
								medicalCard.setCreateTime(createTime);
								medicalCard.setUpdateTime(createTime);
								medicalCard.setPatientId(patient.getPatId());
								String id = medicalCardDao.add(medicalCard);
								medicalCard.setId(id);
								// 新增，插入缓存(需要Id)
								new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.add").start();
								resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
								resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
								resultMap.put("card", medicalCard.getId());
								resultMap.put(BizConstant.COMMON_ENTITY_KEY, medicalCard);

								// 发送成功绑卡的消息
								// medicalCard.setAppId(appId); // 发模板消息需要用到
								try {
									CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
									commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);
								} catch (Exception e) {
									logger.error("绑卡推送消息异常...medicalcardId={}.", id);
									resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
									resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
								}
							} catch (Exception e) {
								resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
								resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
								logger.info("绑卡-新增诊疗卡出错");
							}
						} else if (medicalCards.size() == 1) {
							// 更新这条信息
							logger.info("绑卡-更新旧诊疗卡...");
							try {
								tempCard = medicalCards.get(0);
								medicalCard.setId(tempCard.getId());
								medicalCard.setUpdateTime(new Date().getTime());
								medicalCard.setPatientId(patient.getPatId());
								medicalCardDao.update(medicalCard);
								// 更新，修改缓存
								// baseManager.setMedicalCardToCache(medicalCard,
								// CacheConstant.UPDATE_OP_TYPE_UPDATE);
								medicalCard.setCreateTime(tempCard.getCreateTime() == null ? 0L : tempCard.getCreateTime());
								new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_UPDATE), "bindCard.update")
										.start();
								resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
								resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
								resultMap.put("card", medicalCard.getId());
								resultMap.put(BizConstant.COMMON_ENTITY_KEY, medicalCard);

								// 发送成功绑卡的消息
								// medicalCard.setAppId(appId); // 发模板消息需要用到
								try {
									CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
									commonMsgPushSvc.pushMsg(medicalCard, MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS);
								} catch (Exception e) {
									logger.error("绑卡成功，发送绑卡信息异常...familyId={}.", medicalCard.getFamilyId());
									resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
									resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_SUCCESS);
								}
							} catch (Exception e) {
								resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
								resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
								logger.info("绑卡-更新旧诊疗卡出错");
							}

						} else {
							// 出错了，这个要提示用户，不能保存了。
							resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
							resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
							logger.info("数据库里面还有多个这张卡的信息");
						}
					}
				} else {
					logger.error("接口返回成功，但是没有result对象信息. 返回: {}.", JSON.toJSONString(resMap));
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, MedicalCardConstant.BIND_FAIL);
				}
			} else {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, resMap.get(BizConstant.INTERFACE_MAP_MSG_KEY).toString());
			}
		} else {
			logger.info("已经绑定了卡，就不去做绑定操作了...familyId={}.", medicalCard.getFamilyId());
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "该就诊卡已经被绑定。");
			resultMap.put(BizConstant.COMMON_ENTITY_KEY, medicalCards.get(0));

			if (medicalCards.size() > 0) {
				logger.error("多个人绑定了同一个卡？？！！...familyId={}.", medicalCard.getFamilyId());
			}
		}
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yxw.mobileapp.biz.medicalcard.service.MedicalCardService#findCardSuperviseParams(java.util.Map)
	 */

	@Override
	public List<MedicalCard> findCardSuperviseParams(Map<String, Object> params) {
		long start = System.currentTimeMillis();
		List<MedicalCard> medicalCards = new ArrayList<MedicalCard>();
		// 设置线程池的数量
		int cpuNums = Runtime.getRuntime().availableProcessors();
		ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
		List<FutureTask<List<MedicalCard>>> taskList = new ArrayList<FutureTask<List<MedicalCard>>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			HashMap<String, Object> newMap = new HashMap<String, Object>();
			newMap.putAll(params);
			newMap.put("hashTableName", tableName);
			QueryCardForParamsCallable collectCall = new QueryCardForParamsCallable(newMap);
			// 创建每条指令的采集任务对象
			FutureTask<List<MedicalCard>> collectTask = new FutureTask<List<MedicalCard>>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<List<MedicalCard>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<MedicalCard> threadRes = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(threadRes)) {
					medicalCards.addAll(threadRes);
					logger.info("date:{} findCardForParams query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("就诊人后台管理页面查询方法，耗时:{} millis.", System.currentTimeMillis() - start);
		}
		return medicalCards;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.mobileapp.biz.medicalcard.service.MedicalCardService#unBindCardByState(com.yxw.mobileapp.biz.medicalcard
	 * .entity.MedicalCard)
	 */

	@Override
	public Map<String, Object> unBindCardByState(MedicalCard medicalCard) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			medicalCard.setUpdateTime(new Date().getTime());
			this.medicalCardDao.update(medicalCard);
			Thread thread = new Thread(new CardCacheRunnable(medicalCard, CacheConstant.UPDATE_OP_TYPE_DEL), "unbindCard");
			thread.start();
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
			resultMap.put("card", medicalCard.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("解绑或绑定就诊卡失败，卡Id=" + medicalCard.getId());
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.FAIL);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "解绑或绑定就诊卡失败");
		}

		return resultMap;
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdAndFamilyIdAndHospitalCode(String openId, String familyId, String hospitalCode) {
		String hashTableName = SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true);
		return medicalCardDao.findCardsByOpenIdAndFamilyIdAndHospitalCode(openId, hashTableName, familyId, hospitalCode);
	}

	@Override
	public void updateFamilyId(MedicalCard medicalCard) {
		medicalCardDao.updateFamilyId(medicalCard);
	}

	@Override
	public List<MedicalCard> findCardsByCardNoAndAppCodeAndHospitalCode(String cardNo, String appCode, String hospitalCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("cardNo", cardNo);
		params.put("appCode", appCode);
		params.put("hospitalCode", hospitalCode);
		params.put("state", MedicalCardConstant.MEDICALCARD_BOUND);
		return findCardSuperviseParams(params);
	}

	/*@Override
	public String findCardByCardNoAndHospitalIdAndPlatformType(String cardNo, String hospitalId, String platformType) {

		try {
			int type = BizConstant.MODE_TYPE_WEIXIN.equals(platformType) ? 1 : 2;
			List<SimpleMedicalCard> simpleMedicalCards = cache.getMedicalCardsByCardNo(cardNo, hospitalId, type);
			//从缓存中获取
			if (!CollectionUtils.isEmpty(simpleMedicalCards)) {
				String openId = null;
				//logger.info("通过缓存拿到卡...:{}", JSON.toJSONString(simpleMedicalCards));
				for (SimpleMedicalCard simpleMedicalCard : simpleMedicalCards) {
					if (simpleMedicalCard.getState() == 1) {
						openId = simpleMedicalCard.getOpenId();
						break;
					}
				}
				return openId;
			} else {

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("cardNo", cardNo);
				params.put("hospitalId", hospitalId);
				return medicalCardDao.findOpenIdByCardNoHospitalCode(params);
			}
		} catch (Exception e) {
			logger.error("找就诊信息异常. errorMsg: {}. cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			return null;
		}
	}*/
}
