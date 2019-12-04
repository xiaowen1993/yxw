package com.yxw.commons.entity.mobile.biz.clinic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.MessagePushParamsVo;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;

public class ClinicRecord extends Record {

	private static final long serialVersionUID = 2667850994899983660L;

	/**
	 * 异常处理时间间隔数组
	 * 默认时间间隔为[0s,30s,1.5min,3min,5min,10min,1h,2h,6h,15h]
	 */
	protected static int[] HANDLE_DELAYS = { 0, 30000, 90000, 180000, 300000, 600000, 3600000, 7200000, 21600000, 54000000 };

	private String branchHospitalName;

	/**
	 * 本次支付的His门诊编号
	 */
	private String mzFeeId;

	/**
	 * 本次支付的His的订单编号，用"," 隔开
	 */
	private String payIds;

	/**
	 * 总金额
	 */
	private String totalFee;

	/**
	 * 医保金额
	 */
	private String medicareFee;

	/**
	 * 实付金额
	 */
	private String payFee;

	/**
	 * 条形码(字符串)
	 */
	private String barcode;

	/**
	 * 是否支付医保(存放是否执行医保流程的配置，不入库)
	 */
	private Integer supportMedicare;

	/**
	 * 医保类型：公费，医保，自费，农村医疗等。。。
	 */
	private String medicareType;

	/**
	 * 是否异常 1 有 0 没有 BizConstant.IS_HAPPEN_EXCEPTION_YES
	 * BizConstant.IS_HAPPEN_EXCEPTION_NO
	 */
	private Integer isException;

	/**
	 * 是否处理成功 HANDLED_EXCEPTION_SUCCESS = 1 HANDLED_EXCEPTION_FAILURE = 0
	 */
	private Integer isHandleSuccess;

	/**
	 * 处理次数 大于3次的异常不再处理
	 */
	private Integer handleCount;

	/**
	 * 处理日志
	 */
	private String handleLog;

	private String statusLabel;

	/**
	 * 支付超时时间
	 */
	private Long payTimeoutTime;

	/**
	 * 是否有效的订单
	 */
	private Integer isValid;

	/**
	 * 订单生成时间
	 */
	private Long createTime;

	/**
	 * 医院返回的取药等信息
	 */
	private String hisMessage;

	/**
	 * 医院返回的收据号
	 */
	private String receiptNum;

	/**
	 * 医生名称
	 */
	private String doctorName;

	/**
	 * 科室名称
	 */
	private String deptName;

	/**
	 * 真实的原金额
	 */
	private String realYuanFee;

	/**
	 * 退费金额（支持部分退费）
	 */
	private String refundFee;

	/**
	 * 订单状态 (未缴费，已缴费，缴费失败，缴费异常)
	 */
	private Integer clinicStatus;

	/**
	 * 查询的开始时间（只在已缴费列表部分有）
	 */
	private Long queryBeginTime;

	/**
	 * 查询的结束时间
	 */
	private Long queryEndTime;

	/**
	 * 交易类型 1：支付 2：退费 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	private String tradeType;

	/**
	 * 交易时间 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	private String tradeTime;

	private String clinicTime;

	/**
	 * 社保结算门诊流水号
	 */
	private String sSClinicNo;

	/**
	 * 社保结算单据号
	 */
	private String sSBillNumber;

	/**
	 * 是否允许医保结算
	 */
	private String canUseInsurance;

	/**
	 * 处方类型
	 */
	private String recipeType;

	/**
	 * 处方ID号
	 */
	private String recipeId;

	/**
	 * 最大处方号 
	 */
	private String mzBillId;

	/**
	 * 退款流水号
	 */
	private String cancelSerialNo;

	/**
	 * 退款单据号
	 */
	private String cancelBillNo;

	/**
	 * 门诊类别
	 */
	private String mzCategory;

	/**
	 * 接诊科室代码
	 */
	private String deptCode;

	/**
	 * 接诊医生代码
	 */
	private String doctorCode;

	/**
	 * 自费金额
	 */
	private String payAmout;

	/**
	 * 个人账户结算金额
	 */
	private String accountAmout;

	/**
	 * 统筹基金结算金额
	 */
	private String medicareAmount;

	/**
	 * 总金额
	 */
	private String totalAmout;

	/**
	 * 记账合计
	 */
	private String insuranceAmout;

	/**
	 * 医疗机构编码【医院编号】
	 * */
	private String hospOrgCode;

	/**
	 * 开单时间
	 * */
	private String prescribeTime;

	/**
	 * 处方json
	 * */
	private String prescription;

	/**
	 * 用户身份证
	 */
	private String idCardNo;

