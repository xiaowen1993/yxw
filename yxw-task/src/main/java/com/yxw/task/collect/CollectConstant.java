/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-12-8</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.task.collect;

/**
 * @Package: com.yxw.task.collect
 * @ClassName: CollectConstant
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-12-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CollectConstant {
	/**
	 * 每核Cpu负载的最大线程队列数
	 */
	public static final float POOL_SIZE = 1.5f;

	public static final int threadNum;

	static {
		int cpuNums = Runtime.getRuntime().availableProcessors();
		float MathNum = cpuNums * CollectConstant.POOL_SIZE;
		threadNum = (int) MathNum;
	}
}
