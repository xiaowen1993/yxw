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
 * 挂号-->号源信息-->医生信息-->明细信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class Detail extends Reserve implements Serializable {

	private static final long serialVersionUID = 9142409296099738092L;
	/**
	 * 时段,见TimeType
	 * @see com.yxw.interfaces.constants.TimeType
	 */
	private String timeFlag;
	/**
	 * 是否有分时,0:无分时,1:有分时
	 */
	private String hasDetailTime;
	/**
	 * 开始时间,格式：HH24:MI
	 */
	private String beginTime;
	/**
	 * 结束时间,格式：HH24:MI
	 */
	private String endTime;
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
	/**
	 * 挂号费,单位:分
	 */
	private String regFee;
	/**
	 * 诊疗费,单位:分
	 */
	private String treatFee;
	/**
	 * 排班ID
	 */
	private String workId;

	public Detail() {

	}

	/**
	 * @param timeFlag
	 * @param hasDetailTime
	 * @param beginTime
	 * @param endTime
	 * @param workStatus
	 * @param totalNum
	 * @param leftNum
	 * @param regFee
	 * @param treatFee
	 * @param workId
	 */

	public Detail(String timeFlag, String hasDetailTime, String beginTime, String endTime, String workStatus, String totalNum, String leftNum,
			String regFee, String treatFee, String workId) {
		super();
		this.timeFlag = timeFlag;
		this.hasDetailTime = hasDetailTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.workStatus = workStatus;
		this.totalNum = totalNum;
		this.leftNum = leftNum;
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.workId = workId;
	}

	/**
	 * @return the timeFlag
	 */

	public String getTimeFlag() {
		return timeFlag;
	}

	/**
	 * @param timeFlag
	 *            the timeFlag to set
	 */

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	/**
	 * @return the hasDetailTime
	 */

	public String getHasDetailTime() {
		return hasDetailTime;
	}

	/**
	 * @param hasDetailTime
	 *            the hasDetailTime to set
	 */

	public void setHasDetailTime(String hasDetailTime) {
		this.hasDetailTime = hasDetailTime;
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

}
