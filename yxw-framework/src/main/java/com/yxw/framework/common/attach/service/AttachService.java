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
package com.yxw.framework.common.attach.service;

import java.util.List;

import com.yxw.framework.common.attach.entity.Attach;
import com.yxw.framework.mvc.service.BaseService;

/**
* @author Administrator
* @version 1.0
* @since 2015-5-8
*/

public interface AttachService extends BaseService<Attach, String> {
	List<Attach> findByAttachList(Attach attach);

	/**
	 * 新增/保存附件内容
	 * @param attach
	 * @return
	 */
	public Attach saveOrUpdate(Attach attach);

	/**
	 * 根据attachId查询
	 * @param attachId
	 * @return
	 */
	public Attach findByAttachId(String attachId);
}