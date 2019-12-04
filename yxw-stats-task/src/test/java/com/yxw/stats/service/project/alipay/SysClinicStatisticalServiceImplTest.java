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
import com.yxw.stats.entity.project.SysClinicStatistical;
import com.yxw.stats.service.project.SysClinicStatisticalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class SysClinicStatisticalServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "alipaySysClinicStatisticalService")
	private SysClinicStatisticalService sysClinicStatisticalService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCurrClinicStatsMaxDate() {
		String maxDate = sysClinicStatisticalService.findCurrClinicStatsMaxDate("12");
		System.out.println(maxDate);
	}

	@Test
	public void testFindClinicStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "12");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysClinicStatistical> sysClinicStatisticals = sysClinicStatisticalService.findClinicStatsData(params);

		System.out.println(JSONObject.toJSONString(sysClinicStatisticals));
	}

	@Test
	public void testBatchInsertClinicStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "12");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysClinicStatistical> sysClinicStatisticals = sysClinicStatisticalService.findClinicStatsData(params);

		System.out.println(JSONObject.toJSONString(sysClinicStatisticals));

		sysClinicStatisticalService.batchInsertClinicStatsData(sysClinicStatisticals);
	}

}
