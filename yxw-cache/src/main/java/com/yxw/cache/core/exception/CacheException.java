/**
 * Copyright© 2014-2016 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2016年4月1日
 * @version 1.0
 */
package com.yxw.cache.core.exception;

/**
 * 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2016年4月1日
 */
public class CacheException extends RuntimeException {
	private static final long serialVersionUID = 1401593546385403720L;

	public CacheException() {
		super();
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
