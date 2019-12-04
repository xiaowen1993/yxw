package com.yxw.mobileapp.biz.usercenter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.usercenter.dao.MedicalCardDao;

@Repository(value = "medicalCardDao")
public class MedicalCardDaoImpl extends BaseDaoImpl<MedicalCard, String> implements MedicalCardDao {

	private static Logger logger = LoggerFactory.getLogger(MedicalCardDaoImpl.class);

	private final static String SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE = "findCardsByOpenIdAndHospitalCode";
	private final static String SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE = "findCardByCardNoAndHospitalCode";
	// private final static String SQLNAME_FIND_CARD_BY_IDNO_AND_HOSPITALCODE =
	// "findCardByIdNoAndHospitalCode";
	private final static String SQLNAME_FIND_ALL_FOR_CACHE = "findAllForCache";
	private final static String SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE_AND_OWNERSHIP =
			"findCardsByOpenIdAndHospitalCodeAndOwnership";
	private final static String SQLNAME_FIND_CARDS_BY_OPENID = "findCardsByOpenId";
	private final static String SQLNAME_FIND_CARDS_BY_OPENID_FOR_EASYHEALTH = "findCardsByOpenIdForEasyHealth";
	private final static String SQLNAME_FIND_BY_ID_FROM_HASH_TABLE = "findByIdFromHashTable";
	private final static String SQLNAME_FIND_BY_ID_AND_HASH_TABLE = "findByIdAndHashTableName";
	private final static String SQLNAME_FIND_CARD_COUNT_BY_OPENID_AND_HOSPITALCODE = "getCardCountByOpenIdAndHospitalCode";
	private final static String SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID = "findCardByCardNoAndHospitalCodeAndOpenId";
	private final static String SQLNAME_FIND_OPENID_BY_CARDNO = "findOpenIdByCardNo	";
	private final static String SQLNAME_UPDATE_ADMISSION_NO = "updateAdmissionNo";
	private final static String SQLNAME_FIND_CARD_FOR_PARAMS = "findCardForParams";
	private final static String SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITALCODE = "findCardByIdNoAndHospitalCode";
	private final static String SQLNAME_FIND_ALL_CARDS_BY_OPEN_ID = "findAllCardsByOpenId";
	private final static String SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITAL_CODE_AND_OPEN_ID = "findCardByIdNoAndHospitalCodeAndOpenId";
	private final static String SQLNAME_FIND_CARD_BY_HOSPITAL_ID_AND_OPEN_ID_AND_CARD_NO = "findCardByHospitalIdAndOpenIdAndCardNo";
	private final static String SQLNAME_FIND_CARD_BY_OPEN_ID_AND_HOSPITAL_CODE_AND_IDNO = "findCardByOpenIdAndHospitalCodeAndIdNo";
	private final static String SQLNAME_REMOVE_CARDS_BY_FAMILY_ID = "removeCardsByFamilyId";
	private final static String SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID = "findCardsByOpenIdAndFamilyId";
	private final static String SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID_AND_HOSPITAL_CODE =
			"findCardsByOpenIdAndFamilyIdAndHospitalCode";
	private final static String SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID_AND_FAMILYID =
			"findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId";
	private final static String SQLNAME_UPDATE_FAMILYID = "updateFamilyId";

