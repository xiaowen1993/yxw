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

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->科室号源信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class DeptReg extends Reserve implements Serializable {

	private static final long serialVersionUID = 7866884427995728544L;
	/**
	 * 号源日期,格式：YYYY-MM-DD
	 */
	private String scheduleDate;
	/**
	 * 号源总数
	 */
	private String totalNum;
	/**
	 * 剩余可预约号源数
	 */
	private String leftNum;

	public DeptReg() {
		super();
	}

	/**
	 * @param scheduleDate
	 * @param totalNum
	 * @param leftNum
	 */

	public DeptReg(String scheduleDate, String totalNum, String leftNum) {
		super();
		this.scheduleDate = scheduleDate;
		this.totalNum = totalNum;
		this.leftNum = leftNum;
	}

	/**
	 * @return the scheduleDate
	 */

	public String getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            the scheduleDate to set
	 */

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
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
