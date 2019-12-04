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
package com.yxw.platform.area.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.app.biz.area.service.AreaService;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;

/**
 * 省市区域级联 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2016年4月20日
 */
@Controller
@RequestMapping("/sys/area")
public class AreaController extends BizBaseController<Area, String> {

	//private Logger logger = LoggerFactory.getLogger(AreaController.class);

	@Autowired
	private AreaService areaService;

	@Override
	protected BaseService<Area, String> getService() {
		return areaService;
	}

	@ResponseBody
	@RequestMapping(value = "/getAreasByParentId", method = RequestMethod.POST)
	public Object getAreasByParentId(@RequestParam(value = "parentId", required = true) String parentId, @RequestParam(value = "level", required = true) String level) {
		List<Area> areas = new ArrayList<Area>();
		try {
			if ("2".equals(level)) {
				// areas = areaService.findTwoLevelAreasByParentId(parentId);
				areas = areaService.getCacheTwoLevelAreasByParentId(parentId);
			} else if ("3".equals(level)) {
				// areas = areaService.findThreeLevelAreasByParentId(parentId);
				areas = areaService.getCacheThreeLevelAreasByParentId(parentId);
			}

			return new RespBody(Status.OK, areas);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "获取区域信息失败");
		}
	}
}
