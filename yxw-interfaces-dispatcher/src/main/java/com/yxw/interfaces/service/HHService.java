/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.service;

import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.charge.ChargeRecordRequest;
import com.yxw.interfaces.vo.charge.ChargeRequest;

/**
 * 广州红十字会医院-医享网络标准接口
 * @Package: com.yxw.interfaces.service
 * @ClassName: HHService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface HHService extends YxwService {
	/**
	 * 红会新增 充值3001 接口
	 */
	public abstract Response charge(ChargeRequest chargeRequest);

	/**
	 * 红会新增 充值记录查询
	 */
	public abstract Response getChargeRecord(ChargeRecordRequest chargeRecordRequest);

}
