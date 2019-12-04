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
package com.yxw.stats.data.common.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package: com.yxw.mobileapp.common
 * @ClassName: HashTableNameGenerator
 * @Statement: <p>hash取模算法分表</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SimpleHashTableNameGenerator {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHashTableNameGenerator.class);
	/**
	 * 分表的子表数
	 */
	public static final int subTableCount = 10;

	/**
	 * 挂号记录的表名
	 */
	public static final String REGISTER_RECORD_TABLE_NAME = "BIZ_REGISTER_RECORD";

	/**
	 * 就诊卡的表名
	 */
	public static final String MEDICAL_CARD_TABLE_NAME = "BIZ_MEDICAL_CARD";

	/**
	 * 门诊记录的表名
	 */
	public static final String CLINIC_RECORD_TABLE_NAME = "BIZ_CLINIC_RECORD";

	/**
	 * 押金补缴记录的表名
	 */
	public static final String DEPOSIT_RECORD_TABLE_NAME = "BIZ_DEPOSIT_RECORD";

	/**
	 * 评价记录的表名
	 */
	public static final String VOTE_TABLE_NAME = "BIZ_VOTE";

	/**
	 * 根据openId为关键值   得到挂号记录的 hash分表
	 * @param openId
	 * @return
	 */
	public static String getRegRecordHashTable(String openId) {
		String hashTableName = getSplitTableName(REGISTER_RECORD_TABLE_NAME, openId, subTableCount, true);
		if (logger.isDebugEnabled()) {
			logger.debug("openId:{}  hashTable name:{}", new Object[] { openId, hashTableName });
		}
		return hashTableName;
	}

	/**
	 * 根据openId为关键值   得到绑卡的 hash分表
	 * @param openId
	 * @return
	 */
	public static String getMedicalCardHashTable(String openId) {
		String hashTableName = getSplitTableName(MEDICAL_CARD_TABLE_NAME, openId, subTableCount, true);
		if (logger.isDebugEnabled()) {
			logger.debug("getMedicalCardHashTable name:{}", hashTableName);
		}
		return hashTableName;
	}

	public static String getClinicRecordHashTable(String openId) {
		String hashTableName = getSplitTableName(CLINIC_RECORD_TABLE_NAME, openId, subTableCount, true);
		if (logger.isDebugEnabled()) {
			logger.debug("getClinicHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getDepositRecordHashTable(String openId) {
		String hashTableName = getSplitTableName(DEPOSIT_RECORD_TABLE_NAME, openId, subTableCount, true);
		if (logger.isDebugEnabled()) {
			logger.debug("getDepositHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getVoteHashTable(String openId) {
		String hashTableName = getSplitTableName(VOTE_TABLE_NAME, openId, subTableCount, true);
		if (logger.isDebugEnabled()) {
			logger.debug("getVoteHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	/**
	 * 得到hsah子表名</p>
	 * @param tableName      原表名<br>
	 * @param splitKeyVal    hash取模的关键值(根据splitKeyVal值来取模)<br>
	 * @param subTableCount  要拆分子表总数<br>
	 * @param isNeedUniform  是否需要均匀散列<br>
	 * @return
	 */
	private static String getSplitTableName(String tableName, Object splitKeyVal, Integer subTableCount, Boolean isNeedUniform) {
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

	/* */
	public static void main(String[] args) {
		System.out.println(getRegRecordHashTable("oAFPxsx2_lFgyS0CucdzxfkUj3nM"));
	}

}
