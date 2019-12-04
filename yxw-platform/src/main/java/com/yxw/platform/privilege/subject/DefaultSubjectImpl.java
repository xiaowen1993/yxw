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

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.platform.privilege.vo.AuthorizationVo;

/**
 * @Package: com.yxw.platform.privilege.subject
 * @ClassName: DefaultSubject
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月8日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class DefaultSubjectImpl implements Subject {
	private AuthorizationVo vo;

	/**
	 * @param vo
	 */
	public DefaultSubjectImpl(AuthorizationVo vo) {
		super();
		this.vo = vo;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.subject.Subject#hasRole(java.lang.String)
	 */
	@Override
	public boolean hasRole(String value) {

		Set<String> roles = vo.getRoleMap().keySet();

		return roles.contains(value);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.subject.Subject#isPermission(java.lang.String)
	 */
	@Override
	public boolean hasPermission(String value) {
		boolean ret = false;

		Map<String, Resource> resourceMap = vo.getResourceMap();
		String _value = value;
		//模糊匹配,仅支持*、?匹配方式
		if (_value.indexOf("*") > 0 || _value.indexOf("?") > 0) {
			_value = _value.replaceAll("\\*", "\\\\w*");
			_value = _value.replaceAll("\\?", "\\\\w{1}");

			Pattern pattern = Pattern.compile(_value);
			for (Map.Entry<String, Resource> entity : resourceMap.entrySet()) {
				//只针对资源类型为链接进行模糊匹配
				if (entity.getValue().getType() == UserConstant.RESOURCE_TYPE_LINK) {
					ret = pattern.matcher(entity.getValue().getAbstr()).matches();
					if (ret) {
						break;
					}
				}
			}
		} else {
			ret = resourceMap.keySet().contains(value);
		}
		return ret;
	}

}
