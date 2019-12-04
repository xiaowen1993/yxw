/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.inside;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: stopRegistration
 * @Statement: <p>窗口停诊输入参数 旧版本参数格式</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterArg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2282553559013021670L;

	/**
	 * arg0(appid):医院微信公众号的appid
	 */
	private String arg0;

	/**
	 * arg1(deptCode): 科室代码
	 */
	private String arg1;

	/**
	 * arg2(deptName):科室名称
	 */
	private String arg2;

	/**
	 * arg3(doctorCode):医生代码
	 */
	private String arg3;

	/**
	 * arg4(doctorName):医生名称
	 */
	private String arg4;

	/**
	 * 	arg5(stopRegistrationTime):停诊开始时间(格式：YYYY-MM-DD)
	 */
	private String arg5;

	/**
	 * 	arg6(startRegistrationTime):停诊结束时间(格式：YYYY-MM-DD)
	 */
	private String arg6;

	/**
	 * 	arg7(reason):停诊原因
	 */
	private String arg7;

	/**
	 * 	arg8(type): 1.微信 2.支付宝
	 */
	private String arg8;

	/**
	 * 	arg9(orderList):医享网平台的微信公众服务号预约订单号列表，一个或多个，订单号间用英文逗号隔开
	 */
	private String arg9;

	public StopRegisterArg() {
		super();
	}

	public StopRegisterArg(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7,
			String arg8, String arg9) {
		super();
		this.arg0 = arg0;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.arg4 = arg4;
		this.arg5 = arg5;
		this.arg6 = arg6;
		this.arg7 = arg7;
		this.arg8 = arg8;
		this.arg9 = arg9;
	}

	public String getArg0() {
		return arg0;
	}

	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public String getArg3() {
		return arg3;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	public String getArg4() {
		return arg4;
	}

	public void setArg4(String arg4) {
		this.arg4 = arg4;
	}

	public String getArg5() {
		return arg5;
	}

	public void setArg5(String arg5) {
		this.arg5 = arg5;
	}

	public String getArg6() {
		return arg6;
	}

	public void setArg6(String arg6) {
		this.arg6 = arg6;
	}

	public String getArg7() {
		return arg7;
	}

	public void setArg7(String arg7) {
		this.arg7 = arg7;
	}

	public String getArg8() {
		return arg8;
	}

	public void setArg8(String arg8) {
		this.arg8 = arg8;
	}

	public String getArg9() {
		return arg9;
	}

	public void setArg9(String arg9) {
		this.arg9 = arg9;
	}

}
