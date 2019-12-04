/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-12</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.service;

import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.rule.service
 * @ClassName: RuleFriedExpressService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RuleFriedExpressService extends BaseService<RuleFriedExpress, String> {
	public String saveRuleFriedExpress(RuleFriedExpress ruleFriedExpress);

	public RuleFriedExpress findByHospitalId(String hospitalId);
}
