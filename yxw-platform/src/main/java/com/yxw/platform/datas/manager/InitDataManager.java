package com.yxw.platform.datas.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.yxw.app.biz.area.service.AreaService;
import com.yxw.app.biz.location.service.AppLocationService;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.commons.entity.platform.app.location.AppLocation;
import com.yxw.commons.entity.platform.app.optional.AppOptional;
import com.yxw.commons.entity.platform.app.optional.AppOptionalModule;
import com.yxw.commons.entity.platform.area.Area;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.rule.RuleClinic;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleOnlineFiling;
import com.yxw.commons.entity.platform.rule.RulePayment;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.entity.platform.rule.RuleQuery;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.entity.platform.rule.RuleTiedCard;
import com.yxw.commons.entity.platform.rule.RuleUserCenter;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.platform.app.carrieroperator.service.CarrieroperatorService;
import com.yxw.platform.app.color.service.AppColorService;
import com.yxw.platform.app.optional.service.AppOptionalModuleService;
import com.yxw.platform.app.optional.service.AppOptionalService;
import com.yxw.platform.common.service.CommonService;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.hospital.service.PlatformSettingsService;
import com.yxw.platform.msgpush.service.MsgCustomerService;
import com.yxw.platform.msgpush.service.MsgTemplateLibraryService;
import com.yxw.platform.msgpush.service.MsgTemplateService;
import com.yxw.platform.privilege.service.RoleResourceService;
import com.yxw.platform.privilege.service.RoleService;
import com.yxw.platform.privilege.service.UserRoleService;
import com.yxw.platform.privilege.service.UserService;
import com.yxw.platform.rule.service.RuleClinicService;
import com.yxw.platform.rule.service.RuleEditService;
import com.yxw.platform.rule.service.RuleHisDataService;
import com.yxw.platform.rule.service.RuleOnlineFilingService;
import com.yxw.platform.rule.service.RulePaymentService;
import com.yxw.platform.rule.service.RulePushService;
import com.yxw.platform.rule.service.RuleQueryService;
import com.yxw.platform.rule.service.RuleRegisterService;
import com.yxw.platform.rule.service.RuleTiedCardService;
import com.yxw.platform.rule.service.RuleUserCenterService;

public class InitDataManager {

	private static boolean isHasInit = false;
	// 医院
	private HospitalService hospitalService = SpringContextHolder.getBean(HospitalService.class);
	// 角色
	private RoleService roleService = SpringContextHolder.getBean(RoleService.class);
	// 角色资源
	private RoleResourceService roleResourceService = SpringContextHolder.getBean(RoleResourceService.class);
	// 用户
	private UserService userService = SpringContextHolder.getBean(UserService.class);
	// 用户角色
	private UserRoleService userRoleService = SpringContextHolder.getBean(UserRoleService.class);
	// 通用服务
	private CommonService commonService = SpringContextHolder.getBean(CommonService.class);
	// 主要规则
	private RuleEditService editService = SpringContextHolder.getBean(RuleEditService.class);
	// 在线建档规则
	private RuleOnlineFilingService onlineFilingService = SpringContextHolder.getBean(RuleOnlineFilingService.class);
	// 绑卡规则
	private RuleTiedCardService tiedCardService = SpringContextHolder.getBean(RuleTiedCardService.class);
	// 挂号规则
	private RuleRegisterService registerService = SpringContextHolder.getBean(RuleRegisterService.class);
	// 查询规则
	private RuleQueryService queryService = SpringContextHolder.getBean(RuleQueryService.class);
	// 支付方式规则
	private RulePaymentService paymentService = SpringContextHolder.getBean(RulePaymentService.class);
	// 门诊缴费规则
	private RuleClinicService clinicService = SpringContextHolder.getBean(RuleClinicService.class);
	// 推送规则
	private RulePushService pushService = SpringContextHolder.getBean(RulePushService.class);
	// 个人中心规则
	private RuleUserCenterService userCenterService = SpringContextHolder.getBean(RuleUserCenterService.class);
	// 轮询规则
	private RuleHisDataService ruleHisDataService = SpringContextHolder.getBean(RuleHisDataService.class);
	// 模版消息的模板
	private MsgTemplateService msgTemplateService = SpringContextHolder.getBean(MsgTemplateService.class);
	// 模版消息模板库
	private MsgTemplateLibraryService msgTemplateLibraryService = SpringContextHolder.getBean(MsgTemplateLibraryService.class);
	// 客服消息
	private MsgCustomerService msgCustomerService = SpringContextHolder.getBean(MsgCustomerService.class);
	// 所有地址
	private AreaService areaService = SpringContextHolder.getBean(AreaService.class);
	// App定位
	private AppLocationService appLocationService = SpringContextHolder.getBean(AppLocationService.class);
	// 功能
	private AppOptionalService appOptionalService = SpringContextHolder.getBean(AppOptionalService.class);
	// 功能主模块
	private AppOptionalModuleService appOptionalModuleService = SpringContextHolder.getBean(AppOptionalModuleService.class);
	// 运营轮播
	private CarrieroperatorService carrieroperatorService = SpringContextHolder.getBean(CarrieroperatorService.class);
	// 系统配色
	private AppColorService appColorService = SpringContextHolder.getBean(AppColorService.class);
	// cache缓存使用
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	// 接入平台
	private PlatformSettingsService platformSettingsService = SpringContextHolder.getBean(PlatformSettingsService.class);
	// 平台
	private PlatformService platformService = SpringContextHolder.getBean(PlatformService.class);

