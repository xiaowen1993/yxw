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
package com.yxw.platform.hospital.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.hospital.dao
 * @ClassName: WhiteListDaoDetail
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
public interface WhiteListDetailDao extends BaseDao<WhiteListDetail, String> {
	public List<WhiteListDetail> findWhiteListDetail(Map<String, Object> param);

	public WhiteListDetail findByWhiteListIdAndPhone(String whiteListId, String phone);

	public WhiteListDetail findByWhiteListIdAndOpenId(String whiteListId, String openId);

	public void updateOpenIdByPhone(Map<String, Object> paraMap);

	/**
	 * 得到所有的白名单信息 供缓存初始化使用 add by Yuce
	 * 
	 * @return
	 */
	public List<WhiteListVo> findAllWhiteInfo();

	public List<WhiteListVo> findWhiteDetailsByApp(String appId, String appCode);

	public List<WhiteListDetail> findByWhiteListId(String whiteListId);
}
