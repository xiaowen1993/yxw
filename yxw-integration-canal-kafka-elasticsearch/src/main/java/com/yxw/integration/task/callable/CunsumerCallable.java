package com.yxw.integration.task.callable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.yxw.integration.common.PropertiesUtil;
import com.yxw.integration.elasticsearch.HandlerMessageCallable;
import com.yxw.integration.task.threadpool.SimpleThreadFactory;

public class CunsumerCallable implements Runnable {

	protected final static Logger logger = LoggerFactory.getLogger(CunsumerCallable.class);

	private static int debug = PropertiesUtil.debug;

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private KafkaConsumer<String, String> consumer;

	public CunsumerCallable(KafkaConsumer<String, String> consumer) {
		super();
		this.consumer = consumer;
	}

	public void run() {

		String topic = consumer.subscription().iterator().next();
		int threadNum = 5;
		if (consumer.partitionsFor(topic).size() > 0) {
			threadNum = consumer.partitionsFor(topic).size();
		}

		ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("handlerMessage" + "-child"));
		List<FutureTask<TopicPartition>> taskList = new ArrayList<FutureTask<TopicPartition>>();

		try {
			while (!closed.get()) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);

				if (debug > 0) {
					logger.debug("receive message: (" + new Gson().toJson(records) + ")");
				}

				for (TopicPartition partition : records.partitions()) {
					List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

					HandlerMessageCallable collectCall = new HandlerMessageCallable(partition, consumer, partitionRecords);
					FutureTask<TopicPartition> collectTask = new FutureTask<TopicPartition>(collectCall);

					taskList.add(collectTask);
					collectExec.submit(collectTask);

				}

				try {
					for (FutureTask<TopicPartition> taskF : taskList) {
						TopicPartition partition = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
						if (null != partition) {
							List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
							if (!CollectionUtils.isEmpty(partitionRecords)) {
								long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
								consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
							}
						}
					}

				} catch (Exception e) {
					logger.error("Failed to get sync thread information,failed message: {}", e);
				}

			}
		} catch (Exception e) {
			logger.error("Fail to perform index operation,failed: {}", e);
			if (!closed.get()) {
				throw e;
			}
		} finally {
			if (debug > 0) {
				logger.debug("this current consumer close toString info:{}", consumer.toString());
			}
			consumer.close();
		}

	}

	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
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

}
