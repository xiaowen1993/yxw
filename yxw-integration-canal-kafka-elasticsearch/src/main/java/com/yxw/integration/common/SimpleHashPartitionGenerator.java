package com.yxw.integration.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHashPartitionGenerator {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHashPartitionGenerator.class);
	/**
	 * 分区数
	 */
	public static final int subPartitionCount = 5;

	/**
	 * 根据记录ID值 得到该记录所在分区的 hash分表
	 * 
	 * @param ID
	 * @return
	 */
	public static int getPartition(String id) {
		int partition = getSplitPartition(id, true);
		if (logger.isDebugEnabled()) {
			logger.debug("ID:{}  partition id:{}", new Object[] { id, partition });
		}
		return partition;
	}

	private static int getSplitPartition(String id, boolean isNeedUniform) {
		long hashVal = id.hashCode();

		if (isNeedUniform) {
			hashVal = ( hashVal * 2654435769L ) >> 28;
		}

		hashVal = ( hashVal & 0xff ) % subPartitionCount;
		return new Long(hashVal).intValue();
	}

}
