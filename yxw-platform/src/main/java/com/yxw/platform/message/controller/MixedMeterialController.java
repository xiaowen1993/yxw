package com.yxw.platform.message.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.MixedMeterialRespBody;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.utils.JSONUtils;

/**
 * 混合素材（单/多图文）控制类
 * 
 * @author luob
 * @version 1.0
 * */
@Controller
@RequestMapping("/message/mixedMeterial")
public class MixedMeterialController extends BaseController<MixedMeterial, String> {

	private final int BACK_FROM_IMAGETEXT = 1;// 是否是从图文页面处返回
	@Autowired
	private MixedMeterialService mixedMeterialService;
	//@Autowired
	//private HospitalService hospitalService;

	@Override
	protected BaseService<MixedMeterial, String> getService() {
		return mixedMeterialService;
	}

	/**
	 * 分页查询所有的图文
	 * 
	 * */
	@RequestMapping(params = "method=list")
	public String list(@RequestParam(required = false,
			defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "5") int pageSize, String hospitalId, ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", hospitalId);
		PageInfo<MixedMeterial> mixedMeterials = mixedMeterialService.findListByPage(params, new Page<MixedMeterial>(pageNum, pageSize));
		modelMap.put("mixedMeterials", mixedMeterials);
		modelMap.put("hospitalId", hospitalId);
		return "/sys/meterial/pic_text_list";
	}

