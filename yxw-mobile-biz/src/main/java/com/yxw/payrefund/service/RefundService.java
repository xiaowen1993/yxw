package com.yxw.payrefund.service;

import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;

public interface RefundService {

	/**
	 * 微信退费
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param refund
	 * @return
	 */
	public WechatPayRefundResponse wechatPayRefund(WechatPayRefund refund);

	/**
	 * 支付宝退费
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param refund
	 * @return
	 */
	public AlipayRefundResponse alipayRefund(AlipayRefund refund);

	/**
	 * 银联退费
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param refund
	 * @return
	 */
	public UnionpayRefundResponse unionpayRefund(UnionpayRefund refund);

}