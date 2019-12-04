/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月14日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.service;

import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.clinicpay.AckPayOrderRequest;
import com.yxw.interfaces.vo.clinicpay.FriedAndDeliveredRequest;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetailRequest;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetail_FDPRequest;
import com.yxw.interfaces.vo.clinicpay.MZFeeRequest;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetailRequest;
import com.yxw.interfaces.vo.clinicpay.PayFeeRequest;
import com.yxw.interfaces.vo.doctor.MedOrderRequest;
import com.yxw.interfaces.vo.hospitalized.BedRecordRequest;
import com.yxw.interfaces.vo.hospitalized.OutMedicineRequest;
import com.yxw.interfaces.vo.hospitalized.OutSummaryRequest;
import com.yxw.interfaces.vo.hospitalized.SettleBedFeeRequest;
import com.yxw.interfaces.vo.hospitalized.deposit.BedFeeRequest;
import com.yxw.interfaces.vo.hospitalized.deposit.ComplementDepositRequest;
import com.yxw.interfaces.vo.hospitalized.deposit.DepositRequest;
import com.yxw.interfaces.vo.hospitalized.deposit.PayDepositRequest;
import com.yxw.interfaces.vo.hospitalized.list.BedFeeItemRequest;
import com.yxw.interfaces.vo.hospitalized.list.PerBedFeeRequest;
import com.yxw.interfaces.vo.inspection.ExaminationRequest;
import com.yxw.interfaces.vo.inspection.ExamineDetailRequest;
import com.yxw.interfaces.vo.inspection.ExamineRequest;
import com.yxw.interfaces.vo.inspection.InspectDetailRequest;
import com.yxw.interfaces.vo.inspection.InspectRequest;
import com.yxw.interfaces.vo.intelligent.waiting.ExamineQueueRequest;
import com.yxw.interfaces.vo.intelligent.waiting.MZQueueRequest;
import com.yxw.interfaces.vo.intelligent.waiting.MedicineQueueRequest;
import com.yxw.interfaces.vo.microsite.DeptRequest;
import com.yxw.interfaces.vo.microsite.DoctorRequest;
import com.yxw.interfaces.vo.microsite.HospitalRequest;
import com.yxw.interfaces.vo.mycenter.CardRequest;
import com.yxw.interfaces.vo.mycenter.MZPatientRequest;
import com.yxw.interfaces.vo.other.report.GrossIncomeRequest;
import com.yxw.interfaces.vo.other.report.IpVisitorRequest;
import com.yxw.interfaces.vo.other.report.OpVisitorRequest;
import com.yxw.interfaces.vo.register.DocTimeRequest;
import com.yxw.interfaces.vo.register.PatientTypeRequest;
import com.yxw.interfaces.vo.register.RegDeptRequest;
import com.yxw.interfaces.vo.register.RegDoctorRequest;
import com.yxw.interfaces.vo.register.RegInfoRequest;
import com.yxw.interfaces.vo.register.RegRecordRequest;
import com.yxw.interfaces.vo.register.TakeNoRequest;
import com.yxw.interfaces.vo.register.UnpaidRegRecordRequest;
import com.yxw.interfaces.vo.register.appointment.AckRefundRegRequest;
import com.yxw.interfaces.vo.register.appointment.CancelRegRequest;
import com.yxw.interfaces.vo.register.appointment.OrderRegRequest;
import com.yxw.interfaces.vo.register.appointment.PayRegRequest;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.interfaces.vo.register.onduty.AckRefundCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.CancelCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.OrderCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.PayCurRegRequest;
import com.yxw.interfaces.vo.register.onduty.RefundCurRegRequest;
import com.yxw.interfaces.vo.register.replacereg.ReplaceRegRequest;
import com.yxw.interfaces.vo.register.stopreg.StopRegRequest;

