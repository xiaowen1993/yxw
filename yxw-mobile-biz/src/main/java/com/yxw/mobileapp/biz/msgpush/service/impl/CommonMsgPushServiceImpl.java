package com.yxw.mobileapp.biz.msgpush.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.entity.MsgPushEntity;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.vo.MessagePushParamsVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.msgpush.thread.MessagePushRunnable;
import com.yxw.mobileapp.biz.msgpush.thread.msgpool.MessagePushThreadPool;
import com.yxw.mobileapp.common.websocket.YxwMessage;
import com.yxw.mobileapp.common.websocket.YxwMessage.MessageType;
import com.yxw.mobileapp.common.websocket.YxwSocketHandler;

@Service(value = "commonMsgPushService")
public class CommonMsgPushServiceImpl implements CommonMsgPushService {

	private static Logger logger = LoggerFactory.getLogger(CommonMsgPushService.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	/**
	 * 是否推送消息
	 * 
	 * @param entity
	 * @param actionType
	 *            消息推送点 见 CommonMsgPushService.ACTION_TYPE_XXXX 定义
	 */
	public void pushMsg(MsgPushEntity entity, int actionType) {
		if (logger.isDebugEnabled()) {
			logger.debug("pushMsg. entity: {}, actionType=" + actionType, JSON.toJSONString(entity));
		}
		MessagePushParamsVo vo = entity.convertMessagePushParams();

		RulePush rulePush = ruleConfigManager.getRulePushByHospitalId(vo.getHospitalId(), vo.getPlatformType());
		if (rulePush != null) {
			String code = null;
			int isPush = 0;
			switch (actionType) {
			case MessageConstant.ACTION_TYPE_BIND_CARD_SUCCESS:
				isPush = rulePush.getBindCardSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getBindCardSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CREATE_CARD_SUCCESS:
				isPush = rulePush.getCreateCardSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getCreateCardSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_LOCK_RES_SUCCESS:
				isPush = rulePush.getLockResSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getLockResSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_PREDAY_VISIT:
				isPush = rulePush.getPredayVisit();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getPredayVisitCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CURDAY_VISIT:
				isPush = rulePush.getCurdayVisit();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getCurdayVisitCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_ON_DUTY_PAY_SUCCESS:
				isPush = rulePush.getOnDutyPaySuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getOnDutyPaySucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_APPOINT_PAY_SUCCESS:
				isPush = rulePush.getAppointPaySuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getAppointPaySucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_APPOINT_PAY_FAIL:
				isPush = rulePush.getAppointPayFail();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getAppointPayFailCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_APPOINT_PAY_EXP:
				isPush = rulePush.getAppointPayExp();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getAppointPayExpCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CANCEL_ON_DUTY:
				isPush = rulePush.getCancelOnDuty();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getCancelOnDutyCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CANCEL_APPOINTMENT:
				isPush = rulePush.getCancelAppointment();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getCancelAppointmentCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_REFUND_SUCCESS:
				isPush = rulePush.getRefundSuccess();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getRefundSuccessCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_REFUND_FAIL:
				isPush = rulePush.getRefundFail();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getRefundFailCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_REFUND_EXCEPTION:
				isPush = rulePush.getRefundException();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getRefundExceptionCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_STOP_VISIT:
				isPush = rulePush.getStopVisit();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getStopVisitCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_WAIT_VISIT:
				isPush = rulePush.getWaitVisit();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getWaitVisitCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_PAY_SUCCESS:
				isPush = rulePush.getClinicPaySuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getClinicPaySucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_PAY_FAIL:
				isPush = rulePush.getClinicPayFail();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getClinicPayFailCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_PAY_EXP:
				isPush = rulePush.getClinicPayExp();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getClinicPayExpCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_DEPOSIT_PAY_SUCCESS:
				isPush = rulePush.getPayDepositSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getPayDepositSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_DEPOSIT_PAY_FAIL:
				isPush = rulePush.getPayDepositFail();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getPayDepositFailCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_DEPOSIT_PAY_EXP:
				isPush = rulePush.getPayDepositExp();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getPayDepositExpCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_GENERATE_REPORT:
				isPush = rulePush.getGenerateReport();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getGenerateReportCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_REFUND_SUCCESS:
				isPush = rulePush.getClinicRefundSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getClinicRefundSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_PART_REFUND_SUCCESS:
				isPush = rulePush.getClinicPartRefundSuc();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getClinicPartRefundSucCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_CLINIC_SUCCESS_COMMENT:
				isPush = rulePush.getSendComment();
				if (isPush == MessageConstant.IS_NEED_PUSH_MSG_YES) {
					code = rulePush.getSendCommentCode();
				}
				break;
			case MessageConstant.ACTION_TYPE_PERFECT_USER_INFO:
				// isPush = rulePush.getSendComment();
				// if (isPush == IS_NEED_PUSH_MSG_YES) {
				// code = rulePush.getSendCommentCode();
				// }

				// 所有都推，但是需要一个code!!!
				break;
			default:
				break;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("sendMessage: actionType={}, code={}.", new Object[] { actionType, code });
			}

			if (StringUtils.isNotBlank(code)) {
				String[] pushArray = rulePush.getModeArray();
				vo.setTemplateCode(code);
				for (int i = 0; i < pushArray.length; i++) {
					vo.setMsgTarget(pushArray[i]);
					if (!vo.getMsgTarget().equals("0")) {
						MessagePushThreadPool.messageThreadPool.execute(new MessagePushRunnable(vo));

						/**发消息 start*/
						YxwSocketHandler handler = SpringContextHolder.getBean(YxwSocketHandler.class);
						Map<String, Object> contentMap = new HashMap<>();
						contentMap.put("hasUnread", true);
						contentMap.put("unreadCount", 1);

						YxwMessage unreadMsg = new YxwMessage();
						unreadMsg.setMessageType(MessageType.MSGPUSH);
						unreadMsg.setToUser(vo.getOpenId());
						unreadMsg.setFromUser("system");
						unreadMsg.setContent(JSON.toJSONString(contentMap));
						unreadMsg.setPushTime(System.currentTimeMillis());
						handler.sendMessage(unreadMsg);
						/**发消息 end*/
					}
				}
			}
		} else {
			logger.warn("rulePush is null.hospitalId:{},appId:{}");
		}
	}
}
