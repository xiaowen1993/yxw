package com.yxw.insurance.biz.constant;

public enum IdCardTypeEnum {

	身份证("1", "I"), 护照("4", "P"), 军官证("5", "S"), 港澳证("3", "O"), 台湾证("2", "O");
	private String idCardType;
	private String typeNum;

	IdCardTypeEnum(String typeNum, String idCardType) {
		this.idCardType = idCardType;
		this.typeNum = typeNum;
	}

	public static String getIdCardType(String typeNum) {
		for (IdCardTypeEnum i : IdCardTypeEnum.values()) {
			if (typeNum.equals(i.getTypeNum())) {
				return i.getIdCardType();
			}
		}
		return getIdCardType("2");
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getTypeNum() {
		return typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

}
