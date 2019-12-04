/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-12</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.common.service;

import java.util.List;

import com.yxw.commons.vo.cache.WhiteListVo;

/**
 * @Package: com.yxw.platform.common.service
 * @ClassName: CommonService
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface CommonService {
	public List<WhiteListVo> findAllWhiteDetails();

	public List<WhiteListVo> findWhiteDetailsByApp(String appId, String appCode);
}
