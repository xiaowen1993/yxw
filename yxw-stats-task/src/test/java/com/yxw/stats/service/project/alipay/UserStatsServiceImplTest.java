package com.yxw.stats.service.project.alipay;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yxw.stats.entity.project.UserStats;
import com.yxw.stats.service.project.UserStatsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class UserStatsServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "alipayUserStatsService")
	private UserStatsService userStatsService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindCurrUserStatsMaxDate() {
		String maxDate = userStatsService.findCurrUserStatsMaxDate("12");
		System.out.println(maxDate);
	}

	@Test
	public void batchInsertUserStatsData() {
		String jsonstr =
				"[{\"cumulateuser\":247898,\"newuser\":447,\"canceluser\":476,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-11-01\"},{\"cumulateuser\":248204,\"newuser\":456,\"canceluser\":150,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-11-02\"},{\"cumulateuser\":247927,\"newuser\":470,\"canceluser\":489,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-10-31\"},{\"cumulateuser\":247946,\"newuser\":421,\"canceluser\":77,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-10-30\"},{\"cumulateuser\":248581,\"newuser\":457,\"canceluser\":80,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-11-03\"},{\"cumulateuser\":248974,\"newuser\":464,\"canceluser\":71,\"hospitalid\":\"13\",\"user_source\":0,\"appid\":\"wxc3678a507117e457\",\"date\":\"2018-11-04\"}]";

		List<UserStats> userStatsEntitys = JSONObject.parseArray(jsonstr, UserStats.class);

		userStatsService.batchInsertUserStatsData(userStatsEntitys);
	}
}
