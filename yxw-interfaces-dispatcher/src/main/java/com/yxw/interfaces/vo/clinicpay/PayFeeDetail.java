/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊已缴费记录明细
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: PayFeeDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayFeeDetail extends Reserve implements Serializable {

	private static final long serialVersionUID = -4755321528244529791L;
	/**
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 缴费项明细集合
	 */
	private List<Detail> details;

	public PayFeeDetail() {
		super();
	}

	/**
	 * @param mzFeeId
	 * @param details
	 */
	public PayFeeDetail(String mzFeeId, List<Detail> details) {
		super();
		this.mzFeeId = mzFeeId;
		this.details = details;
	}

	/**
	 * @return the mzFeeId
	 */
	public String getMzFeeId() {
		return mzFeeId;
	}

	/**
	 * @param mzFeeId the mzFeeId to set
	 */
	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	/**
	 * @return the details
	 */
	public List<Detail> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(List<Detail> details) {
		this.details = details;
	}

}
