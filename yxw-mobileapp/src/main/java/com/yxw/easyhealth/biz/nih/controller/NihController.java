package com.yxw.easyhealth.biz.nih.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.app.biz.nih.service.NihService;
import com.yxw.commons.entity.mobile.biz.nih.NihRecord;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

@Controller
@RequestMapping("/app/nih/")
public class NihController {
	private static Logger logger = LoggerFactory.getLogger(NihController.class);

	private NihService service = SpringContextHolder.getBean(NihService.class);

	@RequestMapping("index")
	public ModelAndView toIndexView() {
		return new ModelAndView("/easyhealth/biz/nih/nihIndex");
	}

	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Object addRecord(NihRecord record) {
		try {
			if (record != null) {
				record.setIsValid(1);
				record.setCreateTime(System.currentTimeMillis());
				record.setUpdateTime(System.currentTimeMillis());
				service.add(record);
				return new RespBody(Status.OK, "添加成功");
			}
			return new RespBody(Status.ERROR, "没有添加数据");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR, "写入失败");
		}
	}

	@RequestMapping("list")
	public ModelAndView toListView() {
		return new ModelAndView("/easyhealth/biz/nih/list");
	}

	@ResponseBody
	@RequestMapping(value = "list/getDatas", method = RequestMethod.POST)
	public Object getDatas(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String userId, String appCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("appCode", appCode);
		params.put("hashTableName", SimpleHashTableNameGenerator.getNihHashTable(userId, true));
		PageInfo<NihRecord> pageInfo = service.findListByPage(params, new Page<NihRecord>(pageNum, pageSize));
		return new RespBody(Status.OK, pageInfo);
	}

	@RequestMapping("detail")
	public ModelAndView toDetailView() {
		return null;
	}
}
