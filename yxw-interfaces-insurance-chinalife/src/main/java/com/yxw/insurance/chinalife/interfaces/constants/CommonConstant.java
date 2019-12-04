package com.yxw.insurance.chinalife.interfaces.constants;

import java.text.SimpleDateFormat;

import com.yxw.framework.config.SystemConfig;

public class CommonConstant {

	//dev
	//	public static final String WS_URL = "http://157.255.21.5:8081/cfs/services/mdExchange";
	//	public static final String WS_URL = "http://183.6.161.130:9009/cfs/services/mdExchange";
	//	public static final String TOKEN = "48111ef247c298b2b6d48de21359a3ea";
	//	public static final String SENDER_CODE = "AD59F7D76F126F896B0C319AE425A456"; //外接系统编号

	//rc
	//	public static final String WS_URL = "http://157.255.21.5:8082/cfs/services/mdExchange";
	//	public static final String WS_URL = "http://183.6.161.130:7101/cfs/services/mdExchange";
	//	public static final String TOKEN = "5c5d397263d7719e12d7b837895b7007";
	//	public static final String SENDER_CODE = "440101"; //外接系统编号

	//消息接受者的代码
	//	public static final String RECEIVER_CODE = "UNCL";
	//标准的版本号信息
	//	public static final String STANDARD_VERSION_CODE = "1.0.0";
	//业务类型
	//	public static final String BUSINESS_TYPE = "3";

	public static final String WS_URL = SystemConfig.getStringValue("ws_url");
	public static final String TOKEN = SystemConfig.getStringValue("token");
	public static final String RECEIVER_CODE = SystemConfig.getStringValue("receiver_code");
	public static final String SENDER_CODE = SystemConfig.getStringValue("sender_code");
	public static final String STANDARD_VERSION_CODE = SystemConfig.getStringValue("standard_version_code");
	public static final String BUSINESS_TYPE = SystemConfig.getStringValue("business_type");

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String MESSAGE_STATUS_CODE_SUCCESS = "10";
	public static final String BUSINESS_STATUS_SUCCESS = "1";

	public static final String SUCCESS = "0";

	public static final String ERROR = "1";

}
