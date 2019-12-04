/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号支付信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class PayCurReg extends Reserve implements Serializable {

	private static final long serialVersionUID = -599463960852340804L;
	/**
	 * 医院预约流水号,有些医院不一定会在挂号接口返回医院流水号,特在支付接口增加此参数
	 */
	private String hisOrdNum;
	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 就诊序号
	 */
	private String serialNum;
	/**
	 * 就诊位置
	 */
	private String visitLocation;
	/**
	 * 条形码
	 */
	private String barCode;
	/**
	 * 就诊说明
	 */
	private String visitDesc;

	public PayCurReg() {
		super();
	}

	/**
	 * @param hisOrdNum
	 * @param receiptNum
	 * @param serialNum
	 * @param visitLocation
	 * @param barCode
	 * @param visitDesc
	 */
	public PayCurReg(String hisOrdNum, String receiptNum, String serialNum, String visitLocation, String barCode, String visitDesc) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.receiptNum = receiptNum;
		this.serialNum = serialNum;
		this.visitLocation = visitLocation;
		this.barCode = barCode;
		this.visitDesc = visitDesc;
	}

	/**
	 * @return the receiptNum
	 */

	public String getReceiptNum() {
		return receiptNum;
	}

	/**
	 * @param receiptNum
	 *            the receiptNum to set
	 */

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	/**
	 * @return the serialNum
	 */

	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum
	 *            the serialNum to set
	 */

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * @return the visitLocation
	 */

	public String getVisitLocation() {
		return visitLocation;
	}

	/**
	 * @param visitLocation
	 *            the visitLocation to set
	 */

	public void setVisitLocation(String visitLocation) {
		this.visitLocation = visitLocation;
	}

	/**
	 * @return the barCode
	 */

	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode
	 *            the barCode to set
	 */

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the visitDesc
	 */

	public String getVisitDesc() {
		return visitDesc;
	}

	/**
	 * @param visitDesc
	 *            the visitDesc to set
	 */

	public void setVisitDesc(String visitDesc) {
		this.visitDesc = visitDesc;
	}

	/**
	 * @return the hisOrdNum
	 */
	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum the hisOrdNum to set
	 */
	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

}
