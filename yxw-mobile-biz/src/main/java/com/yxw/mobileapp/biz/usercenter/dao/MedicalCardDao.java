package com.yxw.mobileapp.biz.usercenter.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.medicalcard.dao
 * @ClassName: MedicalCardDao
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-6-3
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MedicalCardDao extends BaseDao<MedicalCard, String> {
	/**
	 * 根据医院code 、openid查询绑定的诊疗卡
	 * 
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过Id来找卡
	 * @param id
	 * @param hashTableName
	 * @return
	 */
	public MedicalCard findByIdAndHashTableName(String id, String hashTableName);

	/**
	 * 查询绑卡数
	 * 
	 * @param openId
	 * @return
	 */
	public int getCardCountByOpenIdAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过就诊卡号查找卡(同一分院，就诊卡号唯一)
	 * 
	 * @param cardNo
	 * @param HospitalCode
	 * @return
	 */
	public List<MedicalCard> findCardByCardNoAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过身份证照这个医院下的卡
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> findCardsByIdNoAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过身份证找卡(同一个分院，同一张身份证只能绑定一个诊疗卡)
	 * 
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndHospitalCodeAndOwnership(MedicalCard medicalCard);

	/**
	 * 根据openid,ownership, hospitalCode找某个医院下用户绑的某些卡（按与本人关系区分）
	 * 
	 * @param medicalCard
	 * @return
	 */
	public MedicalCard findCardByIdNoAndHospitalCodeAndOpenId(MedicalCard medicalCard);

	/**
	 * 得到子表的所有的绑卡信息
	 * 
	 * @return
	 */
	public List<SimpleMedicalCard> findAllForCache(String hashTableName);

	/**
	 * 根据openId查询是否有绑卡记录
	 * 
	 * @param openId
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenId(Map<String, Object> params);

	public List<MedicalCard> findCardsByOpenIdForEasyHealth(Map<String, Object> params);

	/**
	 * 根据medicalCardId 在指定的子表中查询就诊卡
	 * @param medicalCardId
	 * @param hashTableName
	 * @return
	 */
	public MedicalCard findByIdFromHashTable(String medicalCardId, String hashTableName);

	public List<MedicalCard> findCardByCardNoAndHospitalCodeAndOpenId(MedicalCard medicalCard);

	/**
	 * 根据诊疗卡号查找openId
	 * @param params
	 * @return
	 */
	public List<MedicalCard> findOpenIdByCardNo(Map<String, Object> params);

	/**
	 * 根据参数查询绑卡用于后台解绑
	 * hospitalId:医院Id 
	 * name：姓名 
	 * cardNo：卡号
	 * idNo：绑卡证件号
	 * mobile：电话号码
	 * @param params
	 * @return
	 */
	public abstract List<MedicalCard> findCardForParams(Map<String, Object> params);

	/**
	 * 更新住院信息
	 * @param medicalCard
	 */
	public void updateAdmissionNo(MedicalCard medicalCard);

	/**
	 * 通过openId找到这个人绑的所有的卡
	 * @param openId
	 * @param hashTableName
	 * @return
	 */
	public List<MedicalCard> findAllCardsByOpenId(String openId, String hashTableName);

	/**
	 * 通过卡号,openId,hospitalId找到卡
	 * @param hospitalId
	 * @param openId
	 * @param cardNo
	 * @param hashTableName
	 * @return
	 */
	public List<MedicalCard> findCardByHospitalIdAndOpenIdAndCardNo(String hospitalId, String openId, String cardNo, String hashTableName);

	/**
	 * 通过openId, hospitalCode, 证件号码找卡
	 * @param openId
	 * @param hospitalCode
	 * @param idNo
	 * @return
	 */
	public MedicalCard findCardByOpenIdAndHospitalCodeAndIdNo(String openId, String hashTableName, String hospitalCode, String idType, String idNo);

	/**
	 * 清空这个家人的所有就诊卡信息
	 * @param openId
	 * @param hashTableName
	 * @param idNo
	 */
	public void removeCardsByFamilyId(String openId, String hashTableName, String familyId);

	/**
	 * 找个某个人的所有绑卡信息
	 * @param openId
	 * @param hashTableName
	 * @param familyId
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndFamilyId(String openId, String hashTableName, String familyId);

	/**
	 * 获取家人在这个医院的绑卡数据
	 * @param openId
	 * @param hashTableName
	 * @param familyId
	 * @param hospitalCode
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndFamilyIdAndHospitalCode(String openId, String hashTableName, String familyId, String hospitalCode);

	/**
	 * 获取该用户在该医院的该卡号是否被绑定了。
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> findCardByCardNoAndHospitalCodeAndOpenIdAndFamilyId(MedicalCard medicalCard);

	/**
	 * 更新familyId
	 * @param medicalCard
	 * @return
	 */
	public void updateFamilyId(MedicalCard medicalCard);
}
