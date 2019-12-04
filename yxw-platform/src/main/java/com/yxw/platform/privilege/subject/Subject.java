/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月8日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.subject;

/**
 * @Package: com.yxw.platform.privilege.utils
 * @ClassName: Subject
 * @Statement: <p>权限查检接口</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月8日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface Subject {
	public boolean hasRole(String name);

	public boolean hasPermission(String name);
}
