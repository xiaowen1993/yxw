/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年10月23日
 * @version 1.0
 */
package com.yxw.mobileapp.invoke.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.mobileapp.invoke.OutsideInvokeService;
import com.yxw.mobileapp.invoke.dto.Request;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年10月23日  
 */
public class OutsideInvokeServiceImplTest extends Junit4SpringContextHolder {

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年10月23日 
	 * @throws java.lang.Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年10月23日 
	 * @throws java.lang.Exception 
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年10月23日 
	 * @throws java.lang.Exception 
	 */
	@Before
	public void setUp() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年10月23日 
	 * @throws java.lang.Exception 
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.yxw.mobileapp.invoke.impl.OutsideInvokeServiceImpl#openService(com.yxw.mobileapp.invoke.dto.Request)}.
	 */
	@Test
	public void testOpenService() {
		OutsideInvokeService outsideInvokeService = SpringContextHolder.getBean("outsideInvokeService");

		Request req = new Request();
		req.setMethodCode("templateMsgPush");
		req.setParams("<appId>201709041310042517</appId><platformType>innerChinaLife</platformType><userType>2</userType><toUser>b296582f8d6b43b0b02754a2b67d8c68</toUser><templateCode>12000</templateCode><url></url><topColor></topColor><msgContent>{\"first\":{\"color\":\"#173177\",\"value\":\"你好，你有一项新的待缴费项目。\"},\"keyword1\":{\"color\":\"#173177\",\"value\":\"王大宝\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\"888888888888\"},\"keyword3\":{\"color\":\"#173177\",\"value\":\"北京医院\"},\"keyword4\":{\"color\":\"#173177\",\"value\":\"检查费 – B型超声\"},\"keyword5\":{\"color\":\"#173177\",\"value\":\"200.00元\"},\"remark\":{\"color\":\"#173177\",\"value\":\"使用微信支付，可有效减少排队等候时间，无需手续费。\"}}</msgContent>");
		req.setResponseType("0");

		outsideInvokeService.openService(req);
	}
}
