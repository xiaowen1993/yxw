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
package com.yxw.platform.hospital.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.hospital.PlatformSettingsMenu;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.MenuDao;
import com.yxw.platform.hospital.dao.PlatformSettingsMenuDao;
import com.yxw.platform.hospital.service.MenuService;
import com.yxw.platform.hospital.service.PlatformSettingsService;

/**
 * 后台自定义菜单管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu, String> implements MenuService {

	private Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private PlatformSettingsMenuDao platformSettingsMenuDao;

	@Autowired
	private PlatformSettingsService platformSettingsService;

	// private HospitalAndOptionCache hospitalAndOptionCache =
	// SpringContextHolder.getBean(HospitalAndOptionCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<Menu, String> getDao() {
		return menuDao;
	}

	@Override
	public void savePlatformSettingsMenu(String userId, Hospital hospital, String platformsId, String menuItem, String childItem,
			String platformSettingsId, String platformCode) {
		// 一级菜单数据
		JSONArray jMenuItem = JSONArray.parseArray(menuItem);
		// 二级菜单数据
		JSONArray jChildItem = JSONArray.parseArray(childItem);
		// app医院与功能缓存集合对象
		Map<String, Object> cacheOptionMap = new HashMap<String, Object>();

		List<String> item = menuDao.findDeleteMenuIdByPlatformSettingsId(platformSettingsId);
		logger.info("item:{}", JSON.toJSONString(item));
		platformSettingsMenuDao.deleteByPlatformSettingsId(platformSettingsId);
		if (!CollectionUtils.isEmpty(item)) {
			menuDao.batchDelete(item);
		}

		if (jMenuItem.size() > 0) {
			List<PlatformSettingsMenu> platformSettingsMenusList = new ArrayList<PlatformSettingsMenu>();
			// 遍历一级json 添加菜单
			for (Object parentMenu : jMenuItem) {
				JSONObject jParams = JSONObject.parseObject(parentMenu.toString());
				Integer mark = jParams.getInteger("mark");
				Menu menu = JSONObject.parseObject(jParams.toJSONString(), Menu.class);
				// String parentId = null;
				menu.setEp(userId);
				String parentId = menuDao.add(menu);
				// if (StringUtils.isNotEmpty(menu.getId())) {
				// menu.setEp(userId);
				// menuDao.update(menu);
				// parentId = menu.getId();
				// } else {
				//
				// }
				if (platformCode.startsWith(BizConstant.MODE_TYPE_APP)) {// 如果是健康易，则更新缓存
					String appId = "";
					PlatformSettings platformSettings =
							platformSettingsService.findByHospitalIdAndAppCode(hospital.getId(), BizConstant.MODE_TYPE_APP);
					if (platformSettings != null && StringUtils.isNotBlank(platformSettings.getAppId())) {
						appId = platformSettings.getAppId();
					}

					String bizCode = jParams.getString("bizCode");
					HospitalInfoByEasyHealthVo cacheVo =
							new HospitalInfoByEasyHealthVo(hospital.getId(), hospital.getCode(), hospital.getName(), hospital.getAreaCode(), appId,
									bizCode, platformCode);
					cacheOptionMap.put(bizCode.concat(CacheConstant.SPLIT_CHAR).concat(cacheVo.getAreaCode()), cacheVo);
				}

				platformSettingsMenusList.add(new PlatformSettingsMenu(platformSettingsId, menu.getId()));
				// 遍历二级菜单 添加
				if (jChildItem.size() > 0) {
					for (Object childMenu : jChildItem) {
						jParams = JSONObject.parseObject(childMenu.toString());
						Integer childMark = jParams.getInteger("mark");
						if (childMark == mark) {
							menu = JSONObject.parseObject(jParams.toJSONString(), Menu.class);
							menu.setParentId(parentId);
							// if (StringUtils.isNotEmpty(menu.getId())) {
							// menu.setEp(userId);
							// menuDao.update(menu);
							// } else {
							//
							// }
							menu.setCp(userId);
							menuDao.add(menu);
							platformSettingsMenusList.add(new PlatformSettingsMenu(platformSettingsId, menu.getId()));
						}
					}
				}
			}

			// 添加菜单和接入平台配置中间表
			if (platformSettingsMenusList.size() > 0) {
				for (PlatformSettingsMenu platformSettingsMenu : platformSettingsMenusList) {
					platformSettingsMenu.setCp(userId);
					platformSettingsMenuDao.add(platformSettingsMenu);
				}
			}
		}
		// if (platformCode.equals(HospitalAndOptionCache.appCode))
		// {//如果是健康易，则更新缓存
		if (platformCode.startsWith(BizConstant.MODE_TYPE_APP)) {// 如果是健康易，则更新缓存
			// hospitalAndOptionCache.updateHospitalByMap(cacheOptionMap,
			// hospital.getCode());
			List<Object> params = new ArrayList<Object>();
			params.add(cacheOptionMap);
			params.add(hospital.getCode());
			serveComm.set(CacheType.HOSPITAL_AND_OPTION_CACHE, "updateHospitalByMap", params);
		}
	}

	@Override
	public List<Menu> findMenuByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds) {
		return menuDao.findMenuByHospitalIdAndPlatformIds(hospitalId, platformIds);
	}

	/**
	 * 根据医院ID查询
	 * 
	 * @param hospitalId
	 * @param platformCode
	 * @return
	 */
	@Override
	public List<Menu> findMenuByHospitalIdAndPlatformCode(String hospitalId) {
		return menuDao.findMenuByHospitalIdAndPlatformCode(hospitalId);
	}

}
