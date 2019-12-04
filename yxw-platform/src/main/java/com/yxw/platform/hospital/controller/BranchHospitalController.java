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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSONObject;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.BranchHospitalService;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.utils.PinyinUtils;

/**
 * 后台医院分院管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/branchHospital")
public class BranchHospitalController extends BizBaseController<BranchHospital, String> {

	private Logger logger = LoggerFactory.getLogger(BranchHospitalController.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private BranchHospitalService branchHospitalService;

	@Override
	protected BaseService<BranchHospital, String> getService() {
		return branchHospitalService;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(@RequestParam(value = "hospitalId", required = true) String hospitalId, ModelMap modelMap) {
		logger.debug("/sys/branchHospital/toView.hospitalId: " + hospitalId);

		Hospital hospital = hospitalService.findById(hospitalId);
		modelMap.put("hospital", hospital);
		modelMap.put("hospitalId", hospital.getId());
		ModelAndView view = new ModelAndView("/sys/hospital/branchHospital", modelMap);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestParam(value = "hospitalId", required = true) String hospitalId, String branchHospitals,
			HttpServletRequest request) {
		logger.debug("/sys/hospital/add.hospitalId: " + hospitalId);
		logger.debug("/sys/hospital/add.branchHospitals: " + branchHospitals);
		try {
			Hospital hospital = hospitalService.findById(hospitalId);
			//RuleRegister ruleEdit = ruleConfigManager.getRuleRegisterByHospitalCode(hospital.getCode());

			User user = getLoginUser(request);
			boolean isUnique = true;
			Integer flag = 0;
			String msg = "";
			List<BranchHospital> branchHospitalList = JSONObject.parseArray(branchHospitals, BranchHospital.class);
			if (branchHospitalList.size() > 0) {
				/*
				 * modify by yuce  逻辑修改  增加分院不受规则控制
				if (branchHospitalList.size() > 1) {
					if (ruleEdit != null) {
						if (ruleEdit.getIsHasBranch() == null) {
							return new RespBody(Status.ERROR, "保存分院失败，当前医院不能能保存多家分院！");
						} else {
							if (ruleEdit.getIsHasBranch().equals(0)) {
								return new RespBody(Status.ERROR, "保存分院失败，当前医院不能能保存多家分院！");
							}
						}
					}
				}
				*/
				for (BranchHospital bh : branchHospitalList) {
					if (StringUtils.isNotEmpty(bh.getId())) {
						bh.setCp(user.getId());
					} else {
						bh.setEp(user.getId());
					}
					isUnique = branchHospitalService.isUniqueNameForHospitalId(bh);
					if (!isUnique) {
						flag = 0;
						msg = bh.getName();
						break;
					} else {
						if (StringUtils.isBlank(hospital.getCode())) {
							hospital.setCode(PinyinUtils.getChineseFirstWord(bh.getName()));
						} else {
							//更换医院名称后重新生成医院Code
							/*
							String hospitalCode = PinyinUtils.getChineseFirstWord(bh.getName());
							if (!hospital.getCode().equalsIgnoreCase(hospitalCode)) {
								hospital.setCode(hospitalCode);
							}
							*/
						}
						isUnique = branchHospitalService.isUniqueCodeForHospitalId(bh);
						while (!isUnique) {
							bh.setCode(PinyinUtils.getChineseFirstWord(bh.getName()) + "_" + PKGenerator.generateId());
							isUnique = branchHospitalService.isUniqueCodeForHospitalId(bh);
						}
					}
				}
			}
			if (!isUnique) {
				if (flag == 0) {
					return new RespBody(Status.ERROR, "保存分院失败，分院名称“" + msg + "”已经存在！");
				}
			}

			branchHospitalService.batchSaveBranchHospital(branchHospitalList);
			BaseDatasManager basedataManager = SpringContextHolder.getBean(BaseDatasManager.class);
			List<CodeAndInterfaceVo> vos = hospitalService.queryCodeAndInterfaceById(hospitalId);
			basedataManager.updateHospitalForBranch(vos, hospital);
			// basedataManager.updateHospitalForBranch(hospitalId);
			return new RespBody(hospitalId, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存分院失败，数据保存异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(String branchHospitalId) {
		logger.info("delete for " + branchHospitalId);
		try {
			branchHospitalService.deleteById(branchHospitalId);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "删除分院失败，数据操作异常！");
		}
	}
}