/**
 * 医享网络标准接口
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public interface YxwService {
	/**
	 * 微网站-->医院信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getHospitals(HospitalRequest hospitalRequest);

	/**
	 * 微网站-->科室列表查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getDepts(DeptRequest deptRequest);

	/**
	 * 微网站-->医生列表查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getDoctors(DoctorRequest doctorRequest);

	/**
	 * 个人中心-->患者信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMZPatient(MZPatientRequest mzPatientRequest);

	/**
	 * 个人中心-->首诊患者建档
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response createCard(CardRequest cardRequest);

	/**
	 * 挂号-->可挂号科室查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getRegDepts(RegDeptRequest regDeptRequest);

	/**
	 * 挂号-->可挂号医生查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getRegDoctors(RegDoctorRequest regDoctorRequest);

	/**
	 * 挂号-->号源信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getRegInfo(RegInfoRequest regInfoRequest);

	/**
	 * 挂号-->医生分时查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getDocTime(DocTimeRequest docTimeRequest);

	/**
	 * 挂号-->患者类型查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getPatientType(PatientTypeRequest patientTypeRequest);
	
	/**
	 * 挂号-->预约挂号-->预约挂号
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response orderReg(OrderRegRequest orderRegRequest);

	/**
	 * 挂号-->预约挂号-->取消预约
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response cancelReg(CancelRegRequest cancelRegRequest);

	/**
	 * 挂号-->预约挂号-->预约支付
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response payReg(PayRegRequest payRegRequest);

	/**
	 * 挂号-->预约挂号-->预约退费确认
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response ackRefundReg(AckRefundRegRequest ackRefundRegRequest);

	/**
	 * 挂号-->预约挂号-->预约退费
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response refundReg(RefundRegRequest refundRegRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response orderCurReg(OrderCurRegRequest orderCurRegRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号取消
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response cancelCurReg(CancelCurRegRequest cancelCurRegRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号费用减免查询
	 * 
	 * @param request
	 * @return
	 */
	//public abstract CurRegFee getCurRegFee(CurRegFeeRequest curRegFeeRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号支付
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response payCurReg(PayCurRegRequest payCurRegRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号退费确认
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response ackRefundCurReg(AckRefundCurRegRequest ackRefundCurRegRequest);

	/**
	 * 挂号-->当天挂号-->当天挂号退费
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response refundCurReg(RefundCurRegRequest refundCurRegRequest);

	/**
	 * 挂号-->挂号记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getRegRecords(RegRecordRequest regRecordRequest);

	/**
	 * 挂号-->跨渠道未支付挂号记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getUnpaidRegRecords(UnpaidRegRecordRequest unpaidRegRecordRequest);
	
	/**
	 * 挂号-->报到取号
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response takeNo(TakeNoRequest takeNoRequest);
	
	/**
	 * 医生停诊-->医生停诊信息
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getStopReg(StopRegRequest stopRegRequest);

	/**
	 * 医生停诊-->医生停诊订单处理确认
	 * 
	 * @param request
	 * @return
	 */
	//public abstract Response confirmStopReg(ConfirmStopRegRequest confirmStopRegRequest);

	/**
	 * 医生停诊-->医生替诊信息
	 *
	 * @param replaceRegRequest
	 * @return
	 */
	public abstract Response getReplaceReg(ReplaceRegRequest replaceRegRequest);

	/**
	 * 诊疗付费-->门诊待缴费记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMZFeeList(MZFeeRequest mzFeeRequest);

	/**
	 * 诊疗付费-->门诊待缴费记录明细查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMZFeeDetail(MZFeeDetailRequest mzFeeDetailRequest);

	/**
	 * 诊疗付费-->门诊待缴费记录明细查询(含代煎配送及支付限制)
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMZFeeDetail(MZFeeDetail_FDPRequest mzFeeDetail_FDPRequest);
	
	/**
	 * 诊疗付费-->设置代煎配送
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response setFriedAndDelivered(FriedAndDeliveredRequest friedAndDeliveredRequest);
	
	/**
	 * 诊疗付费-->门诊缴费订单支付
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response ackPayOrder(AckPayOrderRequest ackPayOrderRequest);

	/**
	 * 诊疗付费-->门诊已缴费记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getPayFeeList(PayFeeRequest payFeeRequest);

	/**
	 * 诊疗付费-->门诊已缴费记录明细查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getPayFeeDetail(PayFeeDetailRequest payFeeDetailRequest);

	/**
	 * 住院患者服务-->住院记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getBedRecords(BedRecordRequest bedRecordRequest);

	/**
	 * 住院患者服务-->住院押金-->住院费用查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getBedFee(BedFeeRequest bedFeeRequest);

	/**
	 * 住院患者服务-->住院押金-->住院押金补缴支付
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response payDeposit(PayDepositRequest payDepositRequest);

	/**
	 * 住院患者服务-->住院押金-->住院押金补缴记录查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getDepositList(DepositRequest depositRequest);

	/**
	 * 住院患者服务-->住院押金-->住院押金不足
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response complementDeposit(ComplementDepositRequest complementDepositRequest);

	/**
	 * 住院患者服务-->住院费用清单-->清单基本信息
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getPerBedFee(PerBedFeeRequest perBedFeeRequest);

	/**
	 * 住院患者服务-->住院费用清单-->清单明细列表
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getBedFeeitem(BedFeeItemRequest bedFeeItemRequest);
	
	/**
	 * 住院患者服务-->出院清账
	 * 
	 * @return
	 */
	public abstract Response settleBedFee(SettleBedFeeRequest settleBedFeeRequest);

	/**
	 * 住院患者服务-->出院带药查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getOutMedicine(OutMedicineRequest outMedicineRequest);

	/**
	 * 住院患者服务-->出院小结查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getOutSummary(OutSummaryRequest outSummaryRequest);

	/**
	 * 医生服务-->住院医嘱查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMedOrders(MedOrderRequest medOrderRequest);

	/**
	 * 智能查询-->候诊排队查询-->门诊候诊信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMZQueue(MZQueueRequest mzQueueRequest);

	/**
	 * 智能查询-->候诊排队查询-->检查/检验/体检排队信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getExamineQueue(ExamineQueueRequest examineQueueRequest);

	/**
	 * 智能查询-->候诊排队查询-->取药排队信息查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getMedicineQueue(MedicineQueueRequest medicineQueueRequest);

	/**
	 * 检验/检查/体检报告查询-->检查结果列表查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getExamineList(ExamineRequest examineRequest);

	/**
	 * 检验/检查/体检报告查询-->检查结果详情查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getExamineDetail(ExamineDetailRequest examineDetailRequest);

	/**
	 * 检验/检查/体检报告查询-->检验报告列表查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getInspectList(InspectRequest inspectRequest);

	/**
	 * 检验/检查/体检报告查询-->检验报告详情查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getInspectDetail(InspectDetailRequest inspectDetailRequest);

	/**
	 * 检验/检查/体检报告查询-->体检报告列表查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getExaminationList(ExaminationRequest examinationRequest);

	/**
	 * 其他功能-->院长报告-->总收入查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getGrossIncome(GrossIncomeRequest grossIncomeRequest);

	/**
	 * 其他功能-->院长报告-->门诊人次查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getOpVisitors(OpVisitorRequest opVisitorRequest);

	/**
	 * 其他功能-->院长报告-->住院人次查询
	 * 
	 * @param request
	 * @return
	 */
	public abstract Response getIpVisitors(IpVisitorRequest ipVisitorRequest);

}
