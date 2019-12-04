package com.yxw.solr.vo.rebuild;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RebuildRequest implements Serializable {

	private static final long serialVersionUID = -3338033812641372113L;
	
	// 业务编码(使用abs): 1挂号 2门诊 3住院
	private Integer bizCode;
	
	// platform/branchCode -- 由前端拼好给到我们这边，就类似调用hospitalService.getHospitalInfos方法一样
	private Map<String, List<String>> hospitalInfosMap = new HashMap<>();
	
	private String hospitalCode;
	
	private String beginDate;

	public Integer getBizCode() {
		return bizCode;
	}

	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public Map<String, List<String>> getHospitalInfosMap() {
		return hospitalInfosMap;
	}

	public void setHospitalInfosMap(Map<String, List<String>> hospitalInfosMap) {
		this.hospitalInfosMap = hospitalInfosMap;
	}


}
