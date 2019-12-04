package com.yxw.integration.common;

import java.util.Properties;

public class PropertiesUtil {
	public static int debug = 0;
	public static String destinations = "";

	public static String kafkaIp;
	public static Integer kafkaPort;

	public static String elasticsearchIp;
	public static Integer elasticsearchPort;

	public ConfigProperties configProperties;

	public static void getProperties() {
		ReadProperties readProperties = new ReadProperties();
		Properties p = readProperties.read();
		String tmp = "";

		tmp = String.valueOf(p.get("debug"));
		if (!"".equals(tmp)) {
			debug = Integer.valueOf(tmp);
		}

		if ("rc".equals(String.valueOf(p.get("release")))) {//正式环境
			tmp = String.valueOf(p.get("canal.destinations.rc"));
			if (!"".equals(tmp)) {
				destinations = tmp;
			}
		} else {
			tmp = String.valueOf(p.get("canal.destinations.dev"));
			if (!"".equals(tmp)) {
				destinations = tmp;
			}
		}

		tmp = String.valueOf(p.get("kafka.ip"));
		if (!"".equals(tmp)) {
			kafkaIp = tmp;
		}

		tmp = String.valueOf(p.get("kafka.port"));
		if (!"".equals(tmp)) {
			kafkaPort = Integer.valueOf(tmp);
		}

		tmp = String.valueOf(p.get("elasticsearch.ip"));
		if (!"".equals(tmp)) {
			elasticsearchIp = tmp;
		}

		tmp = String.valueOf(p.get("elasticsearch.port"));
		if (!"".equals(tmp)) {
			elasticsearchPort = Integer.valueOf(tmp);
		}

	}

	public static ConfigProperties getProperties(String propertiesFilename) {

		ConfigProperties cp = new ConfigProperties();

		ReadProperties readProperties = new ReadProperties();
		Properties p = readProperties.read(propertiesFilename);
		String tmp = "";

		//canal配置
		tmp = String.valueOf(p.get("ip"));
		if (!"".equals(tmp)) {
			cp.setIp(tmp);
		}

		tmp = String.valueOf(p.get("port"));
		if (!"".equals(tmp)) {
			cp.setPort(Integer.parseInt(tmp));
		}

		tmp = String.valueOf(p.get("destination"));
		if (!"".equals(tmp)) {
			cp.setDestination(String.valueOf(p.get("destination")));
		}

		tmp = String.valueOf(p.get("username"));
		if (!"".equals(tmp)) {
			cp.setUsername(String.valueOf(p.get("username")));
		}

		tmp = String.valueOf(p.get("password"));
		if (!"".equals(tmp)) {
			cp.setPassword(String.valueOf(p.get("password")));
		}

		tmp = String.valueOf(p.get("filter"));
		if (!"".equals(tmp)) {
			cp.setFilter(tmp);
		}

		//kafak配置
		tmp = String.valueOf(p.get("kafkaIp"));
		if (!"".equals(tmp)) {
			cp.setKafkaIp(tmp);
		}
		tmp = String.valueOf(p.get("kafkaPort"));
		if (!"".equals(tmp)) {
			cp.setKafkaPort(Integer.parseInt(tmp));
		}

		return cp;
	}

	/**
	 * @return the debug
	 */
	public static int getDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public static void setDebug(int debug) {
		PropertiesUtil.debug = debug;
	}

	/**
	 * @return the destinations
	 */
	public static String getDestinations() {
		return destinations;
	}

	/**
	 * @param destinations the destinations to set
	 */
	public static void setDestinations(String destinations) {
		PropertiesUtil.destinations = destinations;
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
	public Integer getKafkaPort() {
		return kafkaPort;
	}

	/**
	 * @param kafkaPort the kafkaPort to set
	 */
	public void setKafkaPort(Integer kafkaPort) {
		this.kafkaPort = kafkaPort;
	}

	/**
	 * @return the elasticsearchIp
	 */
	public static String getElasticsearchIp() {
		return elasticsearchIp;
	}

	/**
	 * @param elasticsearchIp the elasticsearchIp to set
	 */
	public static void setElasticsearchIp(String elasticsearchIp) {
		PropertiesUtil.elasticsearchIp = elasticsearchIp;
	}

	/**
	 * @return the elasticsearchPort
	 */
	public static Integer getElasticsearchPort() {
		return elasticsearchPort;
	}

	/**
	 * @param elasticsearchPort the elasticsearchPort to set
	 */
	public static void setElasticsearchPort(Integer elasticsearchPort) {
		PropertiesUtil.elasticsearchPort = elasticsearchPort;
	}

}