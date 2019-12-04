package com.yxw.stats.utils;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;

import com.alibaba.fastjson.JSONObject;
import com.yxw.stats.entity.project.UserStats;
import com.yxw.stats.entity.stats.YxDataUserStats;
import com.yxw.utils.DateUtils;

public class Test {

	//jwmVO8gIc11j38/s7uaIt1cbl3wiVc5bouBgDCeZpRKdYNTpMW4l46UHwqUmVtLkblm8dHj7qz+/7wJ2XJ0kN6VLqqNMCYs6ymCnqDAL8hfnuUNicDjxscQ2XUVLyDbWdpEnvn/UH4twSHx4IOKC9aAnWOf/U/VKfS8xEQHabeU=

	public static void main(String[] args) throws Exception {
		/*JO joDate = new JO();
		joDate.put("begin_date", DateUtils.formatDate("2018-10-11", "yyyy-MM-dd", "yyyyMMdd"));
		joDate.put("end_date", DateUtils.formatDate("2018-11-05", "yyyy-MM-dd", "yyyyMMdd"));

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("app_id", "2015020500030484");
		params.put("biz_content", joDate.getMyJSONObject().toJSONString());
		params.put("charset", AlipayUtils.INPUT_CHARSET);
		params.put("method", "alipay.open.public.user.data.batchquery");
		params.put("sign_type", "RSA");
		params.put("timestamp", "2018-11-06 15:02:02");
		params.put("version", "1.0");

		String privateKey =
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKbvTGzeEnW9vojDxY8vAOusaTk00d3ZFx+m5qGwqWo4ChYy2x/F308qV8x6SueYeu7HQULZm4URTBFSngRv0udObtiZ2i0cHN+9aNN003PRpoPsvmkllfcv+IfqErpfaoXNHsfOSsgm2pgmduv0sH+lAFFQw4ViznrjF51xk7vZAgMBAAECgYEAk8W1Y5HZyXxj0/FpSuyS5gzDQK6vMLFhtNUrx+n4rlxZIXl3W9LvcMbM3edLs2PZPtIHxUOYVd0P9y2pT8glasqWhEgU8MEljtNTevrtufuaiYHw4W4A8eN4YD6vxJWYpWj+jb0EUKiPjV81x3PppBhqJYDDwh9t2RVLi3vmYzkCQQDOubsFuvw3rmKnJe05E14KvNKCppdkOFK6mR/vsdrFuK18i3wwk64pSUzERfMeZEeUjFNjslwDMpgi23yowopLAkEAzrmPSr3Z8Yt6+Uk3juRPPhSoxa6R2unw8CR2UtO7K/QcYxdh+08YT07ZEGaVb/Y0wSV/p/5S/TSJks63J6676wJBALjk9ZlSfHNwzVuiWGJMKDiCWeXxCkbg+V1bx2yjLoDZhJF4UENCUusqXDbQOXpdRoheWdc5l4Jkph3HId/u37ECQAuV+MLumv1fnNfF07hNcWeATuktXH1RaTB8SqlftnjDKKghElu8VhdzytSRe0SYHuLIUDxBE7OfMyqoLTmrIFsCQANlBx0PhLaKq5A7ceJLh6lqLRqcMeBC/Bj41N2p+WS8rp5BD1C1N1gGdznG3bLNotYUg7PP6Pm75vKJghlB4VM=";
		String sign = AlipayUtils.getSign(params, privateKey, AlipayUtils.INPUT_CHARSET);
		System.out.println(sign);
		JO jo =
				new JO(
						"{\"alipay_open_public_user_data_batchquery_response\":{\"msg\":\"Success\",\"data_list\":[{\"date\":\"20181011\",\"cumulate_user_cnt\":325383,\"grow_user_cnt\":111,\"cancel_user_cnt\":5,\"new_user_cnt\":116},{\"date\":\"20181012\",\"cumulate_user_cnt\":325492,\"grow_user_cnt\":110,\"cancel_user_cnt\":8,\"new_user_cnt\":118},{\"date\":\"20181013\",\"cumulate_user_cnt\":325622,\"grow_user_cnt\":130,\"cancel_user_cnt\":4,\"new_user_cnt\":134},{\"date\":\"20181014\",\"cumulate_user_cnt\":325729,\"grow_user_cnt\":107,\"cancel_user_cnt\":11,\"new_user_cnt\":118},{\"date\":\"20181015\",\"cumulate_user_cnt\":325883,\"grow_user_cnt\":154,\"cancel_user_cnt\":10,\"new_user_cnt\":164},{\"date\":\"20181016\",\"cumulate_user_cnt\":326009,\"grow_user_cnt\":126,\"cancel_user_cnt\":10,\"new_user_cnt\":136},{\"date\":\"20181017\",\"cumulate_user_cnt\":326122,\"grow_user_cnt\":113,\"cancel_user_cnt\":10,\"new_user_cnt\":123},{\"date\":\"20181018\",\"cumulate_user_cnt\":326213,\"grow_user_cnt\":91,\"cancel_user_cnt\":12,\"new_user_cnt\":103},{\"date\":\"20181019\",\"cumulate_user_cnt\":326329,\"grow_user_cnt\":116,\"cancel_user_cnt\":8,\"new_user_cnt\":124},{\"date\":\"20181020\",\"cumulate_user_cnt\":326417,\"grow_user_cnt\":88,\"cancel_user_cnt\":7,\"new_user_cnt\":95},{\"date\":\"20181021\",\"cumulate_user_cnt\":326513,\"grow_user_cnt\":96,\"cancel_user_cnt\":2,\"new_user_cnt\":98},{\"date\":\"20181022\",\"cumulate_user_cnt\":326653,\"grow_user_cnt\":140,\"cancel_user_cnt\":9,\"new_user_cnt\":149},{\"date\":\"20181023\",\"cumulate_user_cnt\":326778,\"grow_user_cnt\":125,\"cancel_user_cnt\":7,\"new_user_cnt\":132},{\"date\":\"20181024\",\"cumulate_user_cnt\":326882,\"grow_user_cnt\":105,\"cancel_user_cnt\":15,\"new_user_cnt\":120},{\"date\":\"20181025\",\"cumulate_user_cnt\":326990,\"grow_user_cnt\":108,\"cancel_user_cnt\":11,\"new_user_cnt\":119},{\"date\":\"20181026\",\"cumulate_user_cnt\":327105,\"grow_user_cnt\":115,\"cancel_user_cnt\":13,\"new_user_cnt\":128},{\"date\":\"20181027\",\"cumulate_user_cnt\":327212,\"grow_user_cnt\":108,\"cancel_user_cnt\":9,\"new_user_cnt\":117},{\"date\":\"20181028\",\"cumulate_user_cnt\":327281,\"grow_user_cnt\":69,\"cancel_user_cnt\":10,\"new_user_cnt\":79},{\"date\":\"20181029\",\"cumulate_user_cnt\":327417,\"grow_user_cnt\":136,\"cancel_user_cnt\":8,\"new_user_cnt\":144},{\"date\":\"20181030\",\"cumulate_user_cnt\":327543,\"grow_user_cnt\":126,\"cancel_user_cnt\":13,\"new_user_cnt\":139},{\"date\":\"20181031\",\"cumulate_user_cnt\":327677,\"grow_user_cnt\":134,\"cancel_user_cnt\":12,\"new_user_cnt\":146},{\"date\":\"20181101\",\"cumulate_user_cnt\":327815,\"grow_user_cnt\":138,\"cancel_user_cnt\":9,\"new_user_cnt\":147},{\"date\":\"20181102\",\"cumulate_user_cnt\":327937,\"grow_user_cnt\":123,\"cancel_user_cnt\":14,\"new_user_cnt\":137},{\"date\":\"20181103\",\"cumulate_user_cnt\":328031,\"grow_user_cnt\":94,\"cancel_user_cnt\":11,\"new_user_cnt\":105},{\"date\":\"20181104\",\"cumulate_user_cnt\":328117,\"grow_user_cnt\":87,\"cancel_user_cnt\":10,\"new_user_cnt\":97},{\"date\":\"20181105\",\"cumulate_user_cnt\":328269,\"grow_user_cnt\":153,\"cancel_user_cnt\":10,\"new_user_cnt\":163}],\"code\":\"10000\"},\"sign\":\"jwmVO8gIc11j38\\/s7uaIt1cbl3wiVc5bouBgDCeZpRKdYNTpMW4l46UHwqUmVtLkblm8dHj7qz+\\/7wJ2XJ0kN6VLqqNMCYs6ymCnqDAL8hfnuUNicDjxscQ2XUVLyDbWdpEnvn\\/UH4twSHx4IOKC9aAnWOf\\/U\\/VKfS8xEQHabeU=\"}");
		System.out.println(jo.getMyJSONObject().getJSONObject("alipay_open_public_user_data_batchquery_response"));

		Map<String, Object> map = jo.getMyJSONObject().getJSONObject("alipay_open_public_user_data_batchquery_response");*/

		/*Maps.EntryTransformer<String, Object, String> entryTransformer = new Maps.EntryTransformer<String, Object, String>() {
			public String transformEntry(String key, Object value) {
				return value.toString();
			}
		};

		Map<String, String> mapParams = Maps.transformEntries(map, entryTransformer);
		System.out.println(mapParams);*/

		/*	String publicKey =
					"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCm70xs3hJ1vb6Iw8WPLwDrrGk5NNHd2RcfpuahsKlqOAoWMtsfxd9PKlfMekrnmHrux0FC2ZuFEUwRUp4Eb9LnTm7YmdotHBzfvWjTdNNz0aaD7L5pJZX3L/iH6hK6X2qFzR7HzkrIJtqYJnbr9LB/pQBRUMOFYs564xedcZO72QIDAQAB";
			String responseSign =
					"jwmVO8gIc11j38/s7uaIt1cbl3wiVc5bouBgDCeZpRKdYNTpMW4l46UHwqUmVtLkblm8dHj7qz+/7wJ2XJ0kN6VLqqNMCYs6ymCnqDAL8hfnuUNicDjxscQ2XUVLyDbWdpEnvn/UH4twSHx4IOKC9aAnWOf/U/VKfS8xEQHabeU=";

			boolean a = AlipayUtils.checkSign(params, responseSign, publicKey, AlipayUtils.INPUT_CHARSET);
			System.out.println(a);*/

		UserStats userStats = new UserStats();
		userStats.setAppid("ssss");
		userStats.setCanceluser(12l);
		userStats.setCumulateuser(23l);
		userStats.setDate(DateUtils.StringToDateYMD("2108-01-11"));
		userStats.setHospitalid("13");
		userStats.setId("23");
		userStats.setNewuser(14l);

		YxDataUserStats yxDataUserStats = new YxDataUserStats();
		BeanUtils.copyProperties(userStats, yxDataUserStats);

		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(YxDataUserStats.class, "newuserWechat");
		MethodParameter mp = BeanUtils.getWriteMethodParameter(pd);
		mp.getMethod().invoke(yxDataUserStats, 55l);

		System.out.println(JSONObject.toJSONString(yxDataUserStats));
	}
}
