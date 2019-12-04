/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-1</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.common.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.framework.mvc.controller.BaseController;

/**
 * @Package: com.yxw.platform.common.controller
 * @ClassName: BizBaseController
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-1
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class BizBaseController<T extends BaseEntity, PK extends Serializable> extends BaseController<T, PK> {
	public User getLoginUser(HttpServletRequest request) {
		Object obj = getSessionAttribute(request, UserConstant.LOGINED_USER);
		if (obj != null) {
			return (User) obj;
		} else {
			return null;
		}
	}
}
