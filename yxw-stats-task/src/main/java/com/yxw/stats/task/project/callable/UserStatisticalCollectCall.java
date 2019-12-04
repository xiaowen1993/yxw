package com.yxw.stats.task.project.callable;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.entity.project.Hospital;
import com.yxw.stats.service.project.HospitalService;
import com.yxw.stats.service.project.UserStatsService;
import com.yxw.stats.utils.UserStatsUtil;
import com.yxw.utils.DateUtils;

public class UserStatisticalCollectCall implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(UserStatisticalCollectCall.class);

	private HospitalService wechatHospitalService = SpringContextHolder.getBean("hospitalService");

	private HospitalService alipayHospitalService = SpringContextHolder.getBean("alipayHospitalService");

	private UserStatsService wechatUserStatsService = SpringContextHolder.getBean("userStatsService");

	private UserStatsService alipayUserStatsService = SpringContextHolder.getBean("alipayUserStatsService");

	/**
	 * 平台类型
	 */
	private String platformType;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public UserStatisticalCollectCall(String platformType) {
		super();
		this.platformType = platformType;
	}

	@Override
	public String call() throws Exception {

		String result = "";

		try {
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
				wechatHandle(platformType);
			}

			if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
				alipayHandle(platformType);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String wechatHandle(String platformType) throws Exception {
		List<Hospital> hospitals = wechatHospitalService.findAllHospital();

		for (Hospital hospital : hospitals) {
			String beginDate = CommConstants.INIT_DATE;

			String statsMaxDate = wechatUserStatsService.findCurrUserStatsMaxDate(String.valueOf(hospital.getId()));
			if (!StringUtils.isBlank(statsMaxDate)) {
				beginDate = statsMaxDate;
			} else {
				beginDate = "2015-01-01";
			}

			Calendar calendar = Calendar.getInstance();
			String nowDate = DateUtils.formatDate(calendar.getTime());

			calendar.setTime(DateUtils.StringToDateYMD(beginDate));
			calendar.add(Calendar.DATE, 1);
			beginDate = DateUtils.formatDate(calendar.getTime());

			String appId = hospital.getAppid();
			String hospitalId = String.valueOf(hospital.getId());

			if (!StringUtils.isBlank(appId) && !StringUtils.equals(nowDate, beginDate)) {

				UserStatsUtil.syncWechatData(beginDate, appId, hospitalId, platformType);
			}

		}

		return "OK";
	}

	private String alipayHandle(String platformType) throws Exception {
		List<Hospital> hospitals = alipayHospitalService.findAllHospital();

		for (Hospital hospital : hospitals) {
			String beginDate = CommConstants.INIT_DATE;

			String statsMaxDate = alipayUserStatsService.findCurrUserStatsMaxDate(String.valueOf(hospital.getId()));
			if (!StringUtils.isBlank(statsMaxDate)) {
				beginDate = statsMaxDate;
			} else {
				beginDate = "2015-01-01";
			}

			Calendar calendar = Calendar.getInstance();
			String nowDate = DateUtils.formatDate(calendar.getTime());

			calendar.setTime(DateUtils.StringToDateYMD(beginDate));
			calendar.add(Calendar.DATE, 1);
			beginDate = DateUtils.formatDate(calendar.getTime());

			String appId = hospital.getAppid();
			String hospitalId = String.valueOf(hospital.getId());

			if (!StringUtils.isBlank(appId) && !StringUtils.equals(nowDate, beginDate)) {

				UserStatsUtil.syncAlipayData(beginDate, appId, hospitalId, platformType);
			}

		}

		return "OK";
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

}