/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年12月26日
 * @version 1.0
 */
package com.yxw.integration.elasticsearch;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.integration.common.PropertiesUtil;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年12月26日  
 */
public class HandlerMessageCallable implements Callable<TopicPartition> {
	protected final static Logger logger = LoggerFactory.getLogger(HandlerMessageCallable.class);
	private static int debug = PropertiesUtil.debug;

	private TopicPartition partition;
	private KafkaConsumer<String, String> consumer;

	private List<ConsumerRecord<String, String>> partitionRecords;

	private AtomicInteger successed = new AtomicInteger(0);

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年12月26日 
	 * @param partitionRecords 
	 */
	public HandlerMessageCallable(TopicPartition partition, KafkaConsumer<String, String> consumer,
			List<ConsumerRecord<String, String>> partitionRecords) {
		super();
		this.partition = partition;
		this.consumer = consumer;
		this.partitionRecords = partitionRecords;
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年12月26日  
	 */
	public TopicPartition call() {
		TopicPartition topicPartition = null;
		try {

			String topic = "";
			Map<String, Map<String, Object>> idDataMap = null;
			for (ConsumerRecord<String, String> record : partitionRecords) {
				idDataMap = new LinkedHashMap<String, Map<String, Object>>();
				Map<String, Object> field = new LinkedHashMap<String, Object>();

				topic = record.topic().toLowerCase();

				JSONObject jsonObject = JSONArray.parseObject(record.value());

				String type = (String) JSONPath.eval(jsonObject, "$.head.type");

				if (StringUtils.equals("INSERT", type) || StringUtils.equals("UPDATE", type)) {
					String id = "";
					com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("after");
					Iterator<Object> it = jsonArray.iterator();
					while (it.hasNext()) {
						JSONObject obj = (JSONObject) it.next();
						if (StringUtils.equals(obj.getString("name"), "ID")) {
							id = obj.getString("value");
						}
						if (StringUtils.equals(obj.getString("update"), "true")) {
							field.put(obj.getString("name").toLowerCase(), obj.get("value"));
						}
					}

					idDataMap.put(id, field);

					if (debug > 0) {
						logger.debug("================================================record.offset:{}, record.value:{}",
								new Object[] { record.offset(), record.value() });
					}

					if (record.value().contains( ( "INSERT" ))) {
						ElasticsearchController.insertById(topic, StringUtils.substringAfterLast(topic, "."), id, field);
					} else if (record.value().contains( ( "UPDATE" ))) {
						ElasticsearchController.update(topic, StringUtils.substringAfterLast(topic, "."), id, field);
					}

				} else if (StringUtils.equals("DELETE", type)) {

					String id = "";
					com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray("before");
					Iterator<Object> it = jsonArray.iterator();
					while (it.hasNext()) {
						JSONObject obj = (JSONObject) it.next();
						if (StringUtils.equals(obj.getString("name"), "ID")) {
							id = obj.getString("value");
							break;
						}
					}

					ElasticsearchController.deleteById(topic, StringUtils.substringAfterLast(topic, "."), id);

					if (debug > 0) {
						logger.debug("================================================record.offset:{}, record.value:{}",
								new Object[] { record.offset(), record.value() });
					}
				}

				topic = "";
				idDataMap.clear();
				idDataMap = null;
			}

			topicPartition = partition;

			successed.incrementAndGet();

		} catch (Exception e) {
			logger.error("sync elasticsearch index data failue, error message:{}", e);
		}

		return topicPartition;
	}

	/**
	 * @return the partitionRecords
	 */
	public List<ConsumerRecord<String, String>> getPartitionRecords() {
		return partitionRecords;
	}

	/**
	 * @param partitionRecords the partitionRecords to set
	 */
	public void setPartitionRecords(List<ConsumerRecord<String, String>> partitionRecords) {
		this.partitionRecords = partitionRecords;
	}

	/**
	 * @return the consumer
	 */
	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}

