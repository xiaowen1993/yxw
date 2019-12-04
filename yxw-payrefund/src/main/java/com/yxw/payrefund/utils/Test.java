/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月6日
 * Created by cwq
 */
package com.yxw.payrefund.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.payrefund.WechatPayAsynResponse;
import com.yxw.framework.common.http.HttpClientUtil;

/**
 * @ClassName: com.ctb.mobile.utils.Test
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年5月6日 下午9:16:06
 */

public class Test {
	public static void main(String[] args) throws Exception {
		String payAsynResponseStr =
				"{\"agtOrderNo\":\"4200000298201905078848339186\",\"appId\":\"wx7245e798af225271\",\"attach\":\"{\\\"isRestful\\\":true,\\\"tradeMode\\\":\\\"1\\\",\\\"code\\\":\\\"111\\\",\\\"notifyUrl\\\":\\\"http://sjb65x.natappfree.cc/mobile/p/n\\\"}\",\"mchId\":\"1227059102\",\"openId\":\"o9ymnt7Fknhj3kHTvgR3b0ASDRgU\",\"orderNo\":\"Y2501201905071628561400030000002\",\"resultCode\":\"success\",\"totalFee\":\"1\",\"tradeState\":\"success\",\"tradeTime\":\"2019-05-07 16:27:42\",\"tradeType\":\"JSAPI\"}";
		/* WechatPayAsynResponse payAsynResponse = JSONObject.parseObject(payAsynResponseStr, WechatPayAsynResponse.class);
		 HashMapper<WechatPayAsynResponse, String, String> mapper = new DecoratingStringHashMapper<WechatPayAsynResponse>(
		         new BeanUtilsHashMapper<WechatPayAsynResponse>(WechatPayAsynResponse.class));*/

		WechatPayAsynResponse payAsynResponse = JSONObject.parseObject(payAsynResponseStr, WechatPayAsynResponse.class);
		Map<String, String> params = BeanUtils.describe(payAsynResponse);

		HttpClientUtil.getInstance().post("http://sjb65x.natappfree.cc/mobile/p/n", params);
	}
}
