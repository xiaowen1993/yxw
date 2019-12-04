package com.yxw.integration.task.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.integration.common.PropertiesUtil;
import com.yxw.integration.task.callable.CunsumerCallable;
import com.yxw.integration.task.threadpool.SimpleThreadFactory;

public class CunsumerCollector {

	protected final static Logger logger = LoggerFactory.getLogger(CunsumerCollector.class);

	private static int debug = PropertiesUtil.debug;

	private String topic;

	private Integer partition;

	public CunsumerCollector(String topic, Integer partition) {
		super();
		this.topic = topic;
		this.partition = partition;
	}

	public CunsumerCollector() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void start() {
		final List<CunsumerCallable> cunsumerCallables = new ArrayList<CunsumerCallable>();
		int threadNum = partition.intValue();
		ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory(topic + "-" + "cunsumer" + "-child"));

		Properties props = new Properties();
		props.put("bootstrap.servers", PropertiesUtil.kafkaIp + ":" + PropertiesUtil.kafkaPort);
		props.put("group.id", topic.substring(0, topic.lastIndexOf(".")));//不同ID 可以同时订阅消息。例如：topic=209.new_yx129_platform.BIZ_DEPOSIT_RECORD，group.id=209.new_yx129_platform
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		for (int i = 0; i < threadNum; i++) {

			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList(topic));//订阅TOPIC

			CunsumerCallable cunsumerCallable = new CunsumerCallable(consumer);
			cunsumerCallables.add(cunsumerCallable);

			collectExec.execute(cunsumerCallable);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (CunsumerCallable cunsumerCallable : cunsumerCallables) {
					cunsumerCallable.shutdown();
				}
				collectExec.shutdown();
				try {
					collectExec.awaitTermination(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
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
