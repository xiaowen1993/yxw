/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.cache;

/**
 * @Package: com.yxw.platform.sdk.vo
 * @ClassName: AccessTokenVo
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: luob
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AccessTokenVo {

	private long getTime;
	private String expiresTime;
	private String accessToken;

	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}

	public String getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public AccessTokenVo(long getTime, String expiresTime, String accessToken) {
		super();
		this.getTime = getTime;
		this.expiresTime = expiresTime;
		this.accessToken = accessToken;
	}

	public AccessTokenVo() {
		super();
	}

}
