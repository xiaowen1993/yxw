/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.stats.dao.platform;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.HospIdAndAppSecret;

@Repository("hospIdAndAppSecretDao")
public class HospIdAndAppSecretDaompl extends BaseDaoImpl implements HospIdAndAppSecretDao {

	private static Logger logger = LoggerFactory.getLogger(HospIdAndAppSecretDaompl.class);

	private final static String SQLNAME_FIND_VALID_WECHAT_APP_INFO = "findValidWechatAppInfo";

	/**
	 * 获取可用的微信公众号app信息
	 * */
	public List<HospIdAndAppSecret> findValidWechatAppInfo() {
		List<HospIdAndAppSecret> list = null;
		try {
			list = sqlSession.selectList("com.yxw.stats.entity.platform.HospIdAndAppSecret" + "." + SQLNAME_FIND_VALID_WECHAT_APP_INFO);
		} catch (Exception e) {
			logger.error(String.format("获取可用的微信公众号app信息出错!语句：%s", getSqlName(SQLNAME_FIND_VALID_WECHAT_APP_INFO)), e);
			throw new SystemException(String.format("获取可用的微信公众号app信息出错!语句：%s", getSqlName(SQLNAME_FIND_VALID_WECHAT_APP_INFO)), e);
		}
		return list;
	}

}
