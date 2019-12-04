package com.yxw.task.callable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.vo.cache.StopRegisterException;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.stopreg.StopReg;
import com.yxw.interfaces.vo.register.stopreg.StopRegRequest;
import com.yxw.mobileapp.biz.outside.service.InterfaceService;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.biz.register.service.StopRegisterRecordService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.task.collect.StopRegisterCollector;
import com.yxw.task.taskitem.StopRegisterTask;
import com.yxw.task.vo.StopRegTask;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HospitalBaseInfoCollectCall
 * @Statement: <p>
 *             停诊处理
 *             </p>
 * @JDK version used:
 * @Author: Yuce
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterCollectCall implements Callable<List<StopRegTask>> {
	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
	private InterfaceService interfaceService = SpringContextHolder.getBean(InterfaceService.class);
	private StopRegisterRecordService stopRegisterRecordService = SpringContextHolder.getBean(StopRegisterRecordService.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	/**
	* 请求成功代码
	*/
	public static final String RES_SUCCESS_CODE = "0";

	/**
	 * 请求失败代码
	 */
	public static final String RES_FAILURE_CODE = "-1";

	/**
	 * 处理业务员 0：查询停诊接口 1：处理停诊业务
	 */
	private Integer dealType;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	/**
	 * 平台code
	 */
	private String appCode;

	/**
	 * 停诊接口查询参数实体
	 */
	private StopRegRequest stopRegRequest;

	/**
	 * 停诊接口返回数据（需要处理的停诊挂号数据）
	 */
	private List<StopRegTask> stopRegTasks;

	/**
	 * 挂号记录查询表名
	 */
	private String hashTableName;

	public StopRegisterCollectCall(Integer dealType, String hospitalCode, String branchHospitalCode, String appCode,
			StopRegRequest stopRegRequest, List<StopRegTask> stopRegTasks, String hashTableName) {
		super();
		this.dealType = dealType;
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.appCode = appCode;
		this.stopRegRequest = stopRegRequest;
		this.stopRegTasks = stopRegTasks;
		this.hashTableName = hashTableName;
	}

	public StopRegisterCollectCall(Integer dealType, String hospitalCode, String branchHospitalCode, String appCode,
			StopRegRequest stopRegRequest) {
		super();
		this.dealType = dealType;
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.appCode = appCode;
		this.stopRegRequest = stopRegRequest;
	}

	public StopRegisterCollectCall(Integer dealType, List<StopRegTask> stopRegTasks, String hashTableName) {
		super();
		this.dealType = dealType;
		this.stopRegTasks = stopRegTasks;
		this.hashTableName = hashTableName;
	}

	public List<StopRegTask> call() {

		List<StopRegTask> result = new ArrayList<StopRegTask>();
		if (dealType.equals(StopRegisterCollector.STOP_REGISTER_QUERY_INTERFACE_DEAL_TYPE)) {//查询停诊接口
			Map<String, Object> resMap = registerBizManager.getStopReg(hospitalCode, stopRegRequest);
			StopRegisterTask.logger.info("StopRegisterTask查询停诊接口,resMap:{}.", com.alibaba.fastjson.JSON.toJSONString(resMap));
			boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			if (isException) {
				StopRegisterException stopRegisterException = new StopRegisterException();
				BeanUtils.copyProperties(stopRegRequest, stopRegisterException);
				stopRegisterException.setHospitalCode(hospitalCode);
				StopRegisterTask.logger.info("hospitalCode:{},查询停诊接口失败,异常写入:{}.", hospitalCode,
						com.alibaba.fastjson.JSON.toJSONString(stopRegisterException));

				List<Object> params = new ArrayList<Object>();
				params.add(branchHospitalCode);
				params.add(stopRegisterException);
				serveComm.set(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "setExceptionStopRegisterToCache", params);

			} else {
				boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
				if (isSuccess) {
					/**
					 * 添加分院code，供分表查询挂号记录时提供分院code。（不同分院号源可能相同）
					 * 2015年8月5日 21:04:21 周鉴斌 添加
					 */
					@SuppressWarnings("unchecked")
					List<StopReg> stopRegs = (List<StopReg>) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
					if (stopRegs != null) {
						for (StopReg stopReg : stopRegs) {
							StopRegTask stopRegTask = new StopRegTask();
							BeanUtils.copyProperties(stopReg, stopRegTask);
							stopRegTask.setBranchHospitalCode(branchHospitalCode);
							result.add(stopRegTask);
						}
					}

					List<Object> params = new ArrayList<Object>();
					params.add(hospitalCode);
					params.add(branchHospitalCode);
					params.add(stopRegRequest.getRegType());
					serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "removeExceptionStopRegisterFromCache", params);
				}
			}
		} else if (dealType.equals(StopRegisterCollector.STOP_REGISTER_SERVICE_DEAL_TYPE)) {
			//停诊业务处理
			serviceDeal();
		}
		return result;

	}

	/**
	 * 停诊业务处理
	 */
	private void serviceDeal() {

		try {
			StopRegisterTask.logger.info("stopRegTasks : {}", JSON.toJSONString(stopRegTasks));
			if (!CollectionUtils.isEmpty(stopRegTasks)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < stopRegTasks.size(); i++) {
					StopRegTask srt = stopRegTasks.get(i);
					sb.append("CONCAT(\"").append(srt.getBranchHospitalCode()).append("\",\"").append(srt.getHisOrdNum()).append("\"),");
				}

				if (StringUtils.isNotBlank(sb.toString())) {
					String condition = sb.substring(0, sb.length() - 1);
					List<RegisterRecord> records = registerService.findStopRegisterRecordForPoll(condition, hashTableName);
					if (StopRegisterTask.logger.isDebugEnabled()) {
						StopRegisterTask.logger.debug("hisOrdNo : {} , hashTableName : {} , condition {} ", condition, hashTableName,
								JSON.toJSONString(records));
					}
					if (!CollectionUtils.isEmpty(records)) {
						List<RegisterRecord> needHandleRecords = new LinkedList<>(records);
						RegisterRecord tempRecord = null;
						Iterator<RegisterRecord> iter = needHandleRecords.iterator();
						while (iter.hasNext()) {
							tempRecord = iter.next();
							if (tempRecord == null || tempRecord.getRegStatus() != RegisterConstant.STATE_NORMAL_HAD) {
								iter.remove();
							}
						}

						if (needHandleRecords.size() > 0) {
							// 停诊单入库
							StopRegisterRecord tempStopRecord = null;
							List<StopRegisterRecord> stopRegisterRecords = new LinkedList<>();
							for (RegisterRecord record : needHandleRecords) {
								tempStopRecord = new StopRegisterRecord();
								BeanUtils.copyProperties(record, tempStopRecord);
								tempStopRecord.setBranchId(record.getBranchHospitalId());
								tempStopRecord.setLaunchTime(System.currentTimeMillis());
								if (record.getPayStatus() == OrderConstant.STATE_PAYMENT) {
									tempStopRecord.setHasTrade((byte) 1);
								}
								stopRegisterRecords.add(tempStopRecord);
							}
							stopRegisterRecordService.batchInsert(stopRegisterRecords);

							for (RegisterRecord record : needHandleRecords) {
								StopRegisterTask.logger.info("轮询停诊订单[{}], 实体: {}.",
										new Object[] { record.getOrderNo(), JSON.toJSONString(record) });
								//commonMsgPushSvc.pushMsg(record, CommonMsgPushService.ACTION_TYPE_STOP_VISIT);
								StopRegisterTask.logger.info("record{} ,开始执行停诊逻辑stopRegisterDealPay", record.getOrderNo());
								interfaceService.stopRegisterDealPay(record);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			StopRegisterTask.logger.error("date : {} serviceDeal is error , hashTableName : {} ",
					new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()), hashTableName });
			e.printStackTrace();
		}
	}

	public List<StopRegTask> getStopRegTasks() {
		return stopRegTasks;
	}

	public void setStopRegTasks(List<StopRegTask> stopRegTasks) {
		this.stopRegTasks = stopRegTasks;
	}

	public String getHashTableName() {
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public StopRegRequest getStopRegRequest() {
		return stopRegRequest;
	}

	public void setStopRegRequest(StopRegRequest stopRegRequest) {
		this.stopRegRequest = stopRegRequest;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
