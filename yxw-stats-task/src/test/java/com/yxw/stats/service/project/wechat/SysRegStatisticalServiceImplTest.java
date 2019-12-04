package com.yxw.stats.service.project.wechat;

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
import com.yxw.stats.entity.project.SysRegStatistical;
import com.yxw.stats.service.project.SysRegStatisticalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class SysRegStatisticalServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "sysRegStatisticalService")
	private SysRegStatisticalService sysRegStatisticalService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCurrRegisterStatsMaxDate() {
		String maxDate = sysRegStatisticalService.findCurrRegisterStatsMaxDate("21");
		System.out.println(maxDate);
	}

	@Test
	public void testFindRegisterStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "21");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysRegStatistical> sysRegisterStatisticals = sysRegStatisticalService.findRegisterStatsData(params);

		System.out.println(JSONObject.toJSONString(sysRegisterStatisticals));
	}

	@Test
	public void testBatchInsertRegisterStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "21");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysRegStatistical> sysRegisterStatisticals = sysRegStatisticalService.findRegisterStatsData(params);

		System.out.println(JSONObject.toJSONString(sysRegisterStatisticals));

		sysRegStatisticalService.batchInsertRegisterStatsData(sysRegisterStatisticals);
	}

}
