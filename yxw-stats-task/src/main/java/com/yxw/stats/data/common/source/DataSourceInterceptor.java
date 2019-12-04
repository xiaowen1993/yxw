package com.yxw.stats.data.common.source;

import org.aspectj.lang.JoinPoint;

public class DataSourceInterceptor {

	public static final String YXW_DATA_SOURCE = "yxwDataSqlSessionFactory";

	public static final String YXW_PROJECT_WECHAT_DATA_SOURCE = "yxwProjectWechatSqlSessionFactory";

	public static final String YXW_PROJECT_ALIPAY_DATA_SOURCE = "yxwProjectAlipaySqlSessionFactory";

	public static final String YXW_PLATFORM_DATA_SOURCE = "yxwPlatformSqlSessionFactory";

	public void setYxwDataSource(JoinPoint jp) {

		SqlSessionContentHolder.setContextType(YXW_DATA_SOURCE);

	}

	public void setYxwProjectWechatDataSource(JoinPoint jp) {

		SqlSessionContentHolder.setContextType(YXW_PROJECT_WECHAT_DATA_SOURCE);

	}

	public void setYxwProjectAlipayDataSource(JoinPoint jp) {

		SqlSessionContentHolder.setContextType(YXW_PROJECT_ALIPAY_DATA_SOURCE);

	}

	public void setYxwPlatformDataSource(JoinPoint jp) {

		SqlSessionContentHolder.setContextType(YXW_PLATFORM_DATA_SOURCE);

	}
}
