/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年7月13日
 * @version 1.0
 */
package com.yxw.payrefund.service;

import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年7月13日  
 */
public interface QueryService {

	/**
	 * 微信订单查询
	 * @param refund
	 * @return
	 * @throws Exception
	 */
	public WechatPayOrderQueryResponse wechatPayOrderQuery(WechatPayOrderQuery queryOrder);

	/**
	 * 支付宝订单查询
	 * @param refundAli
	 * @return
	 * @throws Exception
	 */
	public AlipayOrderQueryResponse alipayOrderQuery(AlipayOrderQuery queryOrder);

	/**
	 * 银联订单查询
	 * @param refund
	 * @return
	 * @throws Exception
	 */
	public UnionpayOrderQueryResponse unionpayOrderQuery(UnionpayOrderQuery queryOrder);

}
