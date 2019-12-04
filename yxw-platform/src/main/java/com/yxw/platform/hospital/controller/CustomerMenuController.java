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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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

import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.hospital.Optional;
import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.PlatformOptionalService;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.MenuService;
import com.yxw.platform.hospital.service.PaySettingsService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.hospital.service.PlatformSettingsService;
import com.yxw.platform.hospital.utils.ConvertMenu;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.sdk.SDKPublicArgs;
import com.yxw.platform.sdk.YXW;

/**
 * 后台功能选择管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/customerMenu")
public class CustomerMenuController extends BizBaseController<Menu, String> {

	private Logger logger = LoggerFactory.getLogger(CustomerMenuController.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private PlatformOptionalService hospitalOptionalService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private PlatformSettingsService platformSettingsService;

	@Autowired
	private MixedMeterialService mixedMeterialService;

	@Autowired
	private PaySettingsService paySettingsService;

	@Override
	protected BaseService<Menu, String> getService() {
		return menuService;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(@RequestParam(value = "hospitalId", required = true) String hospitalId, String platformsId, ModelMap modelMap) {
		logger.info("/sys/customerMenu/toView.hospitalId: " + hospitalId);
		// 接入平台
		List<Platform> platformsList = platformService.findAll();
		String[] platformIds = new String[1];
		if (platformsId == null) {
			platformIds[0] = platformsList.get(1).getId();
		} else {
			platformIds[0] = platformsId;
		}
		// 菜单集合
		List<Menu> menuList = menuService.findMenuByHospitalIdAndPlatformIds(hospitalId, platformIds);
		// 图文集合
		List<String> grapicsIdList = new ArrayList<String>();
		List<Menu> resultMenu = new ArrayList<Menu>();

		// 遍历菜单设置图文及子菜单
		if (menuList.size() > 0) {
			// 获取图文菜单的菜单Id
			for (Menu menu : menuList) {
				if (StringUtils.isNotEmpty(menu.getGrapicsId())) {
					grapicsIdList.add(menu.getGrapicsId());
				}
			}

			List<MixedMeterial> mixedMeterialList = new ArrayList<MixedMeterial>();
			String[] grapicsIds = new String[grapicsIdList.size()];
			if (grapicsIdList.size() > 0) {
				for (int i = 0; i < grapicsIdList.size(); i++) {
					grapicsIds[i] = grapicsIdList.get(i);
				}
				// 根据图文ID查询图文集合
				mixedMeterialList = mixedMeterialService.findMixedMeterialsByIds(grapicsIds);
			}

			// 遍历菜单设置图文及子菜单
			for (Menu menu : menuList) {
				if (StringUtils.isNotEmpty(menu.getGrapicsId())) {
					if (mixedMeterialList.size() > 0) {
						for (MixedMeterial mixedMeterial : mixedMeterialList) {
							if (mixedMeterial.getId().equals(menu.getGrapicsId())) {
								menu.setMixedMeterial(mixedMeterial);
							}
						}
					}
				}

				if (menu.getParentId() == null) {
					List<Menu> childMenuList = new ArrayList<Menu>();
					for (Menu childMenu : menuList) {
						if (childMenu.getParentId() != null && childMenu.getParentId().equals(menu.getId())) {
							childMenuList.add(childMenu);
						}
					}
					menu.setChildMenus(childMenuList);
					resultMenu.add(menu);
				}
			}
		}

		// 医院功能
		List<PlatformOptional> selHospitalOptionalsList = hospitalOptionalService.findByHospital(hospitalId);

		//当前接入平台
		Platform platformVo = new Platform();
		if (platformsList.size() > 0) {
			HashMap<String, Object> platformsMap = new HashMap<String, Object>();
			for (Platform platform : platformsList) {
				platformsMap.put(platform.getCode(), platform);
				if (platformIds[0].equals(platform.getId())) {
					platformVo = platform;
				}
			}
			modelMap.put("platformsMap", platformsMap);
		}

		modelMap.put("selHospitalOptionalsList", selHospitalOptionalsList);
		modelMap.put("resultMenu", resultMenu);
		modelMap.put("hospitalId", hospitalId);
		modelMap.put("platformVo", platformVo);
		//		HospitalAndOptionCache hospitalAndOptionCache = SpringContextHolder.getBean(HospitalAndOptionCache.class);
		//		String bizCode = "clinicPay";
		//		List<HospitalInfoByEasyHealthVo> item = hospitalAndOptionCache.getHospitalByOption(bizCode);
		//		logger.info("item:{}", JSON.toJSONString(item));
		ModelAndView view = new ModelAndView("/sys/hospital/menu");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestParam(value = "hospitalId", required = true) String hospitalId, String platformsId, String platformsCode,
			String menuItem, String childItem, HttpServletRequest request) {
		try {
			logger.info("menuItem" + menuItem);
			logger.info("childItem:" + childItem);
			// Hospital hospital = hospitalService.findById(hospitalId);
			String[] platformIds = { platformsId };
			User user = getLoginUser(request);
			List<PlatformSettings> platformSettingsList = platformSettingsService.findByHospitalIdAndPlatformIds(hospitalId, platformIds);
			// 获取接入平台配置 用于插入菜单
			if (platformSettingsList.size() > 0) {
				PlatformSettings platformSettings = platformSettingsList.get(0);
				Hospital hospital = hospitalService.findById(hospitalId);
				menuService.savePlatformSettingsMenu(user.getId(), hospital, platformSettings.getPlatformId(), menuItem, childItem,
						platformSettings.getId(), platformsCode);
			} else {
				return new RespBody(Status.ERROR, "保存菜单配置信息失败，未检索到平台配置信息！");
			}

			return new RespBody(hospitalId, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存菜单配置信息失败，数据保存异常！");
		}
	}

	/**
	 * 获取菜单配置
	 * 
	 * @param hospitalId
	 * @param platformsId
	 * @param menuItem
	 * @param childItem
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	public Map<String, Object> getMenu(String hospitalId, String platformsId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			String[] platformIds = { platformsId };
			List<Menu> menuList = menuService.findMenuByHospitalIdAndPlatformIds(hospitalId, platformIds);
			List<Menu> resultMenu = new ArrayList<Menu>();
			if (menuList.size() > 0) {
				for (Menu menu : menuList) {
					if (menu.getParentId() == null) {
						List<Menu> childMenuList = new ArrayList<Menu>();
						for (Menu childMenu : menuList) {
							if (childMenu.getParentId() != null && childMenu.getParentId().equals(menu.getId())) {
								childMenuList.add(childMenu);
							}
						}
						menu.setChildMenus(childMenuList);
						resultMenu.add(menu);
					}
				}
			}

			result.put("status", Status.OK);
			result.put("resultMenu", resultMenu);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", Status.ERROR);
			result.put("message", "保存菜单配置信息失败，数据保存异常！");
			return result;
		}
	}

	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public Map<String, Object> publish(String hospitalId, String platformsId, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			// 链接地址前缀
			String urlPrefix = SystemConfig.getStringValue("url_prefix");
			// 平台ID
			String[] platformIds = { platformsId };

			// 菜单集合
			List<Menu> menuList = menuService.findMenuByHospitalIdAndPlatformIds(hospitalId, platformIds);
			if (menuList.size() <= 0) {
				result.put("status", false);
				result.put("message", "未检索到菜单，请先保存菜单");
			}
			// 平台配置集合
			PlatformSettings platformSettings = platformSettingsService.findByHospitalIdAndPlatformId(hospitalId, platformsId);
			PaySettings paySettings = paySettingsService.findByHospitalIdAndModeCode(hospitalId, platformSettings.getCode());
			Integer isParent = 0;
			if (paySettings != null && paySettings.getIsSubPay() != null) {
				isParent = paySettings.getIsSubPay();
			}
			List<Menu> resultMenu = new ArrayList<Menu>();

			// 获取菜单关联的功能，并写入map
			List<PlatformOptional> hospitalOptionals = hospitalOptionalService.findByHospital(hospitalId);
			Map<String, Optional> optionalMap = new HashMap<String, Optional>();
			if (hospitalOptionals.size() > 0) {
				for (PlatformOptional hospitalOptional : hospitalOptionals) {
					optionalMap.put(hospitalOptional.getOptional().getId(), hospitalOptional.getOptional());
				}
			}

			if (menuList.size() > 0) {
				for (Menu menu : menuList) {
					if (menu.getParentId() == null) {
						List<Menu> childMenuList = new ArrayList<Menu>();
						for (Menu childMenu : menuList) {
							if (childMenu.getParentId() != null && childMenu.getParentId().equals(menu.getId())) {
								childMenuList.add(childMenu);
							}
						}
						menu.setChildMenus(childMenuList);
						resultMenu.add(menu);
					}
				}

				// 菜单推送对象
				Object resultmenu = null;
				if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformSettings.getCode())) {// 微信菜单
					resultmenu = ConvertMenu.wechatConvert(resultMenu, optionalMap, platformSettings, isParent);
				} else {
					resultmenu = ConvertMenu.alipayConvert(resultMenu, optionalMap, platformSettings);
				}
				if (resultMenu != null) {
					JSONObject json =
							YXW.createMenu(platformSettings.getCode(), platformSettings.getAppId(), platformSettings.getPrivateKey(), resultmenu);
					logger.info("菜单内容（JSON）：" + com.alibaba.fastjson.JSONObject.toJSONString(resultmenu));
					result.put("status", json.getBoolean("flag"));
					result.put("message", json.getString("errcode") + ":" + json.getString("msg"));
				} else {
					result.put("status", false);
					result.put("message", "菜单创建失败,菜单转换失败");
				}
				return result;
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("status", false);
			result.put("message", "菜单创建失败");
			e.printStackTrace();
			return result;
		}

	}

	@RequestMapping("/dialogLink")
	public ModelAndView dialogLink(String hospitalId, String platformsId) {
		return new ModelAndView("/sys/hospital/dialog-link");
	}

	@RequestMapping("/dialogMaterial")
	public ModelAndView dialogMaterial(String hospitalId, String platformsId, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("thirdType", "1");
		request.setAttribute("type", "2");
		return new ModelAndView("/sys/hospital/dialog-material");
	}
}
