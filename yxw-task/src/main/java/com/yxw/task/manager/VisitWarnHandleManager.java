package com.yxw.task.manager;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;

/**
 * 就诊通知提示
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: VisitWarnManager
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-10-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class VisitWarnHandleManager {

	private CommonMsgPushService commonMsgPushService = SpringContextHolder.getBean(CommonMsgPushService.class);

	/**
	 * 处理就诊当天的提醒
	 * @param hospitalCode
	 */
	public void handleCurDateVisitWarn(String hospitalCode, List<RegisterRecord> records) {
		if (!CollectionUtils.isEmpty(records)) {
			for (RegisterRecord record : records) {
				commonMsgPushService.pushMsg(record, MessageConstant.ACTION_TYPE_CURDAY_VISIT);
			}
		}
	}

	/**
	 * 处理就诊前一天的提醒
	 * @param hospitalCode
	 */
	public void handlePreDateVisitWarn(String hospitalCode, List<RegisterRecord> records) {
		if (!CollectionUtils.isEmpty(records)) {
			for (RegisterRecord record : records) {
				commonMsgPushService.pushMsg(record, MessageConstant.ACTION_TYPE_PREDAY_VISIT);
			}
		}
	}

}
