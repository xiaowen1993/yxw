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
package com.yxw.platform.hospital.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayMobilePublicQrcodeCreateResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.app.biz.area.service.AreaService;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.sdk.SDKPublicArgs;
import com.yxw.platform.sdk.alipay.AlipaySDK;
import com.yxw.platform.sdk.alipay.utils.MessageSendUtils;
import com.yxw.utils.PinyinUtils;

/**
 * 后台医院管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/hospital")
public class HospitalController extends BizBaseController<Hospital, String> {

	private Logger logger = LoggerFactory.getLogger(HospitalController.class);

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private AreaService areaService;

	@Override
	protected BaseService<Hospital, String> getService() {
		return hospitalService;
	}

	/**
	 * 医院管理列表
	 * 
	 * @param pageNum
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @param search
	 *            过滤条件
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(@RequestParam(required = false,
			defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "5") int pageSize, @RequestParam(required = false,
			defaultValue = "") String search, String menuCode, ModelMap modelMap, HttpServletRequest request) {
		logger.info("医院管理列表分页查询,pageNum=[" + pageNum + "],pageSize=[" + pageSize + "]");
		// 获取session中保存的
		@SuppressWarnings("unchecked")
		List<Hospital> hospitalList = (List<Hospital>) request.getSession().getAttribute(UserConstant.HOSPITAL_LIST);
		PageInfo<Hospital> hospitals = null;
		if (hospitalList.size() > 0) {
			String[] hospitalIds = new String[hospitalList.size()];
			for (int i = 0; i < hospitalList.size(); i++) {
				hospitalIds[i] = hospitalList.get(i).getId();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 设置搜索条件
			params.put("search", search);
			// 把session中保存的ID做查询条件
			params.put("hospitalIds", hospitalIds);
			hospitals = hospitalService.findListByPage(params, new Page<Hospital>(pageNum, pageSize));
		}
		modelMap.put("search", search);
		modelMap.put("hospitals", hospitals);
		
		if (StringUtils.isNotBlank(menuCode)) {
			modelMap.put("menuCode", menuCode);
		}
		
		//modelMap.put("areaCodeMap", BizConstant.areaCode);
		
		return "/sys/hospital/list";
	}

	/**
	 * 启用/禁用
	 * 
	 * @param id
	 *            医院ID
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus",
			method = RequestMethod.POST)
	public Object updateStatus(@RequestParam(value = "id",
			required = true) String id, @RequestParam(value = "status",
			required = true) int status, String code, HttpServletRequest request) {
		logger.info("启用/禁用,id=" + id + ",status=" + status + ",code=" + code);
		Hospital hospital = null;
		try {
			hospital = hospitalService.findById(id);
			// hospital.setId(id);
			hospital.setStatus(status);
			hospital.setCode(code); // 更新缓存用到
			hospital.setEp(getLoginUser(request).getId());

			// 判断是否添加了医院规则 没添加规则不允许启用
			if (hospital.getStatus() == HospitalConstant.HOSPITAL_VALID_STATUS) {
				Map<String, Object> resMap = hospitalService.isValidActivated(hospital.getId());
				Boolean isValid = (Boolean) resMap.get(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY);
				if (!isValid) {
					return new RespBody(Status.ERROR, resMap.get(BizConstant.METHOD_INVOKE_RES_MSG_KEY));
				}
			}
			hospitalService.updateStatus(hospital);
			return new RespBody(Status.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, hospital.getCnStatus() + "医院失败，数据更新异常！");
		}
	}

	/**
	 * 批量 启用/禁用
	 * 
	 * @param id
	 *            医院ID
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/updateBulkStatus",
			method = RequestMethod.POST)
	public Object updateBulkStatus(@RequestParam("hospitalIds[]") String[] hospitalIds, int status, HttpServletRequest request) {
		logger.info("启用/禁用,id=" + hospitalIds + ",status=" + status);
		try {
			List<Hospital> hospitalList = hospitalService.findByHospitalIds(hospitalIds);
			if (hospitalList.size() > 0) {
				for (Hospital hospital : hospitalList) {
					hospital.setStatus(status);
					hospital.setEp(getLoginUser(request).getId());
					hospitalService.updateStatus(hospital);
				}
			}
			logger.info("查询需要修改启用/禁用状态的医院集合" + hospitalList.size());
			//
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "批量 " + ((status == 0) ? "停用" : "启用") + "医院失败，数据更新异常！");
		}

	}

	@RequestMapping("/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, ModelMap modelMap) {
		//modelMap.put("areaCodeMap", BizConstant.areaCode);
		
//		List<Area> oneLevelAreas = areaService.findOneLevelAreas();
		List<Area> oneLevelAreas = areaService.getCacheOneLevelAreas();
		modelMap.put("oneLevelAreas", oneLevelAreas);
		
		return new ModelAndView("/sys/hospital/hospital");
	}

	/**
	 * 保存医院信息
	 * 
	 * @param id
	 *            有id更新，无id新增
	 * @param name
	 *            医院名称
	 * @param code
	 *            医院编码
	 * @param contactName
	 *            联系人
	 * @param contactTel
	 *            联系人电话
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save",
			method = RequestMethod.POST)
	public Object save(String id, String name, String code, String areaCode, String areaName, String contactName, String contactTel, String guideURL, String cloudURL,
			String trafficURL, String logo, HttpServletRequest request) {
		logger.info("保存医院信息,id:{}, name:{}, code:{},areaCode:{}, contactName:{}, contactTel:{}", new Object[] { id, name, code, areaCode, contactTel,
				contactName });
		Hospital hospital = new Hospital(name, code, contactName, contactTel, areaCode, areaName.substring(1).replace("/", "-"), guideURL, cloudURL, trafficURL, logo);
		hospital.setId(id);
		try {
			boolean isUnique = hospitalService.isUniqueName(hospital);
			if (!isUnique) {
				return new RespBody(Status.ERROR, "保存医院失败，医院名称" + name + "已经存在！");
			} else {
				if (StringUtils.isBlank(hospital.getCode())) {
					hospital.setCode(PinyinUtils.getChineseFirstWord(name));
				} else {
					// 更换医院名称后重新生成医院Code
					/*
					 * String hospitalCode =
					 * PinyinUtils.getChineseFirstWord(name); if
					 * (!hospital.getCode().equalsIgnoreCase(hospitalCode)) {
					 * hospital.setCode(hospitalCode); }
					 */
				}

				isUnique = hospitalService.isUniqueCode(hospital);
				while (!isUnique) {
					hospital.setCode(PinyinUtils.getChineseFirstWord(name) + "_" + PKGenerator.generateId());
					isUnique = hospitalService.isUniqueCode(hospital);
				}
			}
			User user = getLoginUser(request);
			if (StringUtils.isNotEmpty(id)) {
				hospital.setEt(new Date());
				hospital.setEp(user.getId());
				hospitalService.update(hospital);
				// 更新hospital 对应的的信息
			} else {
				hospital.setCt(new Date());
				hospital.setCp(user.getId());
				id = hospitalService.add(hospital);
				hospital.setId(id);
			}
			return new RespBody(id, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存医院失败，数据保存异常！");
		}
	}

	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(value = "id",
			required = true) String id, ModelMap modelMap) {
		Hospital hospital = hospitalService.findById(id);
		modelMap.put("hospital", hospital);
		modelMap.put("hospitalId", hospital.getId());
		//modelMap.put("areaCodeMap", BizConstant.areaCode);
		
		// List<Area> oneLevelAreas = areaService.findOneLevelAreas();
		List<Area> oneLevelAreas = areaService.getCacheOneLevelAreas();
		modelMap.put("oneLevelAreas", oneLevelAreas);
		// List<Area> twoLevelAreas = areaService.findTwoLevelAreasByParentId(hospital.getAreaCode().split("\\/")[1]);
		List<Area> twoLevelAreas = areaService.getCacheTwoLevelAreasByParentId(hospital.getAreaCode().split("\\/")[1]);
		modelMap.put("twoLevelAreas", twoLevelAreas);
		// List<Area> threeLevelAreas = areaService.findThreeLevelAreasByParentId(hospital.getAreaCode().split("\\/")[2]);
		List<Area> threeLevelAreas = areaService.getCacheThreeLevelAreasByParentId(hospital.getAreaCode().split("\\/")[2]);
		modelMap.put("threeLevelAreas", threeLevelAreas);
		
		ModelAndView view = new ModelAndView("/sys/hospital/hospital", modelMap);
		return view;
	}

	@RequestMapping("/getEWM")
	public Object getEWM(String appId, ModelMap modelMap, HttpServletRequest request) {
		HospIdAndAppSecretVo vo = MessageSendUtils.obtainByAppId(appId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeType", "TEMP");
		map.put("expireSecond", "");
		map.put("showLogo", "Y");
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		scene.put("sceneId", "alipay_");
		map1.put("scene", scene);
		map.put("codeInfo", map1);
		SDKPublicArgs.logger.info("map" + JSON.toJSONString(map));
		AlipayMobilePublicQrcodeCreateResponse response = AlipaySDK.getRWM(appId, vo.getAppSecret(), JSON.toJSONString(map));
		if (null != response && response.isSuccess()) {
			logger.info("response.getBody()" + response.getBody());
		}
		return "";
	}
}
