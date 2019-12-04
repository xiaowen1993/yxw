package com.yxw.solr.constants;

public class OutsideResultConstant {
	/**
	 * 成功
	 */
	public final static int RESULT_CODE_SUCCESS = 1;
	
	/**
	 * 失败
	 */
	public final static int RESULT_CODE_ERROR = 0;
	
	/**
	 * 请求异常
	 */
	public final static int RESULT_CODE_EXCEPTION = -1;
	
	/**
	 * 请求成功，接口已受理
	 */
	public final static int RESULT_CODE_ACCEPT = 2;
	
	/**
	 * 参数不能为空
	 */
	public final static int RESULT_CODE_PARAMS_NONE = 3;
	
	/**
	 * 参数不存在/乱传的参数-参数错误
	 */
	public final static int RESULT_CODE_PARAMS_NOT_EXITST = 4;
	
	/**
	 * 参数格式错误
	 */
	public final static int RESULT_CODE_PARAMS_FORMAT_ERROR = 5;
}
