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
package com.yxw.platform.hospital.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.PayModeDao;

/**
 * 后台支付方式配置信息管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class PayModeDaoImpl extends BaseDaoImpl<PayMode, String> implements PayModeDao {

	//private Logger logger = LoggerFactory.getLogger(PayModeDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.dao.impl.PayModeDao#findAlls()
	 */
	//    @Override
	//	public List<PayMode> findAlls(){
	//    	try {
	//            return sqlSession.selectList("findAlls");
	//        } catch (Exception e) {
	//            logger.error(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s", "findAlls"), e);
	//            throw new SystemException(String.format("根据医院ID查询接入平台配置信息列表出错！语句：%s","findAlls"), e);
	//        }
	//    }

}
