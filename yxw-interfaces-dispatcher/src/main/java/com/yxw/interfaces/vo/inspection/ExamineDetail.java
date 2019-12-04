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
package com.yxw.interfaces.vo.inspection;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 检验/检查/体检报告查询-->检查结果详情
 * @Package: com.yxw.interfaces.entity.inspection
 * @ClassName: ExamineDetail
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月17日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExamineDetail extends Reserve implements Serializable {

	private static final long serialVersionUID = 5136973131712191109L;
	/**
	 * 检查部位
	 */
	private String checkPart;
	/**
	 * 检查方法
	 */
	private String checkMethod;
	/**
	 * 检查所见
	 */
	private String checkSituation;
	/**
	 * 诊断意见
	 */
	private String option;
	/**
	 * 医嘱项
	 */
	private String advice;

	public ExamineDetail() {
		super();
	}

	/**
	 * @param checkPart
	 * @param checkMethod
	 * @param checkSituation
	 * @param option
	 * @param advice
	 */
	public ExamineDetail(String checkPart, String checkMethod, String checkSituation, String option, String advice) {
		super();
		this.checkPart = checkPart;
		this.checkMethod = checkMethod;
		this.checkSituation = checkSituation;
		this.option = option;
		this.advice = advice;
	}

	/**
	 * @return the checkPart
	 */
	public String getCheckPart() {
		return checkPart;
	}

	/**
	 * @param checkPart the checkPart to set
	 */
	public void setCheckPart(String checkPart) {
		this.checkPart = checkPart;
	}

	/**
	 * @return the checkMethod
	 */
	public String getCheckMethod() {
		return checkMethod;
	}

	/**
	 * @param checkMethod the checkMethod to set
	 */
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	/**
	 * @return the checkSituation
	 */
	public String getCheckSituation() {
		return checkSituation;
	}

	/**
	 * @param checkSituation the checkSituation to set
	 */
	public void setCheckSituation(String checkSituation) {
		this.checkSituation = checkSituation;
	}

	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @return the advice
	 */
	public String getAdvice() {
		return advice;
	}

	/**
	 * @param advice the advice to set
	 */
	public void setAdvice(String advice) {
		this.advice = advice;
	}

}
