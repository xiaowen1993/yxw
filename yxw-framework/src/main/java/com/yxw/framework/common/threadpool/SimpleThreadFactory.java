/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年2月12日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * threadFactory简单实现,改写自DefaultThreadFactory,使线程具有更有意义的名称
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年2月12日
 */

public class SimpleThreadFactory implements ThreadFactory {
	protected static Logger logger = Logger.getLogger(SimpleThreadFactory.class);
	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	public SimpleThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = ( s != null ) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}

	public SimpleThreadFactory(String threadPoolName) {
		SecurityManager s = System.getSecurityManager();
		group = ( s != null ) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = threadPoolName + "-" + poolNumber.getAndIncrement() + "-thread-";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}
