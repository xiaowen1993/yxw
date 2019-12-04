/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年12月22日
 * @version 1.0
 */
package com.yxw.integration.task.taskitem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.integration.common.PropertiesUtil;
import com.yxw.integration.task.collect.CunsumerCollector;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年12月22日  
 */
public class CunsumerRunnable implements Runnable {

	protected final static Logger logger = LoggerFactory.getLogger(CunsumerRunnable.class);

	private static int debug = 0;

	private String topic;

	private Integer partition;

	public CunsumerRunnable() {
		super();
	}

	public CunsumerRunnable(String topic, Integer partition) {
		super();
		this.topic = topic;
		this.partition = partition;
	}

	@Override
	public void run() {

		CunsumerCollector collector = new CunsumerCollector(topic, partition);
		collector.start();

	}

	public static void main(String[] args) {

		PropertiesUtil.getProperties();
		debug = PropertiesUtil.debug;

		Properties props = new Properties();
		props.put("bootstrap.servers", PropertiesUtil.kafkaIp + ":" + PropertiesUtil.kafkaPort);
		props.put("client.id", "adminClient");

		org.apache.kafka.clients.admin.AdminClient adminClient = KafkaAdminClient.create(props);
		org.apache.kafka.clients.admin.ListTopicsResult listTopicsResult = adminClient.listTopics();

		try {
			Set<String> topics = listTopicsResult.names().get();

			for (String topic : topics) {
				org.apache.kafka.clients.admin.DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Arrays.asList(topic));
				Collection<KafkaFuture<TopicDescription>> topicDescriptions = describeTopicsResult.values().values();

				for (KafkaFuture<TopicDescription> kafkaFuture : topicDescriptions) {
					TopicDescription topicDescription = kafkaFuture.get();
					if (StringUtils.contains(topicDescription.name(), ".")) {
						new Thread(new CunsumerRunnable(topicDescription.name(), topicDescription.partitions().size()),
								topicDescription.name() + "-" + "cunsumer" + "-main").start();
					}
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the partition
	 */
	public Integer getPartition() {
		return partition;
	}

	/**
	 * @param partition the partition to set
	 */
	public void setPartition(Integer partition) {
		this.partition = partition;
	}

}