	/**
	 * 医保返回支付串
	 * */
	private String ssItems;

	/**
	 * 是否已理赔，1：已理赔  0：未理赔
	 */
	private Integer isClaim;

	public String getPayIds() {
		return payIds;
	}

	public void setPayIds(String payIds) {
		this.payIds = payIds;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getMedicareFee() {
		return medicareFee;
	}

	public void setMedicareFee(String medicareFee) {
		this.medicareFee = medicareFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public Integer getIsHandleSuccess() {
		return isHandleSuccess;
	}

	public void setIsHandleSuccess(Integer isHandleSuccess) {
		this.isHandleSuccess = isHandleSuccess;
	}

	public Integer getHandleCount() {
		return handleCount;
	}

	public void setHandleCount(Integer handleCount) {
		this.handleCount = handleCount;
	}

	public String getHandleLog() {
		return handleLog;
	}

	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}

	public Long getPayTimeoutTime() {
		return payTimeoutTime;
	}

	public void setPayTimeoutTime(Long payTimeoutTime) {
		this.payTimeoutTime = payTimeoutTime;
	}

	@Override
	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getClinicRecordHashTable(this.openId, true);
		}
		return hashTableName;
	}

	@Override
	public String getPayDate() {
		if (payTime != null) {
			payDate = BizConstant.YYYYMMDD.format(new Date(payTime));
		} else if (createTime != null) {
			payDate = BizConstant.YYYYMMDD.format(new Date(createTime));
		} else {
			payDate = "";
		}
		return payDate;
	}

	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);
		params.setOpenId(this.openId);

		params.setPlatformType(this.appCode);

		@SuppressWarnings("unchecked")
		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);

		// 跳到缴费明细部分的跳转需要参数
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.ORDERNO_KEY).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.orderNo).concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.OPENID)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.openId).concat(BizConstant.URL_PARAM_CHAR_CONCAT)
						.concat(BizConstant.APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appId)
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.APPCODE).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.appCode);

		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);

		return params;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	@Override
	public Integer getPayTotalFee() {
		if (StringUtils.isNotBlank(payFee)) {
			return Integer.parseInt(payFee);
		} else {
			return 0;
		}
	}

	@Override
	public Integer getRefundTotalFee() {
		if (StringUtils.isNotBlank(this.refundFee)) {
			return Integer.parseInt(this.refundFee);
		} else {
			if (StringUtils.isNotBlank(payFee)) {
				return Integer.parseInt(payFee);
			} else {
				return 0;
			}
		}
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getHisMessage() {
		if (StringUtils.isBlank(hisMessage) || "null".equals(hisMessage)) {
			this.hisMessage = "";
		}
		return hisMessage;
	}

	public void setHisMessage(String hisMessage) {
		this.hisMessage = hisMessage;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public Integer getSupportMedicare() {
		return supportMedicare;
	}

	public void setSupportMedicare(Integer supportMedicare) {
		this.supportMedicare = supportMedicare;
	}

	public String getMedicareType() {
		return medicareType;
	}

	public void setMedicareType(String medicareType) {
		this.medicareType = medicareType;
	}

	public String getReceiptNum() {
		if (StringUtils.isBlank(receiptNum) || "null".equals(receiptNum)) {
			receiptNum = "";
		}
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Integer getClinicStatus() {
		return clinicStatus;
	}

	public void setClinicStatus(Integer clinicStatus) {
		this.clinicStatus = clinicStatus;
	}

	public Long getQueryBeginTime() {
		return queryBeginTime;
	}

	public void setQueryBeginTime(Long queryBeginTime) {
		this.queryBeginTime = queryBeginTime;
	}

	public Long getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(Long queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public ClinicPayVo convertToClinicPayVo() {
		ClinicPayVo vo = new ClinicPayVo();
		try {
			BeanUtils.copyProperties(vo, this);
			vo.setHisOrdNum(this.getHisOrdNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public String getRealYuanFee() {
		if (this.payFee != null) {
			BigDecimal source = new BigDecimal(Integer.parseInt(this.payFee));
			BigDecimal divisor = new BigDecimal(100);
			realYuanFee = source.divide(divisor).toString();
		}
		return realYuanFee;
	}

	public void setRealYuanFee(String realYuanFee) {
		this.realYuanFee = realYuanFee;
	}

	public String getDoctorName() {
		if (StringUtils.isBlank(doctorName) || "null".equals(doctorName)) {
			doctorName = "";
		}
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDeptName() {
		if (StringUtils.isBlank(deptName) || "null".equals(deptName)) {
			deptName = "";
		}
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getStatusLabel() {
		switch (this.clinicStatus) {
		case ClinicConstant.STATE_NO_PAY:
			statusLabel = "未缴费";
			break;
		case ClinicConstant.STATE_PAY_SUCCESS:
			statusLabel = "缴费成功";
			break;
		case ClinicConstant.STATE_PART_REFUND:
			statusLabel = "部分退费";
			break;
		case ClinicConstant.STATE_PART_REFUND_SUCCESS:
			statusLabel = "部分退费成功";
			break;
		case ClinicConstant.STATE_PART_REFUND_FAIL:
			statusLabel = "部分退费失败";
			break;
		case ClinicConstant.STATE_PERSON_HANDLE_SUCCESS:
			statusLabel = "退费成功";
			break;
		case ClinicConstant.STATE_PERSON_HANDLE_EXCEPTION:
			statusLabel = "退费失败";
			break;
		default:
			statusLabel = "缴费失败";
			break;
		}
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(String clinicTime) {
		this.clinicTime = clinicTime;
	}

	public String getsSClinicNo() {
		return sSClinicNo;
	}

	public void setsSClinicNo(String sSClinicNo) {
		this.sSClinicNo = sSClinicNo;
	}

	public String getsSBillNumber() {
		return sSBillNumber;
	}

	public void setsSBillNumber(String sSBillNumber) {
		this.sSBillNumber = sSBillNumber;
	}

	public String getCanUseInsurance() {
		return canUseInsurance;
	}

	public void setCanUseInsurance(String canUseInsurance) {
		this.canUseInsurance = canUseInsurance;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getMzBillId() {
		return mzBillId;
	}

	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
	}

	public String getCancelSerialNo() {
		return cancelSerialNo;
	}

	public void setCancelSerialNo(String cancelSerialNo) {
		this.cancelSerialNo = cancelSerialNo;
	}

	public String getCancelBillNo() {
		return cancelBillNo;
	}

	public void setCancelBillNo(String cancelBillNo) {
		this.cancelBillNo = cancelBillNo;
	}

	public String getMzCategory() {
		return mzCategory;
	}

	public void setMzCategory(String mzCategory) {
		this.mzCategory = mzCategory;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(String payAmout) {
		this.payAmout = payAmout;
	}

	public String getAccountAmout() {
		return accountAmout;
	}

	public void setAccountAmout(String accountAmout) {
		this.accountAmout = accountAmout;
	}

	public String getMedicareAmount() {
		return medicareAmount;
	}

	public void setMedicareAmount(String medicareAmount) {
		this.medicareAmount = medicareAmount;
	}

	public String getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getInsuranceAmout() {
		return insuranceAmout;
	}

	public void setInsuranceAmout(String insuranceAmout) {
		this.insuranceAmout = insuranceAmout;
	}

	public String getHospOrgCode() {
		return hospOrgCode;
	}

	public void setHospOrgCode(String hospOrgCode) {
		this.hospOrgCode = hospOrgCode;
	}

	public String getPrescribeTime() {
		return prescribeTime;
	}

	public void setPrescribeTime(String prescribeTime) {
		this.prescribeTime = prescribeTime;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getSsItems() {
		return ssItems;
	}

	public void setSsItems(String ssItems) {
		this.ssItems = ssItems;
	}

	public Integer getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}

	public OrdersQueryResult convertToOrdersQueryResult() {
		OrdersQueryResult result = new OrdersQueryResult();
		result.setTradeTime(this.getTradeTime());
		result.setTradeType(this.getTradeType());
		result.setOrderMode(String.valueOf(BizConstant.BIZ_TYPE_CLINIC));
		result.setTradeMode(this.getTradeMode());
		result.setAgtPayOrdNum(this.getAgtOrdNum());
		result.setAgtRefundOrdNum(this.getAgtRefundOrdNum());
		result.setHisPayOrdNum(this.getHisOrdNo());
		result.setHisRefundOrdNum(this.getRefundHisOrdNo());
		result.setPsPayOrdNum(this.getOrderNo());
		result.setPsRefundOrdNum(this.getRefundOrderNo());
		result.setRefundTotalFee(String.valueOf(this.getRefundTotalFee()));
		result.setPayTotalFee("");
		return result;
	}

	public Long getNextFireTime() {
		return this.updateTime
				+ HANDLE_DELAYS[this.handleCount == null ? 0 : ( this.handleCount >= HANDLE_DELAYS.length ? HANDLE_DELAYS.length - 1
						: this.handleCount )];
	}

	public static int getMaxHandleCount() {
		return HANDLE_DELAYS.length;
	}

	public static int[] getHandleDelays() {
		return HANDLE_DELAYS;
	}

}
