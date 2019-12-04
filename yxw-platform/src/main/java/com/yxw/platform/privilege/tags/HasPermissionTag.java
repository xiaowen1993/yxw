/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @Package: com.yxw.platform.privilege.tags
 * @ClassName: HasPermissionTag
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月2日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HasPermissionTag extends SecureTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9180958900268780107L;

	private String value = null;

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.tags.SecureTag#onDoStartTag()
	 */
	@Override
	public int onDoStartTag() throws JspException {
		boolean show = showTagBody(getValue());

		return show ? TagSupport.EVAL_BODY_INCLUDE : TagSupport.SKIP_BODY;
	}

	protected boolean showTagBody(String name) {
		return super.getSubject().hasPermission(name);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