	@Override
	public List<MedicalCard> findCardsByOpenIdAndHospitalCode(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getOpenId());
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE)), e);
			throw new SystemException(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE)), e);
		}
	}

	@Override
	public int getCardCountByOpenIdAndHospitalCode(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getOpenId());
			Assert.notNull(medicalCard.getHospitalCode());
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_CARD_COUNT_BY_OPENID_AND_HOSPITALCODE), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("查询绑卡数出错!语句：%s", getSqlName(SQLNAME_FIND_CARD_COUNT_BY_OPENID_AND_HOSPITALCODE)), e);
			throw new SystemException(String.format("查询绑卡数出错!语句：%s", getSqlName(SQLNAME_FIND_CARD_COUNT_BY_OPENID_AND_HOSPITALCODE)), e);
		}
		//return findCardsByOpenIdAndHospitalCode(medicalCard).size();
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdAndHospitalCodeAndOwnership(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getHospitalCode());
			Assert.notNull(medicalCard.getOpenId());
			//			Assert.notNull(medicalCard.getOwnership());
			//			Assert.notNull(medicalCard.getPlatform());
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE_AND_OWNERSHIP), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("查询某账号下某与本人关系的卡！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE_AND_OWNERSHIP)), e);
			throw new SystemException(String.format("查询某账号下某与本人关系的卡！语句：%s",
					getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_AND_HOSPITALCODE_AND_OWNERSHIP)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardByCardNoAndHospitalCode(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getCardNo());
			Assert.notNull(medicalCard.getHospitalCode());
			Assert.notNull(medicalCard.getPlatformMode());
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("通过就诊卡号查询诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE)), e);
			throw new SystemException(String.format("通过就诊卡号查询诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE)), e);
		}
	}

	@Override
	public List<SimpleMedicalCard> findAllForCache(String hashTableName) {
		List<SimpleMedicalCard> cards = null;
		try {
			Assert.notNull(hashTableName, "hashTableName is null.");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hashTableName", hashTableName);
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_FOR_CACHE), param);
		} catch (Exception e) {
			logger.error(String.format("查询系统所有绑卡信息出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_FOR_CACHE)), e);
			throw new SystemException(String.format("查询系统所有绑卡信息出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_FOR_CACHE)), e);
		}
		if (cards == null) {
			cards = new ArrayList<SimpleMedicalCard>();
		}
		return cards;
	}

	@Override
	public List<MedicalCard> findCardsByOpenId(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPENID), params);
		} catch (Exception e) {
			logger.error(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID)), e);
			throw new SystemException(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdForEasyHealth(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_FOR_EASYHEALTH), params);
		} catch (Exception e) {
			logger.error(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_FOR_EASYHEALTH)), e);
			throw new SystemException(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID_FOR_EASYHEALTH)), e);
		}
	}

	public MedicalCard findByIdFromHashTable(String medicalCardId, String hashTableName) {
		try {
			Assert.notNull(medicalCardId, "medicalCardId is null");
			Assert.notNull(hashTableName, "hashTableName is null");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", medicalCardId);
			params.put("hashTableName", hashTableName);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID_FROM_HASH_TABLE), params);
		} catch (Exception e) {
			logger.error(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_FROM_HASH_TABLE)), e);
			throw new SystemException(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_FROM_HASH_TABLE)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardByCardNoAndHospitalCodeAndOpenId(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getCardNo());
			Assert.notNull(medicalCard.getHospitalCode());
			Assert.notNull(medicalCard.getPlatformMode());
			Assert.notNull(medicalCard.getOpenId());
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("通过就诊卡号查询诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID)), e);
			throw new SystemException(String.format("通过就诊卡号查询诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID)), e);
		}
	}

	@Override
	public List<MedicalCard> findOpenIdByCardNo(Map<String, Object> params) {
		try {
			Assert.notNull(params);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_OPENID_BY_CARDNO), params);
		} catch (Exception e) {
			logger.error(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID)), e);
			throw new SystemException(String.format("查询绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPENID)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardForParams(Map<String, Object> params) {
		List<MedicalCard> cards = null;
		try {
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_FOR_PARAMS), params);
		} catch (Exception e) {
			logger.error(String.format("根据参数查询绑卡用于后台解绑出错!语句：%s", getSqlName(SQLNAME_FIND_CARD_FOR_PARAMS)), e);
			throw new SystemException(String.format("根据参数查询绑卡用于后台解绑出错!语句：%s", getSqlName(SQLNAME_FIND_CARD_FOR_PARAMS)), e);
		}
		if (cards == null) {
			cards = new ArrayList<MedicalCard>();
		}
		return cards;
	}

	@Override
	public MedicalCard findByIdAndHashTableName(String id, String hashTableName) {
		try {
			Assert.notNull(id, "id is null");
			Assert.notNull(hashTableName, "hashTableName is null");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("hashTableName", hashTableName);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID_AND_HASH_TABLE), params);
		} catch (Exception e) {
			logger.error(String.format("根据Id查找绑定就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_AND_HASH_TABLE)), e);
			throw new SystemException(String.format("根据Id查找绑定的就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID_AND_HASH_TABLE)), e);
		}
	}

	@Override
	public void updateAdmissionNo(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getId(), "Id为空");
			Assert.notNull(medicalCard.getAdmissionNo(), "住院号为空");
			sqlSession.update(getSqlName(SQLNAME_UPDATE_ADMISSION_NO), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("更新住院信息出错！语句：%s", getSqlName(SQLNAME_UPDATE_ADMISSION_NO)), e);
			throw new SystemException(String.format("更新住院信息出错！语句：%s", getSqlName(SQLNAME_UPDATE_ADMISSION_NO)), e);
		}

	}

	@Override
	public MedicalCard findCardByIdNoAndHospitalCodeAndOpenId(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getOpenId(), "OpenId为空");
			Assert.notNull(medicalCard.getIdNo(), "IdNo为空");
			Assert.notNull(medicalCard.getPlatformMode(), "platform为空(目前健康易)");
			Assert.notNull(medicalCard.getHospitalCode(), "hospitalCode为空");
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITAL_CODE_AND_OPEN_ID), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("根据IdNo查找绑定就诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITAL_CODE_AND_OPEN_ID)), e);
			throw new SystemException(String.format("根据IdNo查找绑定的就诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITAL_CODE_AND_OPEN_ID)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardsByIdNoAndHospitalCode(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getIdNo());
			Assert.notNull(medicalCard.getIdType());
			Assert.notNull(medicalCard.getHospitalCode());
			Assert.notNull(medicalCard.getPlatformMode());
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITALCODE), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("通过证件号码查询诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITALCODE)), e);
			throw new SystemException(String.format("通过证件号码查询诊卡出错！语句：%s", getSqlName(SQLNAME_FIND_CARD_BY_ID_NO_AND_HOSPITALCODE)), e);
		}
	}

	@Override
	public List<MedicalCard> findAllCardsByOpenId(String openId, String hashTableName) {
		List<MedicalCard> cards = null;
		try {
			Assert.notNull(openId, "OpenId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_CARDS_BY_OPEN_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据OpenId查找所有的绑卡数据出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_CARDS_BY_OPEN_ID)), e);
			throw new SystemException(String.format("根据OpenId查找所有的绑卡数据出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_CARDS_BY_OPEN_ID)), e);
		}
		if (cards == null) {
			cards = new ArrayList<MedicalCard>();
		}
		return cards;
	}

	@Override
	public List<MedicalCard> findCardByHospitalIdAndOpenIdAndCardNo(String hospitalId, String openId, String cardNo, String hashTableName) {
		List<MedicalCard> cards = null;
		try {
			Assert.notNull(openId, "OpenId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(hospitalId, "hospitalId不能为空");
			Assert.notNull(cardNo, "cardNo不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("hospitalId", hospitalId);
			params.put("cardNo", cardNo);
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_BY_HOSPITAL_ID_AND_OPEN_ID_AND_CARD_NO), params);
		} catch (Exception e) {
			logger.error(String.format("根据OpenId,hospitalId,cardNo查找所有的绑卡数据出错!语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_HOSPITAL_ID_AND_OPEN_ID_AND_CARD_NO)), e);
			throw new SystemException(String.format("根据OpenId,hospitalId,cardNo查找所有的绑卡数据出错!语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_HOSPITAL_ID_AND_OPEN_ID_AND_CARD_NO)), e);
		}
		if (cards == null) {
			cards = new ArrayList<MedicalCard>();
		}
		return cards;
	}

	@Override
	public MedicalCard findCardByOpenIdAndHospitalCodeAndIdNo(String openId, String hashTableName, String hospitalCode, String idType,
			String idNo) {
		try {
			Assert.notNull(openId, "openId is null");
			Assert.notNull(hashTableName, "hashTableName is null");
			Assert.notNull(hospitalCode, "hospitalCode is null");
			Assert.notNull(idType, "idType is null");
			Assert.notNull(idNo, "idNo is null");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("hospitalCode", hospitalCode);
			params.put("idType", idType);
			params.put("idNo", idNo);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_CARD_BY_OPEN_ID_AND_HOSPITAL_CODE_AND_IDNO), params);
		} catch (Exception e) {
			logger.error(String.format("根据IdNo,openId,hospitalCode查找绑定就诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_OPEN_ID_AND_HOSPITAL_CODE_AND_IDNO)), e);
			throw new SystemException(String.format("根据IdNo,openId,hospitalCode查找绑定的就诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_OPEN_ID_AND_HOSPITAL_CODE_AND_IDNO)), e);
		}
	}

	@Override
	public void removeCardsByFamilyId(String openId, String hashTableName, String familyId) {
		try {
			Assert.notNull(openId, "openId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(familyId, "familyId 不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("familyId", familyId);
			params.put("updateTime", System.currentTimeMillis());
			sqlSession.update(getSqlName(SQLNAME_REMOVE_CARDS_BY_FAMILY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("清空家人就诊卡信息出错！语句：%s", getSqlName(SQLNAME_REMOVE_CARDS_BY_FAMILY_ID)), e);
			throw new SystemException(String.format("清空家人就诊卡信息出错！语句：%s", getSqlName(SQLNAME_REMOVE_CARDS_BY_FAMILY_ID)), e);
		}
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdAndFamilyId(String openId, String hashTableName, String familyId) {
		List<MedicalCard> cards = null;
		try {
			Assert.notNull(openId, "OpenId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(familyId, "familyId不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("familyId", familyId);
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID), params);
		} catch (Exception e) {
			logger.error(String.format("根据OpenId,familyId查找所有的绑卡数据出错!语句：%s", getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID)), e);
			throw new SystemException(String.format("根据OpenId,familyId查找所有的绑卡数据出错!语句：%s",
					getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID)), e);
		}
		if (cards == null) {
			cards = new ArrayList<MedicalCard>();
		}
		return cards;
	}

	@Override
	public List<MedicalCard> findCardsByOpenIdAndFamilyIdAndHospitalCode(String openId, String hashTableName, String familyId,
			String hospitalCode) {
		List<MedicalCard> cards = null;
		try {
			Assert.notNull(openId, "OpenId不能为空");
			Assert.notNull(hashTableName, "hashTableName不能为空");
			Assert.notNull(familyId, "familyId不能为空");
			Assert.notNull(hospitalCode, "hospitalCode不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("hashTableName", hashTableName);
			params.put("familyId", familyId);
			params.put("hospitalCode", hospitalCode);
			cards = sqlSession.selectList(getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID_AND_HOSPITAL_CODE), params);
		} catch (Exception e) {
			logger.error(String.format("根据OpenId,familyId,hospitalCode查找所有的绑卡数据出错!语句：%s",
					getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID_AND_HOSPITAL_CODE)), e);
			throw new SystemException(String.format("根据OpenId,familyId,hospitalCode查找所有的绑卡数据出错!语句：%s",
					getSqlName(SQLNAME_FIND_CARDS_BY_OPEN_ID_AND_FAMILY_ID_AND_HOSPITAL_CODE)), e);
		}
		if (cards == null) {
			cards = new ArrayList<MedicalCard>();
		}
		return cards;
	}

	@Override
	public List<MedicalCard> findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getCardNo(), "卡号不能为空");
			Assert.notNull(medicalCard.getHospitalCode(), "医院代码不能为空");
			Assert.notNull(medicalCard.getPlatformMode(), "平台不能为空");
			Assert.notNull(medicalCard.getOpenId(), "openId不能为空");
			Assert.notNull(medicalCard.getFamilyId(), "familyId不能为空");
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID_AND_FAMILYID), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("通过就诊卡号,familyId查询诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID_AND_FAMILYID)), e);
			throw new SystemException(String.format("通过就诊卡号, familyId查询诊卡出错！语句：%s",
					getSqlName(SQLNAME_FIND_CARD_BY_CARDNO_AND_HOSPITALCODE_AND_OPENID_AND_FAMILYID)), e);
		}
	}

	@Override
	public void updateFamilyId(MedicalCard medicalCard) {
		try {
			Assert.notNull(medicalCard.getId(), "Id为空");
			Assert.notNull(medicalCard.getFamilyId(), "FamilyId为空");
			sqlSession.update(getSqlName(SQLNAME_UPDATE_FAMILYID), medicalCard);
		} catch (Exception e) {
			logger.error(String.format("更家人ID出错！语句：%s", getSqlName(SQLNAME_UPDATE_FAMILYID)), e);
			throw new SystemException(String.format("更新家人ID出错！语句：%s", getSqlName(SQLNAME_UPDATE_FAMILYID)), e);
		}
	}

}
