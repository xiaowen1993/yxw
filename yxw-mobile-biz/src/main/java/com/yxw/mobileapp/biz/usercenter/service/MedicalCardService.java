package com.yxw.mobileapp.biz.usercenter.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.vo.cache.CommonParamsVo;
import com.yxw.framework.mvc.service.BaseService;

public interface MedicalCardService extends BaseService<MedicalCard, String> {
	/**
	 * 查询绑定的诊疗卡
	 * 
	 * @param openId
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过Id和OpenId查找就诊卡
	 * 
	 * @param id
	 * @param openId
	 * @return
	 */
	public MedicalCard findByIdAndOpenId(String id, String openId);

	/**
	 * 查询绑卡数
	 * 
	 * @param openId
	 * @return
	 */
	public int getCardCountByOpenIdAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 检测是否绑定本人就诊卡
	 * 
	 * @param openId
	 * @param branchId
	 * @return
	 */
	public boolean checkSelfCardExist(MedicalCard medicalCard);

	/**
	 * 解绑
	 * 
	 * @param medicalCard
	 * @return
	 */
	public Map<String, Object> unBindCard(MedicalCard medicalCard);

	/**
	 * 通过就诊卡号查找卡(同一分院，就诊卡号唯一)
	 * 
	 * @param cardNo
	 * @param branchId
	 * @return
	 */
	public List<MedicalCard> findCardByCardNoAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 通过证件号码查找卡(同一分院，就诊卡号唯一)
	 * 
	 * @param cardNo
	 * @param branchId
	 * @return
	 */
	public List<MedicalCard> findCardByIdNoAndHospitalCode(MedicalCard medicalCard);

	/**
	 * 建档
	 * 
	 * @param medicalCard
	 * @return
	 */
	public Map<String, Object> createCard(MedicalCard medicalCard);

	/**
	 * 通过身份证找卡(同一个分院，同一张身份证只能绑定一个诊疗卡)
	 * 
	 * @param cardNo
	 * @param branchId
	 * @return
	 */
	public MedicalCard findCardByIdNoAndHospitalCodeAndOpenId(MedicalCard medicalCard);

	/**
	 * 自动带出诊疗卡信息
	 * 
	 * @param medicalCard
	 * @return
	 */
	public Map<String, String> autoGetCardNo(MedicalCard medicalCard);

	/**
	 * 查询绑定的某类型卡(openId, state=1, hospitalCode, ownership)
	 * 
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> findCardsByOwnership(MedicalCard medicalCard);

	/**
	 * 根据openId查询是否有绑卡记录
	 * 
	 * @param openId
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenId(Map<String, Object> params);

	/**
	 * 根据参数查询绑卡
	 * 
	 * @return
	 */
	public List<MedicalCard> findCardForParams(Map<String, Object> params);

	/**
	 * 更新住院号信息
	 * 
	 * @param medicalCard
	 * @return
	 */
	public Map<String, Object> updateAdmissionNo(MedicalCard medicalCard);

	/**
	 * 判断是否能够进行绑卡
	 * 
	 * @param medicalCard
	 * @param maxNum
	 * @return
	 */
	public Map<String, Object> whetherCanBindCard(MedicalCard medicalCard, int maxNum);

	/**
	 * 健康易自动绑卡
	 * 
	 * @param medicalCard
	 */
	public String autoBindCardForEasyHealth(MedicalCard medicalCard);

	/**
	 * 自动绑定本人的卡
	 * 
	 * @param vo
	 * @param user
	 */
	public String autoBindSelfCardForEasyHealth(CommonParamsVo vo, String userName, String userCardNo, String userModbile);

	/**
	 * 找出所有这个账号的所有绑卡数据
	 * 
	 * @param openId
	 */
	public List<MedicalCard> findAllCardsByOpenId(String openId);

	/**
	 * 通过医院编号，openId，卡号找到一张卡（有多张则优先取绑定的，没有绑定的则取第一张）
	 * 
	 * @param hospitalId
	 * @param openId
	 * @param cardNo
	 * @return
	 */
	public MedicalCard findCardByHospitalIdAndOpenIdAndCardNo(String hospitalId, String openId, String cardNo);

	/**
	 * 就诊人后台管理系统 查询方法
	 * 
	 * @return
	 */
	public List<MedicalCard> findCardSuperviseParams(Map<String, Object> params);

	/**
	 * 根据参数 修改绑卡状态
	 * 
	 * @param medicalCard
	 * @return
	 */

	public Map<String, Object> unBindCardByState(MedicalCard medicalCard);

	/**
	 * 通过身份证、OpenId、医院代码，找本地有绑定的卡
	 * @param openId
	 * @param HospitalCode
	 * @param idNo
	 * @return
	 */
	public MedicalCard findCardByOpenIdAndHospitalCodeAndIdNo(String openId, String HospitalCode, String idType, String idNo);

	/**
	 * 健康易绑卡（新绑卡，可以多个人绑定同一张卡, 建档完后去绑卡）
	 * @param medicalCard
	 * @param maxNum
	 * @return
	 */
	public Map<String, Object> syncBindCardForEasyHealth(MedicalCard medicalCard);

	/**
	 * 健康易绑卡 (走绑卡接口)
	 * @param medicalCard
	 * @return
	 */
	public Map<String, Object> bindCard(MedicalCard medicalCard, String verifyConditionType);

	/**
	 * 清空就诊卡
	 * @param openId
	 * @param idNo
	 */
	public void removeCards(String openId, String familyId);

	/**
	 * 通过openId, familyId找出这个人的所有卡资料
	 * @param openId
	 * @param familyId
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndFamilyId(String openId, String familyId);

	/**
	 * 找出这个用户在这个医院所有的绑卡信息 
	 * @param openid
	 * @param familyId
	 * @param hospitalCode
	 * @return
	 */
	public List<MedicalCard> findCardsByOpenIdAndFamilyIdAndHospitalCode(String openId, String familyId, String hospitalCode);

	/**
	 * 找出该平台+医院下该就诊卡有没有被绑定
	 * @param cardNo
	 * @param appCode
	 * @param hospitalCode
	 * @return
	 */
	public List<MedicalCard> findCardsByCardNoAndAppCodeAndHospitalCode(String cardNo, String appCode, String hospitalCode);

	/**
	 * 更新familyId
	 * @param medicalCard
	 * @return
	 */
	public void updateFamilyId(MedicalCard medicalCard);

	//	public String findCardByCardNoAndHospitalIdAndPlatformType(String cardNo, String hospitalId, String platformType);
}
