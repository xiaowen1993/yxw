/**
 * FileLocator.java
 * $Revision$
 */
package com.yxw.task.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @Package: com.yxw.platform.quartz.utils
 * @ClassName: FileLocator
 * @Statement: <p>
 *             配置文件的查找 以及资源文件中属性的获取
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FileLocator {
	private static Logger log = Logger.getLogger(FileLocator.class);

	/**
	 * getPropertyValue 得到资源文件的某个属性值
	 * 
	 * @param fileName
	 *            为相对于classes的路径 也就是src目录下的文件 e.g:获取src目录下的config.properties
	 *            文件中的test对应的值 config文件的目录为project/src/config.properties
	 *            getPropertyValue("config","test") 注：文件一定要是properties为后缀的文件
	 * @param propertyName
	 * @return
	 * @author : Asiainfo R&D deparment(GuangZhou)/Yuce
	 */
	public static String getPropertyValue(String fileName, String propertyName) {
		Locale locale = Locale.getDefault();
		ResourceBundle localeResource = ResourceBundle.getBundle(fileName, locale);
		String propertyValue = localeResource.getString(propertyName);
		if (propertyValue == null)
			log.error("can't find " + propertyName + " in " + fileName);
		return propertyValue;
	}

	/**
	 * getConfigFile
	 * <p>
	 * 得到配置文件
	 * </p>
	 * 
	 * @param fileName
	 *            classpath相对路径文件
	 * @return
	 * @author : Asiainfo R&D deparment(GuangZhou)/Yuce
	 */
	public static File getConfigFile(String fileName) {
		File file = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			classLoader = FileLocator.class.getClassLoader();
		URL fileUrl = classLoader.getResource(fileName);
		if (fileUrl == null) {
			log.error(" in classpath can't  locate file: " + fileName);
		} else {
			file = new File(fileUrl.getFile());
			if (!file.isFile()) {
				log.error(fileUrl.getFile() + " is not file");
				file = null;
			}
		}
		return file;
	}

	/**
	 * getConfigStream 方法
	 * <p>
	 * 获取配置文件的流
	 * </p>
	 * 
	 * @param fileName
	 *            classpath相对路径文件
	 * @return
	 * @author : Asiainfo R&D deparment(GuangZhou)/Yuce
	 */
	public static InputStream getConfigStream(String fileName) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(getConfigFile(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(" in classpath can't  locate file: " + fileName);
		}
		return in;
	}

	/**
	 * getJavaDirFile方法
	 * <p>
	 * 获取与java 同目录的文件
	 * </p>
	 * 
	 * @param clazz
	 * @return
	 */
	public static File getJavaDirFile(Class<?> clazz, String fileName) {
		URL fileUrl = clazz.getResource(fileName);
		File file = new File(fileUrl.getFile());
		if (!file.isFile()) {
			log.error(fileUrl.getFile() + " is not file");
			file = null;
		}
		return file;
	}

	public static String getConfFile(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		URL confURL = classLoader.getResource(fileName);
		if (confURL == null)
			confURL = classLoader.getResource("META-INF/" + fileName);
		if (confURL == null) {

			System.err.println(" in classpath can't  locate file: " + fileName);
			return null;
		} else {
			File file1 = new File(confURL.getFile());
			if (file1.isFile()) {
				System.out.println(" locate file: " + confURL.getFile());
				return confURL.getFile();
			} else {
				System.err.println(" it is not a file: " + confURL.getFile());
				return null;
			}
		}
	}

	public static InputStream getConfStream(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileLocator.class.getClassLoader();
		}
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null)
			stream = classLoader.getResourceAsStream("META-INF/" + fileName);
		if (stream == null) {
			System.err.println("PropsUtil error: cann't find config file:-->" + fileName);
		}
		return stream;
	}

	public static Properties LoadProperties(String fileName) throws Exception {
		Properties prop = new Properties();
		prop.load(getConfStream(fileName));
		return prop;
	}
}
