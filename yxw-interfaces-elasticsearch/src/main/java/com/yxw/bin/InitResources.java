/**
 * Copyright© 2014-2018 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2018年1月11日
 * @version 1.0
 */
package com.yxw.bin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.yxw.framework.config.SystemConfig;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2018年1月11日  
 */
public class InitResources {

	public static void main(String[] args) {
		try {

			URL u = ClassLoader.getSystemResource("");
			Collection<File> listFiles = FileUtils.listFiles(FileUtils.toFile(u), FileFilterUtils.suffixFileFilter("properties"), null);

			Collection<File> targetlistFiles = Collections2.filter(listFiles, new Predicate<File>() {
				@Override
				public boolean apply(File file) {
					boolean status = false;
					String release = SystemConfig.getStringValue("release");

					if (release.contains("dev")) {
						if (file.getName().contains("_" + release)) {
							status = true;
						} else {
							if (file.getName().contains("_rc")) {
								status = false;
							} else {
								status = true;
							}
						}
					} else {
						if (file.getName().contains("_" + release)) {
							status = true;
						} else {
							if (file.getName().contains("_dev")) {
								status = false;
							} else {
								status = true;
							}

						}
					}

					return status;
				}
			});

			loadProperties(targetlistFiles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadProperties(Collection<File> listFiles) throws IOException {
		if (listFiles == null) {
			return;
		}
		for (File file : listFiles) {

			FileInputStream fileInputStream = FileUtils.openInputStream(file);

			Properties prop = new Properties();
			prop.load(fileInputStream);

			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();

				SystemConfig.put(key, prop.getProperty(key));
			}

			fileInputStream.close();
		}
	}
}
