package com.yxw.platform.terminal.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.terminal.Terminal;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.terminal.service.TerminalService;

@Controller
@RequestMapping("/sys/terminal")
public class TerminalController extends BizBaseController<Terminal, String> {
	
	private Logger logger = LoggerFactory.getLogger(TerminalController.class);

	@Autowired
	private TerminalService terminalService;

	@Override
	protected BaseService<Terminal, String> getService() {
		return terminalService;
	}
	
	@RequestMapping(value = "/device")
	public String list(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(required = false, defaultValue = "5") int pageSize,
	        @RequestParam(required = false, defaultValue = "") String search, ModelMap modelMap, HttpServletRequest request) {
		logger.info("分页查询,pageNum=[" + pageNum + "],pageSize=[" + pageSize + "]");

		PageInfo<Terminal> devices = null;

		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		String hospitalId = request.getParameter("hospitalId");
		params.put("hospitalId", hospitalId);
		params.put("search", search);
		
		
		devices = terminalService.findListByPage(params, new Page<Terminal>(pageNum, pageSize));

		modelMap.put("search", search);
		modelMap.put("devices", devices);
		modelMap.put("hospitalId", hospitalId);

		return "/sys/terminal/device";
	}
	
	@RequestMapping("/toAdd")
	public ModelAndView toAdd(String hospitalId, ModelMap modelMap) {
		modelMap.put("hospitalId", hospitalId);
		return new ModelAndView("/sys/terminal/deviceView");
	}
	
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(required = true) String id, ModelMap modelMap) {
		Terminal device = terminalService.findById(id);
		modelMap.put("device", device);

		return new ModelAndView("/sys/terminal/deviceView");
	}
	
	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveDevice")
	public Object saveOptional(Terminal entity, HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("保存...Terminal:{}", JSONObject.toJSONString(entity));
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(PKGenerator.generateId());
				terminalService.add(entity);
			} else {
				terminalService.update(entity);
			}
			
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存失败，请重试。");
		}
	}

}
