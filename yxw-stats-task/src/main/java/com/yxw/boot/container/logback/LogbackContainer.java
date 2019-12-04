package com.yxw.boot.container.logback;

import java.io.FileNotFoundException;
import java.net.URL;

import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;

import com.yxw.boot.container.Container;
import com.yxw.framework.common.spring.ext.LogbackConfigurer;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.exception.SystemException;

public class LogbackContainer implements Container {

	public static String LOGBACK_FILE_PATH = SystemConfig.getStringValue("logback_config_path");

	@Override
	public void start() {

		try {
			URL u = ClassLoader.getSystemResource("");
			LogbackConfigurer.initLogging(u.getPath() + LOGBACK_FILE_PATH);
			LoggerFactory.getLogger(LogbackContainer.class).info("using logger: logback");
		} catch (FileNotFoundException e) {
			throw new SystemException("logbak config file is not found.");
		} catch (JoranException e) {
			throw new SystemException("init logback is error.");
		}

	}

	@Override
	public void stop() {
	}
}