	/***
	 * 删除图文
	 * 
	 **/
	@RequestMapping(params = "method=delete")
	@ResponseBody
	public RespBody delete(String id) {
		if (id != null) {
			try {
				mixedMeterialService.deleteById(id);
				return new RespBody(Status.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new RespBody(Status.ERROR);
			}
		}
		return new RespBody(Status.ERROR);
	}

	/**
	 * 获取图文信息 ---跳转到图文详情页
	 * 
	 * @param id
	 * @param appId
	 * */
	@RequestMapping("/meterialDetail/get")
	public String obtainMeterialDetail(String id, String appId, ModelMap modelMap) {
		MixedMeterial entity = mixedMeterialService.findById(id);
		modelMap.addAttribute("mixedMeterial", entity);
		modelMap.addAttribute("appId", appId);
		return "/sys/message/wechat/showGraphicDetail";
	}

	/**
	 * 获取图文信息
	 * 
	 * @param id
	 * @param appId
	 * */
	@ResponseBody
	@RequestMapping(params = "method=getMeterialById")
	public RespBody getMeterialById(String id, ModelMap modelMap) {
		try {
			MixedMeterial entity = mixedMeterialService.findById(id);
			modelMap.addAttribute("mixedMeterial", entity);
			return new RespBody(Status.OK, entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 保存单图文
	 * 
	 * @param mixedMeterial
	 * @return
	 * */
	@RequestMapping(params = "method=saveSimple")
	@ResponseBody
	public RespBody saveSimple(MixedMeterial mixedMeterial, String hospitalId, HttpServletRequest request) {
		try {
			// 事先生成图文ID
			String id = PKGenerator.generateId();
			mixedMeterial.setId(id);
			String link = mixedMeterial.getLink();
			// 若未设置图文的点击后跳转链接，则默认生成查看图文详情的链接
			if (link == null || "".equals(link)) {
				mixedMeterial.setLink(request.getRequestURL() + "/meterialDetail/get?id=" + id);
			}
			mixedMeterial.setHospitalId(hospitalId);
			mixedMeterialService.add(mixedMeterial);
			MixedMeterial entity = mixedMeterialService.findById(id);
			// 返回单图文的ID和编辑时间
			return new MixedMeterialRespBody(Status.OK, id, entity.getEt().toLocaleString());
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 保存多图文
	 * 
	 * @param jsonstr
	 *            序列化的form表单参数
	 * @remark 多图文中第一个显示的封面图文既是父图文，isParent的值为1
	 *         ,子图文isParent=0，parentId为父图文ID，回复则只要与父图文关联即可
	 * @return
	 * */
	@RequestMapping(params = "method=saveMulti")
	@ResponseBody
	public RespBody saveMulti(String jsonstr, String hospitalId, HttpServletRequest request) {
		try {
			List<Map<String, Object>> list = JSONUtils.parseJSONList(jsonstr);
			MixedMeterial mixedMeterial = null;
			String parentId = PKGenerator.generateId();
			for (Map<String, Object> map : list) {
				mixedMeterial = new MixedMeterial();
				String id = PKGenerator.generateId();
				mixedMeterial.setId(id);
				mixedMeterial.setHospitalId(hospitalId);
				mixedMeterial.setAuthor(String.valueOf(map.get("author")));
				mixedMeterial.setContent(String.valueOf(map.get("content")));
				mixedMeterial.setCoverPicPath(String.valueOf(map.get("coverPicPath")));
				mixedMeterial.setDescription(String.valueOf(map.get("description")));
				mixedMeterial.setLink(String.valueOf(map.get("link")));
				mixedMeterial.setType(map.get("type") != null ? Integer.valueOf((Integer) map.get("type")) : null);
				mixedMeterial.setTitle(String.valueOf(map.get("title")));
				mixedMeterial.setState(map.get("state") != null ? Integer.valueOf((Integer) map.get("state")) : null);
				mixedMeterial.setIsParent(map.get("isParent") == null ? 0 : (Integer) map.get("isParent"));
				if ((Integer) map.get("isParent") == 1) {
					mixedMeterial.setId(parentId);
				} else {
					mixedMeterial.setParentId(parentId);
				}
				// 若未设置图文的点击后跳转链接，则默认生成查看图文详情的链接
				String link = mixedMeterial.getLink();
				if (link == null || "".equals(link)) {
					if ((Integer) map.get("isParent") == 1) {
						mixedMeterial.setLink(request.getRequestURL() + "/meterialDetail/get?id=" + parentId);
					} else {
						mixedMeterial.setLink(request.getRequestURL() + "/meterialDetail/get?id=" + id);
					}
				}
				mixedMeterialService.add(mixedMeterial);
			}
			// 返回多图文中的父图文
			return new RespBody(Status.OK, parentId);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 填充单图文
	 * 
	 * @param id
	 *            图文ID
	 * @param hospitalId
	 *            当前医院ID
	 * @param thirdType
	 *            公众平台类型
	 * @param request
	 * */
	@RequestMapping(params = "method=getSimpleText")
	public String getSimpleText(String id, String hospitalId, String thirdType, HttpServletRequest request) {
		MixedMeterial mixedMeterial = mixedMeterialService.findById(id);
		request.setAttribute("mixedMeterial", mixedMeterial);
		request.setAttribute("singleMeterialId", id);
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("thirdType", thirdType);
		// 从多图文页面保存后跳回添加界面
		request.setAttribute("backFromImageText", BACK_FROM_IMAGETEXT);
		if ("1".equals(thirdType)) {
			return "/sys/message/wechat/focused";
		} else {
			return "/sys/message/alipay/focused";
		}
	}

	/***
	 * 填充多图文
	 * 
	 * @param ids
	 *            多个图文的ID
	 * @param hospitalId
	 *            当前医院ID
	 * @param thirdType
	 *            公众平台类型
	 * @param request
	 * */
	@RequestMapping(params = "method=getMulti")
	public String getMulti(String ids, String hospitalId, String thirdType, HttpServletRequest request) {
		MixedMeterial mixedMeterial = mixedMeterialService.findById(ids);
		request.setAttribute("mixedMeterial", mixedMeterial);
		request.setAttribute("multiMeterialIds", ids);
		request.setAttribute("hospitalId", hospitalId);
		// 从多图文页面保存后跳回添加界面
		request.setAttribute("backFromImageText", BACK_FROM_IMAGETEXT);
		if ("1".equals(thirdType)) {
			return "/sys/message/wechat/focused";
		} else {
			return "/sys/message/alipay/focused";
		}
	}

	/**
	 * 医院管理编辑素材图文
	 * 
	 * @param id
	 *            图文素材的ID
	 * */
	@RequestMapping(params = "method=toEditMeterial")
	public String editMeterial(String id, int meterialType, String flagTime, String hospitalId, HttpServletRequest request) {
		MixedMeterial mixedMeterial = mixedMeterialService.findById(id);
		request.setAttribute("mixedMeterial", mixedMeterial);
		request.setAttribute("flagTime", flagTime);
		request.setAttribute("hospitalId", hospitalId);
		if (meterialType == MessageConstant.METERIAL_TYPE_SINGLE) {
			return "/sys/message/editSingleText";
		} else {
			request.setAttribute("allData", mixedMeterial.toString());
			// System.out.println(mixedMeterial.toString());
			return "/sys/message/editMultiText";
		}
	}

	/**
	 * 医院管理保存编辑后的素材图文
	 * 
	 * @param mixedMeterial
	 *            素材对象
	 * */
	@RequestMapping(params = "method=editSingle")
	@ResponseBody
	public RespBody editSingle(MixedMeterial mixedMeterial) {
		try {
			MixedMeterial entity = mixedMeterialService.findById(mixedMeterial.getId());
			if (entity != null) {
				entity.setAuthor(mixedMeterial.getAuthor());
				entity.setContent(mixedMeterial.getContent());
				entity.setCoverPicPath(mixedMeterial.getCoverPicPath());
				entity.setWechatPicPath("");
				entity.setDescription(mixedMeterial.getDescription());
				entity.setLink(mixedMeterial.getLink());
				entity.setTitle(mixedMeterial.getTitle());
				entity.setEt(new Date());
				mixedMeterialService.update(entity);
			} else {
				return new RespBody(Status.ERROR);
			}
			return new RespBody(Status.OK, entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 医院管理保存编辑后的素材图文
	 * 
	 * @param mixedMeterial
	 *            素材对象
	 * */
	@RequestMapping(params = "method=editMulti")
	@ResponseBody
	public RespBody editMulti(String jsonstr, String parentId, String delIds) {
		try {
			List<Map<String, Object>> list = JSONUtils.parseJSONList(jsonstr);
			MixedMeterial mixedMeterial = null;
			for (Map<String, Object> map : list) {
				// 当有ID的时候则是在编辑已有素材
				mixedMeterial = mixedMeterialService.findById(String.valueOf(map.get("id")));
				if (mixedMeterial != null) {
					mixedMeterial.setAuthor(String.valueOf(map.get("author")));
					mixedMeterial.setContent(String.valueOf(map.get("content")));
					mixedMeterial.setCoverPicPath(String.valueOf(map.get("coverPicPath")));
					mixedMeterial.setWechatPicPath("");
					mixedMeterial.setDescription(String.valueOf(map.get("description")));
					mixedMeterial.setLink(String.valueOf(map.get("link")));
					mixedMeterial.setType(map.get("type") != null ? Integer.valueOf((Integer) map.get("type")) : null);
					mixedMeterial.setTitle(String.valueOf(map.get("title")));
					mixedMeterial.setState(map.get("state") != null ? Integer.valueOf((Integer) map.get("state")) : null);
					mixedMeterialService.update(mixedMeterial);
				} else {
					mixedMeterial = new MixedMeterial();
					mixedMeterial.setAuthor(String.valueOf(map.get("author")));
					mixedMeterial.setContent(String.valueOf(map.get("content")));
					mixedMeterial.setCoverPicPath(String.valueOf(map.get("coverPicPath")));
					mixedMeterial.setWechatPicPath("");
					mixedMeterial.setDescription(String.valueOf(map.get("description")));
					mixedMeterial.setLink(String.valueOf(map.get("link")));
					mixedMeterial.setType(map.get("type") != null ? Integer.valueOf((Integer) map.get("type")) : null);
					mixedMeterial.setTitle(String.valueOf(map.get("title")));
					mixedMeterial.setState(map.get("state") != null ? Integer.valueOf((Integer) map.get("state")) : null);
					mixedMeterial.setParentId(parentId);
					mixedMeterial.setIsParent(0);
					mixedMeterialService.add(mixedMeterial);
				}
			}
			if (delIds != null) {
				for (String delId : delIds.split(",")) {
					mixedMeterialService.deleteById(delId);
				}
			}
			// 返回多图文中的父图文
			return new RespBody(Status.OK, parentId);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 素材管理编辑素材图文
	 * 
	 * @param id
	 *            图文素材的ID
	 * */
	@RequestMapping(params = "method=toEditMeterialOnManage")
	public String editMeterialOnManage(String id, int meterialType, String hospitalId, HttpServletRequest request) {
		MixedMeterial mixedMeterial = mixedMeterialService.findById(id);
		request.setAttribute("mixedMeterial", mixedMeterial);
		request.setAttribute("hospitalId", hospitalId);
		if (meterialType == MessageConstant.METERIAL_TYPE_SINGLE) {
			return "/sys/meterial/editSingleText";
		} else {
			request.setAttribute("allData", mixedMeterial.toString());
			return "/sys/meterial/editMultiText";
		}
	}

	/**
	 * 添加单图文回复
	 * */
	@RequestMapping(params = "method=addSingleText")
	public String addSingleText(String hospitalId, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		return "/sys/meterial/singleText";
	}

	/**
	 * 添加多图文回复
	 * */
	@RequestMapping(params = "method=addMixed")
	public String addMixed(String hospitalId, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		return "/sys/meterial/multiText";
	}

	/**
	 * 选择图文
	 * 
	 * @param thirdType
	 *            平台类型
	 * 
	 * @param type
	 *            回复类型
	 * 
	 * */
	@RequestMapping(params = "method=chooseMeteList")
	public String chooseMeteList(@RequestParam(required = false,
			defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "10") int pageSize, String hospitalId, String thirdType, String type, ModelMap modelMap, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", hospitalId);
		PageInfo<MixedMeterial> mixedMeterials = mixedMeterialService.findListByPage(params, new Page<MixedMeterial>(pageNum, pageSize));
		modelMap.put("mixedMeterials", mixedMeterials);
		modelMap.put("hospitalId", hospitalId);
		modelMap.put("thirdType", thirdType);
		modelMap.put("type", type);
		return "/sys/meterial/select_pic_text_list";
	}

}
