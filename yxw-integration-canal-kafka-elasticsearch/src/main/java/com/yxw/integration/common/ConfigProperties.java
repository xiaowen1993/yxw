/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年12月21日
 * @version 1.0
 */
package com.yxw.integration.common;

import java.io.Serializable;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年12月21日  
 */
public class ConfigProperties implements Serializable {

	private static final long serialVersionUID = -3730467693972617616L;

	public String ip = "127.0.0.1";
	public int port = 11111;
	public String destination = "";
	public String username = "";
	public String password = "";
	public String filter = "";

	//kafak配置
	public String kafkaIp = "127.0.0.1";
	public int kafkaPort = 9092;
	public int partition = 5;

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年12月21日  
	 */
	public ConfigProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年12月21日 
	 * @param ip
	 * @param port
	 * @param destination
	 * @param username
	 * @param password
	 * @param filter
	 * @param kafkaIp
	 * @param kafkaPort 
	 */
	public ConfigProperties(String ip, int port, String destination, String username, String password, String filter, String kafkaIp,
			int kafkaPort) {
		super();
		this.ip = ip;
		this.port = port;
		this.destination = destination;
		this.username = username;
		this.password = password;
		this.filter = filter;
		this.kafkaIp = kafkaIp;
		this.kafkaPort = kafkaPort;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
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

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the kafkaIp
	 */
	public String getKafkaIp() {
		return kafkaIp;
	}

	/**
	 * @param kafkaIp the kafkaIp to set
	 */
	public void setKafkaIp(String kafkaIp) {
		this.kafkaIp = kafkaIp;
	}

	/**
	 * @return the kafkaPort
	 */
	public int getKafkaPort() {
		return kafkaPort;
	}

	/**
	 * @param kafkaPort the kafkaPort to set
	 */
	public void setKafkaPort(int kafkaPort) {
		this.kafkaPort = kafkaPort;
	}

	/**
	 * @return the partition
	 */
	public int getPartition() {
		return partition;
	}

	/**
	 * @param partition the partition to set
	 */
	public void setPartition(int partition) {
		this.partition = partition;
	}

}
