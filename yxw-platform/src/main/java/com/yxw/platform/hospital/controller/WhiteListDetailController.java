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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.WhiteListDetailService;

/**
 * @Package: com.yxw.platform.hospital.controller
 * @ClassName: WhiteListController
 * @Statement: <p>后台白名单管理 Controller</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/whiteListDetail")
public class WhiteListDetailController extends BizBaseController<WhiteListDetail, String> {
	private Logger logger = LoggerFactory.getLogger(WhiteListDetailController.class);

	@Autowired
	private WhiteListDetailService whiteListDetailService;

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public RespBody save(WhiteListVo vo, HttpServletRequest request) {
		try {
			User user = super.getLoginUser(request);
			Boolean isUniquePhone = whiteListDetailService.isCheckUniquePhone(vo.getWhiteListId(), vo.getPhone());
			Map<String, Object> resInfo = new HashMap<String, Object>();
			if (isUniquePhone) {
				WhiteListDetail detail = vo.convertDetail();
				detail.setCp(user.getId());
				detail.setCt(new Date());
				detail.setEp(user.getId());
				detail.setEt(new Date());
				whiteListDetailService.add(detail);

				if (BizConstant.WHITE_LIST_IS_OPEN_YES == vo.getIsOpen()) {
					ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
					List<Object> params = new ArrayList<Object>();
					params.add(vo);
//					WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);
//					whiteListCache.addWhiteInfo(vo);
					serveComm.set(CacheType.WHITE_LIST_CACHE, "addWhiteInfo", params);
				}

				resInfo.put(BizConstant.COMMON_AJAX_REQ_SUCCESS_KEY, true);
				resInfo.put(BizConstant.COMMON_ENTITY_KEY, detail);
			} else {
				resInfo.put(BizConstant.COMMON_AJAX_REQ_SUCCESS_KEY, false);
				resInfo.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "该电话号码已经添加,请填写其它手机号码完成添加!");
				logger.info("该电话号码已经添加,请填写其它手机号码完成添加!");
			}

			return new RespBody(Status.OK, resInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存白名单明细信息失败，数据保存异常！");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/delDetail", method = RequestMethod.POST)
	public RespBody delDetail(WhiteListVo vo, HttpServletRequest request) {
		try {
			whiteListDetailService.deleteById(vo.getId());

//			WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);
//			whiteListCache.delWhiteDetail(vo);
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> params = new ArrayList<Object>();
			params.add(vo);
			serveComm.set(CacheType.WHITE_LIST_CACHE, "delWhiteDetail", params);

			Map<String, Object> resInfo = new HashMap<String, Object>();
			resInfo.put(BizConstant.COMMON_AJAX_REQ_SUCCESS_KEY, true);
			resInfo.put(BizConstant.COMMON_ENTITY_KEY, vo);
			return new RespBody(Status.OK, resInfo);
		} catch (Exception e) {
			logger.error("删除白名单明细信息失败，数据保存异常！ errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			return new RespBody(Status.ERROR, "删除白名单明细信息失败，数据保存异常！");
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.controller.BaseController#getService()
	 */
	@Override
	protected BaseService<WhiteListDetail, String> getService() {
		return whiteListDetailService;
	}

}
