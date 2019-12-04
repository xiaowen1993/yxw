package com.yxw.stats.task.project.callable;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.entity.project.Hospital;
import com.yxw.stats.entity.project.SysClinicStatistical;
import com.yxw.stats.entity.project.SysDepositStatistical;
import com.yxw.stats.entity.project.SysMedicalCardStatistical;
import com.yxw.stats.entity.project.SysRegStatistical;
import com.yxw.stats.entity.project.UserStats;
import com.yxw.stats.entity.stats.YxDataSysClinicStatistical;
import com.yxw.stats.entity.stats.YxDataSysDepositStatistical;
import com.yxw.stats.entity.stats.YxDataSysMedicalCardStatistical;
import com.yxw.stats.entity.stats.YxDataSysRegStatistical;
import com.yxw.stats.entity.stats.YxDataUserStats;
import com.yxw.stats.service.project.HospitalService;
import com.yxw.stats.service.project.SysClinicStatisticalService;
import com.yxw.stats.service.project.SysDepositStatisticalService;
import com.yxw.stats.service.project.SysMedicalCardStatisticalService;
import com.yxw.stats.service.project.SysRegStatisticalService;
import com.yxw.stats.service.project.UserStatsService;
import com.yxw.stats.service.stats.YxDataSysClinicStatisticalService;
import com.yxw.stats.service.stats.YxDataSysDepositStatisticalService;
import com.yxw.stats.service.stats.YxDataSysMedicalCardStatisticalService;
import com.yxw.stats.service.stats.YxDataSysPhoneNumberAttributionStatisticalService;
import com.yxw.stats.service.stats.YxDataSysRegStatisticalService;
import com.yxw.stats.service.stats.YxDataUserStatsService;
import com.yxw.stats.utils.JO;
import com.yxw.utils.DateUtils;

