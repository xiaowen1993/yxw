/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-9-6</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.registry.vo;

import java.io.Serializable;

/**
 * @Package: com.yxw.platform.registry.vo
 * @ClassName: RegistryParamVo
 * @Statement: <p>Dubbo服务注册的参数对象</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegistryParamVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4528444358596715955L;

	/**
	 * 服务提供者的名称
	 */
	private String application;

	/**
	 * 服务引用BeanId(必填)
	 */
	private String serviceId;

	/**
	 * 服务接口名(必填)
	 */
	private String interfaceClass;

	/**
	 * 集群方式，可选：failover/failfast/failsafe/failback/forking
	 */
	private String cluster;

	/**
	 * 服务方法调用超时时间(毫秒)
	 */
	private String timeout;

	/**
	 * 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0
	 */
	private String retries;

	/**
	 * 对每个提供者的最大连接数，rmi、http、hessian等短连接协议表示限制连接数，dubbo等长连接协表示建立的长连接个数
	 */
	private String connections;

	/**
	 * 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
	 */
	private String loadbalance;

	private String serviceUrl;

	private String version;

	public RegistryParamVo(String serviceId, String interfaceClass, String cluster, String timeout, String serviceUrl, String version) {
		super();
		this.serviceId = serviceId;
		this.interfaceClass = interfaceClass;
		this.cluster = cluster;
		this.timeout = timeout;
		this.serviceUrl = serviceUrl;
		this.version = version;
	}

	public RegistryParamVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRetries() {
		return retries;
	}

	public void setRetries(String retries) {
		this.retries = retries;
	}

	public String getConnections() {
		return connections;
	}

	public void setConnections(String connections) {
		this.connections = connections;
	}

	public String getLoadbalance() {
		return loadbalance;
	}

	public void setLoadbalance(String loadbalance) {
		this.loadbalance = loadbalance;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}
