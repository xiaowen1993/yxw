/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-28</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.rule.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.MsgMode;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleFriedExpress;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleInHospital;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.controller.HospitalController;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.privilege.service.UserService;
import com.yxw.platform.rule.service.RuleClinicService;
import com.yxw.platform.rule.service.RuleEditService;
import com.yxw.platform.rule.service.RuleFriedExpressService;
import com.yxw.platform.rule.service.RuleHisDataService;
import com.yxw.platform.rule.service.RuleInHospitalService;
import com.yxw.platform.rule.service.RuleOnlineFilingService;
import com.yxw.platform.rule.service.RulePaymentService;
import com.yxw.platform.rule.service.RulePushService;
import com.yxw.platform.rule.service.RuleQueryService;
import com.yxw.platform.rule.service.RuleRegisterService;
import com.yxw.platform.rule.service.RuleTiedCardService;
import com.yxw.platform.rule.service.RuleUserCenterService;

/**
 * @Package: com.yxw.platform.rule.controller
 * @ClassName: SwitchRuleConfigController
 * @Statement: <p>
 *             编辑各类规则切换控制类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-28
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/ruleIndex")
public class SwitchRuleConfigController extends BizBaseController<Hospital, String> {
	private Logger logger = LoggerFactory.getLogger(HospitalController.class);

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private HospitalService hospitalService = SpringContextHolder.getBean(HospitalService.class);

	private RuleEditService editService = SpringContextHolder.getBean(RuleEditService.class);

	private RuleOnlineFilingService onlineFilingService = SpringContextHolder.getBean(RuleOnlineFilingService.class);

	private RuleTiedCardService tiedCardService = SpringContextHolder.getBean(RuleTiedCardService.class);

	private RuleRegisterService registerService = SpringContextHolder.getBean(RuleRegisterService.class);

	private RulePaymentService paymentService = SpringContextHolder.getBean(RulePaymentService.class);

	private RuleClinicService clinicService = SpringContextHolder.getBean(RuleClinicService.class);

	private RuleQueryService queryService = SpringContextHolder.getBean(RuleQueryService.class);

	private RuleFriedExpressService ruleFriedExpressService = SpringContextHolder.getBean(RuleFriedExpressService.class);

	private RuleInHospitalService ruleInHospitalService = SpringContextHolder.getBean(RuleInHospitalService.class);

	private RulePushService pushService = SpringContextHolder.getBean(RulePushService.class);

	private RuleUserCenterService userCenterService = SpringContextHolder.getBean(RuleUserCenterService.class);

	private RuleHisDataService hisDataService = SpringContextHolder.getBean(RuleHisDataService.class);

	private RuleConfigManager ruleManager = SpringContextHolder.getBean(RuleConfigManager.class);
	
	private PlatformService platformService = SpringContextHolder.getBean(PlatformService.class);

	@Override
	protected BaseService<Hospital, String> getService() {
		// TODO Auto-generated method stub
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
	@RequestMapping(value = "/hospitalList")
	public String ruleList(@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "5") int pageSize, @RequestParam(required = false, defaultValue = "") String search,
			ModelMap modelMap, HttpServletRequest request) {
		logger.info("医院管理列表分页查询,pageNum=[" + pageNum + "],pageSize=[" + pageSize + "]");

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
			hospitals = hospitalService.findHadBranchByPage(params, new Page<Hospital>(pageNum, pageSize));
		}
		if (hospitals != null) {
			completeRuleInfo(hospitals);
		}
		modelMap.put("search", search);
		modelMap.put("hospitals", hospitals);

		return "/sys/rule/list";
	}

	/**
	 * 将修改人 发布人的姓名信息加入
	 * 
	 * @param hospitals
	 * @return
	 */
	private void completeRuleInfo(PageInfo<Hospital> hospitals) {
		Set<String> userIds = new HashSet<String>();
		for (Hospital hospital : hospitals.getList()) {
			if (StringUtils.isNotBlank(hospital.getLastHandlerId())) {
				userIds.add(hospital.getLastHandlerId());
			}

		}
		if (userIds.size() > 0) {
			UserService userservice = SpringContextHolder.getBean(UserService.class);
			String[] ids = new String[userIds.size()];
			List<User> users = userservice.findByIds(Arrays.asList(userIds.toArray(ids)));
			Map<String, String> idAndName = new HashMap<String, String>();
			for (User u : users) {
				idAndName.put(u.getId(), u.getUserName());
			}

			String userName = null;
			for (Hospital hospital : hospitals.getList()) {
				if (StringUtils.isNotBlank(hospital.getLastHandlerId())) {
					userName = idAndName.get(hospital.getLastHandlerId());
					if (StringUtils.isNotBlank(userName)) {
						hospital.setLastHandler(userName);
					}

				}

			}
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/toEdit")
	public String toEdit(String hospitalId, String ruleType, ModelMap modelMap) {
		if (hospitalId != null) {
			Hospital hospital = hospitalService.findById(hospitalId);
			if (hospital != null) {
				modelMap.put("hospitalId", hospital.getId());
				modelMap.put("hospitalName", hospital.getName());
			}

			if (RuleConstant.RULE_TYPE_EDIT.equalsIgnoreCase(ruleType)) {
				// 全局规则
				RuleEdit ruleEdit = editService.findByHospitalId(hospitalId);
				if (ruleEdit == null) {
					ruleEdit = RuleEdit.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleEdit", ruleEdit);
			} else if (RuleConstant.RULE_TYPE_ONLINEFILING.equalsIgnoreCase(ruleType)) {
				// 在线建档规则
				RuleOnlineFiling onlineFiling = onlineFilingService.findByHospitalId(hospitalId);
				if (onlineFiling == null) {
					onlineFiling = RuleOnlineFiling.getDefaultRule(hospitalId);
				}
				modelMap.put("onlineFiling", onlineFiling);

			} else if (RuleConstant.RULE_TYPE_TIEDCARD.equalsIgnoreCase(ruleType)) {
				// 绑卡规则
				RuleTiedCard ruleTiedCard = tiedCardService.findByHospitalId(hospitalId);
				if (ruleTiedCard == null) {
					ruleTiedCard = RuleTiedCard.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleTiedCard", ruleTiedCard);
			} else if (RuleConstant.RULE_TYPE_REGISTER.equalsIgnoreCase(ruleType)) {
				// 挂号规则
				RuleRegister ruleRegister = registerService.findByHospitalId(hospitalId);
				if (ruleRegister == null) {
					ruleRegister = RuleRegister.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleRegister", ruleRegister);
			} else if (RuleConstant.RULE_TYPE_QUERY.equalsIgnoreCase(ruleType)) {
				// 查询规则
				RuleQuery ruleQuery = queryService.findByHospitalId(hospitalId);
				if (ruleQuery == null) {
					ruleQuery = RuleQuery.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleQuery", ruleQuery);
			} else if (RuleConstant.RULE_TYPE_PAYMENT.equalsIgnoreCase(ruleType)) {
				// 获取所有的支付方式
				// List<PlatformPaymentVo> vos = platformService.findAllPlatformPayModes();
				List<PlatformPaymentVo> vos = platformService.findAllPlatformPayModesByHospitalId(hospitalId);
				logger.info(JSON.toJSONString(vos));
				// 支付方式规则
				RulePayment rulePayment = paymentService.findByHospitalId(hospitalId);
				if (rulePayment == null) {
					rulePayment = RulePayment.getDefaultRule(hospitalId);
				}
				modelMap.put("rulePayment", rulePayment);
				String json = rulePayment.getTradeTypes();
				if (StringUtils.isBlank(json)) {
					Map<String, String> payModesMap = new HashMap<>();
					// 没有设定默认的，则生成一个
					for (PlatformPaymentVo vo: vos) {
						if (vo.getState() == 1) {
							payModesMap.put(vo.getPlatformCode(), StringUtils.repeat("0", ",", vo.getPayModes().size()));
						}
					}
					json = JSON.toJSONString(payModesMap);
				}
				Map<String, String> map = JSON.parseObject(json, Map.class);
				// map.put("innerUnionPay", "1,2");	// 测试
				modelMap.put("payModes", map);
				modelMap.put("platforms", vos);
				
				// 默认支付方式
				json = rulePayment.getDefaultTradeTypes();
				Map<String, String> defaultTypeMap = null;
				if (StringUtils.isBlank(json)) { 
					defaultTypeMap = new HashMap<>();
					for (PlatformPaymentVo vo: vos) {
						defaultTypeMap.put(vo.getPlatformCode(), "-1");
					}
				} else {
					defaultTypeMap = JSON.parseObject(json, Map.class);
				}
				logger.info(defaultTypeMap.toString());
				modelMap.put("defaultTradeTypes", defaultTypeMap);
			} else if (RuleConstant.RULE_TYPE_CLINIC.equalsIgnoreCase(ruleType)) {
				// 门诊缴费规则
				RuleClinic ruleClinic = clinicService.findByHospitalId(hospitalId);
				if (ruleClinic == null) {
					ruleClinic = RuleClinic.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleClinic", ruleClinic);
			} else if (RuleConstant.RULE_TYPE_PUSH.equalsIgnoreCase(ruleType)) {
				/*Map<String, Object> params = new HashMap<String, Object>();
				params.put("hospitalId", hospitalId);
				params.put("platformType", "wechat");
				// 推送规则
				RulePush rulePushWechat = pushService.findByHospitalId(params);
				params = new HashMap<String, Object>();
				params.put("hospitalId", hospitalId);
				params.put("platformType", "alipay");
				RulePush rulePushAlipay = pushService.findByHospitalId(params);
				params = new HashMap<String, Object>();
				params.put("hospitalId", hospitalId);
				params.put("platformType", "app");
				RulePush rulePushApp = pushService.findByHospitalId(params);
				if (rulePushWechat == null) {
					rulePushWechat = RulePush.getDefaultRule(hospitalId);
				}
				if (rulePushAlipay == null) {
					rulePushAlipay = RulePush.getDefaultRule(hospitalId);
				}
				if (rulePushApp == null) {
					rulePushApp = RulePush.getDefaultRule(hospitalId);
				}
				modelMap.put("rulePushWechat", rulePushWechat);
				modelMap.put("rulePushAlipay", rulePushAlipay);
				modelMap.put("rulePushApp", rulePushApp);*/
				
				/**
				 * 加载平台，加载推送信息
				 */
				List<PlatformMsgModeVo> platforms = platformService.findAllPlatformMsgModesByHospitalId(hospitalId);
				Map<String, PlatformMsgModeVo> platformMap = Maps.uniqueIndex(platforms, new Function<PlatformMsgModeVo, String>() {

					@Override
					public String apply(PlatformMsgModeVo input) {
						return input.getPlatformCode();
					}
				});
				modelMap.put("platforms", platformMap);
				
				// 各平台对应的推送
				// 此处不一定使用linkedhashmap, 效率偏低了. 可以在页面中做一个后点击事件。
				// Map<String, Object> rules = new LinkedHashMap<String, Object>();
				Map<String, Object> rules = new HashMap<String, Object>();
				Map<String, Object> params = new HashMap<String, Object>();
				for (PlatformMsgModeVo platform: platforms) {
					String platformCode = platform.getPlatformCode();
					params.clear();
					params.put("hospitalId", hospitalId);
					params.put("platformType", platformCode);
					RulePush rulePush = pushService.findByHospitalId(params);
					if (rulePush == null) {
						rulePush = RulePush.getDefaultRule(hospitalId);
					}
					
					// 对于0，还原回实际的方式
					if (rulePush.getPushModes().equals("0")) {
						StringBuffer pushModes = new StringBuffer();
						for (MsgMode mode: platform.getMsgModes()) {
							pushModes.append(mode.getTargetId()).append(",");
						}
						rulePush.setPushModes(pushModes.substring(0, pushModes.length() - 1));
					}
					
					rules.put(platformCode, rulePush);
				}
				
				params.clear();
				params = null;
				modelMap.put("rules", rules);
				
			} else if (RuleConstant.RULE_TYPE_FRIEDEXPRESS.equalsIgnoreCase(ruleType)) {
				// 代煎配送规则
				RuleFriedExpress ruleFriedExpress = ruleFriedExpressService.findByHospitalId(hospitalId);
				if (ruleFriedExpress == null) {
					ruleFriedExpress = RuleFriedExpress.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleFriedExpress", ruleFriedExpress);
			} else if (RuleConstant.RULE_TYPE_IN_HOSPITAL.equalsIgnoreCase(ruleType)) {
				// 住院规则配置
				RuleInHospital ruleInHospital = ruleInHospitalService.findByHospitalId(hospitalId);
				if (ruleInHospital == null) {
					ruleInHospital = RuleInHospital.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleInHospital", ruleInHospital);
			} else if (RuleConstant.RULE_TYPE_USERCENTER.equalsIgnoreCase(ruleType)) {
				// 用户中心规则
				RuleUserCenter ruleUserCenter = userCenterService.findByHospitalId(hospitalId);
				if (ruleUserCenter == null) {
					ruleUserCenter = RuleUserCenter.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleUserCenter", ruleUserCenter);
			} else if (RuleConstant.RULE_TYPE_HIS_DATA.equalsIgnoreCase(ruleType)) {
				//his基础数据获取规则配置
				RuleHisData ruleHisData = hisDataService.findByHospitalId(hospitalId);
				if (ruleHisData == null) {
					ruleHisData = RuleHisData.getDefaultRule(hospitalId);
				}
				modelMap.put("ruleHisData", ruleHisData);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" go : " + "/sys/rule/" + ruleType);
		}
		modelMap.put("ruleType", ruleType);
		return "/sys/rule/" + ruleType;
	}

	/**
	 * 发布规则
	 * 
	 * @param request
	 * @param hospitalId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/publishRule", method = RequestMethod.POST)
	public RespBody publishRule(HttpServletRequest request, String hospitalId) {
		// Assert.isNull(hospitalId);

		User user = getLoginUser(request);
		Hospital hospital = new Hospital();
		hospital.setId(hospitalId);
		hospital.setIsPublishRule(HospitalConstant.HOSPITAL_RULE_PUBLISH);
		hospital.setRulePublishTime(new Date());
		hospital.setLastHandlerId(user.getId());
		hospitalService.publishRule(hospital);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lastHandler", user.getUserName());
		map.put("publishTime", df.format(hospital.getRulePublishTime()));
		map.put("publishstatus", "已发布");
		return new RespBody(Status.OK, map);
	}

	@ResponseBody
	@RequestMapping(value = "/saveDefaultRule", method = RequestMethod.POST)
	public RespBody saveDefaultRule(HttpServletRequest request, String hospitalId) {
		try {
			List<Hospital> hospitals = hospitalService.findByHospitalIds(new String[] { hospitalId });
			if (!CollectionUtils.isEmpty(hospitals)) {
				Hospital hospital = hospitals.get(0);

				// 如果是启用的则不需要进行默认处理, 否则进行默认配置。 
				// 默认配置规则: 如果本身已经进行了配置，即缓存中存在，则不进行配置。
				if (hospital.getStatus() == 0) {
					// 全局配置
					RuleEdit ruleEdit = ruleManager.getRuleEditByHospitalId(hospitalId);
					if (ruleEdit == null) {
						ruleEdit = RuleEdit.getDefaultRule(hospitalId);
						editService.saveRuleEdit(ruleEdit);
					}

					// 绑卡配置
					RuleTiedCard ruleTiedCard = ruleManager.getRuleTiedCardByHospitalId(hospitalId);
					if (ruleTiedCard == null) {
						ruleTiedCard = RuleTiedCard.getDefaultRule(hospitalId);
						tiedCardService.saveRuleTiedCard(ruleTiedCard);
					}

					// 挂号配置
					RuleRegister ruleRegister = ruleManager.getRuleRegisterByHospitalId(hospitalId);
					if (ruleRegister == null) {
						ruleRegister = RuleRegister.getDefaultRule(hospitalId);
						registerService.saveRuleRegister(ruleRegister);
					}

					// 支付方式配置
					RulePayment rulePayment = ruleManager.getRulePaymentByHospitalId(hospitalId);
					if (rulePayment == null) {
						rulePayment = RulePayment.getDefaultRule(hospitalId);
						paymentService.saveRulePayment(rulePayment);
					}

					// 门诊缴费配置
					RuleClinic ruleClinic = ruleManager.getRuleClinicByHospitalId(hospitalId);
					if (ruleClinic == null) {
						ruleClinic = RuleClinic.getDefaultRule(hospitalId);
						clinicService.saveRuleClinic(ruleClinic);
					}

					// 查询配置
					RuleQuery ruleQuery = ruleManager.getRuleQueryByHospitalId(hospitalId);
					if (ruleQuery == null) {
						ruleQuery = RuleQuery.getDefaultRule(hospitalId);
						queryService.saveRuleQuery(ruleQuery);
					}

					// 个人中心配置
					RuleUserCenter ruleUserCenter = ruleManager.getRuleUserCenterByHospitalId(hospitalId);
					if (ruleUserCenter == null) {
						ruleUserCenter = RuleUserCenter.getDefaultRule(hospitalId);
						userCenterService.saveRuleUserCenter(ruleUserCenter);
					}

					// 在线建档配置
					RuleOnlineFiling ruleOnlineFiling = ruleManager.getRuleOnlineFilingByHospitalId(hospitalId);
					if (ruleOnlineFiling == null) {
						ruleOnlineFiling = RuleOnlineFiling.getDefaultRule(hospitalId);
						onlineFilingService.saveRuleOnlineFiling(ruleOnlineFiling);
					}

					// 消息推送配置
					RulePush rulePushApp = ruleManager.getRulePushByHospitalId(hospitalId, "app");
					if (rulePushApp == null) {
						rulePushApp = RulePush.getDefaultRule(hospitalId);
						rulePushApp.setPlatformType("app");
						pushService.saveRulePush(rulePushApp);
					}

					// 代煎配送规则
					RuleFriedExpress ruleFriedExpress = ruleManager.getRuleFriedExpressByHospitalId(hospitalId);
					if (ruleFriedExpress == null) {
						ruleFriedExpress = RuleFriedExpress.getDefaultRule(hospitalId);
						ruleFriedExpressService.saveRuleFriedExpress(ruleFriedExpress);
					}

					// 住院规则配置
					RuleInHospital ruleInHospital = ruleManager.getRuleInHospitalByHospitalId(hospitalId);
					if (ruleInHospital == null) {
						ruleInHospital = RuleInHospital.getDefaultRule(hospitalId);
						ruleInHospitalService.saveRuleInHospital(ruleInHospital);
					}

					// his轮询配置
					RuleHisData ruleHisData = ruleManager.getRuleHisDataByHospitalId(hospitalId);
					if (ruleHisData == null) {
						ruleHisData = RuleHisData.getDefaultRule(hospitalId);
						hisDataService.saveRuleHisData(ruleHisData);
					}

					return new RespBody(Status.OK, "成功初始化默认规则! 可以进行规则的发布和医院的医用了!");
				} else {
					return new RespBody(Status.OK, "已经已启用, 不需要再初始化默认规则.");
				}
			} else {
				return new RespBody(Status.ERROR, "无效的hospitalId.");
			}
		} catch (Exception e) {
			logger.error("初始化默认规则异常. errorMsg: {}. errorCause: {}.", e.getMessage(), e.getCause());
			return new RespBody(Status.ERROR, "初始化默认规则异常.");
		}
	}
}
