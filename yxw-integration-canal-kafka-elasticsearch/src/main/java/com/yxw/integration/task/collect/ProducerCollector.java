package com.yxw.integration.task.collect;

import java.net.InetSocketAddress;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.yxw.integration.common.ConfigProperties;
import com.yxw.integration.task.callable.ProducerCallable;

public class ProducerCollector {

	protected final static Logger logger = LoggerFactory.getLogger(ProducerCollector.class);

	private ConfigProperties cprops;

	public ProducerCollector(ConfigProperties cprops) {
		super();
		this.cprops = cprops;
	}

	public ProducerCollector() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void start() {

		CanalConnector connector =
				CanalConnectors.newSingleConnector(new InetSocketAddress(cprops.getIp(), cprops.getPort()), cprops.getDestination(),
						cprops.getUsername(), cprops.getPassword());
		connector.connect();
		if (!"".equals(cprops.getFilter())) {
			connector.subscribe(cprops.getFilter());
		} else {
			connector.subscribe();
		}
		connector.rollback();

		Properties props = new Properties();
		props.put("bootstrap.servers", cprops.getKafkaIp() + ":" + cprops.getKafkaPort());
		props.put("client.id", cprops.getDestination() + "_" + "producer" + RandomStringUtils.random(4, "0123456789"));
		props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("acks", "1");
		Producer producer = new KafkaProducer<>(props);

		ProducerCallable pc = new ProducerCallable(cprops.getDestination(), producer, connector);
		pc.call();

		Runtime.getRuntime().addShutdownHook(new Thread() {

			public void run() {

				try {
					logger.info("## stop the canal client");
					producer.close();
				} catch (Throwable e) {
					logger.warn("##something goes wrong when stopping canal:\n{}", ExceptionUtils.getFullStackTrace(e));
				} finally {
					logger.info("## canal client is down.");
				}
			}

		});
	}

}
