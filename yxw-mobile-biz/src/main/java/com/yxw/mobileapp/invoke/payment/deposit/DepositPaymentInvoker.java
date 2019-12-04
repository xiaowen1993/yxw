/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年8月14日
 * @version 1.0
 */
package com.yxw.mobileapp.invoke.payment.deposit;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年8月14日  
 */
public class DepositPaymentInvoker {/*

private static final String DEF_FAILURE_MSG = "不符合医院限定,无法预约支付,预约已取消或者预约已作废.";
private static final String DEF_CLINIC_FAILURE_MSG = "不符合医院限定，无法完成改门诊订单的支付。";
//	private static final String DEF_DEPOSIT_FAILURE_MSG = "不符合医院限定，无法完成改押金补缴订单的支付。";
private static Logger logger = LoggerFactory.getLogger(DepositPaymentInvoker.class);
private static RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
private static RegisterService registerSvc = SpringContextHolder.getBean(RegisterService.class);
// private RegisterRecordCache recordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
private static CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
private static VoteService voteService = SpringContextHolder.getBean(VoteService.class);

// 押金补缴回调
private static InpatientService inpatientService = SpringContextHolder.getBean(InpatientService.class);
private static InpatientBizManager inpatientBizManager = SpringContextHolder.getBean(InpatientBizManager.class);

//回调加锁
private static ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

public static void depositPayMent(Object payAsynResponse) {
if (payAsynResponse instanceof WechatPayAsynResponse) {

} else if (payAsynResponse instanceof AlipayAsynResponse) {

} else if (payAsynResponse instanceof UnionpayAsynResponse) {
// TODO 新增

}

if (logger.isDebugEnabled()) {
logger.debug("depositPayMent invoke payAsynResponse: {}", new Object[] { JSON.toJSONString(payAsynResponse) });
}

PayAsynResponse payAsynRsp = (PayAsynResponse) payAsynResponse;

DepositRecord paramRecord = new DepositRecord();
paramRecord.setOrderNo(orderNo);
paramRecord.setOpenId(openId);
DepositRecord record = inpatientService.findRecord(orderNo, openId);

//重复回调  不处理
if (record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == RegisterConstant.IS_HAD_CALLBACK_YES) {
return;
}

record.setIsHadCallBack(BizConstant.IS_HAD_CALLBACK_YES);

if (!isPaySuccess) {
// 支付失败，不需要调用接口，只要把数据写入缓存即可
// 记录错误信息
record.setFailureMsg(caseMsg);
// 没有发生异常
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
// 是否处理成功：处理失败了
record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
StringBuilder sbLog = new StringBuilder();
sbLog.append("HandleCount:" + record.getHandleCount());
sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
sbLog.append(",HandleMsg:第三方支付失败，订单状态还是未支付。");
record.setHandleLog(handleLog + sbLog.toString());
// 数据写入缓存
Thread cacheThread =
new Thread(new SaveAndUpdateDepositCacheRunnable(record, CacheConstant.CACHE_OP_UPDATE),
"CacheRunnable.saveDepositRecord");
cacheThread.start();
// 数据更新到数据库中
inpatientService.updateOrderPayInfo(record);
} else {
// 是否开启退费线程
boolean isStartRefundThread = false;
long currentTime = System.currentTimeMillis();
// 记录第三方支付流水号
record.setAgtOrdNum(tradeNo);
// 设定完成支付的时间
record.setPayTime(currentTime);
// 更新时间
record.setUpdateTime(currentTime);

// 写入His
Map<String, String> params = generatePayDepositOrderParams(record);
Map<String, Object> resultMap = inpatientBizManager.payDeposit(params);

if (logger.isDebugEnabled()) {
logger.debug("order[{}], after call his interface, his return info: {}",
new Object[] { orderNo, JSON.toJSONString(resultMap) });
}

//支付后与his确认是否出现异常
boolean isException = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
if (isException) {
// 出现了异常，查一次已缴费记录列表。
params = generateGetDepositOrderStatusParams(record);
resultMap = inpatientBizManager.getDepositOrderStatus(params);

Boolean isPutExceptionCache = false; // 异常处理流程
isException = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
if (isException) {
isPutExceptionCache = true;
} else {
boolean isSuccess = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
if (isSuccess) {
@SuppressWarnings("unchecked")
List<Deposit> list = (List<Deposit>) resultMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
if (!CollectionUtils.isEmpty(list)) {
// 存在多笔，则取第一笔
Deposit deposit = list.get(0);
// 有返回则
record = addDepositInfo(record, deposit);
record.setPayStatus(OrderConstant.STATE_PAYMENT);
record.setDepositStatus(InpatientConstant.STATE_PAY_SUCCESS);
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);

inpatientService.updateOrderPayInfo(record);

// 门诊缴费成功 消息推送   
logger.info("订单{}支付成功...需要查询状态", record.getOrderNo());
commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_PAY_SUCCESS);
} else {
// 这种情况，接口按理是不会出现的，但是这里还是要标志下。
logger.info("出现了异常，查一次已缴费记录列表， 查询接口，发现奇葩接口数据： 返回成功，但是没有数据");

isPutExceptionCache = true;
}
} else {
record.setDepositStatus(InpatientConstant.STATE_NO_PAY); // 门诊订单状态，支付失败
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO); // 无异常
record.setPayStatus(OrderConstant.STATE_REFUNDING); // 支付状态，要退费
record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS); // 处理成功
Object msgObj = resultMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
if (msgObj == null) {
record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]," + DEF_DEPOSIT_FAILURE_MSG);
record.setFailureMsg(DEF_DEPOSIT_FAILURE_MSG);
} else {

// record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]," + msgObj.toString());
// record.setFailureMsg(msgObj.toString());
// logger.info("failure info : {}", msgObj.toString());

String sMsg = msgObj.toString();
StringBuffer sb = new StringBuffer();
sb.append("[").append(BizConstant.YYYYMMDDHHMMSS.format(new Date())).append("]");
if (sMsg.length() > 100) {
sMsg = sMsg.substring(0, 100);
}
sb.append(sMsg);

record.setHandleLog(sb.toString());
record.setFailureMsg(sMsg);
logger.info("failure info : {}", msgObj.toString());
}
inpatientService.updateOrderPayInfo(record);

// 门诊缴费失败 消息推送 
commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_PAY_FAIL);
logger.info("订单{}支付失败...查询状态失败.需要开启退费子线程...", record.getOrderNo());
//进入退费子线程
isStartRefundThread = true;
}
}

// 异常处理流程
if (isPutExceptionCache) {
//his确认异常处理
record.setDepositStatus(InpatientConstant.STATE_EXCEPTION_HIS);
record.setPayStatus(OrderConstant.STATE_REFUNDING);
record.setHandleCount(0);
String handleLog = record.getHandleLog() == null ? "" : record.getHandleLog();
StringBuilder sbLog = new StringBuilder();
sbLog.append("HandleCount:0,");
sbLog.append("HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + ",");
sbLog.append("HandleMsg:第3方支付成功后调用医院支付确认接口失败,状态变更为医院支付确认异常[STATE_PAYMENT=2],进入轮询流程;");
record.setHandleLog(handleLog + sbLog.toString());
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
inpatientService.updateOrderPayInfo(record);

// 挂号异常消息推送 
// modify by dfw at 2015-08-28, no error msg before the task end.
// commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_PAY_EXP);
}
} else {
boolean isSuccess = (Boolean) resultMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
if (isSuccess) {
// 添加订单号、流水号等
record = addDepositInfo(record, resultMap.get(BizConstant.INTERFACE_MAP_DATA_KEY));

record.setPayStatus(OrderConstant.STATE_PAYMENT);
record.setDepositStatus(InpatientConstant.STATE_PAY_SUCCESS);
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
inpatientService.updateOrderPayInfo(record);

// 门诊缴费成功 消息推送   
logger.info("订单{}支付成功...不需要查询状态...", record.getOrderNo());
commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_PAY_SUCCESS);
} else {
record.setDepositStatus(InpatientConstant.STATE_FAIL_HIS); // 门诊订单状态，支付失败
record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO); // 无异常
record.setPayStatus(OrderConstant.STATE_REFUNDING); // 支付状态，要退费
record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS); // 处理成功
Object msgObj = resultMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
if (msgObj == null) {
record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]," + DEF_DEPOSIT_FAILURE_MSG);
record.setFailureMsg(DEF_DEPOSIT_FAILURE_MSG);
} else {
//record.setHandleLog("[" + BizConstant.YYYYMMDDHHMMSS.format(new Date()) + "]," + msgObj.toString());
// record.setFailureMsg(msgObj.toString());
// logger.info("failure info : {}", msgObj.toString());
String sMsg = msgObj.toString();
StringBuffer sb = new StringBuffer();
sb.append("[").append(BizConstant.YYYYMMDDHHMMSS.format(new Date())).append("]");
if (sMsg.length() > 100) {
sMsg = sMsg.substring(0, 100);
}
sb.append(sMsg);

record.setHandleLog(sb.toString());
record.setFailureMsg(sMsg);
logger.info("failure info : {}", msgObj.toString());
}
inpatientService.updateOrderPayInfo(record);
logger.info("订单{}支付失败...不需要查询状态.需要开启退费子线程...", record.getOrderNo());
// 门诊缴费失败 消息推送
commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_DEPOSIT_PAY_FAIL);
//进入退费子线程
isStartRefundThread = true;
}
}

if (isStartRefundThread) {
logger.info("押金支付成功后与医院确认异常 : 转入押金退费流程");
Thread refundThread = new Thread(new DepositRefundRunnable(record));
refundThread.start();

}
}
}

private Map<String, String> generatePayDepositOrderParams(DepositRecord record) {
Map<String, String> map = new HashMap<String, String>();
map.put("hospitalCode", record.getHospitalCode());
map.put("branchHospitalCode", record.getBranchHospitalCode());
map.put("patientId", record.getInpatientId());
map.put("admissionNo", record.getAdmissionNo());
map.put("inTime", String.valueOf(record.getInTime()));
map.put("psOrdNum", record.getOrderNo());
map.put("agtOrdNum", record.getAgtOrdNum());
map.put("agtCode", ""); // 没有收单机构代码
map.put("payMode", String.valueOf(record.getPayMode()));
map.put("payAmout", record.getPayFee());
map.put("payTime", BizConstant.YYYYMMDDHHMMSS.format(new Date(record.getPayTime())));

// 下面的参数，在有接口的时候，不必传
map.put("leftFee", record.getBalanceBeforePay());
return map;
}

private Map<String, String> generateGetDepositOrderStatusParams(DepositRecord record) {
Map<String, String> map = new HashMap<String, String>();
map.put("hospitalCode", record.getHospitalCode());
map.put("branchHospitalCode", record.getBranchHospitalCode());
map.put("patCardType", record.getCardType().toString());
map.put("patCardNo", record.getCardNo().toString());
// 接口：0所有，1微信，2支付宝。 	掌上医院：1微信，2支付宝
map.put("payMode", record.getPayMode().toString());
map.put("beginDate", "");
map.put("endDate", "");
map.put("psOrdNum", record.getOrderNo());

// 下面的参数，在有接口的时候，不必传
map.put("leftFee", record.getBalanceBeforePay());
map.put("payFee", record.getPayFee());
return map;
}

private DepositRecord addDepositInfo(DepositRecord record, Object payObj) {
if (payObj != null) {
if (payObj instanceof PayDeposit) {
PayDeposit payOrder = (PayDeposit) payObj;
if (StringUtils.isNotBlank(payOrder.getBarCode())) {
record.setBarcode(payOrder.getBarCode());
}
if (StringUtils.isNoneBlank(payOrder.getHisOrdNum())) {
record.setHisOrdNo(payOrder.getHisOrdNum());
}
if (StringUtils.isNoneBlank(payOrder.getReceiptNum())) {
record.setReceiptNum(payOrder.getReceiptNum());
}
if (StringUtils.isNotBlank(payOrder.getBalance())) {
record.setBalanceAfterPay( ( (PayDeposit) payObj ).getBalance());
}
} else if (payObj instanceof Deposit) {
Deposit payOrder = (Deposit) payObj;
if (StringUtils.isNotBlank(payOrder.getBarCode())) {
record.setBarcode(payOrder.getBarCode());
}
if (StringUtils.isNoneBlank(payOrder.getHisOrdNum())) {
record.setHisOrdNo(payOrder.getHisOrdNum());
}
if (StringUtils.isNotBlank(payOrder.getReceiptNum())) {
record.setReceiptNum(payOrder.getReceiptNum());
}
if (StringUtils.isNotBlank(payOrder.getBalance())) {
record.setBalanceAfterPay( ( (Deposit) payObj ).getBalance());
}
if (StringUtils.isNotBlank(payOrder.getSterilisation())) {
record.setFeeDesc( ( (Deposit) payObj ).getSterilisation());
}
}
}

return record;
}
*/
}
