/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.register.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.mobile.biz.register.StopRegisterLog;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.mobileapp.biz.register.dao.StopRegisterLogDao;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.register.dao.impl
 * @ClassName: StopRegisterLogDaoImpl
 * @Statement: <p>停诊异常日志记录</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "stopRegisterLogDao")
public class StopRegisterLogDaoImpl extends BaseDaoImpl<StopRegisterLog, String> implements StopRegisterLogDao {

}
