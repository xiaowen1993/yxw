/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年12月31日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.vo.register.TakeNo;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.constant.EasyHealthConstant;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;

/**
 * 挂号-报到取号
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月31日
 */
@Controller
@RequestMapping("/easyhealth/register/scanTake")
public class RegisterScanTakeController {
	private static Logger logger = LoggerFactory.getLogger(RegisterScanTakeController.class);
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
	private BaseDatasManager baseManager = SpringContextHolder.getBean(BaseDatasManager.class);

	@RequestMapping(value = "/toTakeNo")
	public ModelAndView toTakeNo(HttpServletRequest request, HttpServletResponse response, String appCode, String areaCode,
			String hospitalCode, String branchCode, String familyId, String openId, ModelMap modelMap) {
		//判断是否从健康易扫码过来

		Object user = request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

		if (StringUtils.isBlank(familyId) || StringUtils.isBlank(openId) || user == null) {
			logger.error("扫码报到失败：session为空 or familyId为空 or openId为空.familyId：{},openId：{}", familyId, openId);

			return new ModelAndView("easyhealth/common/scanDownload");
		}

		List<HospIdAndAppSecretVo> hospitals = baseManager.getHospitalListByAppCode(appCode, areaCode);

		boolean existHospital = false;
		if (hospitals != null) {

			for (HospIdAndAppSecretVo hospital : hospitals) {
				if (hospital.getHospCode().equals(hospitalCode)) {
					existHospital = true;
					break;
				}
			}
		}

		if (!existHospital) {
			logger.error("扫码报到失败：获取医院信息为空.appCode:{}, hospitalCode:{}", appCode, hospitalCode);

			modelMap.put("msg", "报到失败，获取医院信息失败。");
			return new ModelAndView("easyhealth/common/error", modelMap);
		}

		modelMap.put("hospitalCode", hospitalCode);
		modelMap.put("branchCode", branchCode);

		MedicalCard medicalCard = null;
		List<MedicalCard> medicalCards = medicalCardService.findCardsByOpenIdAndFamilyIdAndHospitalCode(openId, familyId, hospitalCode);

		medicalCard = medicalCards != null && medicalCards.size() > 0 ? medicalCards.get(0) : null;

		if (medicalCard != null) {
			modelMap.put("patCardType", medicalCard.getCardType());
			modelMap.put("patCardNo", medicalCard.getCardNo());

			return new ModelAndView("/easyhealth/biz/register/takeNo", modelMap);
		} else {
			logger.error("扫码报到失败：无就诊卡.familyId:{}, openId:{}", familyId, openId);

			//跳转错误页面
			modelMap.put("msg", "报到失败，请先到个人中心关联医院就诊卡。");
			return new ModelAndView("easyhealth/common/error", modelMap);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/takeNo")
	public Object takeNo(String hospitalCode, String branchCode, String patCardType, String patCardNo) {
		Map<String, Object> res = new HashMap<String, Object>();

		Map<String, Object> takeNoResMap = registerBizManager.takeNo(hospitalCode, branchCode, patCardType, patCardNo, "");

		Boolean isHappenException = (Boolean) takeNoResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
		if (isHappenException != null && isHappenException) {
			String message =
					takeNoResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY) == null ? "" : String.valueOf(takeNoResMap
							.get(BizConstant.INTERFACE_MAP_MSG_KEY));
			res.put("status", Status.ERROR);
			res.put("message", message);

			logger.error("调用挂号-报到取号接口出错：{},hospitalCode:{}, branchCode:{}, patCardType:{}, patCardNo:{}", res.toString(), hospitalCode,
					branchCode, patCardType, patCardNo);
		} else {
			Boolean isSuccess = (Boolean) takeNoResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
			if (isSuccess != null && isSuccess) {
				Object obj = takeNoResMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
				if (obj != null) {
					TakeNo takeNo = (TakeNo) obj;

					res.put("status", Status.OK);
					res.put("takeNo", takeNo);
				}
			} else {
				String message =
						takeNoResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY) == null ? "" : String.valueOf(takeNoResMap
								.get(BizConstant.INTERFACE_MAP_MSG_KEY));
				res.put("status", Status.ERROR);
				res.put("message", message);

				logger.error("调用挂号-报到取号接口出错：{},hospitalCode:{}, branchCode:{}, patCardType:{}, patCardNo:{}", res.toString(), hospitalCode,
						branchCode, patCardType, patCardNo);
			}
		}

		return res;
	}
}
