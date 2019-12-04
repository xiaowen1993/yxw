package com.yxw.easyhealth.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.easyhealth.common.callable.QueryHashTableCallable;
import com.yxw.easyhealth.common.service.CommonCardService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.mobileapp.biz.usercenter.dao.MedicalCardDao;

@Service(value = "commonCardService")
public class CommonCardServiceImpl extends BaseServiceImpl<MedicalCard, String> implements CommonCardService {
	private static Logger logger = LoggerFactory.getLogger(CommonCardServiceImpl.class);

	private MedicalCardDao medicalCardDao = SpringContextHolder.getBean(MedicalCardDao.class);

	@Override
	protected BaseDao<MedicalCard, String> getDao() {
		return this.medicalCardDao;
	}

	@Override
	public List<MedicalCard> getAllCards(MedicalCard medicalCard) {
		return medicalCardDao.findCardsByOpenIdAndHospitalCode(medicalCard);
	}

	public List<MedicalCard> findAll() {
		return medicalCardDao.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<SimpleMedicalCard> findAllForCache() {
		long start = System.currentTimeMillis();

		List<SimpleMedicalCard> medicalCards = new ArrayList<SimpleMedicalCard>();

		// 设置线程池的数量
		int cpuNums = Runtime.getRuntime().availableProcessors();
		ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String tableName = SimpleHashTableNameGenerator.MEDICAL_CARD_TABLE_NAME + "_" + i;
			Object[] parameters = new Object[] { tableName };
			Class<?>[] parameterTypes = new Class[] { String.class };
			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(MedicalCardDao.class, "findAllForCache", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<SimpleMedicalCard> threadRes = (List<SimpleMedicalCard>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(threadRes)) {
					medicalCards.addAll(threadRes);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		if (logger.isDebugEnabled()) {
			logger.debug("find all hash medicalCard table cast:{} millis.", System.currentTimeMillis() - start);
		}
		return medicalCards;
	}
}
