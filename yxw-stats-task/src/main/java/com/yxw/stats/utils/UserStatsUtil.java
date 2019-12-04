package com.yxw.stats.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.entity.project.UserStats;
import com.yxw.stats.service.project.UserStatsService;
import com.yxw.utils.DateUtils;

public class UserStatsUtil {

	public static void syncWechatData(String beginDate, String appId, String hospitalId, String platformType) throws Exception {

		// 固定开始时间, 结束时间最大为昨天, 区间最大为range天
		Calendar calendar = Calendar.getInstance();
		int day_of_year_now = calendar.get(Calendar.DAY_OF_YEAR);
		int year_now = calendar.get(Calendar.YEAR);

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		int day_of_year_beginDate = calendar.get(Calendar.DAY_OF_YEAR);
		int year_beginDate = calendar.get(Calendar.YEAR);

		// beginDate lt yesterday || beginDate's year gt now year
		if ( ( day_of_year_now - day_of_year_beginDate <= 0 && year_beginDate == year_now ) || year_beginDate > year_now) {
			return;
		}

		if (day_of_year_now - day_of_year_beginDate > CommConstants.WECHAT_RANGE || year_now != year_beginDate) {
			calendar.add(Calendar.DATE, CommConstants.WECHAT_RANGE - 1);
		} else {
			calendar.add(Calendar.DATE, day_of_year_now - day_of_year_beginDate - 1);
		}

		String endDate = DateUtils.formatDate(calendar.getTime());

		Map<String, JO> saveData = new HashMap<String, JO>();

		// new_user	        新增的用户数量
		// cancel_user	    取消关注的用户数量，new_user减去cancel_user即为净增用户数量
		// cumulate_user	总用户量
		String accessToken = WechatUtils.getAccessTokenByPublicInterface(appId);
		JO userCumulateJO = new JO(StringUtils.defaultIfBlank(WechatUtils.getUserCumulate(accessToken, beginDate, endDate), "{}"));
		//System.out.println("userCumulateJO: " + userCumulateJO.toJsonString());

		if (userCumulateJO.containsKey("errcode")) {
			return;
		}

		List<JO> list = new ArrayList<JO>();
		if (userCumulateJO.containsKey("list")) {
			list = userCumulateJO.getJOArray("list");
		}
		for (JO jo : list) {
			String date = jo.getStr("ref_date", "");
			if (!StringUtils.isBlank(date)) {
				JO joItemByDate = saveData.get(jo.S("ref_date"));

				if (joItemByDate == null) {
					saveData.put(date, jo);
				} else {
					joItemByDate.put("cumulate_user", joItemByDate.getInt("cumulate_user", 0) + jo.getInt("cumulate_user", 0));
				}
			}
		}

		JO userSummaryJO = new JO(StringUtils.defaultIfBlank(WechatUtils.getUserSummary(accessToken, beginDate, endDate), "{}"));
		//System.out.println("userSummaryJO: " + userSummaryJO.toJsonString());

		if (userSummaryJO.containsKey("list")) {
			list = userSummaryJO.getJOArray("list");
		}
		for (JO jo : list) {
			String date = jo.getStr("ref_date", "");
			if (!StringUtils.isBlank(date)) {
				JO joItemByDate = saveData.get(date);

				if (joItemByDate == null) {
					saveData.put(date, jo);
				} else {
					joItemByDate.put("new_user", joItemByDate.getInt("new_user", 0) + jo.getInt("new_user", 0));
					joItemByDate.put("cancel_user", joItemByDate.getInt("cancel_user", 0) + jo.getInt("cancel_user", 0));
				}
			}

		}

		// 保存数据
		saveDataInTable(saveData, appId, hospitalId, platformType);

		calendar.add(Calendar.DATE, 1);
		if (day_of_year_now - calendar.get(Calendar.DAY_OF_YEAR) > 0 || year_now != year_beginDate) {
			String nextBeginDate = DateUtils.formatDate(calendar.getTime());
			System.out.println("nextBeginDate: " + nextBeginDate);

			syncWechatData(nextBeginDate, appId, hospitalId, platformType);
		}

	}

