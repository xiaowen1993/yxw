/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.service;

import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.hospital.service
 * @ClassName: WhiteListService
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface WhiteListService extends BaseService<WhiteList, String> {

	public WhiteList findByAppIdAndCode(String appId, String appCode);

	/**
	 * 指定医院id、平台id、openId是否能访问
	 * 
	 * @param hospitalId
	 * @param platformType
	 * @param openId
	 * @return
	 */
	public boolean isCantVisit(String hospitalId, String appCode, String openId);

	public WhiteList findHospitalId(String hospitalId, String appCode);

}
