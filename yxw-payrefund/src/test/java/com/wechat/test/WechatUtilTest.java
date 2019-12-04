package com.wechat.test;

import java.io.File;
import java.util.Map;

import com.wechat.WechatUtil;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.Refund;
import com.yxw.commons.generator.OrderNoGenerator;

public class WechatUtilTest {

	public static void main(String[] args) {
		try {
			//System.out.println(System.nanoTime());
			//			String orderNo = "";
			//			String refundOrderNo = OrderNoGenerator.genOrderNo(
			//	    			String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL), String.valueOf(TradeConstant.TRADE_MODE_APP_WECHAT_VAL),
			//	    			Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 9, 
			//	    			"99", "");
			//			
			//			System.out.println(orderNo);
			//			System.out.println(refundOrderNo);

			String orderNo =
					OrderNoGenerator.genOrderNo(String.valueOf(BizConstant.MODE_TYPE_INNER_WECHAT_VAL),
							String.valueOf(TradeConstant.TRADE_MODE_APP_WECHAT_VAL), Integer.valueOf(OrderConstant.ORDER_TYPE_PAYMENT), 2,
							"99", "oSJHhso7ydNV6Kq7JEmNjUwQA8V8");
			System.out.println(orderNo);

			System.out.println(Math.abs(orderNo.hashCode()));

			//testRefund();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void testRefund(String orderNo, String refundOrderNo) throws Exception {
		Refund refund = new Refund();
		//refund.setAgtOrderNo("");
		refund.setOrderNo(orderNo);
		refund.setRefundOrderNo(refundOrderNo);
		refund.setTotalFee("1");
		refund.setRefundFee("1");
		refund.setRefundDesc("test");

		String appId = "wx7245e798af225271";
		String mchId = "1227059102";
		String key = "abcdefghijklmnopqrstuvwxyz910629";

		Map<String, String> refundParams =
				WechatUtil.genRefundParams(appId, mchId, refund.getAgtOrderNo(), refund.getOrderNo(), refund.getRefundOrderNo(),
						refund.getTotalFee(), refund.getRefundFee(), refund.getRefundDesc(), key);

		String certPath =
				"/Users/YangXuewen/Documents/eclipse/workspace/yxw/yxw-payrefund/src/main/resources/certs"
						.concat(String.valueOf(File.separatorChar)).concat(appId).concat(".p12");
		System.out.println("加载微信退费证书：" + certPath);
		File cert = new File(certPath);

		String refundRes = WechatUtil.refund(refundParams, cert);
		System.out.println(refundRes);
	}

}
