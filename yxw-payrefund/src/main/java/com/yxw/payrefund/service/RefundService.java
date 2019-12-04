package com.yxw.payrefund.service;

import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;

public interface RefundService {

	public WechatPayRefundResponse wechatPayRefund(WechatPayRefund refund);

	public AlipayRefundResponse alipayRefund(AlipayRefund refund);

	public UnionpayRefundResponse unionpayRefund(UnionpayRefund refund);

}