	/**
	 * 初始化 医院interfaceId appId 信息
	 */
	public void initHospitalCache() throws SystemException {
		// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
		// hospitalCache.initCache();
		try {

			if (!isHasInit) {
				CacheConstant.logger.info("init Hospital config infos   start.................");
				List<CodeAndInterfaceVo> codeAndInterfaceVos = hospitalService.queryAllCodeAndInterfaceIds();
				/* redis缓存 */
				// 缓存医院code和serviceId的关系
				/** hospital.code.interface  **/
				if (!CollectionUtils.isEmpty(codeAndInterfaceVos)) {
					String cacheKey = getInterfaceCaceKey();
					Map<String, String> hospitalData = new HashMap<String, String>();
					for (CodeAndInterfaceVo vo : codeAndInterfaceVos) {
						String fieldName = genInterfaceFieldNameByCode(vo.getHospitalCode(), vo.getBranchHospitalCode());
						hospitalData.put(fieldName, JSON.toJSONString(vo));
					}
					/*
					 * redisSvc.del(cacheKey); redisSvc.hmset(cacheKey, hospitalData);
					 */
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(cacheKey);
					serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

					parameters.add(hospitalData);
					serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
				}
				// 缓存医院与支付平台appId 的关系
				/** hospital.app.interface  **/
				Map<String, String> appAndcodeVoMap = new HashMap<String, String>();
				List<HospIdAndAppSecretVo> hospIdAndAppSecretVos = hospitalService.findAllAppSecret();
				List<HospitalCodeAndAppVo> vos = hospitalService.queryCodeAndApp();
				if (!CollectionUtils.isEmpty(vos) && !CollectionUtils.isEmpty(hospIdAndAppSecretVos)) {
					for (HospIdAndAppSecretVo hospIdAndAppSecretVo : hospIdAndAppSecretVos) {
						final String appCode = hospIdAndAppSecretVo.getAppCode();
						final String hospId = hospIdAndAppSecretVo.getHospId();
						String fieldName = genAppFieldNameByCode(appCode, hospIdAndAppSecretVo.getAppId());
						Collection<HospitalCodeAndAppVo> res = Collections2.filter(vos, new Predicate<HospitalCodeAndAppVo>() {
							@Override
							public boolean apply(HospitalCodeAndAppVo hospitalCodeAndAppVo) {
								return StringUtils.equals(hospId.concat(appCode),
										hospitalCodeAndAppVo.getHospitalId().concat(hospitalCodeAndAppVo.getAppCode()));
							}
						});

						if (!CollectionUtils.isEmpty(res)) {
							appAndcodeVoMap.put(fieldName, JSON.toJSONString(res));
						}
					}

					if (!CollectionUtils.isEmpty(appAndcodeVoMap)) {
						List<Object> parameters = new ArrayList<Object>();
						parameters.add(getAppCacheKey());
						serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

						parameters.add(appAndcodeVoMap);
						serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
					}
				}
				// 缓存医院与接入平台appId 的关系 (目前即我们的平台)
				/** hospital.app.platform  **/
				List<PlatformSettings> platformSettings = platformSettingsService.findAllPlatformRelations();
				Map<String, String> platformSettingsMap = new HashMap<String, String>();
				if (!CollectionUtils.isEmpty(platformSettings)) {
					for (PlatformSettings settings : platformSettings) {
						if (StringUtils.isNotBlank(settings.getHpsId()) && StringUtils.isNotBlank(settings.getCode())) {
							String fieldName = genAppFieldNameByCode(settings.getCode(), settings.getHospital().getId());
							// 目前hospitalId唯一，appCode 也唯一
							platformSettingsMap.put(fieldName, JSON.toJSONString(settings));
						}
					}

					if (!CollectionUtils.isEmpty(platformSettingsMap)) {
						List<Object> parameters = new ArrayList<Object>();
						String cacheKey = getHospitalPlatformCacheKey();
						parameters.add(cacheKey);
						serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

						parameters.add(platformSettingsMap);
						serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
					}
				}
				initAppSecretCache();
				// 初始化医院基础信息缓存
				initHospBaseInfoCache();
				// 初始化平台信息（是否启用平台，需要使用到） -- 变更平台，可以不进行重启，数据库改了后，数据直接写入redis即可
				initPlatformCache();

				CacheConstant.logger.info("init Hospital config infos end.................");
			}

		} catch (Exception e) {
			CacheConstant.logger.error("init cache is error,infos:{}", e);
		}

	}

