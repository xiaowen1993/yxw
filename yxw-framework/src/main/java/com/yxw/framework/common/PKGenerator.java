/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-2</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common;

import java.util.UUID;

/**
 * @Package: com.yxw.framework.common
 * @ClassName: PKGenerator
 * @Statement: <p>
 *             主键ID 生成器
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-2
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PKGenerator {
	/**
	 * 生成数据库主键ID值
	 * 
	 * @return
	 */
	public static String generateId() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	public static void main(String[] args) {
		for (int i = 0; i < 42; i++) {
			System.out.println(generateId());
		}

	}

}
