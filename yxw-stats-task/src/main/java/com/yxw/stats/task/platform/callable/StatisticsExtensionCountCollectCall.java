/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2017 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2017-5-24</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.stats.task.platform.callable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.entity.platform.ExtensionCount;
import com.yxw.stats.service.platform.ExtensionCountService;

public class StatisticsExtensionCountCollectCall implements Callable<List<ExtensionCount>> {

	private ExtensionCountService extensionCountService = SpringContextHolder.getBean(ExtensionCountService.class);

	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public StatisticsExtensionCountCollectCall(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<ExtensionCount> call() throws Exception {
		// TODO Auto-generated method stub
		return extensionCountService.findExtensionCountByDate(params);
	}
}
