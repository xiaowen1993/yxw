/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.yxw.platform.sdk.alipay.executor;

import com.yxw.platform.sdk.alipay.exception.MyException;

/**
 * 业务执行接口
 * 
 * @author taixu.zqq
 * @version $Id: ActionExecutor.java, v 0.1 2014年7月24日 下午3:57:01 taixu.zqq Exp $
 */
public interface ActionExecutor {

	/**
	 * 业务执行方法
	 * 
	 * @return
	 */
	public String execute() throws MyException;

}
