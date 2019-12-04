/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-9-21</p>
 *  <p> Created by dfw</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.clinic.dao;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicPartRefundRecord;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.clinic.dao
 * @ClassName: ClinicPartRefundDao
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-9-21
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface ClinicPartRefundDao extends BaseDao<ClinicPartRefundRecord, String> {
	public Integer countTotalRefundFeeByRefOrderNo(String orderNo, String hospitalCode);

	public ClinicPartRefundRecord findByRefundOrderNo(String refundOrderNo);

	public ClinicPartRefundRecord findByHospitalCodeAndHisOrdNo(String hospitalCode, String hisOrdNo, String orderNo);
}
