package com.yxw.platform.sdk;

/**
 * @Package: com.yxw.platform.sdk
 * @ClassName: SDKConfig
 * @Statement: <p>SDK的相关参数配置 使用SpringBean来加载</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-2
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SDKConfig {
	/**
	 * 当请求openId失败，尝试重复请求openId的次数
	 */
	private Long maxGetOpenIdTime;

	public SDKConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getMaxGetOpenIdTime() {
		return maxGetOpenIdTime;
	}

	public void setMaxGetOpenIdTime(Long maxGetOpenIdTime) {
		this.maxGetOpenIdTime = maxGetOpenIdTime;
	}
}
