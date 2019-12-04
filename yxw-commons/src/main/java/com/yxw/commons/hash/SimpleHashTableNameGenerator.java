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
package com.yxw.commons.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package: com.yxw.mobileapp.common
 * @ClassName: HashTableNameGenerator
 * @Statement: <p>
 *             hash取模算法分表
 *             </p>
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
	public static final int subTableCount = 8;

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
	 * 评价记录的表名
	 */
	public static final String EASY_HEALTH_USER_TABLE_NAME = "EH_USER";
	/**
	 * 消息中心(通知中心)表名
	 */
	public static final String SYS_MSG_CENTER = "SYS_MSG_CENTER";
	/**
	 * 设备信息表名
	 */
	public static final String EH_DEVICE_INFO = "EH_DEVICE_INFO";
	/**
	 * 人表名
	 */
	public static final String BIZ_PEOPLE_TABLE_NAME = "BIZ_PEOPLE";
	/**
	 * 家人表名
	 */
	public static final String BIZ_FAMILY_TABLE_NAME = "BIZ_FAMILY";

	/**
	 * NIH表名
	 */
	public static final String BIZ_NIH_TABLE_NAME = "BIZ_NIH_RECORD";

	/**
	 * 根据openId为关键值 得到挂号记录的 hash分表
	 * 
	 * @param openId
	 * @return
	 */
	public static String getRegRecordHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(REGISTER_RECORD_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("openId:{}  hashTable name:{}", new Object[] { splitKeyVal, hashTableName });
		}
		return hashTableName;
	}

	/**
	 * 根据openId为关键值 得到绑卡的 hash分表
	 * 
	 * @param openId
	 * @return
	 */
	public static String getMedicalCardHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(MEDICAL_CARD_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getMedicalCardHashTable name:{}", hashTableName);
		}
		return hashTableName;
	}

	public static String getClinicRecordHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(CLINIC_RECORD_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getClinicHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getDepositRecordHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(DEPOSIT_RECORD_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getDepositHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getVoteHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(VOTE_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getVoteHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getSysMsgCenterHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(SYS_MSG_CENTER, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getSysMsgCenterHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getEhDeviceInfoHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(EH_DEVICE_INFO, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getEhDeviceInfoHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getEasyHealthUserHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(EASY_HEALTH_USER_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getEasyHealthUserHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getPeopleHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(BIZ_PEOPLE_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getPeopleHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getFamilyHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(BIZ_FAMILY_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getFamilyHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	public static String getNihHashTable(Object splitKeyVal, Boolean isNeedUniform) {
		String hashTableName = getSplitTableName(BIZ_NIH_TABLE_NAME, splitKeyVal, subTableCount, isNeedUniform);
		if (logger.isDebugEnabled()) {
			logger.debug("getNihHashTable name: {}.", hashTableName);
		}
		return hashTableName;
	}

	/**
	 * 得到hsah子表名</p>
	 * 
	 * @param tableName
	 *            原表名<br>
	 * @param splitKeyVal
	 *            hash取模的关键值(根据splitKeyVal值来取模)<br>
	 * @param subTableCount
	 *            要拆分子表总数<br>
	 * @param isNeedUniform
	 *            是否需要均匀散列<br>
	 * @return
	 */
	private static String getSplitTableName(String tableName, Object splitKeyVal, Integer subTableCount, Boolean isNeedUniform) {
		long hashVal = 0;
		if (splitKeyVal instanceof Number) {
			hashVal = Integer.parseInt(splitKeyVal.toString());
		} else {
			hashVal = splitKeyVal.toString().hashCode();
		}

		// 斐波那契（Fibonacci）散列
		if (isNeedUniform) {
			hashVal = ( hashVal * 2654435769L ) >> 28;
		}

		// 避免hashVal超出 MAX_VALUE = 0x7fffffff时变为负数,取绝对值
		//		hashVal = Math.abs(hashVal) % subTableCount;
		hashVal = ( hashVal & 0xff ) % subTableCount;
		String splitableName = tableName + "_" + ( hashVal + 1 );
		return splitableName;
	}

	/* */
	public static void main(String[] args) {
		//		System.out.println(getRegRecordHashTable("b296582f8d6b43b0b02754a2b67d8c68", true));
		//		System.out.println(getRegRecordHashTable("8be3cd3ccb6e48638c1c9a7cd0d2e669", true));
		//		System.out.println(getRegRecordHashTable("b296582f8d6b43b0b02754a2b67d8c68", true));
		//		System.out.println(getRegRecordHashTable("a89f3b785148445b8b7f99474a8b378a", true));
		System.out.println(getRegRecordHashTable("oSJHhsjYkOu-hsU3icrmcsw342AE", true));
		//		System.out.println(getSplitTableName(REGISTER_RECORD_TABLE_NAME, 91, subTableCount, false));

		/*List<String> tables =
				Lists.newArrayList("1c03db57f43201ba", "9d1515c075bb795b", "89c2d2bba45e3d43", "8e561705f2446c53", "6db2c04968506bb1",
						"c6720dd5eacd4e91", "40420d107d5e1cd1", "8f6b19b5486c6ec8", "bcc9607f0a171051", "4cfa761859168b57",
						"a9ad6644ef3da75b", "3fa7d5c1602b9c1a", "489425e5ec319078", "0a5180955fd4fa17", "d085f264e11aebc5",
						"3e30eb3253288aaf", "2ee8a7d67ef125ad", "14f124601cf909d2", "56a15f2db8267f46", "1ff2f61d021dd086",
						"98671f749ee27997", "3ab565fdeba27b00", "b8279fcb8c689da7", "8e8bb09536c17cf2", "473f32802d819820",
						"20afc4c17d86e129", "d9deefb82952ec37", "9e5914532d6fa09d", "5445959f28ca0a23", "989a2f2b4f74c2d6",
						"39a42ae3f2efb259", "bdebb9f8b37d5f44", "5fdbf1c4f7ad8d45", "fcf15914bf0d0d3e", "cf562cff6e43caf5",
						"f2342043562d51d4", "0c3409e993bf7557", "6b62914110727cf4", "f876db3d713f0413", "1bb9f1a7e1b57138",
						"f4922444d2bff7ab", "bc4fdc5d8a100b6e", "5fe26a2c1f54ec37", "8a6d720d6bfd6434", "2cd4b497bb8ce0ab",
						"e6f3770606921c1d", "0a2635c56a55eb6a", "53325ac20b14d246", "1f7b9af0955410bb", "b3a6ef5ac94dff1c",
						"efb237a39776d3de", "85e2f2048e63e440", "dfced09d4bf30762", "5dd9b0c1675d08f4", "09bfe5c403a876fe",
						"b6f5f4f4147ef737", "5738dc0619e9d0b2", "ff349b046ffac54d", "04f9e21f5ea74ce4", "eaf903474003caf4",
						"c562802a30a698c6", "6c8b14b3bc64df90", "c87e588d8a440892", "7156466660256af8", "ccdb7957971d87f3",
						"d40cfa1c16968c8d", "9735f8539d900247", "5fd5defc004e45dd", "2a23bb223f979dac", "21061224eecea37e",
						"d2d3903a273f77d4", "36990baeda972310", "ab2aae58ab920c1c");

		for (String table : tables) {
			getEasyHealthUserHashTable(table, true);
		}*/
	}
}
