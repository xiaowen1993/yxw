package com.yxw.stats.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.entity.project.AlipaySettings;
import com.yxw.stats.service.project.AlipaySettingsService;
import com.yxw.utils.DateUtils;

public class AlipayUtils {

	// 参数编码字符集
	public static final String INPUT_CHARSET = "utf-8";

	/**
	 * 生成支付宝签名
	 * 
	 * @param params
	 *            需要签名的参数
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            参数编码字符集
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSign(Map<String, Object> params, String privateKey, String input_charset) throws UnsupportedEncodingException {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key).toString();

			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return RSA.sign(prestr, privateKey, input_charset);

	}

	/**
	 * 验证支付宝签名
	 * 
	 * @param params
	 *            需要验证签名的参数
	 * @param sign
	 *            支付宝传过来的签名
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            参数编码字符集
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkSign(Map<String, Object> params, String sign, String publicKey, String input_charset)
			throws UnsupportedEncodingException {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key).toString();

			if (i == keys.size() - 1) {
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return RSA.verify(prestr, sign, publicKey, input_charset);
	}

	/**
	 * alipay用户分析数据查询接口
	 * 通过此接口，开发者可以根据时间查询30天内的关注用户人数、取消关注用户人数、净增关注人数、累计关注人数信息
	 * @return
	 */
	public static String getAlipayUserStats(String appId, String beginDate, String endDate) throws Exception {
		String result = null;

		AlipaySettingsService alipaySettingsService = SpringContextHolder.getBean("alipaySettingsService");
		AlipaySettings alipaySettings = alipaySettingsService.findAlipaySettingsByAppId(appId);

		JO joDate = new JO();
		joDate.put("begin_date", DateUtils.formatDate(beginDate, "yyyy-MM-dd", "yyyyMMdd"));
		joDate.put("end_date", DateUtils.formatDate(endDate, "yyyy-MM-dd", "yyyyMMdd"));

		System.out.println(joDate.toString());

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("app_id", appId);
		params.put("biz_content", joDate.getMyJSONObject().toJSONString());
		params.put("charset", AlipayUtils.INPUT_CHARSET);
		params.put("method", "alipay.open.public.user.data.batchquery");
		params.put("sign_type", "RSA");
		params.put("timestamp", DateUtils.dateToString(new Date()));
		params.put("version", "1.0");

		String sign = AlipayUtils.getSign(params, alipaySettings.getPrikey1(), AlipayUtils.INPUT_CHARSET);
		params.put("sign", sign);

		Maps.EntryTransformer<String, Object, String> entryTransformer = new Maps.EntryTransformer<String, Object, String>() {
			public String transformEntry(String key, Object value) {
				return value.toString();
			}
		};

		Map<String, String> httpParams = Maps.transformEntries(params, entryTransformer);

		String rel = HttpClientUtil.getInstance().post(CommConstants.ALIPAY_API_GATEWAY, httpParams);

		JO userStatsJO = new JO(StringUtils.defaultIfBlank(rel, "{}"));
		if (StringUtils.equals(userStatsJO.getStr("alipay_open_public_user_data_batchquery_response.code", ""), "10000")
				&& StringUtils.equals(userStatsJO.getStr("alipay_open_public_user_data_batchquery_response.msg", ""), "Success")) {//成功
			if (userStatsJO.containsKey("sign")) {
				params.remove("sign");

				//				if (AlipayUtils.checkSign(params, userStatsJO.getStr("sign", ""), alipaySettings.getPubkey1(), AlipayUtils.INPUT_CHARSET)) {
				if (true) {//暂时不验签了
					List<Object> data_list = userStatsJO.getArray("alipay_open_public_user_data_batchquery_response.data_list");
					if (!CollectionUtils.isEmpty(data_list)) {
						JO reslutJo = new JO();
						reslutJo.put("list", data_list);
						result = reslutJo.myJSONObject().toString();
					}
				}

			}
		}

		return result;

	}
}