package com.yxw.commons.entity;

import com.yxw.base.entity.BaseEntity;
import com.yxw.commons.vo.MessagePushParamsVo;

/**
 * @Package: com.yxw.mobileapp.common.entity
 * @ClassName: MsgPushEntity
 * @Statement: <p>消息推送类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class MsgPushEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2396831268481366046L;

	public abstract MessagePushParamsVo convertMessagePushParams();

}
