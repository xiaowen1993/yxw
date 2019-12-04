package com.yxw.stats.service.project.alipay;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yxw.stats.entity.project.AlipaySettings;
import com.yxw.stats.service.project.AlipaySettingsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class AlipaySettingsServiceImplTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "alipaySettingsService")
	private AlipaySettingsService alipaySettingsService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindAlipaySettingsByAppId() {
		AlipaySettings alipaySettings = alipaySettingsService.findAlipaySettingsByAppId("2015070900161782");
		System.out.println(JSONObject.toJSONString(alipaySettings));
	}
}
