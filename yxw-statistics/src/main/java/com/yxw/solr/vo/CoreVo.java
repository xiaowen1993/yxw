package com.yxw.solr.vo;

import java.io.Serializable;

import com.yxw.solr.constants.BizConstant;

public class CoreVo implements Serializable {

	private static final long serialVersionUID = -7705684070417886358L;

	private Integer platform;
	
	private Integer bizType;
	
	private String coreName;

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getCoreName() {
		if (platform != -1 && bizType != -1) {
			coreName = BizConstant.bizOrderMap.get(bizType).get(platform);
		} else {
			coreName = "";
		}
		
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object instanceof CoreVo) {
			CoreVo vo = (CoreVo) object;
			if (platform == vo.getPlatform() && bizType == vo.getBizType()) {
				result = true;
			}
		}
		return result;
	}
	
	
}
