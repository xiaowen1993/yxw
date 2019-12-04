/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年8月29日
 * @version 1.0
 */
package com.yxw.task.taskitem;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yxw.framework.utils.Junit4SpringContextHolder;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年8月29日  
 */
public class StopRegisterTaskTest extends Junit4SpringContextHolder {

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月29日 
	 * @throws java.lang.Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月29日 
	 * @throws java.lang.Exception 
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月29日 
	 * @throws java.lang.Exception 
	 */
	@Before
	public void setUp() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月29日 
	 * @throws java.lang.Exception 
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.yxw.task.taskitem.StopRegisterTask#startUp()}.
	 */
	@Test
	public void testStartUp() {
		StopRegisterTask stopRegisterTask = new StopRegisterTask();
		stopRegisterTask.setLoopTime(60000l);
		stopRegisterTask.startUp();
	}

}
