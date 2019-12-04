/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月14日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->设置代煎配送请求参数
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: FriedAndDeliveredRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月14日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class FriedAndDeliveredRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 2005338435909201367L;
	
	/**
	 * 代煎配送项目集合
	 * @see com.yxw.interfaces.vo.clinicpay.FriedAndDeliveredItem
	 */
	private List<FriedAndDeliveredItem> items;

	public FriedAndDeliveredRequest() {
		super();
	}

	public List<FriedAndDeliveredItem> getItems() {
		return items;
	}

	public void setItems(List<FriedAndDeliveredItem> items) {
		this.items = items;
	}
	
}
