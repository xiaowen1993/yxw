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
import com.yxw.commons.entity.platform.hospital.MsgMode;
import com.yxw.commons.entity.platform.msgpush.MsgTemp;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.framework.common.attach.FileHelper;
import com.yxw.framework.common.attach.FileInfo;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.msgpush.service.MsgTempService;
import com.yxw.platform.msgpush.service.MsgTemplateService;

/**
 * 模板消息controller
 * 
 * @Package: com.yxw.platform.msgpush.controller
 * @ClassName: MsgTemplateController
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
@RequestMapping("/msgpush/msgTemplate")
public class MsgTemplateController extends BaseController<MsgTemplate, String> {
	@Autowired
	private MsgTemplateService msgTemplateService;
	@Autowired
	private MsgTempService msgTempService;
	@Autowired
	private PlatformService platformService;

	@Override
	protected BaseService<MsgTemplate, String> getService() {
		return this.msgTemplateService;
	}

	@RequestMapping(value = "/listViewByHospital")
	public ModelAndView listView(String hospitalId) {
		return new ModelAndView("/sys/msgpush/msgTemplateList", "hospitalId", hospitalId);
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public PageInfo<MsgTemp> list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, String hospitalId, String search) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", hospitalId);
		params.put("search", search);
		PageInfo<MsgTemp> msgTemps = msgTempService.findListByPage(params, new Page<MsgTemp>(pageNum, pageSize));
		return msgTemps;
	}

	@RequestMapping(value = "/editTemp")
	public ModelAndView edit(String id, String hospitalId) {
		ModelAndView view = new ModelAndView("/sys/msgpush/msgTemplateEdit");
		MsgTemplate msgTemplate = null;


		if (id != null) {
			msgTemplate = msgTemplateService.findById(id);
			
			if (StringUtils.isBlank(hospitalId)) {
				hospitalId = msgTemplate.getHospitalId();
			}
			// 查询该医院支持的平台
			List<PlatformMsgModeVo> platforms = platformService.findAllPlatformMsgModesByHospitalId(hospitalId);
			// System.out.println(JSON.toJSONString(platforms));
			view.addObject("platforms", platforms);
			
			// 设置platformSource
			String platformSource = null;
			// 设置msgMode
			String msgMode = null;
			for (PlatformMsgModeVo vo: platforms) {
				if (msgTemplate.getSource().intValue() == vo.getTargetId().intValue()) {
					platformSource = vo.getPlatformName();
					for (MsgMode mode: vo.getMsgModes()) {
						if (msgTemplate.getMsgTarget().intValue() == mode.getTargetId().intValue()) {
							msgMode = mode.getName();
							break;
						}
					}
					break;
				}
			}
			
			view.addObject("platformSource", platformSource);
			view.addObject("msgMode", msgMode);
		} else {
			List<PlatformMsgModeVo> platforms = platformService.findAllPlatformMsgModesByHospitalId(hospitalId);
			// System.out.println(JSON.toJSONString(platforms));
			view.addObject("platforms", platforms);
			
			msgTemplate = new MsgTemplate();
			msgTemplate.setHospitalId(hospitalId);
		}

		view.addObject("entity", msgTemplate);

		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delTemp")
	public RespBody delTemp(String id) {
		boolean result = msgTemplateService.delTemplate(id);
		if (result) {
			return new RespBody(Status.OK);
			
		} else {
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMsgTemplate")
	public RespBody saveMsgTemplate(MultipartFile iconFile, MultipartFile animationFile, MsgTemplate entity) {
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
		boolean status = true;
		if (StringUtils.isNotBlank(entity.getId())) {
			msgTemplateService.update(entity);
		} else {
			String id = msgTemplateService.add(entity);
			if (id == null) {
				status = false;
			}
		}
		if (status) {
			return new RespBody(Status.OK);
		} else {
			return new RespBody(Status.ERROR, "申请模板失败!");
		}

	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor dateEditor = new CustomDateEditor(format, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

}
