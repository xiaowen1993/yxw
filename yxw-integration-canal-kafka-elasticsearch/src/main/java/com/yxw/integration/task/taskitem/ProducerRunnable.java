package com.yxw.integration.task.taskitem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.integration.common.ConfigProperties;
import com.yxw.integration.common.PropertiesUtil;
import com.yxw.integration.task.collect.ProducerCollector;

public class ProducerRunnable implements Runnable {
	protected final static Logger logger = LoggerFactory.getLogger(ProducerRunnable.class);

	private static int debug = 0;

	private String destination;

	public ProducerRunnable() {
		super();
	}

	public ProducerRunnable(String destination) {
		super();
		this.destination = destination;
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年12月21日  
	 */
	@Override
	public void run() {

		ConfigProperties cp = PropertiesUtil.getProperties(destination + "_instance.properties");
		ProducerCollector collector = new ProducerCollector(cp);
		collector.start();

	}

	public static void main(String[] args) {

		PropertiesUtil.getProperties();
		debug = PropertiesUtil.debug;
		String destinations = PropertiesUtil.destinations;
		String[] destinationsArr = destinations.split(",");

		for (String destination : destinationsArr) {
			ProducerRunnable producerRunnable = new ProducerRunnable(destination);
			new Thread(producerRunnable, destination + "-" + "producer" + "-main").start();
		}

	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

}
