/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年8月30日
 * @version 1.0
 */
package com.yxw.easyhealth.biz.outside.impl;

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
 * @date 2017年8月30日  
 */

public class OutSideInvokeServiceTest extends Junit4SpringContextHolder {
	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月30日 
	 * @throws java.lang.Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月30日 
	 * @throws java.lang.Exception 
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月30日 
	 * @throws java.lang.Exception 
	 */
	@Before
	public void setUp() throws Exception {
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年8月30日 
	 * @throws java.lang.Exception 
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOpenService() {
		OutsideInvokeService outsideInvokeService = SpringContextHolder.getBean(OutsideInvokeService.class);
		Request request = new Request();
		request.setMethodCode("stopReg");
		request.setResponseType("1");
		request.setParams("<appId>20170525473241695</appId><orderList>Y002420170912145214110918500007,Y242420170919114504110909400012</orderList>");

		/*request.setMethodCode("refundGeneral");
		request.setResponseType("1");
		request.setParams("<appId>201709061310042517</appId><hisNewOrdNum>Y002420170912145059110918500123</hisNewOrdNum><partialOrAllrefund>1</partialOrAllrefund><psOrdNum>Y002420170912145214110918500007</psOrdNum><pushType>0</pushType><refundAmout>1</refundAmout><refundReason>原去掉退费</refundReason><refundTime>2018-01-10 10:10:10</refundTime><refundType>1</refundType><tradeMode>9</tradeMode>");
		*/
		outsideInvokeService.openService(request);
	}

}
