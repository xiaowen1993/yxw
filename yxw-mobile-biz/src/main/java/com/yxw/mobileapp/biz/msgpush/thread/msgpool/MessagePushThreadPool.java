package com.yxw.mobileapp.biz.msgpush.thread.msgpool;

import com.yxw.framework.common.threadpool.FixedThreadPoolExecutor;

public class MessagePushThreadPool {
	/**
	* 消息推送线程池
	*/
	public static FixedThreadPoolExecutor messageThreadPool = new FixedThreadPoolExecutor(10, "messagepush");

	/**
	 * 对外接口消息推送线程池
	 */
	public static FixedThreadPoolExecutor outsideMsgThreadPool = new FixedThreadPoolExecutor(10, 10000, "outsidemessagepush");
}
