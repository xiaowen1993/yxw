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

import java.util.List;

import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 模板库service
 * @Package: com.yxw.platform.msgpush.service
 * @ClassName: MsgTemplateLibraryService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgTemplateLibraryService extends BaseService<MsgTemplateLibrary, String> {
	/**
	 * 根据来源类型查询模板库
	 * @param source
	 * @return
	 */
	public List<MsgTemplateLibrary> findListBySource(String source);
}
