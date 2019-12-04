/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.dao;

import com.yxw.easyhealth.biz.register.entity.RegisterOrder;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.register.dao
 * @ClassName: RegisterOrderDao
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RegisterOrderDao extends BaseDao<RegisterOrder, String> {
	/**
	 * 根据订单编号 查找订单
	 * @param orderNo
	 * @return
	 */
	public RegisterOrder findByOrderNo(String orderNo);

	/**
	 * 根据订单编号 更新订单
	 * @param orderNo
	 * @return
	 */
	public RegisterOrder updateByOrderNo(RegisterOrder order);

	/**
	 * 根据订单编号删除订单
	 * @param orderNo
	 * @return
	 */
	public void deleteByOrderNo(String orderNo);

	/**
	 * 写入订单的退费号
	 * @param orderNo
	 */
	public void updateRefundOrderNo(String orderNo, String relativeOrderNo);
}