	public void initPlatformCache() {
		if (!isHasInit) {
			// 平台
			List<Platform> platforms = platformService.findAll();
			List<Object> params = new ArrayList<>();
			params.add(platforms);
			serveComm.set(CacheType.PLATFORM_CACHE, "cacheAllPlatform", params);
			platforms = null;

			// 支付方式
			List<PlatformPaymentVo> platformPaymentVos = platformService.findAllPlatformPayModes();
			System.out.println("platformPaymentVos+++" + JSONObject.toJSONString(platformPaymentVos, true));
			params.clear();
			params.add(platformPaymentVos);
			serveComm.set(CacheType.PLATFORM_CACHE, "cacheAllPlatformPayModes", params);
			platformPaymentVos = null;

			// 消息推送
			List<PlatformMsgModeVo> platformMsgModeVos = platformService.findAllPlatformMsgModes();
			params.clear();
			params.add(platformMsgModeVos);
			serveComm.set(CacheType.PLATFORM_CACHE, "cacheAllPlatformMsgModes", params);
			platformMsgModeVos = null;

			// 最后清空
			params.clear();
			params = null;
		}
	}

	/**
	 * 初始化 白名单 信息
	 */
	public void initWhiteListCache() {
		// WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);
		// whiteListCache.initCahce();
		if (!isHasInit) {
			List<WhiteListVo> vos = commonService.findAllWhiteDetails();
			if (!CollectionUtils.isEmpty(vos)) {
				Map<String, String> dataMap = new HashMap<String, String>();
				String cacheKey = CacheConstant.CACHE_WHITE_LIST;
				for (WhiteListVo vo : vos) {
					String fied = null;
					if (StringUtils.isNotBlank(vo.getOpenId())) {
						fied = getOpenIdField(vo);
						dataMap.put(fied, JSON.toJSONString(vo));
					}

					if (StringUtils.isNotBlank(vo.getPhone())) {
						fied = getPhoneField(vo);
						dataMap.put(fied, JSON.toJSONString(vo));
					}

					String setWhiteFied = vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode());
					dataMap.put(setWhiteFied, "true");
				}
				if (!dataMap.isEmpty()) {

					// redisSvc.hmset(cacheKey, dataMap);
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(cacheKey);
					parameters.add(dataMap);
					serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);

				}
			}
		}
	}

	/**
	 * 初始化 医院规则配置 信息
	 */
	public void initRuleConfigCache() {

		if (!isHasInit) {
			CacheConstant.logger.info("init hospital's rules is start.................");
			// 系统中的所有医院code 与 id对应关系
			BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
			List<CodeAndInterfaceVo> voMap = baseDatasManager.getCodeAndInterfaceMap();

			Map<String, String> codeAndIdMap = new HashMap<String, String>();
			for (CodeAndInterfaceVo vo : voMap) {
				codeAndIdMap.put(vo.getHospitalId(), vo.getHospitalCode());
			}

			String cacheKey = CacheConstant.CACHE_HOSPITAL_RULES_KEY_PREFIX;
			String fieldName = null;

			Map<String, String> dataMap = new HashMap<String, String>();
			// 系统所有的ruleEdit
			List<RuleEdit> edits = editService.findAll();

			// 医院页面底部Logo信息
			String hospitalFootLogo = null;
			for (RuleEdit edit : edits) {
				String hospitalCode = codeAndIdMap.get(edit.getHospitalId());
				if (StringUtils.isEmpty(hospitalCode)) {
					continue;
				}
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_EDIT);
					dataMap.put(fieldName, JSON.toJSONString(edit));
				}

				hospitalFootLogo = edit.getFootLogoInfo();
				if (hospitalFootLogo == null) {
					hospitalFootLogo = "";
				}

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(hospitalCode);
				parameters.add(hospitalFootLogo);
				serveComm.set(CacheType.HOSPITAL_RULE_CACHE, "setHospitalFootLogo", parameters);

				// hospitalFootLogoCache.put(hospitalCode, hospitalFootLogo);
			}

			// 在线建档规则
			List<RuleOnlineFiling> onlineFilings = onlineFilingService.findAll();
			for (RuleOnlineFiling onlineFiling : onlineFilings) {
				String hospitalCode = codeAndIdMap.get(onlineFiling.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_ONLINEFILING);
					dataMap.put(fieldName, JSON.toJSONString(onlineFiling));
				}
			}

			// 绑卡规则
			List<RuleTiedCard> tiedCards = tiedCardService.findAll();
			for (RuleTiedCard tiedCard : tiedCards) {
				String hospitalCode = codeAndIdMap.get(tiedCard.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_TIEDCARD);
					dataMap.put(fieldName, JSON.toJSONString(tiedCard));
				}
			}

			// 挂号规则
			List<RuleRegister> registers = registerService.findAll();
			for (RuleRegister register : registers) {
				String hospitalCode = codeAndIdMap.get(register.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_REGISTER);
					dataMap.put(fieldName, JSON.toJSONString(register));
				}
			}

			// 查询规则
			List<RuleQuery> querys = queryService.findAll();
			for (RuleQuery query : querys) {
				String hospitalCode = codeAndIdMap.get(query.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_QUERY);
					dataMap.put(fieldName, JSON.toJSONString(query));
				}
			}

			// 支付方式规则
			List<RulePayment> payments = paymentService.findAll();
			for (RulePayment payment : payments) {
				String hospitalCode = codeAndIdMap.get(payment.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PAYMENT);
					dataMap.put(fieldName, JSON.toJSONString(payment));
				}
			}

			// 门诊缴费规则
			List<RuleClinic> clinics = clinicService.findAll();
			for (RuleClinic ruleClinic : clinics) {
				String hospitalCode = codeAndIdMap.get(ruleClinic.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_CLINIC);
					dataMap.put(fieldName, JSON.toJSONString(ruleClinic));
				}
			}

			// 推送规则
			List<RulePush> pushs = pushService.findAll();
			for (RulePush push : pushs) {
				String hospitalCode = codeAndIdMap.get(push.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_PUSH.concat(".").concat(push.getPlatformType()));
					dataMap.put(fieldName, JSON.toJSONString(push));
				}
			}

			// 个人中心规则
			List<RuleUserCenter> centers = userCenterService.findAll();
			for (RuleUserCenter center : centers) {
				String hospitalCode = codeAndIdMap.get(center.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_USERCENTER);
					dataMap.put(fieldName, JSON.toJSONString(center));
				}
			}

			// his接口基础数据规则(科室,医生,号源)
			List<RuleHisData> ruleHisdatas = ruleHisDataService.findAll();
			for (RuleHisData ruleHisData : ruleHisdatas) {
				String hospitalCode = codeAndIdMap.get(ruleHisData.getHospitalId());
				if (StringUtils.isNotBlank(hospitalCode)) {
					fieldName = getRuleFieldName(hospitalCode, RuleConstant.RULE_TYPE_HIS_DATA);
					dataMap.put(fieldName, JSON.toJSONString(ruleHisData));
				}
			}

			if (!dataMap.isEmpty()) {
				// redisSvc.hmset(cacheKey, dataMap);
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				parameters.add(dataMap);
				serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
			}

			CacheConstant.logger.info("init hospital's rules is end .................");
		}
	}

	/**
	 * 初始化 消息推送 信息
	 */
	public void initMsgTempCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init msgTemplateLibrary, msgTemplate and msgCustomer config infos  start.................");
			// msgTemplateLibrary
			List<MsgTemplateLibrary> msgTemplateLibraries = msgTemplateLibraryService.findAll();
			if (!CollectionUtils.isEmpty(msgTemplateLibraries)) {
				Map<String, String> map = new HashMap<String, String>();
				String cacheKey = CacheConstant.CACHE_MSG_LIBRARY;
				for (MsgTemplateLibrary msgTemplateLibrary : msgTemplateLibraries) {
					map.put(getMsgTemplateLibraryFieldName(msgTemplateLibrary.getTemplateCode(),
							String.valueOf(msgTemplateLibrary.getSource())), JSON.toJSONString(msgTemplateLibrary));
				}
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

				parameters.add(map);
				serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
			}
			CacheConstant.logger.info("目前模板库共有{}个模板", msgTemplateLibraries.size());
			// msgTemplate
			List<MsgTemplate> msgTemplates = msgTemplateService.findAll();
			if (!CollectionUtils.isEmpty(msgTemplates)) {
				Map<String, String> map = new HashMap<String, String>();
				String cacheKey = CacheConstant.CACHE_MSG_TEMPLATE;
				for (MsgTemplate msgTemplate : msgTemplates) {
					map.put(getMsgTemplateFieldName(msgTemplate.getAppId(), msgTemplate.getCode(), String.valueOf(msgTemplate.getSource()),
							String.valueOf(msgTemplate.getMsgTarget())), JSON.toJSONString(msgTemplate));
				}
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

				parameters.add(map);
				serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
			}
			CacheConstant.logger.info("目前已定义{}个模板消息", msgTemplates.size());

			// msgCustomer
			List<MsgCustomer> msgCustomers = msgCustomerService.findAll();
			if (!CollectionUtils.isEmpty(msgCustomers)) {
				Map<String, String> map = new HashMap<String, String>();
				String cacheKey = CacheConstant.CACHE_MSG_CUSTOMER;
				for (MsgCustomer msgCustomer : msgCustomers) {
					map.put(getMsgCustomerFieldName(msgCustomer.getAppId(), msgCustomer.getCode(), String.valueOf(msgCustomer.getSource())),
							JSON.toJSONString(msgCustomer));
				}
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

				parameters.add(map);
				serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
			}
			CacheConstant.logger.info("目前已定义{}个客服消息", msgCustomers.size());

			CacheConstant.logger.info("init msgTemplateLibrary, msgTemplate and msgCustomer config infos  end.................");
		}
	}

	/**
	 * 初始化 权限相关 信息
	 */
	public void initUserRoleCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init userRole is start.................");

			List<User> userList = userService.findAll();
			List<Object> parameters = new ArrayList<Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (!CollectionUtils.isEmpty(userList)) {
				for (User user : userList) {
					String userId = user.getId();
					List<Role> roleList = userRoleService.findRoleByUserId(userId);
					if (!CollectionUtils.isEmpty(roleList)) {
						map.put(userId, roleList);
					}
				}
			}

			if (! ( map == null || map.isEmpty() )) {
				parameters.add(map);
				serveComm.set(CacheType.USER_ROLE_CACHE, "setRolesToCache", parameters);
			}

			CacheConstant.logger.info("init userRole is end.................");
		}
	}

	public void initRoleResourceCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init roleResource is start.................");

			List<Object> parameters = new ArrayList<Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			List<Role> roleList = roleService.findAll();
			if (!CollectionUtils.isEmpty(roleList)) {
				for (Role role : roleList) {
					String roleId = role.getId();
					List<Resource> resourceList = roleResourceService.findResourceByRoleId(roleId);
					if (!CollectionUtils.isEmpty(resourceList)) {
						map.put(roleId, resourceList);
					}
				}
			}

			parameters.add(map);
			serveComm.set(CacheType.ROLE_RESOURCE_CACHE, "setRoleResourcesToCache", parameters);

			CacheConstant.logger.info("init roleResource is end.................");
		}
	}

	/**
	 * 初始化 健康易中的医院与功能的关系 信息
	 */
	public void initHospitalAndOptionCache() {

		if (!isHasInit) {
			CacheConstant.logger.info("init HospitalOption config infos   start.................");
			Map<String, Object> params = new HashMap<String, Object>();
			// 不传
			// params.put(BizConstant.APPCODE, BizConstant.MODE_TYPE_APP);
			List<HospitalInfoByEasyHealthVo> hospitalInfoByEasyHealthVos =
					hospitalService.queryHospitalAndOptionByAppCodeAndBizCode(params);

			/* redis缓存 */
			// 缓存医院与功能的关系
			if (!CollectionUtils.isEmpty(hospitalInfoByEasyHealthVos)) {
				String cacheKey = getOptionCacheKey();
				// 功能Map
				Map<String, String> optionData = new HashMap<String, String>();
				for (HospitalInfoByEasyHealthVo vo : hospitalInfoByEasyHealthVos) {
					String key =
							vo.getAppCode().concat(CacheConstant.SPLIT_CHAR).concat(vo.getBizCode()).concat(CacheConstant.SPLIT_CHAR)
									.concat(vo.getAreaCode());
					List<HospitalInfoByEasyHealthVo> vos = new ArrayList<HospitalInfoByEasyHealthVo>();
					if (optionData.containsKey(key)) {
						vos = JSON.parseArray(optionData.get(key), HospitalInfoByEasyHealthVo.class);
					}
					vos.add(vo);
					optionData.put(key, JSON.toJSONString(vos));
				}
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

				parameters.add(optionData);
				serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);

				// CacheConstant.logger.info("optionData:{},", JSON.toJSONString(optionData));
			}

			CacheConstant.logger.info("init HospitalOption config infos end.................");
		}
	}

	/**
	 * 初始化 公众号 信息
	 */
	public void initAppSecretCache() {
		if (!isHasInit) {
			List<HospIdAndAppSecretVo> vos = hospitalService.findAllAppSecret();
			Map<String, String> jsonMap = new HashMap<String, String>();
			if (!CollectionUtils.isEmpty(vos)) {
				for (HospIdAndAppSecretVo vo : vos) {
					if (StringUtils.isNotBlank(vo.getAppId()) && StringUtils.isNotBlank(vo.getAppCode())) {
						jsonMap.put(genAppFieldNameByCode(vo.getAppCode(), vo.getAppId()), JSON.toJSONString(vo));
					}
				}
				if (jsonMap.size() > 0) {
					String cacheKey = CacheConstant.CACHE_APP_SECRET_KEY_PREFIX;

					List<Object> parameters = new ArrayList<Object>();
					parameters.add(cacheKey);
					serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

					parameters.add(jsonMap);
					serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
				}
			}
		}

	}

	/**
	 * 初始化 医院基础 信息
	 */
	public void initHospBaseInfoCache() {
		if (!isHasInit) {
			List<Hospital> vos = hospitalService.findAllByStatus();
			if (!CollectionUtils.isEmpty(vos)) {
				Map<String, String> jsonMap = new HashMap<String, String>();
				for (Hospital vo : vos) {
					if (StringUtils.isNotBlank(vo.getId()) && StringUtils.isNotBlank(vo.getCode())) {
						jsonMap.put(genHospBaseInfoName(vo.getId(), vo.getCode()), JSON.toJSONString(vo));
					}
				}
				if (jsonMap.size() > 0) {
					String cacheKey = CacheConstant.CACHE_HOSPITAL_BASEINFO;
					List<Object> parameters = new ArrayList<Object>();
					parameters.add(cacheKey);
					serveComm.delete(CacheType.COMMON_CACHE, "delCache", parameters);

					parameters.add(jsonMap);
					serveComm.set(CacheType.COMMON_CACHE, "setHMCache", parameters);
				}
			}
		}

	}

	/**
	 * 初始化 城市 信息
	 */
	public void initAreasCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init Areas   start.................");

			List<Area> oneLevelAreas = areaService.findOneLevelAreas();
			if (!CollectionUtils.isEmpty(oneLevelAreas)) {

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(oneLevelAreas);
				serveComm.set(CacheType.AREA_CACHE, "setAreas", parameters);

				for (Area area1 : oneLevelAreas) {
					List<Area> twoLevelAreas = areaService.findTwoLevelAreasByParentId(area1.getId());

					parameters.clear();
					parameters.add(twoLevelAreas);
					serveComm.set(CacheType.AREA_CACHE, "setAreas", parameters);

					for (Area area2 : twoLevelAreas) {
						List<Area> threeLevelAreas = areaService.findThreeLevelAreasByParentId(area2.getId());

						parameters.clear();
						parameters.add(threeLevelAreas);
						serveComm.set(CacheType.AREA_CACHE, "setAreas", parameters);
					}
				}
			}

			CacheConstant.logger.info("init Areas   end.................");
		}

	}

	/**
	 * 初始化 APP定位可选城市 信息
	 */
	public void initAppLocationsCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init AppLocation   start.................");

			// 获取一级-省(all)
			List<Area> areas = areaService.getCacheOneLevelAreas();
			Map<String, Area> areaMap = Maps.uniqueIndex(areas, new Function<Area, String>() {

				@Override
				public String apply(Area area) {
					return area.getIdPath();
				}

			});

			// 设置二级-市（选中的）
			List<AppLocation> appLocations = appLocationService.findAll();
			Map<String, List<AppLocation>> locationMap = new HashMap<String, List<AppLocation>>();
			for (AppLocation location : appLocations) {
				if (location.getLevel() == 2) {
					String parentIdPath = location.getCityId().substring(0, location.getCityId().lastIndexOf("/"));

					// 补全信息 vb
					location.setProvinceId(areaMap.get(parentIdPath).getId());
					location.setProvinceName(areaMap.get(parentIdPath).getName());

					String key = location.getPlatformCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(location.getProvinceId());

					if (locationMap.containsKey(key)) {
						List<AppLocation> list = locationMap.get(key);
						list.add(location);
					} else {
						List<AppLocation> list = new ArrayList<>();
						list.add(location);
						locationMap.put(key, list);
					}
				}
			}

			System.err.println(JSON.toJSONString(locationMap));
			if (!CollectionUtils.isEmpty(locationMap)) {
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(locationMap);
				serveComm.set(CacheType.APP_LOCATION_CACHE, "set", parameters);
			}

			CacheConstant.logger.info("init AppLocation   end.................");
		}

	}

	/**
	 * 初始化 APP功能 信息
	 */
	public void initAppOptionalsCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init AppOptionals   start.................");

			List<AppOptionalModule> appOptionalModules = appOptionalModuleService.findAll();
			if (!CollectionUtils.isEmpty(appOptionalModules)) {

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(appOptionalModules);
				serveComm.set(CacheType.APP_OPTIONAL_CACHE, "setAppOptionalModules", parameters);

				for (AppOptionalModule appOptionalModule : appOptionalModules) {
					List<AppOptional> appOptionals = appOptionalService.findByModuleId(appOptionalModule.getId());

					appOptionalService.setCacheAppOptionalsByModuleCode(appOptionals, appOptionalModule.getCode());
				}
			}

			CacheConstant.logger.info("init AppOptionals   end.................");
		}

	}

	/**
	 * 初始化 APP运营数据 信息
	 */
	public void initAppCarrieroperatorsCache() {
		if (!isHasInit) {
			CacheConstant.logger.info("init Carrieroperators   start.................");

			// 运营位置 1启动页;2首页轮播
			List<Carrieroperator> carrieroperators1 = carrieroperatorService.findByOperationPosition("1");
			if (!CollectionUtils.isEmpty(carrieroperators1)) {

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(carrieroperators1);
				serveComm.set(CacheType.APP_CARRIEROPERATOR_CACHE, "setByOperationPosition", parameters);
			}

			List<Carrieroperator> carrieroperators2 = carrieroperatorService.findByOperationPosition("2");
			if (!CollectionUtils.isEmpty(carrieroperators2)) {

				List<Object> parameters = new ArrayList<Object>();
				parameters.add(carrieroperators2);
				serveComm.set(CacheType.APP_CARRIEROPERATOR_CACHE, "setByOperationPosition", parameters);
			}

			CacheConstant.logger.info("init Carrieroperators   end.................");
		}

	}

	/**
	 * 初始化版本配色信息
	 */
	public void initAppColorCache() {
		if (!isHasInit) {

			CacheConstant.logger.info("init app colors end.................");
			List<AppColor> appColors = appColorService.findAll();
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(appColors);
			serveComm.set(CacheType.APP_COLOR_CACHE, "saveAppColors", parameters);
		}

	}

	/**
	 * 
	 * @param ruleType
	 *            参见RuleConstant
	 * @return
	 */
	private String getRuleFieldName(String hospitalCode, String ruleType) {
		return hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(ruleType);
	}

	private String getAppCacheKey() {
		return CacheConstant.CACHE_APP_CODE_KEY_PREFIX;
	}

	private String getHospitalPlatformCacheKey() {
		return CacheConstant.CACHE_HOSPITAL_PLATFORM_KEY_PREFIX;
	}

	private String genHospBaseInfoName(String hospId, String hospCode) {
		return hospId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(hospCode);
	}

	/**
	 * redis hash存储结构中的cache key
	 * 
	 * @return
	 */
	private String getInterfaceCaceKey() {
		return CacheConstant.CACHE_CODE_INTERFACE_KEY_PREFIX;
	}

	/**
	 * 根据医院code和分院code生成redis hash存储结构中的field name
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	private String genInterfaceFieldNameByCode(String hospitalCode, String branchHospitalCode) {
		return hospitalCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(branchHospitalCode);
	}

	private String genAppFieldNameByCode(String appCode, String appId) {
		return appCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(appId);
	}

	/**
	 * 获取医院与功能缓存key
	 * 
	 * @return
	 */
	private String getOptionCacheKey() {
		return CacheConstant.CACHE_AREA_OPTION_KEY_PREFIX;
	}

	private String getOpenIdField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getOpenId());
	}

	private String getPhoneField(WhiteListVo vo) {
		return vo.getAppId().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(vo.getAppCode()).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(vo.getPhone());
	}

	/**
	 * 根据模板code,模板来源生成redis hash存储结构中的field name
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgTemplateLibraryFieldName(String templateCode, String source) {
		return templateCode.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(source);
	}

	/**
	 * 根据appId,模板code,模板来源生成redis hash存储结构中的field name
	 * @param appId
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgCustomerFieldName(String appId, String templateCode, String source) {
		return appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(templateCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(source);
	}

	/**
	 * 根据appId,模板code,模板来源生成redis hash存储结构中的field name
	 * @param appId
	 * @param templateCode
	 * @param source
	 * @return
	 */
	private String getMsgTemplateFieldName(String appId, String templateCode, String source, String msgTarget) {
		return appId.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(templateCode).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
				.concat(source).concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(msgTarget);
	}

	public void finishedInit() {
		isHasInit = true;
	}
}
