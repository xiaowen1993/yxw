package com.yxw.stats.service.project.alipay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yxw.stats.entity.project.SysMedicalCardStatistical;
import com.yxw.stats.service.project.SysMedicalCardStatisticalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class SysMedicalCardStatisticalServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "alipaySysMedicalCardStatisticalService")
	private SysMedicalCardStatisticalService sysMedicalCardStatisticalService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCurrMedicalCardStatsMaxDate() {
		String maxDate = sysMedicalCardStatisticalService.findCurrMedicalCardStatsMaxDate("15");
		System.out.println(maxDate);
	}

	@Test
	public void testFindMedicalCardStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "15");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysMedicalCardStatistical> sysMedicalCardStatisticals = sysMedicalCardStatisticalService.findMedicalCardStatsData(params);

		System.out.println(JSONObject.toJSONString(sysMedicalCardStatisticals));
	}

	@Test
	public void testBatchInsertMedicalCardStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "15");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysMedicalCardStatistical> sysMedicalCardStatisticals = sysMedicalCardStatisticalService.findMedicalCardStatsData(params);

		System.out.println(JSONObject.toJSONString(sysMedicalCardStatisticals));

		sysMedicalCardStatisticalService.batchInsertMedicalCardStatsData(sysMedicalCardStatisticals);
	}

}
