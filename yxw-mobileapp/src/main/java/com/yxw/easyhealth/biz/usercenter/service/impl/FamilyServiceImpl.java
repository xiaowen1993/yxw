package com.yxw.easyhealth.biz.usercenter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.mobile.biz.usercenter.People;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.usercenter.dao.FamilyDao;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.usercenter.service.PeopleService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.biz.usercenter.thread.CardCacheRunnable;

@Service(value = "familyService")
public class FamilyServiceImpl extends BaseServiceImpl<Family, String> implements FamilyService {

	private Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);
	private FamilyDao familyDao = SpringContextHolder.getBean(FamilyDao.class);

	/**
	 * 找小孩的信息
	 * @param openId
	 * @param hashTableName
	 * @param name
	 * @param guardIdType
	 * @param guardIdNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIDAndNameAndGuardIdNo(String openId, String hashTableName, String name, int guardIdType,
			String guardIdNo, int state) {
		return familyDao.findFamiliesByOpenIDAndNameAndGuardIdNo(openId, hashTableName, name, guardIdType, guardIdNo, state);
	}

	/**
	 * 找大人的信息
	 * @param openId
	 * @param hashTableName
	 * @param idType
	 * @param idNo
	 * @return
	 */
	public List<Family> findFamiliesByOpenIdAndIdNo(String openId, String hashTableName, int idType, String idNo, int state) {
		return familyDao.findFamiliesByOpenIdAndIdNo(openId, hashTableName, idType, idNo, state);
	}

	@Override
	public Map<String, Object> saveFamilyInfo(Family family) {
		Map<String, Object> resultMap = checkCanBindFamily(family);

		if (!StringUtils.equals(BizConstant.METHOD_INVOKE_SUCCESS, (String) resultMap.get(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY))) {
			return resultMap;
		}

		// 检测家人是否存在
		List<Family> families = null;
		try {
			if (family.getOwnership().intValue() == FamilyConstants.FAMILY_OWNERSHIP_CHILD) {
				// 走小孩的
				families =
						familyDao.findFamiliesByOpenIDAndNameAndGuardIdNo(family.getOpenId(), family.getHashTableName(), family.getName(),
								family.getGuardIdType(), family.getGuardIdNo(), FamilyConstants.FAMILY_STATE_ENABLE);
			} else {
				// 走大人的
				families =
						familyDao.findFamiliesByOpenIdAndIdNo(family.getOpenId(), family.getHashTableName(), family.getIdType(),
								family.getIdNo(), FamilyConstants.FAMILY_STATE_ENABLE);
			}

			if (!CollectionUtils.isEmpty(families)) {
				if (families.size() > 1) {
					logger.error("存在多条同样的家人数据了: ", JSON.toJSONString(family));
				}

				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				// resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "存在同样的家人数据了: " + family.getName());
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "该身份证已被使用");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("检测家人是否存在异常, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "检测家人是否存在异常");
			return resultMap;
		}

		PeopleService peopleService = SpringContextHolder.getBean(PeopleService.class);
		People people = null;
		try {
			// 检测身份证是否存在
			if (family.getOwnership().intValue() == FamilyConstants.FAMILY_OWNERSHIP_CHILD) {
				// 走小孩的
				people =
						peopleService
								.findByNameAndGuardIdTypeAndGuardIdNo(family.getName(), family.getGuardIdType(), family.getGuardIdNo());
			} else {
				// 走大人的
				people = peopleService.findByIdNo(family.getIdType(), family.getIdNo());
			}

		} catch (Exception e) {
			logger.error("检测身份证是否存在异常, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "检测身份证是否存在异常");
			return resultMap;
		}

		if (people == null) {
			// 人不存在，需要存入表
			try {
				people = family.convertToPeople();
				peopleService.savePeople(people);
				logger.info("保存人信息成功." + JSON.toJSONString(people));
			} catch (Exception e) {
				logger.error("保存人信息异常, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "保存家人信息异常");
				return resultMap;
			}
		}

		// 绑定家人信息
		try {
			if (family.getOwnership().intValue() == FamilyConstants.FAMILY_OWNERSHIP_CHILD) {
				// 走小孩的
				families =
						familyDao.findFamiliesByOpenIDAndNameAndGuardIdNo(family.getOpenId(), family.getHashTableName(), family.getName(),
								family.getGuardIdType(), family.getGuardIdNo(), FamilyConstants.FAMILY_STATE_DISABLE);
			} else {
				// 走大人的
				families =
						familyDao.findFamiliesByOpenIdAndIdNo(family.getOpenId(), family.getHashTableName(), family.getIdType(),
								family.getIdNo(), FamilyConstants.FAMILY_STATE_DISABLE);
			}

			// 检测还能不能绑定家人。（最多只能）

			if (CollectionUtils.isEmpty(families)) {
				// 新增
				family.setState(FamilyConstants.FAMILY_STATE_ENABLE);
				Long currentTime = System.currentTimeMillis();
				family.setCreateTime(currentTime);
				family.setUpdateTime(currentTime);
				familyDao.add(family);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "保存家人信息成功" + family.getName());
			} else {
				// 修改
				if (families.size() > 1) {
					logger.error("同一个人后台有多条已解绑数据: ({})", JSON.toJSONString(family));
				}

				Family tempFamily = families.get(0);
				family.setId(tempFamily.getId());
				Long currentTime = System.currentTimeMillis();
				//				family.setCreateTime(family.getCreateTime());
				family.setUpdateTime(currentTime);
				family.setState(FamilyConstants.FAMILY_STATE_ENABLE);
				familyDao.update(family);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "存在已禁用的家人数据，更新家人信息成功" + family.getName());
			}
		} catch (Exception e) {
			logger.error("保存家人信息异常, errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "保存家人信息异常");
			return resultMap;
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> updateFamilyInfo(Family family) {
		// 目前解绑的时候使用到。
		return null;
	}

	@Override
	protected BaseDao<Family, String> getDao() {
		return familyDao;
	}

	@Override
	public List<Family> findFamiliesByOpenId(String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getFamilyHashTable(openId, true);
		return familyDao.findFamiliesByOpenId(openId, hashTableName, FamilyConstants.FAMILY_STATE_ENABLE);
	}

	@Override
	public Map<String, Object> saveFamilyInfo(EasyHealthUser user) {
		Family family = new Family();
		family.setAppCode("");// 这个暂时没有。
		family.setAreaCode(""); // 这个暂时没有。
		family.setAreaName("");// 这个暂时没有。
		family.setName(user.getName());
		family.setIdType(user.getCardType());
		family.setIdNo(user.getCardNo());
		family.setOpenId(user.getId());
		family.setAddress(user.getAddress());
		family.setOwnership(FamilyConstants.FAMILY_OWNERSHIP_SELF); // 大人
		family.setState(FamilyConstants.FAMILY_STATE_ENABLE);
		family.setMobile(user.getMobile());
		family.setSex(user.getSex());
		family.setBirth(user.getBirthDay());
		return saveFamilyInfo(family);
	}

	@Override
	public Family findFamilyInfo(String familyId, String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getFamilyHashTable(openId, true);
		return familyDao.findFamilyInfo(familyId, openId, hashTableName);
	}

	@Override
	public Family findSelfInfo(String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getFamilyHashTable(openId, true);
		return familyDao.findSelfInfo(openId, hashTableName);
	}

	@Override
	public Map<String, Object> syncMedicalcard(Family family, String hospitalId, String hospitalCode, String appCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String msgInfo = "成功";

		BaseDatasManager baseManager = SpringContextHolder.getBean(BaseDatasManager.class);
		HospIdAndAppSecretVo vo = baseManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
		if (vo != null) {
			List<BranchHospital> branchHospitals = baseManager.queryBranchsByHospitalCode(hospitalCode);
			if (!CollectionUtils.isEmpty(branchHospitals)) {
				BranchHospital branchHospital = branchHospitals.get(0);
				MedicalCard medicalCard = family.convertToMedicalCard();
				// 医院基本时间
				medicalCard.setAppId(vo.getAppId());
				//				medicalCard.setAppCode(vo.getAppCode());
				medicalCard.setAppCode(appCode);
				medicalCard.setHospitalCode(hospitalCode);
				medicalCard.setHospitalName(vo.getHospName());
				medicalCard.setHospitalId(hospitalId);
				medicalCard.setBranchCode(branchHospital.getCode());
				medicalCard.setBranchId(branchHospital.getId());
				medicalCard.setBranchName(branchHospital.getName());
				medicalCard.setFamilyId(family.getId());

				MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
				Map<String, Object> result = medicalCardService.createCard(medicalCard);
				String resultCode = (String) result.get(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY);
				// 建档成功，进入绑卡流程。
				if (BizConstant.SUCCESS.equals(resultCode)) {
					String patientId = (String) result.get("patientId");
					String cardType = (String) result.get("cardType");
					String cardNo = (String) result.get("cardNo");

					if (StringUtils.isNotBlank(cardType) && StringUtils.isNotBlank(cardNo)) {
						medicalCard.setPatientId(patientId);
						medicalCard.setCardType(Integer.valueOf(cardType));
						medicalCard.setCardNo(cardNo);

						// 开始绑卡流程。
						resultMap = medicalCardService.syncBindCardForEasyHealth(medicalCard);
					} else {
						msgInfo = "建档返回数据异常，cardType和cardNo为空";
						resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
						resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msgInfo);
						logger.error(msgInfo + ",: " + JSON.toJSONString(result));
					}
				} else {
					msgInfo = "建档失败";
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msgInfo);
					logger.error(msgInfo);
				}
			} else {
				msgInfo = "无效的医院分院信息";
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msgInfo);
				logger.error(msgInfo);
			}
		} else {
			msgInfo = "无效的医院信息";
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msgInfo);
			logger.error(msgInfo);
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> checkCanBindFamily(Family family) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String isValid = BizConstant.METHOD_INVOKE_FAILURE;
		String msg = "可以进行绑定！";

		try {
			int ownership = family.getOwnership();
			if (ownership == FamilyConstants.FAMILY_OWNERSHIP_SELF) {
				// 本人
				Family tempFamily = findSelfInfo(family.getOpenId());
				if (tempFamily != null) {
					msg = "您已经绑定了一个本人的信息。";
					logger.info(msg);
				} else {
					isValid = BizConstant.METHOD_INVOKE_SUCCESS;
				}
			} else {
				// 家人
				int maxFamilyNum = SystemConfig.getInteger(FamilyConstants.MAX_FAMILY_NUM_KEY, 2);
				List<Family> families = findFamiliesByOpenId(family.getOpenId());

				if (CollectionUtils.isEmpty(families)) {
					isValid = BizConstant.METHOD_INVOKE_SUCCESS;
				} else {
					if (families.size() >= maxFamilyNum) {
						msg = "超过最大家人上限，最多可以绑" + maxFamilyNum + "个家人.";
						logger.info(msg);
					} else {
						isValid = BizConstant.METHOD_INVOKE_SUCCESS;
					}
				}
			}
		} catch (Exception e) {
			logger.error("检测能否绑定家人信息异常...errorMsg: {}. cause: {}.", e.getMessage(), e.getCause());
			msg = "检测能否绑定家人信息异常，绑定失败。";
		}

		resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, isValid);
		resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, msg);

		return resultMap;
	}

	@Override
	public Map<String, Object> unbindFamily(Family family) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 删除关联的就诊卡
		try {
			MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
			List<MedicalCard> families = medicalCardService.findCardsByOpenIdAndFamilyId(family.getOpenId(), family.getId());

			for (MedicalCard card : families) {
				Thread thread = new Thread(new CardCacheRunnable(card, CacheConstant.UPDATE_OP_TYPE_DEL), "unbindCard");
				thread.start();
			}

			medicalCardService.removeCards(family.getOpenId(), family.getId());

			// 删除家人
			try {
				family.setUpdateTime(System.currentTimeMillis());
				family.setState(FamilyConstants.FAMILY_STATE_DISABLE);
				familyDao.update(family);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "删除该家人信息成功！");
			} catch (Exception e) {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "删除该家人信息异常！");
				logger.error("删除该家人信息异常！" + JSON.toJSONString(family));
			}
		} catch (Exception e) {
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_EXCEPTION);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "删除该家人关联的就诊卡异常！");
			logger.error("删除该家人关联的就诊卡异常！" + JSON.toJSONString(family));
		}

		return resultMap;
	}

	@Override
	public List<Family> findAllFamilies(String openId) {
		String hashTableName = SimpleHashTableNameGenerator.getFamilyHashTable(openId, true);
		return familyDao.findAllFamilies(openId, hashTableName, FamilyConstants.FAMILY_STATE_ENABLE);
	}

}
