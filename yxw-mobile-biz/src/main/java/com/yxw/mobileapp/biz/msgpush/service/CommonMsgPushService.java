package com.yxw.mobileapp.biz.msgpush.service;

import com.yxw.commons.entity.MsgPushEntity;

public interface CommonMsgPushService {
	public void pushMsg(MsgPushEntity entity, int actionType);
}
