/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.smarttriage.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.utils
 * @ClassName: PartUtils
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月23日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PartUtils {
	public static final Map<String, Object> partMap = new HashMap<String, Object>();
	static {
		partMap.put("1", "四肢");
		partMap.put("2", "口腔");
		partMap.put("3", "面部");
		partMap.put("4", "臀部");
		partMap.put("5", "腰部");
		partMap.put("6", "会阴");
		partMap.put("7", "皮肤");
		partMap.put("8", "上肢");
		partMap.put("9", "颈部");
		partMap.put("10", "五官");
		partMap.put("11", "眼");
		partMap.put("12", "全身");
		partMap.put("13", "背部");
		partMap.put("14", "下肢");
		partMap.put("15", "颅部");
		partMap.put("16", "胸部");
		partMap.put("17", "腹部");
	}
}
