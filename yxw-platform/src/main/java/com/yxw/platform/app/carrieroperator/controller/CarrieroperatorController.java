package com.yxw.platform.app.carrieroperator.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.platform.app.carrieroperator.service.CarrieroperatorService;

@Controller
@RequestMapping("sys/app/carrieroperator")
public class CarrieroperatorController {
	private static Logger logger = LoggerFactory.getLogger(CarrieroperatorController.class);

	@Autowired
	private CarrieroperatorService carrieroperatorService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@RequestMapping("/getCarrieroperatorById")
	public ModelAndView getCarrieroperatorById(Carrieroperator carrieroperator, ModelMap modelMap, HttpServletRequest request) {

		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			String id = carrieroperator.getId();
			params.put("id", id);
			carrieroperator = carrieroperatorService.findCarrieroperatorById(params);
			modelMap.put("carrieroperator", carrieroperator);

		} catch (Exception e) {
			logger.error("加载运营信息。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new ModelAndView("/sys/app/carrieroperator/addCarrieroperator");
	}

	@RequestMapping("/getAddCarrieroperator")
	public ModelAndView getDialogAddDuty(ModelMap modelMap, HttpServletRequest request) {

		return new ModelAndView("/sys/app/carrieroperator/addCarrieroperator");
	}

	/**
	 * 异步添加 运营管理信息
	 * @param medicalCardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCarrieroperator")
	public Object addCarrieroperator(Carrieroperator carrieroperator, HttpServletRequest request, HttpServletResponse response) {
		try {

			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (!StringUtils.isBlank(carrieroperator.getId())) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("id", carrieroperator.getId());
				Carrieroperator beforUpdateEntity = carrieroperatorService.findCarrieroperatorById(params);

				carrieroperator.setEt(new Date());
				//				 carrieroperator.setOperationPosition(carrieroperator.getOperationPosition().replaceAll(",", ""));
				carrieroperatorService.update(carrieroperator);

				//如果运营位置有改动，则旧运营位置的缓存也需要更新
				if (!beforUpdateEntity.getOperationPosition().equals(carrieroperator.getOperationPosition())) {
					List<Carrieroperator> carrieroperators =
							carrieroperatorService.findByOperationPosition(beforUpdateEntity.getOperationPosition());
					if (!CollectionUtils.isEmpty(carrieroperators)) {

						List<Object> parameters = new ArrayList<Object>();
						parameters.add(carrieroperators);
						serveComm.set(CacheType.APP_CARRIEROPERATOR_CACHE, "setByOperationPosition", parameters);
					}
				}

				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
				resultMap.put("id", carrieroperator.getId());
			} else {

				String id = PKGenerator.generateId();
				carrieroperator.setId(id);
				//				 carrieroperator.setOperationPosition(carrieroperator.getOperationPosition().replaceAll(",", ""));
				carrieroperator.setCt(new Date());
				carrieroperator.setEt(new Date());
				carrieroperatorService.add(carrieroperator);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.SUCCESS);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "");
				resultMap.put("id", id);
			}

			//更新缓存
			List<Carrieroperator> carrieroperators = carrieroperatorService.findByOperationPosition(carrieroperator.getOperationPosition());
			if (!CollectionUtils.isEmpty(carrieroperators)) {

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(carrieroperators);
				serveComm.set(CacheType.APP_CARRIEROPERATOR_CACHE, "setByOperationPosition", parameters);
			}

			return new RespBody(Status.OK, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "操作失败");
		}
	}

	/**
	 * 异步查询 排序是否重复
	 * @param medicalCardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("quertSorting")
	public Object quertSorting(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();

			String sortingNew = request.getParameter("sortingNew");
			String sortingBijiao = request.getParameter("sortingBijiao");
			String operationPosition = request.getParameter("operationPosition");
			params.put("operationPosition", operationPosition);
			List<String> sortingList = carrieroperatorService.findSorting(params);
			if (sortingList != null && sortingList.size() > 0) {
				for (String sorting : sortingList) {
					if (!sortingNew.equals(sortingBijiao)) {
						if (sorting.equals(sortingNew)) {
							return new RespBody(Status.OK, "");
						}
					}
				}
			}

			return new RespBody(Status.ERROR, "");

		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "操作失败");
		}
	}

	@RequestMapping("/queryCarrieroperatorList")
	public ModelAndView queryCcommunityHealth(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(
			required = false, defaultValue = "10") int pageSize, ModelMap modelMap, HttpServletRequest request) {
		logger.info("运营管理分页查询,pageNum=[{}],pageSize=[{}]", new Object[] { pageNum, pageSize });
		HashMap<String, Object> params = new HashMap<String, Object>();

		try {
			PageInfo<Carrieroperator> pager = carrieroperatorService.findListByPage(params, new Page<Carrieroperator>(pageNum, pageSize));

			modelMap.put("pager", pager);

			modelMap.putAll(params);
		} catch (Exception e) {
			logger.error("加载运营信息。errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new ModelAndView("/sys/app/carrieroperator/list");
	}
}
