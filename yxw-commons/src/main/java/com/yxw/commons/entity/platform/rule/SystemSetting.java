package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleEdit
 * @Statement: <p>
 *             系统全局规则
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SystemSetting extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -837205562924384298L;

	private int finishUserInfo;

	private String finishUserInfoCode;

	public int getFinishUserInfo() {
		return finishUserInfo;
	}

	public void setFinishUserInfo(int finishUserInfo) {
		this.finishUserInfo = finishUserInfo;
	}

	public String getFinishUserInfoCode() {
		return finishUserInfoCode;
	}

	public void setFinishUserInfoCode(String finishUserInfoCode) {
		this.finishUserInfoCode = finishUserInfoCode;
	}

}