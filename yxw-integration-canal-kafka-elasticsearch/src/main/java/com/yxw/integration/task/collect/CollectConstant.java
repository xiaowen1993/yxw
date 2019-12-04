package com.yxw.integration.task.collect;

public class CollectConstant {

	public static final float POOL_SIZE = 1.5f;

	public static final int threadNum;

	static {
		int cpuNums = Runtime.getRuntime().availableProcessors();
		float MathNum = cpuNums * CollectConstant.POOL_SIZE;
		threadNum = (int) MathNum;
	}
}
