/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.framework.common.attach.FileHelper;
import com.yxw.framework.common.attach.FileInfo;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.msgpush.service.MsgTemplateLibraryService;

/**
 * 模板库controller
 * 
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: MsgTemplateLibraryController
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/msgpush/msgTemplateLibrary")
public class MsgTemplateLibraryController extends BaseController<MsgTemplateLibrary, String> {
	@Autowired
	private MsgTemplateLibraryService msgTemplateLibraryService;

	@Override
	protected BaseService<MsgTemplateLibrary, String> getService() {
		return this.msgTemplateLibraryService;
	}
	
	@Autowired
	private PlatformService platformService;

	/**
	 * 2017-7-25 17:29:53 - modify
	 * 该方法无法带参数，到页面。需要将平台配置到后台
	 */
	/*@Override
	@RequestMapping(value = "/listView")
	public String listView() {
		return "/sys/msgpush/msgTemplateLibraryList";
	}*/
	
	@RequestMapping(value = "/toListView")
	public ModelAndView toListView() {
		ModelAndView view = new ModelAndView("/sys/msgpush/msgTemplateLibraryList");
		List<PlatformMsgModeVo> platforms = platformService.findAllPlatformMsgModes();
		view.addObject("platforms", platforms);
		return view;
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public PageInfo<MsgTemplateLibrary> list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize, String search, String source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", search);
		params.put("source", source);
		PageInfo<MsgTemplateLibrary> msgTemplateLibrarys =
				msgTemplateLibraryService.findListByPage(params, new Page<MsgTemplateLibrary>(pageNum, pageSize));
		return msgTemplateLibrarys;
	}

	@RequestMapping(value = "/editView")
	public ModelAndView edit(String id, Integer source) {
		MsgTemplateLibrary msgTemplateLibrary = null;
		if (id != null) {
			msgTemplateLibrary = msgTemplateLibraryService.findById(id);
		}
		if (msgTemplateLibrary == null) {
			msgTemplateLibrary = new MsgTemplateLibrary();
		}
		if (source != null) {
			msgTemplateLibrary.setSource(source);
		}
		return new ModelAndView("/sys/msgpush/msgTemplateLibraryEdit", "entity", msgTemplateLibrary);
	}

	@ResponseBody
	@RequestMapping(value = "/findListBySource")
	public RespBody findListBySource(String source) {
		List<MsgTemplateLibrary> msgTemplateLibraries = msgTemplateLibraryService.findListBySource(source);
		return new RespBody(Status.OK, msgTemplateLibraries);
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMsgTemplateLibrary")
	public RespBody saveMsgTemplateLibrary(MultipartFile iconFile, MultipartFile animationFile, MsgTemplateLibrary entity) {
		FileHelper fileHelper = new FileHelper();
		if (iconFile != null) {
			FileInfo fileInfo = fileHelper.saveFile(iconFile, entity.getIconName());
			if (true == fileInfo.isStatus()) {
				entity.setIconPath(fileInfo.getRelativePath());
			} else {
				return new RespBody(Status.ERROR);
			}
		}
		if (animationFile != null) {
			FileInfo fileInfo = fileHelper.saveFile(animationFile, entity.getAnimationName());
			if (true == fileInfo.isStatus()) {
				entity.setAnimationPath(fileInfo.getRelativePath());
			} else {
				return new RespBody(Status.ERROR);
			}
		}

		if (StringUtils.isNotBlank(entity.getId())) {
			getService().update(entity);
		} else {
			getService().add(entity);
		}
		return new RespBody(Status.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/check")
	public RespBody check(@RequestParam Map<String, Serializable> params) {
		boolean status = getService().check(params);
		if (status) {
			return new RespBody(Status.OK);
		} else {
			return new RespBody(Status.ERROR);
		}
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

}
