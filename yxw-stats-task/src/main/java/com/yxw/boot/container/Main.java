package com.yxw.boot.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.yxw.boot.container.logback.LogbackContainer;
import com.yxw.boot.container.spring.SpringContainer;

public class Main {

	public static final List<Class> CONTAINER_CLASSES = Lists.newArrayList(SpringContainer.class, LogbackContainer.class);

	public static final String SHUTDOWN_HOOK_KEY = "true";

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	private static final ReentrantLock LOCK = new ReentrantLock();

	private static final Condition STOP = LOCK.newCondition();

	public static void main(String[] args) {
		try {

			if (StringUtils.equals("true", SHUTDOWN_HOOK_KEY)) {
				Runtime.getRuntime().addShutdownHook(new Thread("container-shutdown-hook") {
					@Override
					public void run() {
						for (Class classes : CONTAINER_CLASSES) {
							try {
								Method method = BeanUtils.findDeclaredMethod(classes, "stop", null);
								method.invoke(classes.newInstance(), new String[] {});

								logger.info(classes.getClass().getSimpleName() + " stopped!");
							} catch (Throwable t) {
								logger.error(t.getMessage(), t);
							}
							try {
								LOCK.lock();
								STOP.signal();
							} finally {
								LOCK.unlock();
							}
						}
					}
				});
			}

			for (Class classes : CONTAINER_CLASSES) {
				Method method = BeanUtils.findDeclaredMethod(classes, "start", null);
				method.invoke(classes.newInstance(), new String[] {});

				logger.info(classes.getClass().getSimpleName() + " started!");
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			System.exit(1);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			System.exit(1);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			System.exit(1);
		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			System.exit(1);
		}

		try {
			LOCK.lock();
			STOP.await();
		} catch (InterruptedException e) {
			logger.warn("system service server stopped, interrupted by other thread!", e);
		} finally {
			LOCK.unlock();
		}
	}
}