public class DataMergeCollectCall implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(DataMergeCollectCall.class);
	private HospitalService wechatHospitalService = SpringContextHolder.getBean("hospitalService");
	private HospitalService alipayHospitalService = SpringContextHolder.getBean("alipayHospitalService");
	private SysDepositStatisticalService wechatSysDepositStatisticalServicee = SpringContextHolder.getBean("sysDepositStatisticalService");
	private SysDepositStatisticalService alipaySysDepositStatisticalService = SpringContextHolder
			.getBean("alipaySysDepositStatisticalService");
	private SysClinicStatisticalService wechatSysClinicStatisticalService = SpringContextHolder.getBean("sysClinicStatisticalService");
	private SysClinicStatisticalService alipaySysClinicStatisticalService = SpringContextHolder
			.getBean("alipaySysClinicStatisticalService");
	private SysRegStatisticalService wechatSysRegStatisticalService = SpringContextHolder.getBean("sysRegStatisticalService");
	private SysRegStatisticalService alipaySysRegStatisticalService = SpringContextHolder.getBean("alipaySysRegStatisticalService");
	private UserStatsService wechatUserStatsService = SpringContextHolder.getBean("userStatsService");
	private UserStatsService alipayUserStatsService = SpringContextHolder.getBean("alipayUserStatsService");
	private SysMedicalCardStatisticalService wechatSysMedicalCardStatisticalService = SpringContextHolder
			.getBean("sysMedicalCardStatisticalService");
	private SysMedicalCardStatisticalService alipaySysMedicalCardStatisticalService = SpringContextHolder
			.getBean("alipaySysMedicalCardStatisticalService");
	private YxDataUserStatsService yxDataUserStatsService = SpringContextHolder.getBean("yxDataUserStatsService");
	private YxDataSysClinicStatisticalService yxDataSysClinicStatisticalService = SpringContextHolder
			.getBean("yxDataSysClinicStatisticalService");
	private YxDataSysDepositStatisticalService yxDataSysDepositStatisticalService = SpringContextHolder
			.getBean("yxDataSysDepositStatisticalService");
	private YxDataSysMedicalCardStatisticalService yxDataSysMedicalCardStatisticalService = SpringContextHolder
			.getBean("yxDataSysMedicalCardStatisticalService");
	private YxDataSysPhoneNumberAttributionStatisticalService yxDataSysPhoneNumberAttributionStatisticalService = SpringContextHolder
			.getBean("yxDataSysPhoneNumberAttributionStatisticalService");
	private YxDataSysRegStatisticalService yxDataSysRegStatisticalService = SpringContextHolder.getBean("yxDataSysRegStatisticalService");

	/**
	 * 平台类型
	 */
	private String platformType;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public DataMergeCollectCall(String platformType) {
		super();
		this.platformType = platformType;
	}

	private String gzyAlipayHospitalId = "14";
	private String gzyWechatHospitalId = "17";

	@Override
	public String call() throws Exception {

		String result = "";

		try {

			List<Hospital> hospitals = Lists.newArrayList();
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
				hospitals = wechatHospitalService.findAllHospital();
			} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
				hospitals = alipayHospitalService.findAllHospital(new Long(gzyAlipayHospitalId));
			}

			for (Hospital hospital : hospitals) {

				mergeUserStatsHandler(hospital);

				mergeMedicalCardStatistical(hospital);

				mergeClinicStatistical(hospital);

				mergeDepositStatistical(hospital);

				mergeRegStatistical(hospital);

			}

			result = "OK";
		} catch (Exception e) {
			logger.error("DataMergeTask 异常: " + e);
		}

		return result;
	}

	private void mergeUserStatsHandler(Hospital hospital) {// tb_user_cumulate
		// TODO Auto-generated method stub
		long startTimeMillisUserCumulate = System.currentTimeMillis();

		List<YxDataUserStats> yxDataUserStatsls = Lists.newArrayList();

		String hospitalId = String.valueOf(hospital.getId());

		String statsMaxDate = yxDataUserStatsService.findCurrUserStatsMaxDate(hospitalId);
		String beginDate = CommConstants.INIT_DATE;
		if (!StringUtils.isBlank(statsMaxDate)) {
			beginDate = statsMaxDate;
		} else {
			beginDate = "2015-01-01";
		}

		Calendar calendar = Calendar.getInstance();
		String endDate = DateUtils.formatDate(calendar.getTime());

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		calendar.add(Calendar.DATE, 1);
		beginDate = DateUtils.formatDate(calendar.getTime());

		List<UserStats> userStatsls = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", String.valueOf(hospital.getId()));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
			userStatsls = wechatUserStatsService.findUserStatsData(params);
		} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			userStatsls = alipayUserStatsService.findUserStatsData(params);
		}

		for (UserStats userStats : userStatsls) {

			JO userStatsJo = new JO(JSONObject.toJSONString(userStats));

			for (String column : CommConstants.USER_STATS_COLUMNS) {
				for (String pf : CommConstants.PLATFORMTYPES) {
					userStatsJo.put(column.concat(StringUtils.capitalize(pf)), 0);
				}
			}

			for (String column : CommConstants.USER_STATS_COLUMNS) {
				userStatsJo.put(column.concat(StringUtils.capitalize(platformType)), userStatsJo.getStr(column, "0"));

				userStatsJo.remove(column);
			}

			// ===========================================================
			// ============== 处理广中药支付宝和微信的数据合并  ==============
			// ===========================================================
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM) && StringUtils.equals(gzyWechatHospitalId, hospitalId)) {

				Map<String, Object> tempParams = new HashMap<String, Object>();
				params.put("hospitalId", String.valueOf(hospital.getId()));
				params.put("beginDate", userStatsJo.S("date"));
				params.put("endDate", userStatsJo.S("date"));

				List<UserStats> tempUserStatsls = alipayUserStatsService.findUserStatsData(tempParams);

				if (!CollectionUtils.isEmpty(tempUserStatsls)) {

					JO alipayUserStatsJo = new JO(JSONObject.toJSONString(tempUserStatsls.get(0)));
					for (String column : CommConstants.USER_STATS_COLUMNS) {
						userStatsJo.put(column.concat(StringUtils.capitalize(CommConstants.ALIPAY_PLATFORM)),
								alipayUserStatsJo.getStr(column, "0"));
					}

				}
			}

			YxDataUserStats yxDataUserStats = JSONObject.parseObject(userStatsJo.myJSONObject().toJSONString(), YxDataUserStats.class);
			yxDataUserStatsls.add(yxDataUserStats);

		}

		if (!CollectionUtils.isEmpty(yxDataUserStatsls)) {
			yxDataUserStatsService.batchInsertUserStatsData(yxDataUserStatsls);
		}

		logger.info("处理 h_" + platformType + ".tb_user_cumulate 数据, 耗时: " + ( System.currentTimeMillis() - startTimeMillisUserCumulate ));
	}

	private void mergeMedicalCardStatistical(Hospital hospital) {// SYS_MEDICAL_CARD_STATISTICAL
		// TODO Auto-generated method stub
		long startTimeMillisMedicalCardStatistical = System.currentTimeMillis();

		List<YxDataSysMedicalCardStatistical> yxDataSysMedicalCardStatisticalls = Lists.newArrayList();

		String hospitalId = String.valueOf(hospital.getId());

		String statsMaxDate = yxDataSysMedicalCardStatisticalService.findCurrMedicalCardStatsMaxDate(hospitalId);

		String beginDate = CommConstants.INIT_DATE;
		if (!StringUtils.isBlank(statsMaxDate)) {
			beginDate = statsMaxDate;
		} else {
			beginDate = "2015-01-01";
		}

		Calendar calendar = Calendar.getInstance();
		String endDate = DateUtils.formatDate(calendar.getTime());

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		calendar.add(Calendar.DATE, 1);
		beginDate = DateUtils.formatDate(calendar.getTime());

		List<SysMedicalCardStatistical> sysMedicalCardStatisticals = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", String.valueOf(hospital.getId()));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
			sysMedicalCardStatisticals = wechatSysMedicalCardStatisticalService.findMedicalCardStatsData(params);
		} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			sysMedicalCardStatisticals = alipaySysMedicalCardStatisticalService.findMedicalCardStatsData(params);
		}

		for (SysMedicalCardStatistical sysMedicalCardStatistical : sysMedicalCardStatisticals) {

			JO sysMedicalCardStatisticalJo = new JO(JSONObject.toJSONString(sysMedicalCardStatistical));

			sysMedicalCardStatisticalJo.put("wechatCount", 0);
			sysMedicalCardStatisticalJo.put("alipayCount", 0);

			Integer count = sysMedicalCardStatisticalJo.getInt("count", 0);

			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
				sysMedicalCardStatisticalJo.put("wechatCount", count);
			} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
				sysMedicalCardStatisticalJo.put("alipayCount", count);
			}
			sysMedicalCardStatisticalJo.remove("count");

			// ===========================================================
			// ============== 处理广中药支付宝和微信的数据合并  ==============
			// ===========================================================
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM) && StringUtils.equals(gzyWechatHospitalId, hospitalId)) {

				Map<String, Object> tempParams = new HashMap<String, Object>();
				params.put("hospitalId", String.valueOf(hospital.getId()));
				params.put("beginDate", sysMedicalCardStatisticalJo.S("date"));
				params.put("endDate", sysMedicalCardStatisticalJo.S("date"));

				List<SysMedicalCardStatistical> tempSysMedicalCardStatisticals =
						alipaySysMedicalCardStatisticalService.findMedicalCardStatsData(tempParams);

				if (!CollectionUtils.isEmpty(tempSysMedicalCardStatisticals)) {

					JO alipaySysMedicalCardStatisticalJo = new JO(JSONObject.toJSONString(tempSysMedicalCardStatisticals.get(0)));
					sysMedicalCardStatisticalJo.put("alipayCount", alipaySysMedicalCardStatisticalJo.getStr("count", "0"));

				}
			}

			YxDataSysMedicalCardStatistical yxDataSysMedicalCardStatistical =
					JSONObject
							.parseObject(sysMedicalCardStatisticalJo.myJSONObject().toJSONString(), YxDataSysMedicalCardStatistical.class);
			yxDataSysMedicalCardStatisticalls.add(yxDataSysMedicalCardStatistical);

		}

		if (!CollectionUtils.isEmpty(yxDataSysMedicalCardStatisticalls)) {
			yxDataSysMedicalCardStatisticalService.batchInsertMedicalCardStatsData(yxDataSysMedicalCardStatisticalls);
		}

		logger.info("处理 h_" + platformType + ".SYS_MEDICAL_CARD_STATISTICAL 数据, 耗时: "
				+ ( System.currentTimeMillis() - startTimeMillisMedicalCardStatistical ));
	}

	private void mergeClinicStatistical(Hospital hospital) {// SYS_CLINIC_STATISTICAL
		// TODO Auto-generated method stub
		long startTimeMillisClinicStatistical = System.currentTimeMillis();

		List<YxDataSysClinicStatistical> yxDataSysClinicStatisticalls = Lists.newArrayList();

		String hospitalId = String.valueOf(hospital.getId());

		String statsMaxDate = yxDataSysClinicStatisticalService.findCurrClinicStatsMaxDate(hospitalId);
		String beginDate = CommConstants.INIT_DATE;
		if (!StringUtils.isBlank(statsMaxDate)) {
			beginDate = statsMaxDate;
		} else {
			beginDate = "2015-01-01";
		}

		Calendar calendar = Calendar.getInstance();
		String endDate = DateUtils.formatDate(calendar.getTime());

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		calendar.add(Calendar.DATE, 1);
		beginDate = DateUtils.formatDate(calendar.getTime());

		List<SysClinicStatistical> sysClinicStatisticalls = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", String.valueOf(hospital.getId()));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
			sysClinicStatisticalls = wechatSysClinicStatisticalService.findClinicStatsData(params);
		} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			sysClinicStatisticalls = alipaySysClinicStatisticalService.findClinicStatsData(params);
		}

		for (SysClinicStatistical sysClinicStatistical : sysClinicStatisticalls) {

			//PAYMENT_WECHAT, NO_PAYMENT_WECHAT, REFUND_WECHAT, CLINIC_PAY_FEE_WECHAT, CLINIC_REFUND_FEE_WECHAT, PART_REFUND_WECHAT
			//PAYMENT_ALIPAY, NO_PAYMENT_ALIPAY, REFUND_ALIPAY, CLINIC_PAY_FEE_ALIPAY, CLINIC_REFUND_FEE_ALIPAY, PART_REFUND_ALIPAY
			JO sysClinicStatisticalJo = new JO(JSONObject.toJSONString(sysClinicStatistical));

			for (String column : CommConstants.CLINIC_STATISTICAL_COLUMNS) {
				for (String pf : CommConstants.PLATFORMTYPES) {
					sysClinicStatisticalJo.put(column.concat(StringUtils.capitalize(pf)), 0);
				}
			}

			for (String column : CommConstants.CLINIC_STATISTICAL_COLUMNS) {
				sysClinicStatisticalJo.put(column.concat(StringUtils.capitalize(platformType)), sysClinicStatisticalJo.getStr(column, "0"));

				sysClinicStatisticalJo.remove(column);
			}

			// ===========================================================
			// ============== 处理广中药支付宝和微信的数据合并  ==============
			// ===========================================================
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM) && StringUtils.equals(gzyWechatHospitalId, hospitalId)) {

				Map<String, Object> tempParams = new HashMap<String, Object>();
				params.put("hospitalId", String.valueOf(hospital.getId()));
				params.put("beginDate", sysClinicStatisticalJo.S("date"));
				params.put("endDate", sysClinicStatisticalJo.S("date"));

				List<SysClinicStatistical> tempSysClinicStatisticalls = alipaySysClinicStatisticalService.findClinicStatsData(tempParams);

				if (!CollectionUtils.isEmpty(tempSysClinicStatisticalls)) {

					JO alipaySysClinicStatisticalJo = new JO(JSONObject.toJSONString(tempSysClinicStatisticalls.get(0)));
					for (String column : CommConstants.USER_STATS_COLUMNS) {
						sysClinicStatisticalJo.put(column.concat(StringUtils.capitalize(CommConstants.ALIPAY_PLATFORM)),
								alipaySysClinicStatisticalJo.getStr(column, "0"));
					}

				}

			}

			YxDataSysClinicStatistical yxDataSysClinicStatistical =
					JSONObject.parseObject(sysClinicStatisticalJo.myJSONObject().toJSONString(), YxDataSysClinicStatistical.class);
			yxDataSysClinicStatisticalls.add(yxDataSysClinicStatistical);

		}

		if (!CollectionUtils.isEmpty(yxDataSysClinicStatisticalls)) {

			yxDataSysClinicStatisticalService.batchInsertClinicStatsData(yxDataSysClinicStatisticalls);
		}

		logger.info("处理 h_" + platformType + ".SYS_CLINIC_STATISTICAL 数据, 耗时: "
				+ ( System.currentTimeMillis() - startTimeMillisClinicStatistical ));

	}

	private void mergeDepositStatistical(Hospital hospital) {// SYS_DEPOSIT_STATISTICAL
		// TODO Auto-generated method stub
		long startTimeMillisDepositStatistical = System.currentTimeMillis();

		List<YxDataSysDepositStatistical> yxDataSysDepositStatisticalls = Lists.newArrayList();

		String hospitalId = String.valueOf(hospital.getId());

		String statsMaxDate = yxDataSysDepositStatisticalService.findCurrDepositStatsMaxDate(hospitalId);
		String beginDate = CommConstants.INIT_DATE;
		if (!StringUtils.isBlank(statsMaxDate)) {
			beginDate = statsMaxDate;
		} else {
			beginDate = "2015-01-01";
		}

		Calendar calendar = Calendar.getInstance();
		String endDate = DateUtils.formatDate(calendar.getTime());

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		calendar.add(Calendar.DATE, 1);
		beginDate = DateUtils.formatDate(calendar.getTime());

		List<SysDepositStatistical> sysDepositStatisticalls = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", String.valueOf(hospital.getId()));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
			sysDepositStatisticalls = wechatSysDepositStatisticalServicee.findDepositStatsData(params);
		} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			sysDepositStatisticalls = alipaySysDepositStatisticalService.findDepositStatsData(params);
		}

		for (SysDepositStatistical sysDepositStatistical : sysDepositStatisticalls) {

			//PAYMENT_WECHAT, NO_PAYMENT_WECHAT, REFUND_WECHAT, DEPOSIT_PAY_FEE_WECHAT, DEPOSIT_REFUND_FEE_WECHAT, PART_REFUND_WECHAT
			//PAYMENT_ALIPAY, NO_PAYMENT_ALIPAY, REFUND_ALIPAY, DEPOSIT_PAY_FEE_ALIPAY, DEPOSIT_REFUND_FEE_ALIPAY, PART_REFUND_ALIPAY
			JO sysDepositStatisticalJo = new JO(JSONObject.toJSONString(sysDepositStatistical));

			for (String column : CommConstants.DEPOSIT_STATISTICAL_COLUMNS) {
				for (String pf : CommConstants.PLATFORMTYPES) {
					sysDepositStatisticalJo.put(column.concat(StringUtils.capitalize(pf)), 0);
				}
			}

			for (String column : CommConstants.DEPOSIT_STATISTICAL_COLUMNS) {
				sysDepositStatisticalJo.put(column.concat(StringUtils.capitalize(platformType)),
						sysDepositStatisticalJo.getStr(column, "0"));

				sysDepositStatisticalJo.remove(column);
			}

			// ===========================================================
			// ============== 处理广中药支付宝和微信的数据合并  ==============
			// ===========================================================
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM) && StringUtils.equals(gzyWechatHospitalId, hospitalId)) {

				Map<String, Object> tempParams = new HashMap<String, Object>();
				params.put("hospitalId", String.valueOf(hospital.getId()));
				params.put("beginDate", sysDepositStatisticalJo.S("date"));
				params.put("endDate", sysDepositStatisticalJo.S("date"));

				List<SysDepositStatistical> tempSysDepositStatisticalls =
						alipaySysDepositStatisticalService.findDepositStatsData(tempParams);

				if (!CollectionUtils.isEmpty(tempSysDepositStatisticalls)) {

					JO alipaySysDepositStatisticalJo = new JO(JSONObject.toJSONString(tempSysDepositStatisticalls.get(0)));
					for (String column : CommConstants.USER_STATS_COLUMNS) {
						sysDepositStatisticalJo.put(column.concat(StringUtils.capitalize(CommConstants.ALIPAY_PLATFORM)),
								alipaySysDepositStatisticalJo.getStr(column, "0"));
					}

				}
			}

			YxDataSysDepositStatistical yxDataSysDepositStatistical =
					JSONObject.parseObject(sysDepositStatisticalJo.myJSONObject().toJSONString(), YxDataSysDepositStatistical.class);
			yxDataSysDepositStatisticalls.add(yxDataSysDepositStatistical);
		}

		if (!CollectionUtils.isEmpty(yxDataSysDepositStatisticalls)) {

			yxDataSysDepositStatisticalService.batchInsertDepositStatsData(yxDataSysDepositStatisticalls);
		}

		logger.info("处理 h_" + platformType + ".SYS_DEPOSIT_STATISTICAL 数据, 耗时: "
				+ ( System.currentTimeMillis() - startTimeMillisDepositStatistical ));
	}

	private void mergeRegStatistical(Hospital hospital) {// SYS_REG_STATISTICAL
		// TODO Auto-generated method stub
		long startTimeMillisRegStatistical = System.currentTimeMillis();

		List<YxDataSysRegStatistical> yxDataSysRegStatisticalls = Lists.newArrayList();

		String hospitalId = String.valueOf(hospital.getId());

		String statsMaxDate = yxDataSysRegStatisticalService.findCurrRegisterStatsMaxDate(hospitalId);
		String beginDate = CommConstants.INIT_DATE;
		if (!StringUtils.isBlank(statsMaxDate)) {
			beginDate = statsMaxDate;
		} else {
			beginDate = "2015-01-01";
		}

		Calendar calendar = Calendar.getInstance();
		String endDate = DateUtils.formatDate(calendar.getTime());

		calendar.setTime(DateUtils.StringToDateYMD(beginDate));
		calendar.add(Calendar.DATE, 1);
		beginDate = DateUtils.formatDate(calendar.getTime());

		List<SysRegStatistical> sysRegStatisticalls = Lists.newArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", String.valueOf(hospital.getId()));
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
			sysRegStatisticalls = wechatSysRegStatisticalService.findRegisterStatsData(params);
		} else if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
			sysRegStatisticalls = alipaySysRegStatisticalService.findRegisterStatsData(params);
		}

		for (SysRegStatistical sysRegStatistical : sysRegStatisticalls) {

			//RESERVATION_PAYMENT_WECHAT, RESERVATION_NO_PAYMENT_WECHAT, RESERVATION_REFUND_WECHAT, DUTY_PAYMENT_WECHAT, DUTY_NO_PAYMENT_WECHAT, DUTY_REFUND_WECHAT, REG_PAY_FEE_WECHAT, REG_REFUND_FEE_WECHAT
			//RESERVATION_PAYMENT_ALIPAY, RESERVATION_NO_PAYMENT_ALIPAY, RESERVATION_REFUND_ALIPAY, DUTY_PAYMENT_ALIPAY, DUTY_NO_PAYMENT_ALIPAY, DUTY_REFUND_ALIPAY, REG_PAY_FEE_ALIPAY, REG_REFUND_FEE_ALIPAY
			JO sysRegStatisticalJo = new JO(JSONObject.toJSONString(sysRegStatistical));

			for (String column : CommConstants.REG_STATISTICAL_COLUMNS) {

				for (String pf : CommConstants.PLATFORMTYPES) {
					sysRegStatisticalJo.put(column.concat(StringUtils.capitalize(pf)), 0);
				}
			}

			for (String column : CommConstants.REG_STATISTICAL_COLUMNS) {
				sysRegStatisticalJo.put(column.concat(StringUtils.capitalize(platformType)), sysRegStatisticalJo.getStr(column, "0"));

				sysRegStatisticalJo.remove(column);
			}

			// ===========================================================
			// ============== 处理广中药支付宝和微信的数据合并  ==============
			// ===========================================================
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM) && StringUtils.equals(gzyWechatHospitalId, hospitalId)) {

				Map<String, Object> tempParams = new HashMap<String, Object>();
				params.put("hospitalId", String.valueOf(hospital.getId()));
				params.put("beginDate", sysRegStatisticalJo.S("date"));
				params.put("endDate", sysRegStatisticalJo.S("date"));

				List<SysRegStatistical> tempSysRegStatisticalls = alipaySysRegStatisticalService.findRegisterStatsData(tempParams);

				if (!CollectionUtils.isEmpty(tempSysRegStatisticalls)) {

					JO alipaySysRegStatisticalJo = new JO(JSONObject.toJSONString(tempSysRegStatisticalls.get(0)));
					for (String column : CommConstants.USER_STATS_COLUMNS) {
						sysRegStatisticalJo.put(column.concat(StringUtils.capitalize(CommConstants.ALIPAY_PLATFORM)),
								alipaySysRegStatisticalJo.getStr(column, "0"));
					}

				}
			}

			YxDataSysRegStatistical yxDataSysRegStatistical =
					JSONObject.parseObject(sysRegStatisticalJo.myJSONObject().toJSONString(), YxDataSysRegStatistical.class);
			yxDataSysRegStatisticalls.add(yxDataSysRegStatistical);

		}

		if (!CollectionUtils.isEmpty(yxDataSysRegStatisticalls)) {

			yxDataSysRegStatisticalService.batchInsertRegisterStatsData(yxDataSysRegStatisticalls);
		}

		logger.info("处理 h_" + platformType + ".SYS_REG_STATISTICAL 数据, 耗时: "
				+ ( System.currentTimeMillis() - startTimeMillisRegStatistical ));
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

}