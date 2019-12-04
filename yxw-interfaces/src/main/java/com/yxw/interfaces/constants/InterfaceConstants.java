/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月26日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.constants;

/**
 * <p>
 * 接口常量
 * </P>
 * 
 * @author 申午武
 * 
 */
public class InterfaceConstants {
	/**
	 * 接口配置文件
	 */
	public static final String INTERFACE_CONFIG = "interface.properties";
	/**
	 * 可挂号科室静态锁
	 */
	public static final String INTERFACE_METHOD_DEPT = "dept";
	/**
	 * 可挂号医生静态锁
	 */
	public static final String INTERFACE_METHOD_DOCTOR = "doctor";
	/**
	 * 可挂号重复的医生静态锁
	 */
	public static final String INTERFACE_METHOD_REPEAT_DOCTOR = "repeatDoctor";
	/**
	 * 号源明细静态锁
	 */
	public static final String INTERFACE_METHOD_REGINFO = "regInfo";
	/**
	 * 患者信息静态锁
	 */
	public static final String INTERFACE_METHOD_MEDICALCARD = "medicalcard";
	/**
	 * 挂号诊疗信息新增静态锁
	 */
	public static final String INTERFACE_METHOD_ORDERREG_MEDICALCARD_ADD = "orderRegMedicalcardAdd";
	/**
	 * 挂号诊疗信息删除静态锁
	 */
	public static final String INTERFACE_METHOD_ORDERREG_MEDICALCARD_DEL = "orderRegMedicalcardDel";
}
