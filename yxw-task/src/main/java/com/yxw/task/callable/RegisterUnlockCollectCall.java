package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.task.vo.RegisterTaskResult;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HospitalBaseInfoCollectCall
 * @Statement: <p>
 *             号源解锁  轮询采集
 *             </p>
 * @JDK version used:
 * @Author: Yuce
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterUnlockCollectCall implements Callable<RegisterTaskResult> {
	private static Logger logger = LoggerFactory.getLogger(RegisterUnlockCollectCall.class);
	/**
	* 请求成功代码
	*/
	public static final String RES_SUCCESS_CODE = "0";

	/**
	 * 请求失败代码
	 */
	public static final String RES_FAILURE_CODE = "-1";

	/**
	 * 医院接口 对应的spring id
	 */
	private String interfaceId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 医院id
	 */
	private String hospitalId;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	//	private YxwService yxwService = SpringContextHolder.getBean(interfaceId);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	//	private RegisterRecordCache registerRecordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
	private RegisterService recordSvc = SpringContextHolder.getBean(RegisterService.class);
	private RegisterBizManager regBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public RegisterUnlockCollectCall(String hospitalId, String hospitalCode, String hospitalName, String branchHospitalCode,
			String interfaceId) {
		super();
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.interfaceId = interfaceId;
		this.branchHospitalCode = branchHospitalCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public RegisterTaskResult call() {

		RegisterTaskResult result = new RegisterTaskResult();
		result.setHospitalId(hospitalId);
		result.setHospitalName(hospitalName);
		result.setInterfaceId(interfaceId);
		result.setHospitalCode(hospitalCode);
		result.setBranchHospitalCode(branchHospitalCode);

		List<Object> params = new ArrayList<Object>();

		if (StringUtils.isNotBlank(interfaceId)) {
			if (StringUtils.isNotEmpty(hospitalCode)) {
				//判断
				String psOrdNum = null;
				try {

					params.add(hospitalCode);
					int num = serveComm.count(CacheType.REGISTER_RECORD_CACHE, "getNeedUnLockRegNumFormCache", params);
					//					long num = registerRecordCache.getNeedUnLockRegNumFormCache(hospitalCode);

					if (num != 0) {
						int loop = 10;
						if (num < 10) {
							loop = (int) num;
						}
						for (int i = 0; i < loop; i++) {
							//得到需要解锁号源(取消挂号)的记录 每次任务只处理链表的头部元素
							SimpleRecord record = null;
							params.clear();
							params.add(hospitalCode);
							List<Object> results = serveComm.get(CacheType.REGISTER_RECORD_CACHE, "getNeedUnLockRegFormCache", params);
							if (!CollectionUtils.isEmpty(results)) {
								record = (SimpleRecord) results.get(0);
							}

							if (record != null) {
								logger.info("发现需解锁订单,订单号:{},医院Code:{}", record.getOrderNo(), record.getHospitalCode());
								//(避免出现用户在支付的操作中时挂号记录超时   而支付超时轮询先完成) 故等待45秒 
								//Thread.sleep(45000);
								//查询支付状态是否为未支付
								boolean isSuccess = true;

								RegisterRecord regRecord = recordSvc.findRecordFromDBByOrderNoOpenId(record.getOrderNo());
								logger.info("{},regRecord.getOnlinePaymentType:{}", regRecord.getOrderNo(),
										regRecord.getOnlinePaymentType());
								if (regRecord != null && regRecord.getPayStatus() == OrderConstant.STATE_NO_PAYMENT
										&& regRecord.getOnlinePaymentType() == BizConstant.PAYMENT_TYPE_MUST) {
									record.setExecCount(record.getExecCount() + 1);

									Integer regType = record.getRegType();
									if (regType == null) {
										regType = RegisterConstant.REG_TYPE_APPOINTMENT;
									}

									String cancleMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));

									String hisOrdNum = record.getHisOrdNo();
									psOrdNum = record.getOrderNo();
									Map<String, Object> interfaceMap = null;
									logger.info(" 支付超时未交费，调用医院取消接口释放号源，订单号:{}", psOrdNum);
									if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
										interfaceMap =
												regBizManager.cancelCurRegister(hospitalCode, branchHospitalCode, cancleMode, hisOrdNum,
														psOrdNum);
									} else {
										interfaceMap =
												regBizManager.cancelRegister(hospitalCode, branchHospitalCode, cancleMode, hisOrdNum,
														psOrdNum);
									}

									Boolean isException = (Boolean) interfaceMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
									if (isException) {
										isSuccess = false;
										logger.error("解锁轮询程序解锁号源失败,isException is true 订单号:{},原因:{}",
												new Object[] { psOrdNum, JSON.toJSONString(interfaceMap) });
									} else {
										String resCode = interfaceMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
										if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
											//成功解锁
											isSuccess = true;
											logger.error("解锁轮询程序解锁号源成功,订单号:{}", psOrdNum);
										} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(resCode)) {
											//不符合医院限定,无法解锁
											isSuccess = true;
											logger.error("解锁轮询程序解锁号源失败,订单号:{},原因:{}",
													new Object[] { psOrdNum, JSON.toJSONString(interfaceMap) });
										} else {
											//解锁失败
											isSuccess = false;
											logger.error("解锁轮询程序解锁号源失败,订单号:{},原因:{}",
													new Object[] { psOrdNum, JSON.toJSONString(interfaceMap) });
										}
									}

									StringBuilder sbLog = new StringBuilder();
									sbLog.append("ExecCount:" + record.getExecCount());
									sbLog.append(";支付超时取消(time):" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
									sbLog.append(",支付超时取消(interfaceMap):" + JSON.toJSONString(interfaceMap));
									sbLog.append(",支付超时取消(结果):" + isSuccess);
									regRecord.setHandleLog(regRecord.getHandleLog() + sbLog.toString());
									recordSvc.updateStatusForPay(regRecord);

								} else {
									logger.info("缓存队列中发现需解锁订单,但是在数据库中查找到该订单已经支付,订单号:{},医院Code:{},订单支付状态:{}", record.getOrderNo(),
											record.getHospitalCode(), regRecord.getPayStatus());
								}

								if (isSuccess) {
									result.setTaskResult(record);
								} else {
									if (record.getExecCount() < 3) {
										//异常或者解锁失败的  解锁次数小于3的 重新放回解锁缓存队列
										logger.error("解锁轮询程序解锁号源失败,订单号:{},解锁次数小于3的 重新放回解锁缓存队列", psOrdNum);
										//registerRecordCache.setNeedUnLockRegToCache(record);
										params.clear();
										params.add(record);
										serveComm.set(CacheType.REGISTER_RECORD_CACHE, "setNeedUnLockRegToCache", params);
									}
								}
							}
						}

					}
				} catch (Exception e) {
					logger.error("解锁轮询程序解锁号源失败,程序运行异常,订单号:{},原因:{}", psOrdNum, e.getMessage());
				}
			}
		}
		return result;

	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}
}
