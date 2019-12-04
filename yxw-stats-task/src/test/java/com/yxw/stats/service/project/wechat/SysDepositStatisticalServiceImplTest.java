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
import com.yxw.stats.entity.project.SysDepositStatistical;
import com.yxw.stats.service.project.SysDepositStatisticalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class SysDepositStatisticalServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "sysDepositStatisticalService")
	private SysDepositStatisticalService sysDepositStatisticalService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCurrDepositStatsMaxDate() {
		String maxDate = sysDepositStatisticalService.findCurrDepositStatsMaxDate("21");
		System.out.println(maxDate);
	}

	@Test
	public void testFindDepositStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "21");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysDepositStatistical> sysDepositStatisticals = sysDepositStatisticalService.findDepositStatsData(params);

		System.out.println(JSONObject.toJSONString(sysDepositStatisticals));
	}

	@Test
	public void testBatchInsertDepositStatsData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", "21");
		params.put("beginDate", "2018-10-25");
		params.put("endDate", "2018-11-01");
		List<SysDepositStatistical> sysDepositStatisticals = sysDepositStatisticalService.findDepositStatsData(params);

		System.out.println(JSONObject.toJSONString(sysDepositStatisticals));

		sysDepositStatisticalService.batchInsertDepositStatsData(sysDepositStatisticals);
	}

}
