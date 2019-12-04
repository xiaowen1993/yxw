package com.yxw.stats.task.project.taskitem;

import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yxw.framework.common.spring.ext.LogbackConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class UserStatisticalTaskTest extends AbstractJUnit4SpringContextTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			URL u = ClassLoader.getSystemResource("");
			LogbackConfigurer.initLogging(u.getPath() + "logback/logback.xml");
		} catch (Exception ex) {
			System.err.println("Cannot Initialize logback");
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStartUp() {
		UserStatisticalTask task = new UserStatisticalTask();
		task.startUp();
	}

}
