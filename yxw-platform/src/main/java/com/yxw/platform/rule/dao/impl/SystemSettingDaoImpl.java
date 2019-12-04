/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.rule.SystemSetting;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.rule.dao.SystemSettingDao;

/**
 * @Package: com.yxw.platform.rule.dao.impl
 * @ClassName: RuleEditDaoImpl
 * @Statement: <p>
 *             编辑规则Dao实现
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class SystemSettingDaoImpl extends BaseDaoImpl<SystemSetting, String> implements SystemSettingDao {

	private static Logger logger = LoggerFactory.getLogger(SystemSettingDaoImpl.class);

	private final static String SQLNAME_FINDBY_HOSPITALID = "";

}
