package com.yxw.commons.entity.mobile.biz.register;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.commons.vo.MessagePushParamsVo;
import com.yxw.commons.vo.TradeParamsVo;
import com.yxw.commons.vo.mobile.biz.RegDoctor;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.interfaces.vo.register.RegRecordRequest;
import com.yxw.interfaces.vo.register.appointment.CancelRegRequest;
import com.yxw.interfaces.vo.register.appointment.OrderRegRequest;
import com.yxw.interfaces.vo.register.appointment.PayRegRequest;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;

/**
 * @Package: com.yxw.platform.register.entity
 * @ClassName: RegisterRecordBase
 * @Statement: <p>挂号记录实体类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-13
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterRecord extends Record {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5791948605594356821L;

	/**
	 * 挂号记录状态
	 * 对应RegisterConstant中的12种定义 <br>
	 * STATE_NORMAL_HAVING = 0 预约中<br>
	 * STATE_NORMAL_HAD = 1 已预约<br>
	 * STATE_NORMAL_USER_CANCEL = 2; 已取消-用户取消<br>
	 * STATE_NORMAL_PAY_TIMEOUT_CANCEL = 3 ;已取消-支付超过规定时长<br>
	 * STATE_NORMAL_STOP_CURE_CANCEL = 4;已取消-停诊取消<br>
	 * STATE_EXCEPTION_HAVING = 5预约异常(his锁号异常)<br>
	 * STATE_EXCEPTION_PAY = 6支付异常<br>
	 * STATE_EXCEPTION_CANCEL = 7 取消挂号异常 <br> -- 当班挂号不写入轮询 
	 * STATE_EXCEPTION_REFUND = 8 退费异常<br> --  不写入轮询
	 * STATE_EXCEPTION_COMMIT = 9 支付后提交his异常<br>
	 * 5  6  8  9 前端显示标示为 预约失败     预约异常处理  查询 his接口his未生产订单时的状态标志位
	 * 7   前端标示为取消中 
	 */
	protected Integer regStatus;

	/**
	 * 挂号时间
	 */
	protected Long registerTime;
	//不存入数据库
	protected String registerDate;

	/**
	 * 是否异常  1 有   0 没有
	 * BizConstant.IS_HAPPEN_EXCEPTION_YES
	 * BizConstant.IS_HAPPEN_EXCEPTION_NO
	 */
	protected Integer isException;

	/**
	 * 是否处理成功 
	 * HANDLED_SUCCESS = 1
	 * HANDLED_FAILURE = 0
	 */
	protected Integer isHandleSuccess;

	/**
	 * 处理次数  大于3次的异常不再处理
	 */
	protected Integer handleCount;

	/**
	 * 处理日志
	 */
	protected String handleLog;

	/**
	 * 挂号类型,1：预约,2：当天
	 * RegisterConstant.REG_TYPE_CUR = 2
	 * RegisterConstant.REG_TYPE_APPOINTMENT = 1
	 */
	protected Integer regType;

	/**
	 * 支付超时时间
	 */
	protected Long payTimeoutTime;

	/**
	 * 就诊时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date scheduleDate;

	/**
	 * 就诊时间  就诊开始时间-就诊结束时间  的联合字符串表示  消息推送时使用
	 */
	protected String scheduleDateLable;

	/**
	 * 分时 时段   1：上午   2：下午     3：晚上
	 * RegisterConstant.TIME_FLAG_ALL = "0"
	 * RegisterConstant.TIME_FLAG_AM = "1"
	 * RegisterConstant.TIME_FLAG_PM = "2"
	 * RegisterConstant.TIME_FLAG_NIT = "3"
	 */
	protected Integer timeFlag;

	/**
	 * 就诊开始时间
	 */
	@DateTimeFormat(pattern = "HH:mm")
	protected Date beginTime;

	/**
	 * 就诊结束时间
	 */
	@DateTimeFormat(pattern = "HH:mm")
	protected Date endTime;

	/**
	 * 挂号方式,1：为本人挂号,2：为子女挂号,3：为他人挂号
	 */
	protected Integer regPersonType;

	/**
	 * 1：微信公众号    2：支付宝服务窗  3：建康易
	 * BizConstant.MODE_TYPE_WEIXIN_VAL = 1
	 * BizConstant.MODE_TYPE_ALIPAY_VAL = 2
	 * BizConstant.MODE_TYPE_EASYHEALTH_WEIXIN_VAL = 3
	 * BizConstant.MODE_TYPE_EASYHEALTH_ALIPAY_VAL
	 * BizConstant.MODE_TYPE_EASYHEALTH_UNIONPAY_VAL
	 */
	//	protected Integer regMode;

	/**
	 * 就诊序号
	 */
	protected String serialNum;

	/**
	 * 就诊位置 ,提醒患者就诊的地方
	 */
	protected String visitLocation;

	/**
	 * 条形码
	 */
	protected String barcode;

	/**
	 * 就诊说明
	 */
	protected String visitDesc;

	/**
	 * 病情描述
	 */
	protected String diseaseDesc;

	/**
	 * 支付控制类型  1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 * BizConstant.PAYMENT_TYPE_MUST = 1
	 * BizConstant.PAYMENT_TYPE_NOT_NEED = 2
	 * BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT = 3
	 */
	protected Integer onlinePaymentType;

	/**
	 * 支付等待时长(分钟)  传值不存数据库
	 */
	protected Integer waitPayTime;

	/**
	 * 是否是有效的挂号记录  比如挂号时锁号异常后轮询处理变为取消状态的记录为无效挂号记录
	 * RegisterConstant.RECORD_IS_VALID_TRUE = 1
	 * RegisterConstant.RECORD_IS_VALID_FALSE = 0
	 */
	protected Integer isValid;

	/**
	 * 科室代码
	 */
	protected String deptCode;

	/**
	 * 科室名称
	 */
	protected String deptName;

	/**
	 * 医生类别 ,0 出诊医生, 1:专家医生,2:专科医生,3:专科
	 */
	protected Integer category;

	/**
	 * 医生编码
	 */
	protected String doctorCode;

	/**
	 * 医生姓名
	 */
	protected String doctorName;

	/**
	 * 医生职称
	 */
	protected String doctorTitle;

	/**
	 *  挂号费,单位：分
	 */
	protected Integer regFee;

	/**
	 * 诊疗费 ,单位：分
	 */
	protected Integer treatFee;

	/**
	 * 优惠挂号费,单位：分
	 */
	protected Integer realRegFee;

	/**
	 * 优惠诊疗费 ,单位：分
	 */
	protected Integer realTreatFee;

	/**
	 * 退款金额  消息推送时使用
	 */
	protected Double refundFee;

	/**
	 * 暂不支付类型时  是否选择支付  页面传值对象 不存入数据库
	 */
	protected Integer isPay;

	/**
	 * 交易类型 1：支付 2：退费
	 * 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	protected String tradeType;

	/**
	 * 交易时间
	 * 2015年8月26日 20:14:54 周鉴斌 增加用于订单下载（对账）
	 */
	protected String tradeTime;

	/**
	 * 排班ID
	 */
	protected String workId;

	private String scheduleDateStr;

	/**
	 * 挂号类别
	 * */
	private String regCategory;

	/**
	 * 是否取号
	 */
	private Integer isTakeNo;

	/**
	 * 当前时刻排在前面的人数
	 */
	private String frontNum;

	/**
	 * 退款流水号
	 * */
	private String cancelSerialNo;

	/**
	 * 参保类型
	 * */
	private String medicalInsuranceType;

	/**
	 * 就诊类型
	 */
	private String mediCareType;

	/**
	 * 预约类型 	 * 预约类型
	 * 一般1
	 * 出院后复查 2
	 * 社区转诊 3
	 * 术后复查 4
	 * 产前检查 5
	 */
	private Integer appointmentType;

	/**
	 * 医疗费用支付项目集合
	 */
	private String ssItems;

	/**
	 * 第3方挂号平台名称
	 */
	private String regAgency;

	/**
	 * 退款单据号
	 * */
	private String cancelBillNo;

	/**
	 * 医疗证号
	 * */
	private String medicalCardNo;

	/**
	 * 当前看诊序号
	 */
	private String currentNum;

	/**
	 * 患者类型ID
	 */
	private String patientTypeId;

	/**
	 * 医疗机构编码【医院编号】
	 * */
	private String hospOrgCode;

	/**
	 * 门诊流水号
	 * */
	private String mzFeeId;

	/**
	 * 社保挂号登记信息串
	 * */
	private String sSInfo;

	/**
	 * 操作员编码
	 * */
	private String operatorCode;

	/**
	 * 预计就诊时间
	 */
	private String visitTime;

	/**
	 * 操作员姓名
	 * */
	private String operatorName;

	/**
	 * 电脑号
	 * */
	private String computerNo;

	/**
	 * 人群类型  本地-1  外地 -2
	 */
	private Integer populationType;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	public Integer getRegFee() {
		if (regFee == null) {
			regFee = 0;
		}
		return regFee;
	}

	public void setRegFee(Integer regFee) {
		this.regFee = regFee;
	}

	public Integer getTreatFee() {
		if (treatFee == null) {
			treatFee = 0;
		}
		return treatFee;
	}

	public void setTreatFee(Integer treatFee) {
		this.treatFee = treatFee;
	}

	public Integer getRealRegFee() {
		if (realRegFee == null) {
			realRegFee = 0;
		}
		return realRegFee;
	}

	public void setRealRegFee(Integer realRegFee) {
		this.realRegFee = realRegFee;
	}

	public Integer getRealTreatFee() {
		if (realTreatFee == null) {
			realTreatFee = 0;
		}
		return realTreatFee;
	}

	public void setRealTreatFee(Integer realTreatFee) {
		this.realTreatFee = realTreatFee;
	}

	public RegisterRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterRecord(String orderNo) {
		super(orderNo);
		// TODO Auto-generated constructor stub
	}

	public RegisterRecord(TradeParamsVo vo) {
		this.hospitalCode = vo.getHospitalCode();
		this.branchHospitalCode = vo.getBranchHospitalCode();
		this.orderNo = vo.getOrderNo();
		this.agtOrdNum = vo.getTradeNo();
		this.regType = vo.getRegType();
		//不能通过交易方式的值确定平台的值
		/*		if (StringUtils.isNotEmpty(vo.getTradeMode())) {
					this.regMode = Integer.valueOf(vo.getTradeMode());
				}*/
	}

	public RegisterRecord(Integer regStatus, Integer payStatus, Integer cardType, String cardNo, Long registerTime, String registerDate,
			Integer isException, Integer isHandleSuccess, Integer handleCount, String handleLog, Integer regType, Long payTimeoutTime,
			Date scheduleDate, Integer timeFlag, Date beginTime, Date endTime, Integer regPersonType, Integer platformMode,
			String serialNum, String visitLocation, String barcode, String visitDesc, String diseaseDesc, Integer onlinePaymentType,
			Integer waitPayTime, Integer isValid) {
		super();
		this.regStatus = regStatus;
		this.payStatus = payStatus;
		this.setCardType(cardType);
		this.setCardNo(cardNo);
		this.registerTime = registerTime;
		this.registerDate = registerDate;
		this.isException = isException;
		this.isHandleSuccess = isHandleSuccess;
		this.handleCount = handleCount;
		this.handleLog = handleLog;
		this.regType = regType;
		this.payTimeoutTime = payTimeoutTime;
		this.scheduleDate = scheduleDate;
		this.timeFlag = timeFlag;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.regPersonType = regPersonType;
		this.platformMode = platformMode;
		this.serialNum = serialNum;
		this.visitLocation = visitLocation;
		this.barcode = barcode;
		this.visitDesc = visitDesc;
		this.diseaseDesc = diseaseDesc;
		this.onlinePaymentType = onlinePaymentType;
		this.waitPayTime = waitPayTime;
		this.isValid = isValid;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Integer regStatus) {
		this.regStatus = regStatus;
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
		this.handleLog = handleLog == null ? null : handleLog.trim();
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	public Long getPayTimeoutTime() {
		return payTimeoutTime;
	}

	public void setPayTimeoutTime(Long payTimeoutTime) {
		this.payTimeoutTime = payTimeoutTime;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSerialNum() {
		return serialNum == null ? "请到医院后自行取号" : serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getVisitLocation() {
		return visitLocation == null ? "请到院后咨询前台" : visitLocation;
	}

	public void setVisitLocation(String visitLocation) {
		this.visitLocation = visitLocation == null ? null : visitLocation.trim();
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode == null ? null : barcode.trim();
	}

	public String getVisitDesc() {
		return visitDesc;
	}

	public void setVisitDesc(String visitDesc) {
		this.visitDesc = visitDesc == null ? null : visitDesc.trim();
	}

	public Integer getRegPersonType() {
		return regPersonType;
	}

	public void setRegPersonType(Integer regPersonType) {
		this.regPersonType = regPersonType;
	}

	public String getDiseaseDesc() {
		return diseaseDesc;
	}

	public void setDiseaseDesc(String diseaseDesc) {
		this.diseaseDesc = diseaseDesc;
	}

	public Integer getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(Integer timeFlag) {
		this.timeFlag = timeFlag;
	}

	public Integer getWaitPayTime() {
		if (waitPayTime == null) {
			if (payTimeoutTime != null) {
				waitPayTime = (int) ( ( payTimeoutTime - registerTime ) / 60000 );
			} else {
				waitPayTime = 0;
			}
		}
		return waitPayTime;
	}

	public void setWaitPayTime(Integer waitPayTime) {
		this.waitPayTime = waitPayTime;
	}

	public Integer getOnlinePaymentType() {
		return onlinePaymentType;
	}

	public void setOnlinePaymentType(Integer onlinePaymentType) {
		this.onlinePaymentType = onlinePaymentType;
	}

	public String getRegisterDate() {
		if (registerTime != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(registerTime);
			registerDate = BizConstant.YYYYMMDDHHMMSS.format(cal.getTime());
		}
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public void setScheduleDateLable(String scheduleDateLable) {
		this.scheduleDateLable = scheduleDateLable;
	}

	public Double getRefundFee() {
		refundFee = Double.valueOf(getRealRegFee() + getRealTreatFee()) / 100;
		return refundFee;
	}

	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

	@Override
	public Integer getPayTotalFee() {
		// TODO Auto-generated method stub
		return getRealRegFee() + getRealTreatFee();
	}

	@Override
	public Integer getRefundTotalFee() {
		// TODO Auto-generated method stub
		return getRealRegFee() + getRealTreatFee();
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
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

	public String getsSInfo() {
		return sSInfo;
	}

	public void setsSInfo(String sSInfo) {
		this.sSInfo = sSInfo;
	}

	/**
	 * 转化为订单信息
	 * @return
	 
	public RegisterOrder convertRegisterOrder() {
		RegisterOrder order = new RegisterOrder();
		order.setOrderNo(this.orderNo);
		order.setOrderNoHashVal(this.orderNoHashVal);
		order.setAppId(appId);
		order.setAppCode(appCode);

		//业务名:科室-医生名称
		String orderTitle = BizConstant.BIZ_TYPE_REGISTER_NAME.concat(":").concat(this.deptName).concat("-").concat(doctorName);
		order.setOrderTitle(orderTitle);
		order.setCardType(this.cardType);
		order.setCardNo(this.cardNo);
		order.setRegType(regType);

		order.setOpenId(this.openId);
		order.setBusinessCode(BizConstant.BIZ_TYPE_REGISTER);
		order.setBusinessName(BizConstant.BIZ_TYPE_REGISTER_NAME);

		order.setHospitalId(this.hospitalId);
		order.setBranchId(branchHospitalId);

		String agtCode = null;
		switch (regMode) {
		case BizConstant.MODE_TYPE_WEIXIN_VAL:
			agtCode = BizConstant.MODE_TYPE_WEIXIN;
			break;
		case BizConstant.MODE_TYPE_ALIPAY_VAL:
			agtCode = BizConstant.MODE_TYPE_ALIPAY;
			break;
		default:
			agtCode = BizConstant.MODE_TYPE_OTHER;
			break;
		}
		order.setHisOrdNum(hisOrdNo);
		order.setAgtCode(agtCode);
		order.setOrderTime(new Date(registerTime));
		order.setWaitPayTime(waitPayTime);
		order.setOnlinePaymentType(onlinePaymentType);
		order.setPayMode(this.regMode);
		order.setPayAmout( ( this.realRegFee == null ? 0 : this.realRegFee ) + ( this.realTreatFee == null ? 0 : this.realTreatFee ));
		return order;
	}
	*/

	public OrderRegRequest convertOrderRequest() {
		OrderRegRequest orderReq = new OrderRegRequest();
		orderReq.setRegType(this.regType == null ? RuleConstant.DEFAULT_REG_USE_TYPE : this.regType.toString());
		orderReq.setBranchCode(branchHospitalCode);
		//		orderReq.setOrderMode(this.platformMode.toString());
		orderReq.setOrderMode(BizConstant.HIS_ORDER_MODE_VAL);//his对于yxw平台，默认值：3
		orderReq.setPsOrdNum(this.orderNo);
		orderReq.setDeptCode(this.deptCode);
		orderReq.setDoctorCode(this.doctorCode);
		orderReq.setScheduleDate(BizConstant.YYYYMMDD.format(this.scheduleDate));
		orderReq.setPatCardNo(this.getCardNo());
		orderReq.setPatCardType(this.getCardType() == null ? RegisterConstant.DEFAULT_CARD_TYPE : this.getCardType().toString());
		orderReq.setRegFee(this.regFee == null ? "0" : this.regFee.toString());
		orderReq.setOrderTime(BizConstant.YYYYMMDDHHMMSS.format(new Date(registerTime)));
		orderReq.setWorkId(this.workId);
		orderReq.setIsInsurance(this.isMedicarePayMents);
		orderReq.setComputerNo(this.computerNo);
		orderReq.setMedicalCardNo(this.medicalCardNo);
		orderReq.setsSCblx(String.valueOf(this.isMedicarePayMents));
		orderReq.setsSComputerNumber(this.computerNo);
		orderReq.setOrderNo(this.receiptNum);

		if (new Integer(1).equals(this.isMedicarePayMents)) {
			orderReq.setTreatFee(String.valueOf(this.personalAccountFee) == null ? "0" : this.personalAccountFee.toString());
		} else {
			orderReq.setTreatFee(this.treatFee == null ? "0" : this.treatFee.toString());
		}
		//人群类型
		if (populationType != null) {
			orderReq.setReservedFieldOne(populationType.toString());
		}

		//预约类型
		if (appointmentType != null) {
			orderReq.setReservedFieldTwo(appointmentType.toString());
		}

		if (timeFlag != null) {
			orderReq.setTimeFlag(timeFlag.toString());
		}
		if (beginTime != null) {
			orderReq.setBeginTime(BizConstant.HHMM.format(beginTime));
		}
		if (endTime != null) {
			orderReq.setEndTime(BizConstant.HHMM.format(endTime));
		}

		if (StringUtils.isNotEmpty(mediCareType)) {
			orderReq.setReservedFieldThree(mediCareType);
		}

		if (StringUtils.isNotEmpty(patientTypeId)) {
			orderReq.setReservedFieldFour(patientTypeId);
		}
		return orderReq;
	}

	public SimpleRecord convertSimpleObj() {
		SimpleRecord record = new SimpleRecord();
		record.setId(id);
		record.setAppId(appId);
		record.setAppCode(appCode);
		record.setOpenId(openId);
		record.setHospitalCode(hospitalCode);
		record.setBranchHospitalCode(branchHospitalCode);
		record.setRegStatus(regStatus);
		record.setPlatformMode(platformMode);
		record.setHisOrdNo(hisOrdNo);
		record.setOrderNo(orderNo);
		record.setPayTimeoutTime(payTimeoutTime);
		record.setRegType(regType);
		record.setReceiptNum(receiptNum);
		record.setTradeMode(tradeMode);
		return record;
	}

	public ExceptionRecord convertExceptionObj() {
		ExceptionRecord record = new ExceptionRecord();
		BeanUtils.copyProperties(this, record);
		return record;
	}

	public RefundRegRequest covertRefundRegRequestVo() {
		RefundRegRequest request = new RefundRegRequest();
		request.setBranchCode(this.branchHospitalCode);
		request.setHisOrdNum(this.hisOrdNo);
		request.setPsOrdNum(this.orderNo);
		request.setPsRefOrdNum(this.refundOrderNo);
		request.setRefundAmout(String.valueOf( ( realRegFee == null ? 0 : realRegFee ) + ( realTreatFee == null ? 0 : realTreatFee )));
		//		request.setRefundMode(String.valueOf(ModeTypeUtil.getPlatformModeType(this.appCode)));
		request.setRefundMode(BizConstant.HIS_ORDER_MODE_VAL);
		request.setRefundTime(BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		request.setAgtRefOrdNum(agtRefundOrdNum);
		return request;
	}

	public String getScheduleDateLable() {
		if (scheduleDate != null) {
			scheduleDateLable = BizConstant.YYYYMMDD.format(scheduleDate);
		}
		if (scheduleDateLable != null && beginTime != null) {
			scheduleDateLable += " ";
			scheduleDateLable = scheduleDateLable.concat(BizConstant.HHMM.format(beginTime));
		}
		if (scheduleDateLable != null && endTime != null) {
			scheduleDateLable += "-";
			scheduleDateLable = scheduleDateLable.concat(BizConstant.HHMM.format(endTime));
		}
		return scheduleDateLable;
	}

	public RegRecordRequest convertRegRecordRequest() {
		RegRecordRequest regRecordRequest = new RegRecordRequest();
		regRecordRequest.setBranchCode(this.branchHospitalCode);
		regRecordRequest.setPsOrdNum(orderNo);
		//		regRecordRequest.setRegMode(platformMode.toString());
		regRecordRequest.setRegMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
		if (cardType != null) {
			regRecordRequest.setPatCardType(cardType.toString());
		}
		regRecordRequest.setReservedFieldOne(this.regType.toString());
		regRecordRequest.setPatCardNo(cardNo);
		return regRecordRequest;
	}

	public CancelRegRequest convertCancelRegRequest() {
		CancelRegRequest cancelRegRequest = new CancelRegRequest();
		//		cancelRegRequest.setCancelMode(String.valueOf(ModeTypeUtil.getPlatformModeType(this.appCode)));
		cancelRegRequest.setCancelMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值
		cancelRegRequest.setHisOrdNum(hisOrdNo);
		cancelRegRequest.setPsOrdNum(orderNo);
		cancelRegRequest.setBranchCode(branchHospitalCode);
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(d);
		cancelRegRequest.setCancelTime(time);
		return cancelRegRequest;
	}

	public PayRegRequest convertPayRegRequest() {
		PayRegRequest payRegRequest = new PayRegRequest();
		payRegRequest.setPsOrdNum(this.orderNo);
		//		payRegRequest.setPayMode(String.valueOf(ModeTypeUtil.getPlatformModeType(this.appCode)));
		payRegRequest.setPayMode(BizConstant.HIS_ORDER_MODE_VAL);
		payRegRequest.setAgtOrdNum(agtOrdNum);
		payRegRequest.setBranchCode(this.branchHospitalCode);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String payTime = null;
		if (this.payTime == null) {
			payTime = formatter.format(new Date());
		} else {
			java.util.GregorianCalendar now = new GregorianCalendar();
			now.setTimeInMillis(this.payTime);
			payTime = formatter.format(now.getTime());
		}
		payRegRequest.setPayTime(payTime);
		payRegRequest.setHisOrdNum(this.hisOrdNo);
		/***************医保**************/
		payRegRequest.setIsInsurance(String.valueOf(this.isMedicarePayMents));
		payRegRequest.setMedicareAmount("0");
		payRegRequest.setAccountAmout(String.valueOf(this.personalAccountFee));
		payRegRequest.setInsuranceAmout(String.valueOf(this.personalAccountFee));
		payRegRequest.setInsuredType(String.valueOf(this.medicalInsuranceType));
		if (new Integer(1).equals(isMedicarePayMents)) {
			payRegRequest.setPatientType("1");
			payRegRequest.setPayAmout(regFee == null ? "0" : regFee.toString());
		} else {
			payRegRequest.setPayAmout(payTotalFee == null ? "0" : payTotalFee.toString());
		}
		payRegRequest.setSSItems(String.valueOf(this.ssItems));
		payRegRequest.setSSMoney(String.valueOf(this.personalAccountFee));
		/**********预约号码************/
		payRegRequest.setReservedFieldThree(this.receiptNum);
		payRegRequest.setOrderNo(this.receiptNum);
		return payRegRequest;
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

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public RegDoctor getRegDoctor() {
		RegDoctor doctor = new RegDoctor();
		BeanUtils.copyProperties(this, doctor);
		return doctor;
	}

	public String getScheduleDateStr() {
		if (!StringUtils.isNotBlank(scheduleDateStr)) {
			if (scheduleDate != null) {
				scheduleDateStr = BizConstant.YYYYMMDD.format(scheduleDate);
			}
		}
		return scheduleDateStr;
	}

	public void setScheduleDateStr(String scheduleDateStr) {
		this.scheduleDateStr = scheduleDateStr;
	}

	public Integer getPopulationType() {
		return populationType;
	}

	public void setPopulationType(Integer populationType) {
		this.populationType = populationType;
	}

	public Integer getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(Integer appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getMediCareType() {
		return mediCareType;
	}

	public void setMediCareType(String mediCareType) {
		this.mediCareType = mediCareType;
	}

	public String getPatientTypeId() {
		return patientTypeId;
	}

	public void setPatientTypeId(String patientTypeId) {
		this.patientTypeId = patientTypeId;
	}

	public String getRegAgency() {
		return regAgency;
	}

	public void setRegAgency(String regAgency) {
		this.regAgency = regAgency;
	}

	public Integer getIsTakeNo() {
		return isTakeNo;
	}

	public void setIsTakeNo(Integer isTakeNo) {
		this.isTakeNo = isTakeNo;
	}

	public String getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getFrontNum() {
		return frontNum;
	}

	public void setFrontNum(String frontNum) {
		this.frontNum = frontNum;
	}

	public String getMedicalCardNo() {
		return medicalCardNo;
	}

	public void setMedicalCardNo(String medicalCardNo) {
		this.medicalCardNo = medicalCardNo;
	}

	public String getComputerNo() {
		return computerNo;
	}

	public void setComputerNo(String computerNo) {
		this.computerNo = computerNo;
	}

	public String getHospOrgCode() {
		return hospOrgCode;
	}

	public void setHospOrgCode(String hospOrgCode) {
		this.hospOrgCode = hospOrgCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getRegCategory() {
		return regCategory;
	}

	public void setRegCategory(String regCategory) {
		this.regCategory = regCategory;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getMedicalInsuranceType() {
		return medicalInsuranceType;
	}

	public void setMedicalInsuranceType(String medicalInsuranceType) {
		this.medicalInsuranceType = medicalInsuranceType;
	}

	public String getSsItems() {
		return ssItems;
	}

	public void setSsItems(String ssItems) {
		this.ssItems = ssItems;
	}

	public void addLogInfo(String logMsg) {

		StringBuilder sbLog = new StringBuilder();
		if (handleCount != null) {
			sbLog.append("HandleCount:" + handleCount + ",");
		}
		sbLog.append("HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:" + logMsg);

		if (StringUtils.isNotBlank(handleLog)) {
			if (handleLog.endsWith(";")) {
				setHandleLog(handleLog + sbLog.toString());
			} else {
				setHandleLog(handleLog + ";" + sbLog.toString());
			}
		} else {
			setHandleLog(sbLog.toString());
		}
	}

	public String getStatusDescription(Integer regStatus) {
		String statusDescription = null;
		if (regStatus == null) {
			statusDescription = "regStatus为空,状态未知";
		} else {
			switch (regStatus) {
			case 0:
				statusDescription = "[STATE_NORMAL_HAVING=0]预约中";
				break;
			case 1:
				statusDescription = "[STATE_NORMAL_HAD=1]已预约";
				break;
			case 2:
				statusDescription = "[STATE_NORMAL_USER_CANCEL=2]已取消-用户取消";
				break;
			case 3:
				statusDescription = "[STATE_NORMAL_PAY_TIMEOUT_CANCEL=3]已取消-支付超过规定时长";
				break;
			case 4:
				statusDescription = "[STATE_NORMAL_STOP_CURE_CANCEL=4]已取消-停诊取消";
				break;
			case 5:
				statusDescription = "[STATE_EXCEPTION_HAVING=5]预约异常(his锁号异常)";
				break;
			case 6:
				statusDescription = "[STATE_EXCEPTION_PAY=6]第3方支付异常";
				break;
			case 7:
				statusDescription = "[STATE_EXCEPTION_PAY_HIS_COMMIT=7]支付后 his确认异常";
				break;
			case 8:
				statusDescription = "[STATE_EXCEPTION_CANCEL_EXCEPTION=8]取消挂号异常";
				break;
			case 9:
				statusDescription = "[STATE_EXCEPTION_REFUND_HIS_CONFIRM=9]第3方退费前,调用his退费确认异常";
				break;
			case 10:
				statusDescription = "[STATE_EXCEPTION_REFUND=10]第3方退费异常";
				break;
			case 11:
				statusDescription = "[STATE_EXCEPTION_REFUND_HIS_COMMIT=11]退费成功后提交his确认异常";
				break;
			case 12:
				statusDescription = "[STATE_EXCEPTION_FAILURE=12]挂号失败";
				break;
			case 13:
				statusDescription = "[STATE_EXCEPTION_CANCEL=13]异常导致的挂号取消,后续轮询处理成功";
				break;
			case 14:
				statusDescription = "[STATE_EXCEPTION_NEED_PERSON_HANDLE=14]业务异常,需人工处理";
				break;
			case 15:
				statusDescription = "[STATE_EXCEPTION_NEED_HOSPITAL_HANDLE=15]业务异常,需医院窗口处理";
				break;
			case 16:
				statusDescription = "[STATE_EXCEPTION_STOP_CURE_CANCEL=16]停诊取消异常(未支付)";
				break;
			case 17:
				statusDescription = "[STATE_EXCEPTION_STOP_REFUND_HIS_CONFIRM=17]hia停诊-预约退费确认异常 ";
				break;
			case 18:
				statusDescription = "[STATE_EXCEPTION_STOP_REFUND=18]停诊-第3方退费异常";
				break;
			case 19:
				statusDescription = "[STATE_EXCEPTION_STOP_REFUND_HIS_COMMIT=19]停诊-退费后his确认异常 ";
				break;
			case 20:
				statusDescription = "[STATE_WINDOW_SUCCESSFUL_REFUND=20]窗口退费成功";
				break;
			case 21:
				statusDescription = "[STATE_WINDOW_EXCEPTION_REFUND=21]窗口退费异常";
				break;
			case 22:
				statusDescription = "[STATE_NORMAL_USER_CANCELING=22]用户预约取消中";
				break;
			case 23:
				statusDescription = "[STATE_PLATFORM_REFUND_SUCCESS=23]后台退费-成功";
				break;
			case 24:
				statusDescription = "[STATE_PLATFORM_REFUND_FAIL=24]后台退费-失败";
				break;
			case 25:
				break;
			}
		}
		return statusDescription;
	}

	/** 
	 * 挂号记录转换为消息推送参数
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年4月9日 
	 * @return 
	 */
	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);

		params.setOpenId(this.openId.replaceAll(" ", "+"));
		params.setPlatformType(this.appCode);

		/**此处代码暂时没发现有什么意义*/
		/*if (StringUtils.isEmpty(branchHospitalName) && StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchHospitalCode)) {
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> cacheParams = new ArrayList<Object>();
			cacheParams.add(hospitalCode);
			cacheParams.add(branchHospitalCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getCodeAndInterfaceVo", cacheParams);
			if (CollectionUtils.isNotEmpty(results)) {
				CodeAndInterfaceVo vo = (CodeAndInterfaceVo) results.get(0);
				branchHospitalName = vo.getBranchName();
			}
		}*/

		@SuppressWarnings("unchecked")
		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);

		//
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.URL_PARAM_ORDER_NO).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.orderNo).concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_OPEN_ID)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.openId).concat(BizConstant.URL_PARAM_CHAR_CONCAT)
						.concat(BizConstant.URL_PARAM_APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appId)
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_APPCODE)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appCode);
		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);
		return params;
	}

}