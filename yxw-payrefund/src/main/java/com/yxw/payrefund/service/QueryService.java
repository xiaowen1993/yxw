package com.yxw.payrefund.service;

import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;

public interface QueryService {

	public WechatPayOrderQueryResponse wechatPayOrderQuery(WechatPayOrderQuery orderQuery);

	public AlipayOrderQueryResponse alipayOrderQuery(AlipayOrderQuery orderQuery);

	public UnionpayOrderQueryResponse unionpayOrderQuery(UnionpayOrderQuery orderQuery);

	//public WechatPayRefundQueryResponse wechatPayRefundQuery(WechatPayRefundQuery refundQuery);
	//public AlipayRefundQueryResponse alipayRefundQuery(AlipayRefundQuery refundQuery);
	//public UnionpayRefundQueryResponse unionpayRefundQuery(UnionpayRefundQuery refundQuery);

}