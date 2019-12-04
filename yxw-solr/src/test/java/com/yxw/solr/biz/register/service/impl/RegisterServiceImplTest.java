package com.yxw.solr.biz.register.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.solr.biz.register.entity.Dept;
import com.yxw.solr.biz.register.service.RegisterService;
import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.task.callable.StatsRegisterCallable;
import com.yxw.solr.task.collector.StatsRegisterCollector;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.solr.vo.register.RegStatsRequest;
import com.yxw.utils.DateUtils;

public class RegisterServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void testFindOrders() {
		RegisterService service = SpringContextHolder.getBean(RegisterService.class);
		RegInfoRequest request = new RegInfoRequest();
		request.setPlatform(PlatformConstant.PLATFORM_EH_VAL); // 非必要，如果没有则会查询多个core
		request.setHospitalCode("szsdermyy"); // 必输
		request.setPatientName("蒲仕红");
		request.setRegBeginTime("2015-11-01");
		request.setRegEndTime("2015-11-10");
		// request.setVisitEndTime("2015-11-01");
		YxwResponse vo = service.findOrders(request);
		System.out.println(JSON.toJSONString(vo));
	}

	@Test
	public void testStatsYesterdayRegInfos() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		try {
			Map<String, Map<String, List<String>>> map = getHospitalInfos();

			for (Entry<String, Map<String, List<String>>> platformMap : map.entrySet()) {
				String platform = platformMap.getKey();

				for (Entry<String, List<String>> hospitalMap : platformMap.getValue().entrySet()) {
					String hospitalCode = hospitalMap.getKey();
					List<String> branchCodes = hospitalMap.getValue();

					for (String branchCode : branchCodes) {
						// 昨天一天统计
						String statsDate = DateUtils.getDayForDate(new Date(), -1);
						// 昨天之前的多日统计
						// String statsDate = "";
						StatsRegisterCallable callable = new StatsRegisterCallable(Integer.valueOf(platform), hospitalCode, branchCode, statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						executorService.submit(callable);
						taskList.add(task);
					}
				}
			}

			for (FutureTask<String> task : taskList) {
				String result = task.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	@Test
	public void testStatsRegInfos() {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		try {
			Map<String, Map<String, List<String>>> map = getHospitalInfos();

			for (Entry<String, Map<String, List<String>>> platformMap : map.entrySet()) {
				String platform = platformMap.getKey();

				for (Entry<String, List<String>> hospitalMap : platformMap.getValue().entrySet()) {
					String hospitalCode = hospitalMap.getKey();
					List<String> branchCodes = hospitalMap.getValue();

					for (String branchCode : branchCodes) {
						// 昨天一天统计
						// String statsDate = DateUtils.getDayForDate(new
						// Date(), -1);
						// 昨天之前的多日统计
						String statsDate = "";
						StatsRegisterCallable callable = new StatsRegisterCallable(Integer.valueOf(platform), hospitalCode, branchCode, statsDate);
						FutureTask<String> task = new FutureTask<String>(callable);
						executorService.submit(callable);
						taskList.add(task);
					}
				}
			}

			List<String> results = new ArrayList<String>();
			for (FutureTask<String> task : taskList) {
				String result = task.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	private Map<String, Map<String, List<String>>> getHospitalInfos() {
		Map<String, Map<String, List<String>>> resultMap = new HashMap<String, Map<String, List<String>>>();

		Map<String, List<String>> easyHealthMap = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("HZ010");
		list1.add("-1");
		easyHealthMap.put("bjdxszyycs", list1);

		List<String> list2 = new ArrayList<String>();
		list2.add("0001");
		list2.add("-1");
		easyHealthMap.put("szetyycs", list2);

		List<String> list3 = new ArrayList<String>();
		list3.add("H0320");
		list3.add("-1");
		easyHealthMap.put("szsdermyycsh", list3);

		List<String> list4 = new ArrayList<String>();
		list4.add("45575559X");
		list4.add("45575559X1");
		list4.add("-1");
		easyHealthMap.put("szsdsrmyy", list4);

		List<String> list5 = new ArrayList<String>();
		list5.add("0001");
		list5.add("-1");
		easyHealthMap.put("szsfybjycs", list5);

		List<String> list6 = new ArrayList<String>();
		list6.add("H0320");
		list6.add("-1");
		easyHealthMap.put("szsykyy", list6);

		List<String> list7 = new ArrayList<String>();
		list7.add("0001");
		list7.add("-1");
		easyHealthMap.put("szszyy", list7);

		// 健康易为3
		resultMap.put("3", easyHealthMap);
		// resultMap.put("-1", easyHealthMap);

		return resultMap;
	}

	private Map<String, Map<String, List<Map<String, List<Dept>>>>> getDepts() {
		Map<String, Map<String, List<Map<String, List<Dept>>>>> platforms = new HashMap<String, Map<String, List<Map<String, List<Dept>>>>>();

		Map<String, List<Map<String, List<Dept>>>> hosp0 = new HashMap<String, List<Map<String, List<Dept>>>>();
		List<Map<String, List<Dept>>> branches0 = new ArrayList<>();
		Map<String, List<Dept>> branch0 = new HashMap<>();
		List<Dept> aryDept0 = new ArrayList<Dept>();
		Dept d01 = new Dept();
		d01.setDeptCode("041");
		d01.setDeptName("耳鼻喉科(副高)");
		aryDept0.add(d01);

		Dept d02 = new Dept();
		d02.setDeptCode("1740");
		d02.setDeptName("骨关节外科");
		aryDept0.add(d02);

		Dept d03 = new Dept();
		d03.setDeptCode("757");
		d03.setDeptName("神经内科");
		aryDept0.add(d03);

		branch0.put("HZ010", aryDept0);
		branches0.add(branch0);
		hosp0.put("bjdxszyycs", branches0);
		platforms.put("3", hosp0); // platform

		// 深三
		Map<String, List<Map<String, List<Dept>>>> hosp1 = new HashMap<String, List<Map<String, List<Dept>>>>();
		List<Map<String, List<Dept>>> branches1 = new ArrayList<>();
		Map<String, List<Dept>> branch1 = new HashMap<>();
		List<Dept> aryDept1 = new ArrayList<Dept>();
		Dept d11 = new Dept();
		d11.setDeptCode("1650");
		d11.setDeptName("妇产科");
		aryDept1.add(d11);

		Dept d12 = new Dept();
		d12.setDeptCode("4281");
		d12.setDeptName("爱心门诊");
		aryDept1.add(d12);

		Dept d13 = new Dept();
		d13.setDeptCode("1653");
		d13.setDeptName("肝病科门诊");
		aryDept0.add(d13);

		branch1.put("45575559X", aryDept1);
		branches1.add(branch1);
		hosp1.put("szsdsrmyy", branches1);
		platforms.put("3", hosp1);
		platforms.put("-1", hosp1);

		return platforms;
	}

	@Test
	public void testGetStatsInfos() {
		RegisterService service = SpringContextHolder.getBean(RegisterService.class);
		RegStatsRequest request = new RegStatsRequest();
		request.setHospitalCode("dgsrmyy");
		request.setBeginDate("2016-05-25");
		request.setEndDate("2016-05-28");
		request.setPlatform(1);

		YxwResponse vo = service.getStatsInfos(request);
		System.out.println(JSON.toJSONString(vo));
	}

	public static void main(String[] args) {
		YxwResponse response = new YxwResponse();
		YxwResult yxwResult = new YxwResult();
		yxwResult.setSize(0);
		yxwResult.setItems(new String("00AA"));
		response.setResult(yxwResult);
		System.out.println(JSON.toJSONString(response, SerializerFeature.WriteNonStringKeyAsString));
	}
	
	@Test
	public void doStats() {
		StatsRegisterCollector collector = new StatsRegisterCollector();
		collector.startUp();
	}

}
