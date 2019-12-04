package com.yxw.platform.registry.dubbo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.platform.registry.vo.RegistryParamVo;

/**
 * @Package: com.yxw.platform.registry.controller
 * @ClassName: DubboRegistryServiceController
 * @Statement: <p>前置转换程序Dubbo服务手动注册(热部署)请求控制类   参考Dubbo API</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-9-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/dubbo")
public class DubboServiceRegistry {
	private static Logger logger = LoggerFactory.getLogger(DubboServiceRegistry.class);

	/**
	 * dubbo ReferenceConfig 对象缓存
	 */
	public static ConcurrentHashMap<String, ReferenceConfig<? extends YxwCommService>> referenceConfigMap =
			new ConcurrentHashMap<String, ReferenceConfig<? extends YxwCommService>>();

//	public static ConcurrentHashMap<String, YxwService> consumerServiceMap = new ConcurrentHashMap<String, YxwService>();
	public static ConcurrentHashMap<String, YxwCommService> consumerServiceMap = new ConcurrentHashMap<String, YxwCommService>();

	@RequestMapping("/consumer/serviceList")
	public String consumerServiceList(HttpServletRequest request) {
		logger.info("addConsumerService.....................");
		return "/pay/to-pay";
	}

	@RequestMapping("/consumer/addService")
	public String addConsumerService(HttpServletRequest request) {
		logger.info("addConsumerService.....................");
		return "/pay/to-pay";
	}

	@ResponseBody
	@RequestMapping(value = "/consumer/registryService", method = RequestMethod.POST)
	public RespBody consumerRegistry(HttpServletRequest request, RegistryParamVo vo) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String serviceId = vo.getServiceId();
		if (StringUtils.isNotEmpty(serviceId)) {
//			YxwService yxwService = consumerServiceMap.get(serviceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
//			if (yxwService == null) {
			if (yxwCommService == null) {
				@SuppressWarnings("unchecked")
//				ReferenceConfig<YxwService> referenceConfig = (ReferenceConfig<YxwService>) referenceConfigMap.get(serviceId);
				ReferenceConfig<YxwCommService> referenceConfig = (ReferenceConfig<YxwCommService>) referenceConfigMap.get("yxwCommService");
				if (referenceConfig == null) {
					// 当前应用配置
					ApplicationConfig application = SpringContextHolder.getBean("yxwApplication");
					// 连接注册中心配置
					RegistryConfig registry = SpringContextHolder.getBean("yxwRegistry");
					// 注意：ReferenceConfig为重对象,内部封装了与注册中心的连接,以及与服务提供方的连接
					// 引用远程服务
					// 此实例很重,封装了与注册中心的连接以及与提供者的连接,需缓存,否则可能造成内存和连接泄漏
					Class<?> classObj = Class.forName(vo.getInterfaceClass());
					referenceConfig = new ReferenceConfig<YxwCommService>();
					referenceConfig.setApplication(application);
					// 多个注册中心可以用setRegistries()
					referenceConfig.setRegistry(registry);
					referenceConfig.setInterface(classObj.getClass());
					referenceConfig.setVersion(vo.getVersion());
					if (StringUtils.isNotEmpty(vo.getServiceUrl())) {
						referenceConfig.setUrl(vo.getServiceUrl());
					}
				}
				//和本地bean一样使用xxxService 注意：此代理对象内部封装了所有通讯细节,对象较重,需缓存复用
//				yxwService = referenceConfig.get();
				yxwCommService = referenceConfig.get();
				consumerServiceMap.put("yxwCommService", yxwCommService);
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "服务手动注册成功!");
			} else {
				resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "服务已注册,无须注册!");
			}
		}
		resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
		return new RespBody(Status.OK, resMap);
	}

	@ResponseBody
	@RequestMapping(value = "/provider/registry", method = RequestMethod.POST)
	public RespBody providerRegistry(HttpServletRequest request, RegistryParamVo vo) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		// 服务实现
		//XxxService xxxService = new XxxServiceImpl();

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName("xxx");

		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("10.20.130.230:9090");
		registry.setUsername("aaa");
		registry.setPassword("bbb");

		// 服务提供者协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(12345);
		protocol.setThreads(200);

		// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
		// 服务提供者暴露服务配置
		// 此实例很重需自行缓存,否则可能造成内存和连接泄漏
		/*
		ServiceConfig<XxxService> service = new ServiceConfig<XxxService>();
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		service.setProtocol(protocol); // 多个协议可以用setProtocols()
		service.setInterface(XxxService.class);
		service.setRef(xxxService);
		service.setVersion("1.0.0");
		
		// 暴露及注册服务
		service.export();
		*/
		return new RespBody(Status.OK, resMap);
	}
}
