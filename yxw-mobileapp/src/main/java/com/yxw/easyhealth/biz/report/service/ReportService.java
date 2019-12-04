package com.yxw.easyhealth.biz.report.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;

public interface ReportService {
	/**
	 * 查询所给卡所在医院的检验列表报告数据
	 * @param medicalCard
	 * @return
	 */
	public List<Map<String, Object>> getInspectList(List<MedicalCard> medicalCards);

	/**
	 * 查询所给卡所在医院的检查列表报告数据
	 * @param medicalCards
	 * @return
	 */
	public List<Map<String, Object>> getExamineList(List<MedicalCard> medicalCards);

	/**
	 * 查询所给卡所在医院的体检列表报告数据
	 * @param medicalCards
	 * @return
	 */
	public List<Map<String, Object>> getCheckupList(List<MedicalCard> medicalCards);

	/**
	 * 查询所给卡所在医院的检验列表报告数据
	 * @param medicalCard
	 * @return
	 */
	public List<Map<String, Object>> getInspectList(List<MedicalCard> medicalCards, String beginDate);

	/**
	 * 查询所给卡所在医院的检查列表报告数据
	 * @param medicalCards
	 * @return
	 */
	public List<Map<String, Object>> getExamineList(List<MedicalCard> medicalCards, String beginDate);

	/**
	 * 查询所给卡所在医院的体检列表报告数据
	 * @param medicalCards
	 * @return
	 */
	public List<Map<String, Object>> getCheckupList(List<MedicalCard> medicalCards, String beginDate);
}
