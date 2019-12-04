package com.yxw.easyhealth.biz.report.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.easyhealth.biz.report.service.ReportService;
import com.yxw.easyhealth.common.callable.QueryHashTableCallable;
import com.yxw.easyhealth.datas.manager.ReportsBizManager;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.inspection.Inspect;
import com.yxw.utils.DateUtils;

@Service(value = "ehReportService")
public class ReportServiceImpl implements ReportService {

	private Logger logger = LoggerFactory.getLogger(ReportService.class);

	private final static int MAX_THREAD_COUNT = 13;

	/**
	 * 存在一个隐患。
	 * 如果存在分院，且分院接口都不同的情况。 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getInspectList(List<MedicalCard> medicalCards) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ExecutorService collectExec = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		for (int i = 0; i < medicalCards.size(); i++) {
			try {
				MedicalCard medicalCard = new MedicalCard();
				BeanUtils.copyProperties(medicalCard, medicalCards.get(i));
				RuleQuery ruleQuery = ruleConfigManager.getRuleQueryByHospitalId(medicalCard.getHospitalId());
				Integer months = ruleQuery.getReportRecordQueryMaxMonths();
				months = months == null ? -12 : ( months - ( months * 2 ) );
				String beginDate = DateUtils.formatDate(DateUtils.getNextMonth(new Date(), months));
				String endDate = DateUtils.formatDate(new Date());
				Object[] parameters = new Object[] { medicalCard, beginDate, endDate };
				Class<?>[] parameterTypes = new Class[] { MedicalCard.class, String.class, String.class };

				QueryHashTableCallable collectCall =
						new QueryHashTableCallable(ReportsBizManager.class, "getInspectListEx", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			} catch (Exception e) {
				logger.error("分线程获取检验列表异常[创建线程]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
			}
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Map<String, Object> inspectMap = (Map<String, Object>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty((List<Inspect>) inspectMap.get(BizConstant.COMMON_ENTITY_LIST_KEY))) {
					list.add(inspectMap);
				}
			}
		} catch (Exception e) {
			logger.error("分线程获取检验列表异常[等待结果]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getExamineList(List<MedicalCard> medicalCards) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ExecutorService collectExec = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
		for (int i = 0; i < medicalCards.size(); i++) {
			try {
				MedicalCard medicalCard = new MedicalCard();
				BeanUtils.copyProperties(medicalCard, medicalCards.get(i));
				RuleQuery ruleQuery = ruleConfigManager.getRuleQueryByHospitalId(medicalCard.getHospitalId());
				Integer months = ruleQuery.getReportRecordQueryMaxMonths();
				months = months == null ? -12 : ( months - ( months * 2 ) );
				String beginDate = DateUtils.formatDate(DateUtils.getNextMonth(new Date(), months));
				String endDate = DateUtils.formatDate(new Date());
				Object[] parameters = new Object[] { medicalCard, beginDate, endDate };
				Class<?>[] parameterTypes = new Class[] { MedicalCard.class, String.class, String.class };

				QueryHashTableCallable collectCall =
						new QueryHashTableCallable(ReportsBizManager.class, "getExamineListEx", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			} catch (Exception e) {
				logger.error("分线程获取检查列表异常[创建线程]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
			}
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Map<String, Object> inspectMap = (Map<String, Object>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty((List<Inspect>) inspectMap.get(BizConstant.COMMON_ENTITY_LIST_KEY))) {
					list.add(inspectMap);
				}
			}
		} catch (Exception e) {
			logger.error("分线程获取检查列表异常[等待结果]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return list;
	}

	@Override
	public List<Map<String, Object>> getCheckupList(List<MedicalCard> medicalCards) {
		// 暂不实现
		return null;
	}

	/**
	 * 存在一个隐患。
	 * 如果存在分院，且分院接口都不同的情况。 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getInspectList(List<MedicalCard> medicalCards, String beginDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ExecutorService collectExec = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 0; i < medicalCards.size(); i++) {
			try {
				MedicalCard medicalCard = new MedicalCard();
				BeanUtils.copyProperties(medicalCard, medicalCards.get(i));
				String endDate = DateUtils.formatDate(new Date());
				Object[] parameters = new Object[] { medicalCard, beginDate, endDate };
				Class<?>[] parameterTypes = new Class[] { MedicalCard.class, String.class, String.class };

				QueryHashTableCallable collectCall =
						new QueryHashTableCallable(ReportsBizManager.class, "getInspectListEx", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			} catch (Exception e) {
				logger.error("分线程获取检验列表异常[创建线程]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
			}
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Map<String, Object> inspectMap = (Map<String, Object>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty((List<Inspect>) inspectMap.get(BizConstant.COMMON_ENTITY_LIST_KEY))) {
					list.add(inspectMap);
				}
			}
		} catch (Exception e) {
			logger.error("分线程获取检验列表异常[等待结果]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getExamineList(List<MedicalCard> medicalCards, String beginDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ExecutorService collectExec = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 0; i < medicalCards.size(); i++) {
			try {
				MedicalCard medicalCard = new MedicalCard();
				BeanUtils.copyProperties(medicalCard, medicalCards.get(i));
				String endDate = DateUtils.formatDate(new Date());
				Object[] parameters = new Object[] { medicalCard, beginDate, endDate };
				Class<?>[] parameterTypes = new Class[] { MedicalCard.class, String.class, String.class };

				QueryHashTableCallable collectCall =
						new QueryHashTableCallable(ReportsBizManager.class, "getExamineListEx", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			} catch (Exception e) {
				logger.error("分线程获取检查列表异常[创建线程]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
			}
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				Map<String, Object> inspectMap = (Map<String, Object>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty((List<Inspect>) inspectMap.get(BizConstant.COMMON_ENTITY_LIST_KEY))) {
					list.add(inspectMap);
				}
			}
		} catch (Exception e) {
			logger.error("分线程获取检查列表异常[等待结果]... errorMsg: {}, cause: {}.", e.getMessage(), e.getCause());
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return list;
	}

	@Override
	public List<Map<String, Object>> getCheckupList(List<MedicalCard> medicalCards, String beginDate) {
		// 暂不实现
		return null;
	}

}
