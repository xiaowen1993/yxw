/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月13日</p>
 *  <p> Created by caiwq</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.attach.dao;

import java.util.List;

import com.yxw.framework.common.attach.entity.Attach;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @author caiwq
 * @version 1.0
 */
public interface AttachDao extends BaseDao<Attach, String> {
	List<Attach> selectList(Attach attach);

	/**
	 * 根据attachId查询
	 * @param attachId
	 * @return
	 */
	public Attach findByAttachId(String attachId);
}
