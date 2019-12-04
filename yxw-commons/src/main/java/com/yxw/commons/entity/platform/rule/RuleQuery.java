package com.yxw.commons.entity.platform.rule;

import com.yxw.base.entity.BaseEntity;

/**
 * 报告查询
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleQuery
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-9-10
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleQuery extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4901189762344348564L;

	private String hospitalId;

	/**
	 * 报告可查询的类别  1：检验    2：检查     3：体检  多个值,号隔开
	 */
	private String reportCouldQueryType;
	private String[] reportCouldQueryTypeArray;

	/**
	 * 报告详情的界面样式  1：数据样式        2：图形样式
	 */
	private Integer reportViewCssType;

	/**
	 * 预约记录可查询的类别  1：预约挂号     2：预约体检    3：预约加床 多个值逗号隔开
	 */
	private String bespeakRecordQueryType;
	private String[] bespeakRecordQueryTypeArray;

	/**
	 * 缴费记录可查询的类别 1：门诊缴费    2：住院押金补缴  多个值逗号隔开
	 */
	private String paymentRecordQueryType;
	private String[] paymentRecordQueryTypeArray;

	/**
	 * 报告记录允许查询时长 单位月
	 */
	private Integer reportRecordQueryMaxMonths;

	/**
	 * 预约记录允许查询时长 单位月
	 */
	private Integer bespeakRecordQueryMaxMonths;

	/**
	 * 缴费记录允许查询时长 单位月
	 */
	private Integer paymentRecordQueryMaxMonths;

	/**
	 * 一日清单允许查询时长
	 */
	private Integer oneDayRecordMaxMonths;

	/**
	 * 排队类型 1、门诊候诊 2、检查检验体检 3、取药排队 4、报告出具进度（4暂时没有接口可以对接）
	 */
	private String queueType;

	private String[] queueTypeArray;

	/**
	 * 是否显示条形码 1显示，0不显示
	 */
	private Integer isShowBarcode;

	/**
	 * 1、读取接口。2、就诊卡号
	 */
	private Integer barcodeData;

	/**
	 * 码制
	 * 1、EAN码 (ean8)
	 * 2、UPC码  (upc)
	 * 3、39码 (code39)
	 * 4、128码 (code128)
	 * 5、库德巴码 (codabar)
	 */
	private Integer barcodeStyle;

	/**
	 * 住院一日清单是否有大项 1:是 2:否
	 */
	private Integer oneDayRecordIsItems;

	/**
	 * 显示门诊待缴费详情方式  0 按明细方式显示, 1 按费别方式显示
	 */
	private Integer showClinicPaidDetailStyle;

	/**
	 * 检验报告详情样式选择
	 */
	private Integer choicesInspectionReportStyle;

	/**
	 * 温馨提示
	 */
	private String tip;

	/**
	 * 是否所有类型的报告查询共享次数限制 1:是 2:否
	 */
	private Integer shareQueryTimesLimit;

	/**
	 * 每日最大查询次数
	 */
	private Integer maxQueryTimesPerDay;

	/**
	 * 达到每日最大查询次数提示语
	 */
	private String reachMaxQueryTimesPerDayTip;

	/**
	 * 每周最大查询次数
	 */
	private Integer maxQueryTimesPerWeek;

	/**
	 * 达到每周最大查询次数提示语
	 */
	private String reachMaxQueryTimesPerWeekTip;
	
	/**
	 * 报告查询验证码有效时长(单位：秒)
	 */
	private Integer captchaEffectiveTime;

	public RuleQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RuleQuery(String hospitalId) {
		super();
		this.hospitalId = hospitalId;
	}


	public RuleQuery(String hospitalId, String reportCouldQueryType, String[] reportCouldQueryTypeArray, Integer reportViewCssType,
			String bespeakRecordQueryType, String[] bespeakRecordQueryTypeArray, String paymentRecordQueryType, String[] paymentRecordQueryTypeArray,
			Integer reportRecordQueryMaxMonths, Integer bespeakRecordQueryMaxMonths, Integer paymentRecordQueryMaxMonths, Integer oneDayRecordMaxMonths,
			String queueType, String[] queueTypeArray, Integer isShowBarcode, Integer barcodeData, Integer barcodeStyle, Integer oneDayRecordIsItems,
			Integer showClinicPaidDetailStyle, Integer choicesInspectionReportStyle, String tip, Integer shareQueryTimesLimit, Integer maxQueryTimesPerDay,
			String reachMaxQueryTimesPerDayTip, Integer maxQueryTimesPerWeek, String reachMaxQueryTimesPerWeekTip, Integer captchaEffectiveTime) {
		super();
		this.hospitalId = hospitalId;
		this.reportCouldQueryType = reportCouldQueryType;
		this.reportCouldQueryTypeArray = reportCouldQueryTypeArray;
		this.reportViewCssType = reportViewCssType;
		this.bespeakRecordQueryType = bespeakRecordQueryType;
		this.bespeakRecordQueryTypeArray = bespeakRecordQueryTypeArray;
		this.paymentRecordQueryType = paymentRecordQueryType;
		this.paymentRecordQueryTypeArray = paymentRecordQueryTypeArray;
		this.reportRecordQueryMaxMonths = reportRecordQueryMaxMonths;
		this.bespeakRecordQueryMaxMonths = bespeakRecordQueryMaxMonths;
		this.paymentRecordQueryMaxMonths = paymentRecordQueryMaxMonths;
		this.oneDayRecordMaxMonths = oneDayRecordMaxMonths;
		this.queueType = queueType;
		this.queueTypeArray = queueTypeArray;
		this.isShowBarcode = isShowBarcode;
		this.barcodeData = barcodeData;
		this.barcodeStyle = barcodeStyle;
		this.oneDayRecordIsItems = oneDayRecordIsItems;
		this.showClinicPaidDetailStyle = showClinicPaidDetailStyle;
		this.choicesInspectionReportStyle = choicesInspectionReportStyle;
		this.tip = tip;
		this.shareQueryTimesLimit = shareQueryTimesLimit;
		this.maxQueryTimesPerDay = maxQueryTimesPerDay;
		this.reachMaxQueryTimesPerDayTip = reachMaxQueryTimesPerDayTip;
		this.maxQueryTimesPerWeek = maxQueryTimesPerWeek;
		this.reachMaxQueryTimesPerWeekTip = reachMaxQueryTimesPerWeekTip;
		this.captchaEffectiveTime = captchaEffectiveTime;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	/**
	 * 报告可查询的类别  1：检验    2：检查     3：体检  多个值,号隔开
	 * @return
	 */
	public String getReportCouldQueryType() {
		return reportCouldQueryType;
	}

	public void setReportCouldQueryType(String reportCouldQueryType) {
		this.reportCouldQueryType = reportCouldQueryType == null ? null : reportCouldQueryType.trim();
	}

	/**
	 * 报告详情的界面样式  1：数据样式        2：图形样式
	 * @return
	 */
	public Integer getReportViewCssType() {
		return reportViewCssType;
	}

	public void setReportViewCssType(Integer reportViewCssType) {
		this.reportViewCssType = reportViewCssType;
	}

	/**
	 * 预约记录可查询的类别  1：预约挂号     2：预约体检    3：预约加床 多个值逗号隔开
	 * @return
	 */
	public String getBespeakRecordQueryType() {
		return bespeakRecordQueryType;
	}

	public void setBespeakRecordQueryType(String bespeakRecordQueryType) {
		this.bespeakRecordQueryType = bespeakRecordQueryType == null ? null : bespeakRecordQueryType.trim();
	}

	/**
	 * 缴费记录可查询的类别 1：门诊缴费    2：住院押金补缴  多个值逗号隔开
	 * @return
	 */
	public String getPaymentRecordQueryType() {
		return paymentRecordQueryType;
	}

	public void setPaymentRecordQueryType(String paymentRecordQueryType) {
		this.paymentRecordQueryType = paymentRecordQueryType == null ? null : paymentRecordQueryType.trim();
	}

	/**
	 * 报告记录允许查询时长 单位月
	 * @return
	 */
	public Integer getReportRecordQueryMaxMonths() {
		return reportRecordQueryMaxMonths;
	}

	public void setReportRecordQueryMaxMonths(Integer reportRecordQueryMaxMonths) {
		this.reportRecordQueryMaxMonths = reportRecordQueryMaxMonths;
	}

	/**
	 * 预约记录允许查询时长 单位月
	 * @return
	 */
	public Integer getBespeakRecordQueryMaxMonths() {
		return bespeakRecordQueryMaxMonths;
	}

	public void setBespeakRecordQueryMaxMonths(Integer bespeakRecordQueryMaxMonths) {
		this.bespeakRecordQueryMaxMonths = bespeakRecordQueryMaxMonths;
	}

	/**
	 *  缴费记录允许查询时长 单位月
	 * @return
	 */
	public Integer getPaymentRecordQueryMaxMonths() {
		return paymentRecordQueryMaxMonths;
	}

	public void setPaymentRecordQueryMaxMonths(Integer paymentRecordQueryMaxMonths) {
		this.paymentRecordQueryMaxMonths = paymentRecordQueryMaxMonths;
	}

	/**
	 * 一日清单允许查询时长
	 * @return
	 */
	public Integer getOneDayRecordMaxMonths() {
		return oneDayRecordMaxMonths;
	}

	public void setOneDayRecordMaxMonths(Integer oneDayRecordMaxMonths) {
		this.oneDayRecordMaxMonths = oneDayRecordMaxMonths;
	}

	public String[] getReportCouldQueryTypeArray() {
		return reportCouldQueryTypeArray;
	}

	public void setReportCouldQueryTypeArray(String[] reportCouldQueryTypeArray) {
		this.reportCouldQueryTypeArray = reportCouldQueryTypeArray;
	}

	public String[] getBespeakRecordQueryTypeArray() {
		return bespeakRecordQueryTypeArray;
	}

	public void setBespeakRecordQueryTypeArray(String[] bespeakRecordQueryTypeArray) {
		this.bespeakRecordQueryTypeArray = bespeakRecordQueryTypeArray;
	}

	public String[] getPaymentRecordQueryTypeArray() {
		return paymentRecordQueryTypeArray;
	}

	public void setPaymentRecordQueryTypeArray(String[] paymentRecordQueryTypeArray) {
		this.paymentRecordQueryTypeArray = paymentRecordQueryTypeArray;
	}

	/**
	 * 得到默认的查询规则
	 * @param hospitalId
	 * @return
	 */
	public static RuleQuery getDefaultRule(String hospitalId) {
		RuleQuery query = new RuleQuery(hospitalId);
		query.setReportCouldQueryType("1");
		query.setReportViewCssType(1);
		query.setBespeakRecordQueryType("1");
		query.setPaymentRecordQueryType("1");
		query.setQueueType("1");
		query.setBarcodeStyle(4);
		query.setShowClinicPaidDetailStyle(0);
		query.setChoicesInspectionReportStyle(1);
		query.setCaptchaEffectiveTime(0);

		return query;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public String[] getQueueTypeArray() {
		return queueTypeArray;
	}

	public void setQueueTypeArray(String[] queueTypeArray) {
		this.queueTypeArray = queueTypeArray;
	}

	public Integer getIsShowBarcode() {
		if (this.isShowBarcode == null) {
			this.isShowBarcode = 0;
		}
		return isShowBarcode;
	}

	public void setIsShowBarcode(Integer isShowBarcode) {
		this.isShowBarcode = isShowBarcode;
	}

	public Integer getBarcodeStyle() {
		if (this.barcodeStyle == null) {
			this.barcodeStyle = 1;
		}
		return barcodeStyle;
	}

	public void setBarcodeStyle(Integer barcodeStyle) {
		this.barcodeStyle = barcodeStyle;
	}

	public Integer getBarcodeData() {
		if (this.barcodeStyle == null) {
			this.barcodeStyle = 4;
		}
		return barcodeData;
	}

	public void setBarcodeData(Integer barcodeData) {
		this.barcodeData = barcodeData;
	}

	/**
	 * 住院一日清单是否有大项 1:是 2:否
	 * @return
	 */
	public Integer getOneDayRecordIsItems() {
		return oneDayRecordIsItems;
	}

	public void setOneDayRecordIsItems(Integer oneDayRecordIsItems) {
		this.oneDayRecordIsItems = oneDayRecordIsItems;
	}

	/**
	 * @return the showClinicPaidDetailStyle
	 */
	public Integer getShowClinicPaidDetailStyle() {
		return showClinicPaidDetailStyle;
	}

	/**
	 * @param showClinicPaidDetailStyle the showClinicPaidDetailStyle to set
	 */
	public void setShowClinicPaidDetailStyle(Integer showClinicPaidDetailStyle) {
		this.showClinicPaidDetailStyle = showClinicPaidDetailStyle;
	}

	/**
	 * @return the choicesInspectionReportStyle
	 */
	public Integer getChoicesInspectionReportStyle() {
		return choicesInspectionReportStyle;
	}

	/**
	 * @param choicesInspectionReportStyle the choicesInspectionReportStyle to set
	 */
	public void setChoicesInspectionReportStyle(Integer choicesInspectionReportStyle) {
		this.choicesInspectionReportStyle = choicesInspectionReportStyle;
	}

	/**
	 * @return the tip
	 */
	public String getTip() {
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	/**
	 * @return the shareQueryTimesLimit
	 */
	public Integer getShareQueryTimesLimit() {
		return shareQueryTimesLimit;
	}

	/**
	 * @param shareQueryTimesLimit the shareQueryTimesLimit to set
	 */
	public void setShareQueryTimesLimit(Integer shareQueryTimesLimit) {
		this.shareQueryTimesLimit = shareQueryTimesLimit;
	}

	/**
	 * @return the maxQueryTimesPerDay
	 */
	public Integer getMaxQueryTimesPerDay() {
		return maxQueryTimesPerDay;
	}

	/**
	 * @param maxQueryTimesPerDay the maxQueryTimesPerDay to set
	 */
	public void setMaxQueryTimesPerDay(Integer maxQueryTimesPerDay) {
		this.maxQueryTimesPerDay = maxQueryTimesPerDay;
	}

	/**
	 * @return the reachMaxQueryTimesPerDayTip
	 */
	public String getReachMaxQueryTimesPerDayTip() {
		return reachMaxQueryTimesPerDayTip;
	}

	/**
	 * @param reachMaxQueryTimesPerDayTip the reachMaxQueryTimesPerDayTip to set
	 */
	public void setReachMaxQueryTimesPerDayTip(String reachMaxQueryTimesPerDayTip) {
		this.reachMaxQueryTimesPerDayTip = reachMaxQueryTimesPerDayTip;
	}

	/**
	 * @return the maxQueryTimesPerWeek
	 */
	public Integer getMaxQueryTimesPerWeek() {
		return maxQueryTimesPerWeek;
	}

	/**
	 * @param maxQueryTimesPerWeek the maxQueryTimesPerWeek to set
	 */
	public void setMaxQueryTimesPerWeek(Integer maxQueryTimesPerWeek) {
		this.maxQueryTimesPerWeek = maxQueryTimesPerWeek;
	}

	/**
	 * @return the reachMaxQueryTimesPerWeekTip
	 */
	public String getReachMaxQueryTimesPerWeekTip() {
		return reachMaxQueryTimesPerWeekTip;
	}

	/**
	 * @param reachMaxQueryTimesPerWeekTip the reachMaxQueryTimesPerWeekTip to set
	 */
	public void setReachMaxQueryTimesPerWeekTip(String reachMaxQueryTimesPerWeekTip) {
		this.reachMaxQueryTimesPerWeekTip = reachMaxQueryTimesPerWeekTip;
	}

	public Integer getCaptchaEffectiveTime() {
		return captchaEffectiveTime;
	}

	public void setCaptchaEffectiveTime(Integer captchaEffectiveTime) {
		this.captchaEffectiveTime = captchaEffectiveTime;
	}

}