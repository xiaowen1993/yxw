/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-20</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common;

/**
 * @Package: com.yxw.framework.common
 * @ClassName: HashSplitTableGenerator
 * @Statement: <p>hash取模算法分表表名生成器</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HashSplitTableGenerator {
	/**
	 * 得到hsah子表名</p>
	 * @param tableName      原表名<br>
	 * @param splitKeyVal    hash取模的关键值(根据splitKeyVal值来取模)<br>
	 * @param subTableCount  要拆分子表总数<br>
	 * @param isNeedUniform  是否需要均匀散列<br>
	 * @return
	 */
	public static String getSplitTableName(String tableName, Object splitKeyVal, Integer subTableCount, Boolean isNeedUniform) {
		long hashVal = 0;
		if (splitKeyVal instanceof Number) {
			hashVal = Integer.parseInt(splitKeyVal.toString());
		} else {
			hashVal = splitKeyVal.toString().hashCode();
		}

		//斐波那契（Fibonacci）散列
		if (isNeedUniform) {
			hashVal = ( hashVal * 2654435769L ) >> 28;
		}

		//避免hashVal超出 MAX_VALUE = 0x7fffffff时变为负数,取绝对值
		hashVal = Math.abs(hashVal) % subTableCount;
		String splitableName = tableName + "_" + ( hashVal + 1 );
		return splitableName;
	}
}
