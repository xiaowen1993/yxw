/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.usercenter.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: DeleteCacheRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CardCacheRunnable implements Runnable {
	private BaseDatasManager baseManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private Logger logger = LoggerFactory.getLogger(CardCacheRunnable.class);
	private MedicalCard medicalCard;
	private String opType;

	public CardCacheRunnable(MedicalCard medicalCard, String opType) {
		super();
		this.medicalCard = medicalCard;
		this.opType = opType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			baseManager.setMedicalCardToCache(medicalCard, opType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("将就诊卡信息放入缓存错误, 错误信息： {}, 错误原因： {}", new Object[] { e.getMessage(), e.getCause() });
		}
	}
}
