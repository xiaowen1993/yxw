/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.WhiteListDetailService;
import com.yxw.platform.hospital.service.WhiteListService;

/**
 * @Package: com.yxw.platform.hospital.controller
 * @ClassName: WhiteListController
 * @Statement: <p>
 *             后台白名单管理 Controller
 *             </p>
 * @JDK version used:
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/whiteListSetings")
public class WhiteListController extends BizBaseController<WhiteList, String> {
	private Logger logger = LoggerFactory.getLogger(WhiteListController.class);

	@Autowired
	private WhiteListService whiteListService;

	@Autowired
	private WhiteListDetailService whiteListDetailService;
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	@RequestMapping("/index")
	public ModelAndView index(WhiteList whiteList) {
		ModelAndView view = new ModelAndView("/sys/hospital/whiteListIndex");
		view.addObject(BizConstant.COMMON_ENTITY_KEY, whiteList);
		return view;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(WhiteList whiteList, HttpServletRequest request) {
		String hospitalId = whiteList.getHospitalId();
		String appCode = whiteList.getAppCode();
		String hospitalCode = whiteList.getHospitalCode();
		logger.info("/sys/whiteListSetings/toView.hospitalId: " + hospitalId);
		ModelAndView view = new ModelAndView("/sys/hospital/whiteList");

		User user = super.getLoginUser(request);
		if (StringUtils.isNotBlank(hospitalId) && StringUtils.isNotBlank(appCode)) {
			whiteList = whiteListService.findHospitalId(hospitalId, appCode);
			if (whiteList == null) {
				whiteList = new WhiteList();
				whiteList.setIsOpen(BizConstant.WHITE_LIST_IS_OPEN_YES);
				whiteList.setHospitalId(hospitalId);
				whiteList.setAppCode(appCode);
				whiteList.setHospitalCode(hospitalCode);
				if (StringUtils.isNotBlank(hospitalCode)) {
					// HospitalCodeAndAppVo vo = baseDatasManager.getAppInfoByHospitalCode(hospitalCode, appCode);
					HospIdAndAppSecretVo vo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
					if (vo != null) {
						whiteList.setAppId(vo.getAppId());
						whiteList.setHospitalName(vo.getHospName());
					}
				}
				whiteList.setCp(user.getId());
				whiteList.setCt(new Date());
			} else {
				if (StringUtils.isNotBlank(hospitalCode)) {
					// HospitalCodeAndAppVo vo = baseDatasManager.getAppInfoByHospitalCode(hospitalCode, appCode);
					HospIdAndAppSecretVo vo = baseDatasManager.getHospitalEasyHealthAppInfo(hospitalId, appCode);
					if (vo != null) {
						whiteList.setAppId(vo.getAppId());
						whiteList.setHospitalName(vo.getHospName());
					}
				}
				String whiteListId = whiteList.getId();
				List<WhiteListDetail> details = whiteListDetailService.findByWhiteListId(whiteListId);
				view.addObject(BizConstant.COMMON_ENTITY_LIST_KEY, details);
			}
		}
		logger.info("whiteList :{}", JSON.toJSONString(whiteList));
		view.addObject(BizConstant.COMMON_ENTITY_KEY, whiteList);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RespBody save(WhiteList whiteList, HttpServletRequest request) {
		try {
			User user = super.getLoginUser(request);
			whiteList.setEp(user.getId());
			whiteList.setEt(new Date());
			boolean isAdd = true;
			if (StringUtils.isNotEmpty(whiteList.getId())) {
				whiteListService.update(whiteList);
				isAdd = false;
			} else {
				whiteList.setCp(user.getId());
				whiteList.setCt(new Date());
				whiteListService.add(whiteList);
			}

			// WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);
			WhiteListVo vo = whiteList.convertWhiteListVo();
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> params = new ArrayList<Object>();

			if (isAdd) {
				// whiteListCache.addWhiteInfo(vo);
				params.add(vo);
				serveComm.set(CacheType.WHITE_LIST_CACHE, "addWhiteInfo", params);
			} else {
				// whiteListCache.updateWhiteInfo(whiteList.getAppId(), whiteList.getAppCode(), whiteList.getIsOpen());
				params.add(whiteList.getAppId());
				params.add(whiteList.getAppCode());
				params.add(whiteList.getIsOpen());
				serveComm.set(CacheType.WHITE_LIST_CACHE, "updateWhiteInfo", params);
			}

			Map<String, Object> resInfo = new HashMap<String, Object>();
			resInfo.put(BizConstant.COMMON_AJAX_REQ_SUCCESS_KEY, true);
			resInfo.put(BizConstant.COMMON_ENTITY_KEY, whiteList);
			return new RespBody(Status.OK, resInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存白名单信息失败，数据保存异常!");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yxw.framework.mvc.controller.BaseController#getService()
	 */
	@Override
	protected BaseService<WhiteList, String> getService() {
		return whiteListService;
	}

}
