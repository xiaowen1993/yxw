package com.yxw.platform.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.entity.platform.message.MeterialGroup;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.message.service.MeterialGroupService;

/**
 * 
 * 素材分组控制类
 * 
 * @author luob
 * @version 1.0
 * */
@Controller
@RequestMapping("/message/meterialGroup")
public class MeterialGroupController extends BaseController<MeterialGroup, String> {

	@Autowired
	private MeterialGroupService meterialGroupService;

	@Override
	protected BaseService<MeterialGroup, String> getService() {
		return meterialGroupService;
	}

	/**
	 * 添加素材分组
	 * */
	@ResponseBody
	@RequestMapping(params = "method=add")
	public RespBody add(MeterialGroup meterialGroup) {
		try {
			meterialGroupService.add(meterialGroup);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 修改素材分组
	 * */
	@ResponseBody
	@RequestMapping(params = "method=edit")
	public RespBody edit(MeterialGroup meterialGroup) {
		try {
			meterialGroupService.update(meterialGroup);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 删除素材分组
	 * */
	@ResponseBody
	@RequestMapping(params = "method=delete")
	public RespBody delete(MeterialGroup meterialGroup) {
		try {
			meterialGroupService.logicDelete(meterialGroup);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}
}
