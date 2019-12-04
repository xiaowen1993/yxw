/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2016 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2016-8-11</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.stats.service.platform;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.UserSubscribeDao;
import com.yxw.stats.entity.platform.UserSubscribe;
import com.yxw.stats.utils.JO;
import com.yxw.stats.utils.WechatUtils;
import com.yxw.utils.DateUtils;
import com.yxw.utils.JSONUtils;
import com.yxw.utils.StringUtils;

@Service("userSubscribeService")
public class UserSubscribeServiceImpl extends BaseServiceImpl<UserSubscribe, String> implements UserSubscribeService {

	private Logger logger = LoggerFactory.getLogger(UserSubscribeServiceImpl.class);

	@Autowired
	private UserSubscribeDao userSubscribeDao;

	@Override
	protected BaseDao<UserSubscribe, String> getDao() {
		// TODO Auto-generated method stub
		return userSubscribeDao;
	}

	@Override
	public List<UserSubscribe> findUserSubscribes(String beginDate, String hospitalId, String platformMode, String appId, String appSecret) {
		logger.info("-----------insertInvokeUserSubscribes begin---------");
		logger.info("beginDate:{},hospitalId:{},platformMode:{},appId:{},appSecret:{}", beginDate, hospitalId, platformMode, appId,
				appSecret);
		List<UserSubscribe> userSubscribes = new ArrayList<UserSubscribe>();
		if (org.apache.commons.lang3.StringUtils.equals(hospitalId, "441fb80035b2440086507e04afb784ca")) {

			Date begin = DateUtils.StringToDateYMD(beginDate);
			Date end = DateUtils.yesterdayDate();
			long diff = DateUtils.DateDiff(begin, end);
			Calendar calendar = DateUtils.date2Calendar(begin);
			String begin_date = null;
			String end_date = null;
			diff = diff + 1;
			int i;

			for (i = 0; i < diff / 7; i++) {

				begin_date = DateUtils.dateToStringYMD(calendar.getTime());

				calendar.add(Calendar.DATE, 6);
				end_date = DateUtils.dateToStringYMD(calendar.getTime());
				calendar.add(Calendar.DATE, 1);

				logger.info("第{}阶段获取数据,begin_date:{},end_date:{}", i + 1, begin_date, end_date);

				String accessToken = WechatUtils.getAccessTokenByPublicInterface(appId, appSecret);

				logger.info("xxxxx--" + "hospitalId:{},appId:{},accessToken:{}", hospitalId, appId, accessToken);

				if (!org.apache.commons.lang3.StringUtils.isBlank(accessToken)
						&& !org.apache.commons.lang3.StringUtils.equals(accessToken, "null")) {
					insideInputUserSubscribeMap(begin_date, end_date, hospitalId, platformMode, appId, accessToken, userSubscribes);
				}

			}

			if (diff % 7 != 0) {

				begin_date = DateUtils.dateToStringYMD(calendar.getTime());
				calendar.add(Calendar.DATE, (int) ( diff % 7 ) - 1);
				end_date = DateUtils.dateToStringYMD(calendar.getTime());
				logger.info("第{}阶段获取数据,begin_date:{},end_date:{}", i + 1, begin_date, end_date);

				String accessToken = WechatUtils.getAccessTokenByPublicInterface(appId, appSecret);

				logger.info("xxxxx--" + "hospitalId:{},appId:{},accessToken{}", hospitalId, appId, accessToken);

				if (!org.apache.commons.lang3.StringUtils.isBlank(accessToken)
						&& !org.apache.commons.lang3.StringUtils.equals(accessToken, "null")) {
					insideInputUserSubscribeMap(begin_date, end_date, hospitalId, platformMode, appId, accessToken, userSubscribes);
				}
			}

			logger.info("-----------insertInvokeUserSubscribes end---------");
		}
		return userSubscribes;
	}

	private void insideInputUserSubscribeMap(String begin_date, String end_date, String hospitalId, String platformMode, String appId,
			String accessToken, List<UserSubscribe> userSubscribes) {

		JO cumulate_result =
				new JO(org.apache.commons.lang3.StringUtils.defaultIfBlank(WechatUtils.getUserCumulate(accessToken, begin_date, end_date),
						"{}"));
		JO summary_result =
				new JO(org.apache.commons.lang3.StringUtils.defaultIfBlank(WechatUtils.getUserSummary(accessToken, begin_date, end_date),
						"{}"));
		logger.info("获取用户增减数据  xxxgetusersummary response:" + summary_result + "hospitalId:" + hospitalId + "accessToken:" + accessToken);
		logger.info("获取累计用户数据  xxxgetusercumulate response:" + cumulate_result + "hospitalId:" + hospitalId + "accessToken:" + accessToken);

		String cumulate_list = cumulate_result.myJSONObject().getString("list");
		String summary_list = summary_result.myJSONObject().getString("list");

		if (StringUtils.isNullOrBlank(cumulate_list) && StringUtils.isNullOrBlank(summary_list)) {
			logger.info("获取关注数为空,请检查微信公众号配置信息是否有误!");

		} else {

			List<Map<String, Object>> cumulateArray = JSONUtils.parseJSONList(cumulate_result.myJSONObject().getString("list"));
			List<Map<String, Object>> summaryArray = JSONUtils.parseJSONList(summary_result.myJSONObject().getString("list"));

			for (Map<String, Object> cumulateMap : cumulateArray) {
				if (!cumulateMap.get("user_source").toString().equals("0")) {
					continue;
				}
				UserSubscribe userSubscribe = new UserSubscribe();
				userSubscribe.setId(UUID.randomUUID().toString());
				userSubscribe.setAppId(appId);
				userSubscribe.setHospitalId(hospitalId);
				userSubscribe.setPlatformMode(platformMode);
				Integer new_user = 0;
				Integer cancel_user = 0;
				String ref_date = (String) cumulateMap.get("ref_date");
				Integer cumulate_user = (Integer) cumulateMap.get("cumulate_user");
				for (Map<String, Object> summaryMap : summaryArray) {

					if (summaryMap.get("ref_date").equals(ref_date)) {
						new_user = (Integer) summaryMap.get("new_user") + new_user;
						cancel_user = (Integer) summaryMap.get("cancel_user") + cancel_user;

					}

				}

				userSubscribe.setRefDate(ref_date);
				userSubscribe.setCumulateUser(cumulate_user);

				userSubscribe.setNewUser(new_user);
				userSubscribe.setCancelUser(cancel_user);
				userSubscribes.add(userSubscribe);
			}
		}
	}

	@Override
	public List<UserSubscribe> getUserSubscribes(String beginDate, String endDate, String appId, String hospitalId, String platformMode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("appId", appId);
		map.put("hospitalId", hospitalId);
		map.put("platformMode", platformMode);
		return userSubscribeDao.getUserSubscribes(map);
	}

	@Override
	public boolean verifyRefDate(String date, String appId, String hospitalId, String platformMode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", date);
		map.put("appId", appId);
		map.put("hospitalId", hospitalId);
		map.put("platformMode", platformMode);
		UserSubscribe userSubscribe = userSubscribeDao.getUserSubscribeByDate(map);
		if (userSubscribe == null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public UserSubscribe getUserSubscribeLastOne(String appId, String hospitalId, String platformMode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("appId", appId);
		map.put("hospitalId", hospitalId);
		map.put("platformMode", platformMode);
		return userSubscribeDao.getUserSubscribeLastOne(map);
	}

}
