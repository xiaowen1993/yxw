package com.yxw.commons.constants.pool;

/**
 * 
 * 功能概要：
 * @author Administrator
 * @date 2017年3月28日
 */
public class PoolConstant {
	/**
	 * 每核Cpu负载的最大线程队列数
	 */
	public static final float POOL_SIZE_FACTOR = 1.5f;

	public static final int threadNum;

	static {
		int cpuNums = Runtime.getRuntime().availableProcessors();
		float MathNum = cpuNums * PoolConstant.POOL_SIZE_FACTOR;
		threadNum = (int) MathNum;
	}
}
