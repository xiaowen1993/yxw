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

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.entity.project.Hospital;
import com.yxw.stats.entity.project.SysDepositStatistical;
import com.yxw.stats.service.project.HospitalService;
import com.yxw.stats.service.project.SysDepositStatisticalService;
import com.yxw.utils.DateUtils;

public class DepositStatisticalCollectCall implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(DepositStatisticalCollectCall.class);

	private HospitalService wechatHospitalService = SpringContextHolder.getBean("hospitalService");

	private HospitalService alipayHospitalService = SpringContextHolder.getBean("alipayHospitalService");

	private SysDepositStatisticalService wechatSysDepositStatisticalService = SpringContextHolder.getBean("sysDepositStatisticalService");

	private SysDepositStatisticalService alipaySysDepositStatisticalService = SpringContextHolder
			.getBean("alipaySysDepositStatisticalService");

	/**
	 * 平台类型
	 */
	private String platformType;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public DepositStatisticalCollectCall(String platformType) {
		super();
		this.platformType = platformType;
	}

	@Override
	public String call() throws Exception {

		String result = "";

		try {
			if (StringUtils.equals(platformType, CommConstants.WECHAT_PLATFORM)) {
				wechatHandle();
			}

			if (StringUtils.equals(platformType, CommConstants.ALIPAY_PLATFORM)) {
				alipayHandle();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String wechatHandle() {
		List<Hospital> hospitals = wechatHospitalService.findAllHospital();

		for (Hospital hospital : hospitals) {
			String beginDate = CommConstants.INIT_DATE;

			String statsMaxDate = wechatSysDepositStatisticalService.findCurrDepositStatsMaxDate(String.valueOf(hospital.getId()));
			if (!StringUtils.isBlank(statsMaxDate)) {
				beginDate = statsMaxDate;
			}

			Calendar calendar = Calendar.getInstance();
			String endDate = DateUtils.formatDate(calendar.getTime());

			calendar.setTime(DateUtils.StringToDateYMD(beginDate));
			calendar.add(Calendar.DATE, 1);
			beginDate = DateUtils.formatDate(calendar.getTime());

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospitalId", String.valueOf(hospital.getId()));
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			List<SysDepositStatistical> sysDepositStatisticals = wechatSysDepositStatisticalService.findDepositStatsData(params);

			wechatSysDepositStatisticalService.batchInsertDepositStatsData(sysDepositStatisticals);

		}

		return "OK";
	}

	private String alipayHandle() {
		List<Hospital> hospitals = alipayHospitalService.findAllHospital();

		for (Hospital hospital : hospitals) {
			String beginDate = CommConstants.INIT_DATE;

			String statsMaxDate = alipaySysDepositStatisticalService.findCurrDepositStatsMaxDate(String.valueOf(hospital.getId()));
			if (!StringUtils.isBlank(statsMaxDate)) {
				beginDate = statsMaxDate;
			}

			Calendar calendar = Calendar.getInstance();
			String endDate = DateUtils.formatDate(calendar.getTime());

			calendar.setTime(DateUtils.StringToDateYMD(beginDate));
			calendar.add(Calendar.DATE, 1);
			beginDate = DateUtils.formatDate(calendar.getTime());

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospitalId", String.valueOf(hospital.getId()));
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			List<SysDepositStatistical> sysDepositStatisticals = alipaySysDepositStatisticalService.findDepositStatsData(params);

			alipaySysDepositStatisticalService.batchInsertDepositStatsData(sysDepositStatisticals);

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