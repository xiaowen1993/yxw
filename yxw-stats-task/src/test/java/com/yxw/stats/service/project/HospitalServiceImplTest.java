package com.yxw.stats.service.project;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yxw.stats.entity.project.Hospital;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class HospitalServiceImplTest extends AbstractJUnit4SpringContextTests {
	private static Logger logger = LoggerFactory.getLogger(HospitalServiceImplTest.class);

	@Resource(name = "hospitalService")
	private HospitalService hospitalService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindAllHospital() {
		List<Hospital> hospitals = hospitalService.findAllHospital();
		System.out.println(JSONObject.toJSONString(hospitals));

	}

}
