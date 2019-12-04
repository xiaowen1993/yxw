/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月26日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.exception;

/**
 * <p>
 * 系统公用的Exception.
 * </P>
 * 
 * @author 申午武
 * 
 */
public class CommonsException extends RuntimeException {
	private static final long serialVersionUID = 1401593546385403720L;

	public CommonsException() {
		super();
	}

	public CommonsException(String message) {
		super(message);
	}

	public CommonsException(Throwable cause) {
		super(cause);
	}

	public CommonsException(String message, Throwable cause) {
		super(message, cause);
	}
}
