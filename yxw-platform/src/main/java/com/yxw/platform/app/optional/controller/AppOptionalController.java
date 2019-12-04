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
package com.yxw.platform.app.optional.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.commons.entity.platform.app.optional.AppOptionalModule;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.app.optional.service.AppOptionalModuleService;
import com.yxw.platform.app.optional.service.AppOptionalService;
import com.yxw.platform.common.controller.BizBaseController;

/**
 * APP功能配置 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2016年4月20日
 */
@Controller
@RequestMapping("/sys/app/optional")
public class AppOptionalController extends BizBaseController<AppOptional, Integer> {

	private Logger logger = LoggerFactory.getLogger(AppOptionalController.class);

	@Autowired
	private AppOptionalService appOptionalService;
	@Autowired
	private AppOptionalModuleService appOptionalModuleService;

	@Override
	protected BaseService<AppOptional, Integer> getService() {
		return appOptionalService;
	}

	@RequestMapping(value = "/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(required = false, defaultValue = "5") int pageSize,
	        @RequestParam(required = false, defaultValue = "") String search, ModelMap modelMap, HttpServletRequest request) {
		logger.info("APP功能配置列表分页查询,pageNum=[" + pageNum + "],pageSize=[" + pageSize + "]");

		PageInfo<AppOptional> appOptionals = null;

		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		params.put("search", search);
		appOptionals = appOptionalService.findListByPage(params, new Page<AppOptional>(pageNum, pageSize));

		modelMap.put("search", search);
		modelMap.put("appOptionals", appOptionals);

		return "/sys/app/optional/list";
	}

	@RequestMapping("/toAdd")
	public ModelAndView toAdd(ModelMap modelMap) {
		// List<AppOptionalModule> appOptionalModules = appOptionalModuleService.findAll();
		List<AppOptionalModule> appOptionalModules = appOptionalModuleService.getCacheAppOptionalModules();
		modelMap.put("appOptionalModules", appOptionalModules);

		return new ModelAndView("/sys/app/optional/view");
	}
	
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(required = true) Integer id, ModelMap modelMap) {
		List<AppOptionalModule> appOptionalModules = appOptionalModuleService.findAll();
		modelMap.put("appOptionalModules", appOptionalModules);
		
		AppOptional appOptional = appOptionalService.findById(id);
		modelMap.put("appOptional", appOptional);

		return new ModelAndView("/sys/app/optional/view");
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveOptional")
	public Object saveOptional(AppOptional entity, HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("保存...AppOptional:{}", JSONObject.toJSONString(entity));
			if (StringUtils.isBlank(entity.getId())) {
				appOptionalService.add(entity);
			} else {
				AppOptional beforUpdateEntity = appOptionalService.findById(Integer.valueOf(entity.getId()));
				
				appOptionalService.update(entity);
				
				//如果板块有改动，则旧版块的缓存也需要更新
				if (!beforUpdateEntity.getAppOptionalModule().getId().equals(entity.getAppOptionalModule().getId())) {
					List<AppOptional> appOptionals = appOptionalService.findByModuleId(beforUpdateEntity.getAppOptionalModule().getId());
					appOptionalService.setCacheAppOptionalsByModuleCode(appOptionals, appOptionals.get(0).getAppOptionalModule().getCode());
				}
			}
			
			// 更新缓存
			List<AppOptional> appOptionals = appOptionalService.findByModuleId(entity.getAppOptionalModule().getId());
			appOptionalService.setCacheAppOptionalsByModuleCode(appOptionals, appOptionals.get(0).getAppOptionalModule().getCode());

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存失败，请重试。");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Object test() {
		try {

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "test error : " + e);
		}
	}
}
