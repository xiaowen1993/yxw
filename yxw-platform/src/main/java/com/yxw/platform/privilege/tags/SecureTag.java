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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.platform.privilege.subject.Subject;
import com.yxw.platform.privilege.subject.SubjectFactory;
import com.yxw.platform.privilege.vo.AuthorizationVo;

/**
 * @Package: com.yxw.platform.privilege.tags
 * @ClassName: SecureTag
 * @Statement: <p>参考shiro SecureTag源码</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月2日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class SecureTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7616761969080101492L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected void verifyAttributes() throws JspException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		verifyAttributes();
		return onDoStartTag();
	}

	public abstract int onDoStartTag() throws JspException;

	protected Subject getSubject() {
		return SubjectFactory.getSubject(getAuthorizationVo());
	}

	protected AuthorizationVo getAuthorizationVo() {
		HttpServletRequest request = ( (ServletRequestAttributes) RequestContextHolder.getRequestAttributes() ).getRequest();
		HttpSession session = request.getSession();
		//HttpSession session = pageContext.getSession();
		AuthorizationVo vo = (AuthorizationVo) session.getAttribute(UserConstant.LOGINED_USER_AUTHORIZATION);
		if (vo == null) {
			vo = new AuthorizationVo("", false);
		}
		return vo;
	}
}
