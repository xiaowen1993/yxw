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

import com.yxw.platform.privilege.vo.AuthorizationVo;

/**
 * @Package: com.yxw.platform.privilege.subject
 * @ClassName: SubjectFactory
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月8日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SubjectFactory {
	public static Subject getSubject(AuthorizationVo vo) {
		return new DefaultSubjectImpl(vo);
	}
}
