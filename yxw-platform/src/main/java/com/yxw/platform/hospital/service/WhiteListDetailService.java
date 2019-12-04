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

import java.util.List;

import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.hospital.service
 * @ClassName: WhiteListDetailService
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
public interface WhiteListDetailService extends BaseService<WhiteListDetail, String> {

	public WhiteListDetail findByWhiteListIdAndPhone(String whiteListId, String phone);

	public List<WhiteListDetail> findByWhiteListId(String whiteListId);

	/**
	 * 判断是否加入白名单
	 * 
	 * @param appId
	 * @param phone
	 *            手机号码
	 * @param platformType
	 *            平台类型
	 * @return
	 * */
	public boolean whetherAddWhiteList(String appId, String phone, String platformType);

	/**
	 * 加入白名单
	 * 
	 * @param appId
	 * @param platformType
	 * @param phone
	 * @param openId
	 * **/
	public Boolean updateWhiteListOpenId(String appId, String platformType, String phone, String openId);

	/**
	 * 是否是唯一手机号
	 * @param phone
	 * @return
	 */
	public Boolean isCheckUniquePhone(String whiteListId, String phone);

}
