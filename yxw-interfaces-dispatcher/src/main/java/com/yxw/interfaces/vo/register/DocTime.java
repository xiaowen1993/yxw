/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->医生分时信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class DocTime extends Reserve implements Serializable {

	private static final long serialVersionUID = -8936903243498424256L;
	/**
	 * 时段,见TimeType
	 * @see com.yxw.interfaces.constants.TimeType
	 */
	private String timeFlag;
	/**
	 * 排班ID
	 */
	private String workId;
	/**
	 * 分时开始时间,格式：HH24:MI
	 */
	private String beginTime;
	/**
	 * 分时结束时间,格式：HH24:MI
	 */
	private String endTime;
	/**
	 * 挂号费,单位：分
	 */
	private String regFee;
	/**
	 * 诊疗费 ,单位：分
	 */
	private String treatFee;

	/**
	 * 出诊状态,见WorkStatusType
	 * @see com.yxw.interfaces.constants.WorkStatusType
	 */
	private String workStatus;
	/**
	 * 号源总数
	 */
	private String totalNum;
	/**
	 * 剩余可预约号源数
	 */
	private String leftNum;

	public DocTime() {
		super();
	}

	/**
	 * @param timeFlag
	 * @param workId
	 * @param beginTime
	 * @param endTime
	 * @param regFee
	 * @param treatFee
	 * @param workStatus
	 * @param totalNum
	 * @param leftNum
	 */
	public DocTime(String timeFlag, String workId, String beginTime, String endTime, String regFee, String treatFee, String workStatus,
			String totalNum, String leftNum) {
		super();
		this.timeFlag = timeFlag;
		this.workId = workId;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.workStatus = workStatus;
		this.totalNum = totalNum;
		this.leftNum = leftNum;
	}

	/**
	 * @return the timeFlag
	 */
	public String getTimeFlag() {
		return timeFlag;
	}

	/**
	 * @param timeFlag the timeFlag to set
	 */
	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	/**
	 * @return the workId
	 */

	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId
	 *            the workId to set
	 */

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	/**
	 * @return the beginTime
	 */

	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */

	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the regFee
	 */

	public String getRegFee() {
		return regFee;
	}

	/**
	 * @param regFee
	 *            the regFee to set
	 */

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	/**
	 * @return the treatFee
	 */

	public String getTreatFee() {
		return treatFee;
	}

	/**
	 * @param treatFee
	 *            the treatFee to set
	 */

	public void setTreatFee(String treatFee) {
		this.treatFee = treatFee;
	}

	/**
	 * @return the workStatus
	 */

	public String getWorkStatus() {
		return workStatus;
	}

	/**
	 * @param workStatus
	 *            the workStatus to set
	 */

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	/**
	 * @return the totalNum
	 */

	public String getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum
	 *            the totalNum to set
	 */

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the leftNum
	 */

	public String getLeftNum() {
		return leftNum;
	}

	/**
	 * @param leftNum
	 *            the leftNum to set
	 */

	public void setLeftNum(String leftNum) {
		this.leftNum = leftNum;
	}

}
