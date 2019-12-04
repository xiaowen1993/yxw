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
package com.yxw.platform.msgpush.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.vo.MsgPushHospitalVo;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 模板消息
 * 
 * @Package: com.yxw.platform.msgpush.service
 * @ClassName: MsgTemplateService
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgTemplateService extends BaseService<MsgTemplate, String> {

	/**
	 * 获取消息模版医院列表
	 * */
	public PageInfo<MsgPushHospitalVo> findHospListByPage(Map<String, Object> params, Page<MsgPushHospitalVo> page);
	
	public boolean delTemplate(String id);
}