	/**
	 * @param consumer the consumer to set
	 */
	public void setConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the partition
	 */
	public TopicPartition getPartition() {
		return partition;
	}

	/**
	 * @param partition the partition to set
	 */
	public void setPartition(TopicPartition partition) {
		this.partition = partition;
	}

	/**
	 * @return the successed
	 */
	public AtomicInteger getSuccessed() {
		return successed;
	}

	/**
	 * @param successed the successed to set
	 */
	public void setSuccessed(AtomicInteger successed) {
		this.successed = successed;
	}

	public static void main(String[] args) {
		String a =
				"{\"head\":{\"binlog_pos\":121869,\"type\":\"INSERT\",\"binlog_file\":\"mysql-bin.000051\",\"db\":\"new_yx129_platform\",\"table\":\"BIZ_DEPOSIT_PART_REFUND_RECORD\"},\"after\":[{\"name\":\"ID\",\"update\":true,\"value\":\"x\"},{\"name\":\"HOSPITAL_ID\",\"update\":true,\"value\":\"scsac\"},{\"name\":\"HOSPITAL_CODE\",\"update\":true,\"value\":\"sacsa\"},{\"name\":\"HOSPITAL_NAME\",\"update\":true,\"value\":\"csac\"},{\"name\":\"BRANCH_ID\",\"update\":true,\"value\":\"sac\"},{\"name\":\"BRANCH_CODE\",\"update\":true,\"value\":\"\"},{\"name\":\"CARD_TYPE\",\"update\":true,\"value\":\"\"},{\"name\":\"CARD_NO\",\"update\":true,\"value\":\"\"},{\"name\":\"OPEN_ID\",\"update\":true,\"value\":\"\"},{\"name\":\"IS_EXCEPTION\",\"update\":true,\"value\":\"\"},{\"name\":\"IS_HANDLE_SUCCESS\",\"update\":true,\"value\":\"\"},{\"name\":\"HANDLE_COUNT\",\"update\":true,\"value\":\"\"},{\"name\":\"HANDLE_LOG\",\"update\":true,\"value\":\"\"},{\"name\":\"PATIENT_NAME\",\"update\":true,\"value\":\"\"},{\"name\":\"ORDER_NO\",\"update\":true,\"value\":\"\"},{\"name\":\"ORDER_NO_HASH_VAL\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_ORDER_NO\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_ORDER_NO_HASH_VAL\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_HIS_ORD_NO\",\"update\":true,\"value\":\"\"},{\"name\":\"AGT_ORD_NUM\",\"update\":true,\"value\":\"\"},{\"name\":\"AGT_REFUND_ORD_NUM\",\"update\":true,\"value\":\"\"},{\"name\":\"FEE_DESC\",\"update\":true,\"value\":\"\"},{\"name\":\"IS_HAD_CALL_BACK\",\"update\":true,\"value\":0},{\"name\":\"RECORD_TITLE\",\"update\":true,\"value\":\"\"},{\"name\":\"FAILURE_MSG\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_STATUS\",\"update\":true,\"value\":0},{\"name\":\"TOTAL_FEE\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_FEE\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_MODE\",\"update\":true,\"value\":\"\"},{\"name\":\"HIS_ORD_NO\",\"update\":true,\"value\":\"\"},{\"name\":\"CREATE_TIME\",\"update\":true,\"value\":\"\"},{\"name\":\"UPDATE_TIME\",\"update\":true,\"value\":\"\"},{\"name\":\"PAY_TIME\",\"update\":true,\"value\":\"\"},{\"name\":\"REFUND_TIME\",\"update\":true,\"value\":\"\"},{\"name\":\"PATIENT_MOBILE\",\"update\":true,\"value\":\"\"}]}";
		JSONObject jsonOject = JSONObject.parseObject(a);
		System.out.println(JSONPath.eval(jsonOject, "$.head.type"));
	}
}