	public static void saveDataInTable(Map<String, JO> saveData, String appId, String hospitalId, String platformType) throws Exception {

		UserStatsService userStatsService = SpringContextHolder.getBean("userStatsService");
		NameFilter nameFilter = new NameFilter() {// 序列化时修改key
					@Override
					public String process(Object object, String name, Object value) {
						if (name.equals("ref_date")) {
							return "date";
						}
						if (name.equals("cumulate_user")) {
							return "cumulateuser";
						}
						if (name.equals("new_user")) {
							return "newuser";
						}
						if (name.equals("cancel_user")) {
							return "canceluser";
						}

						return name;
					}
				};

		if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			userStatsService = SpringContextHolder.getBean("alipayUserStatsService");

			nameFilter = new NameFilter() {// 序列化时修改key
						@Override
						public String process(Object object, String name, Object value) {
							if (name.equals("cumulate_user_cnt")) {
								return "cumulateuser";
							}
							if (name.equals("new_user_cnt")) {
								return "newuser";
							}
							if (name.equals("cancel_user_cnt")) {
								return "canceluser";
							}

							return name;
						}
					};
		}

		List<JO> datas = Lists.newArrayList(saveData.values());
		List<JSONObject> dataStrs = Lists.transform(datas, new Function<JO, JSONObject>() {
			@Override
			public JSONObject apply(JO jo) {
				jo.put("appid", appId);
				jo.put("hospitalid", hospitalId);
				return jo.getMyJSONObject();
			}
		});

		List<UserStats> userStatsEntitys =
				JSONObject.parseArray(JSONArray.toJSONString(dataStrs, new SerializeFilter[] { nameFilter }), UserStats.class);

		if (!CollectionUtils.isEmpty(userStatsEntitys)) {
			userStatsService.batchInsertUserStatsData(userStatsEntitys);
		}
	}

	public static void syncAlipayData(String beginDate, String appId, String hospitalId, String platformType) throws Exception {

		// 固定开始时间, 结束时间最大为昨天, 区间最大为range天
		Calendar calendar = Calendar.getInstance();
		int day_of_year_now = calendar.get(Calendar.DAY_OF_YEAR);
		int year_now = calendar.get(Calendar.YEAR);

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		int day_of_year_beginDate = calendar.get(Calendar.DAY_OF_YEAR);
		int year_beginDate = calendar.get(Calendar.YEAR);

		// beginDate lt yesterday || beginDate's year gt now year
		if ( ( day_of_year_now - day_of_year_beginDate <= 0 && year_beginDate == year_now ) || year_beginDate > year_now) {
			return;
		}

		if (day_of_year_now - day_of_year_beginDate > CommConstants.ALIPAY_RANGE || year_now != year_beginDate) {
			calendar.add(Calendar.DATE, CommConstants.ALIPAY_RANGE - 1);
		} else {
			calendar.add(Calendar.DATE, day_of_year_now - day_of_year_beginDate - 1);
		}

		String endDate = DateUtils.formatDate(calendar.getTime());

		Map<String, JO> saveData = new HashMap<String, JO>();

		List<JO> list = new ArrayList<JO>();

		JO userStatsJO = new JO(StringUtils.defaultIfBlank(AlipayUtils.getAlipayUserStats(appId, beginDate, endDate), "{}"));
		//		System.out.println("userStatsJO: " + userStatsJO.toJsonString());

		if (userStatsJO.containsKey("list")) {
			list = userStatsJO.getJOArray("list");
		}

		for (JO jo : list) {
			String date = jo.getStr("date", "");
			if (!StringUtils.isBlank(date)) {
				JO joItemByDate = saveData.get(date);

				if (joItemByDate == null) {
					saveData.put(date, jo);
				} else {
					joItemByDate.put("new_user_cnt", joItemByDate.getInt("new_user_cnt", 0) + jo.getInt("new_user_cnt", 0));
					joItemByDate.put("cancel_user_cnt", joItemByDate.getInt("cancel_user_cnt", 0) + jo.getInt("cancel_user_cnt", 0));
					joItemByDate.put("grow_user_cnt", joItemByDate.getInt("grow_user_cnt", 0) + jo.getInt("grow_user_cnt", 0));
					joItemByDate.put("cumulate_user_cnt", joItemByDate.getInt("cumulate_user_cnt", 0) + jo.getInt("cumulate_user_cnt", 0));
				}
			}
		}

		// 保存数据
		saveDataInTable(saveData, appId, hospitalId, platformType);

		calendar.add(Calendar.DATE, 1);
		if (day_of_year_now - calendar.get(Calendar.DAY_OF_YEAR) > 0 || year_now != year_beginDate) {
			String nextBeginDate = DateUtils.formatDate(calendar.getTime());
			System.out.println("nextBeginDate: " + nextBeginDate + "appId:" + appId);

			syncAlipayData(nextBeginDate, appId, hospitalId, platformType);
		}

	}

}
