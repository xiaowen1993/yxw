package com.yxw.easyhealth.datas.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.intelligent.waiting.ExamineQueue;
import com.yxw.interfaces.vo.intelligent.waiting.ExamineQueueRequest;
import com.yxw.interfaces.vo.intelligent.waiting.MZQueue;
import com.yxw.interfaces.vo.intelligent.waiting.MZQueueRequest;
import com.yxw.interfaces.vo.intelligent.waiting.MedicineQueue;
import com.yxw.interfaces.vo.intelligent.waiting.MedicineQueueRequest;

public class QueuesBizManager {
	private Logger logger = LoggerFactory.getLogger(QueuesBizManager.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@SuppressWarnings("unchecked")
	public List<ExamineQueue> getExamineQueue(Map<String, String> params) {
		List<ExamineQueue> queues = null;
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

			ExamineQueueRequest request = new ExamineQueueRequest();
			request.setBranchCode(params.get("branchHospitalCode").toString());
			request.setPatCardNo(params.get("patCardNo").toString());
			request.setPatCardType(params.get("patCardType").toString());

			Request req = new Request();
			req.setServiceId(interfaceId);
			req.setMethodName("getExamineQueue");
			req.setInnerRequest(request);

			Response response = yxwCommService.invoke(req);

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				queues = (List<ExamineQueue>) response.getResult();
				if (queues == null) {
					queues = new ArrayList<ExamineQueue>();
					logger.info("patCardNo[{}], getExamineQueue has nothing return.", params.get("patCardNo"));
				}
			}

			// 测试
			/*			queues = new ArrayList<>();
						ExamineQueue queue1 = new ExamineQueue();
						queue1.setCheckName("测试1");
						queue1.setCurrentNum("10010");
						queue1.setDeptLocation("东区一楼");
						queue1.setDeptName("测试科室1");
						queue1.setDutyTime("2012-12-12 12:12:12");
						queue1.setFrontNum("9");
						queue1.setSerialNum("10018");
						queues.add(queue1);

						ExamineQueue queue2 = new ExamineQueue();
						queue2.setCheckName("测试2");
						queue2.setCurrentNum("911");
						queue2.setDeptLocation("东区按二楼");
						queue2.setDeptName("测试科室2");
						queue2.setDutyTime("2012-12-12 12:12:12");
						queue2.setFrontNum("9");
						queue2.setSerialNum("10018");
						queues.add(queue2);*/
		} else {
			logger.error("getExamineQueue error. reason: interfaceId is null");
		}
		return queues;
	}

	@SuppressWarnings("unchecked")
	public List<MZQueue> getMzQueue(Map<String, String> params) {
		List<MZQueue> queues = null;
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

			MZQueueRequest request = new MZQueueRequest();
			request.setBranchCode(params.get("branchHospitalCode").toString());
			request.setPatCardNo(params.get("patCardNo").toString());
			request.setPatCardType(params.get("patCardType").toString());

			Request req = new Request();
			req.setServiceId(interfaceId);
			req.setMethodName("getMZQueue");
			req.setInnerRequest(request);

			Response response = yxwCommService.invoke(req);

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				queues = (List<MZQueue>) response.getResult();
				if (queues == null) {
					queues = new ArrayList<MZQueue>();
					logger.info("patCardNo[{}], getMzQueue has nothing return.", params.get("patCardNo"));
				}
			}

			// 测试
			/*queues = new ArrayList<>();
			MZQueue queue1 = new MZQueue();
			queue1.setCurrentNum("11121");
			queue1.setDeptLocation("西区1楼");
			queue1.setDeptName("神经科");
			queue1.setDoctorName("刘大棒子");
			queue1.setFrontNum("21213");
			queue1.setSerialNum("341421");
			queue1.setVisitTime("2018-10-10 22:22:22");
			queues.add(queue1);

			MZQueue queue2 = new MZQueue();
			queue2.setCurrentNum("11121");
			queue2.setDeptLocation("西区2楼");
			queue2.setDeptName("五官科");
			queue2.setDoctorName("陈大棒子");
			queue2.setFrontNum("21213");
			queue2.setSerialNum("341421");
			queue2.setVisitTime("2018-10-10 22:22:22");
			queues.add(queue2);*/
		} else {
			logger.error("getMzQueue error. reason: interfaceId is null");
		}
		return queues;
	}

	@SuppressWarnings("unchecked")
	public List<MedicineQueue> getMedicineQueue(Map<String, String> params) {
		List<MedicineQueue> queues = null;
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

			MedicineQueueRequest request = new MedicineQueueRequest();
			request.setBranchCode(params.get("branchHospitalCode").toString());
			request.setPatCardNo(params.get("patCardNo").toString());
			request.setPatCardType(params.get("patCardType").toString());

			Request req = new Request();
			req.setServiceId(interfaceId);
			req.setMethodName("getMedicineQueue");
			req.setInnerRequest(request);

			Response response = yxwCommService.invoke(req);

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				queues = (List<MedicineQueue>) response.getResult();
				if (queues == null) {
					queues = new ArrayList<MedicineQueue>();
					logger.info("patCardNo[{}], getExamineQueue has nothing return.", params.get("patCardNo"));
				}
			}

			/*queues = new ArrayList<>();
			MedicineQueue queue1 = new MedicineQueue();
			queue1.setCurrentNum("213421");
			queue1.setFrontNum("2134123");
			queue1.setGetTime("2017-11-11 11:11:11");
			queue1.setLocaltion("南区一楼");
			queue1.setPharmacy("东西大药房");
			queue1.setSerialNum("312321");
			queues.add(queue1);

			MedicineQueue queue2 = new MedicineQueue();
			queue2.setCurrentNum("213421");
			queue2.setFrontNum("2134123");
			queue2.setGetTime("2017-11-11 11:11:11");
			queue2.setLocaltion("南区一楼");
			queue2.setPharmacy("东西大药房");
			queue2.setSerialNum("312321");
			queues.add(queue2);*/
		} else {
			logger.error("getMedicineQueue error. reason: interfaceId is null");
		}
		return queues;
	}

}
