/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-2</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.spring.ext.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;

/**
 * @Package: com.yxw.framework.common.spring.ext.config
 * @ClassName: YxwPropertyPlaceholderConfigurer
 * @Statement: <p>扩展PropertyPlaceholderConfigurer 使应用中能使用spring的文件解析类库 </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-2
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class YxwPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	public YxwPropertyPlaceholderConfigurer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return a merged Properties instance containing both the
	 * loaded properties and properties set on this FactoryBean.
	 */
	public Properties mergeProperties() throws IOException {
		Properties result = new Properties();

		if (this.localOverride) {
			// Load properties from file upfront, to let local properties override.
			loadProperties(result);
		}

		if (this.localProperties != null) {
			for (Properties localProp : this.localProperties) {
				CollectionUtils.mergePropertiesIntoMap(localProp, result);
			}
		}

		if (!this.localOverride) {
			// Load properties from file afterwards, to let those properties override.
			loadProperties(result);
		}

		return result;
	}

	public Map<String, String> convertPropsToMap(java.util.Properties props) {
		Map<String, String> propsMap = new HashMap<String, String>();
		Enumeration<?> propertyNames = props.propertyNames();
		do {
			if (!propertyNames.hasMoreElements()) {
				break;
			}
			java.lang.String propertyName = (java.lang.String) propertyNames.nextElement();
			java.lang.String propertyValue = props.getProperty(propertyName);
			propsMap.put(propertyName, propertyValue);

		} while (true);
		return propsMap;
	}
}
