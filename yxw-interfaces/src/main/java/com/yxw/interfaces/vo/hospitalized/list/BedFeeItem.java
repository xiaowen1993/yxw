/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.hospitalized.list;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->住院费用清单-->清单明细列表
 * @Package: com.yxw.interfaces.entity.hospitalized.list
 * @ClassName: BedFeeItem
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BedFeeItem extends Reserve implements Serializable {

	private static final long serialVersionUID = 4678850932308507985L;

	/**
	 * 费用类别
	 */
	private String costType;
	/**
	 * 费用明细集合
	 */
	private List<Detail> details;
	
	public BedFeeItem() {
		super();
	}

	public BedFeeItem(String costType) {
		super();
		this.costType = costType;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

}
