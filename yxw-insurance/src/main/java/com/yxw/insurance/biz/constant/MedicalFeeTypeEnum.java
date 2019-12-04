package com.yxw.insurance.biz.constant;

public enum MedicalFeeTypeEnum {
		
	西药("01"),
	西药费("01"),
	中成药费("02"),
	中草药费("03"),
	检查费("04"),
	心电图("04"),
	胃镜("04"),
	心监护("04"),
	其他检查("04"),
	专科检查("04"),
	CT费("05"),
	B超("07"),
	其他治疗费("08"),
	处置治疗("08"),
	治疗费("08"),
	注射费("08"),
	免疫检验("09"),
	其他检验("09"),
	生化检验("09"),
	病理化验("09"),
	化验费("09"),
	常规检验与寄生虫("09"),
	普通检验("09"),
	专科麻醉("10"),
	手术费("10"),
	麻醉费("10"),
	放射费("12"),
	卫生材料("18"),
	材料费("18"),
	其它费("19"),
	诊察费("19"),
	诊金("19"),
	门诊诊查费("19"),
	护理费("20"),
	一次性材料("21");

		private String typeNum;
		private String feeType;
	
		MedicalFeeTypeEnum(String typeNum) {
		   this.typeNum = typeNum;
		}
	
	
		public static String getMedicalFeeType(String feeType){
			 for(MedicalFeeTypeEnum i : MedicalFeeTypeEnum.values()){
				    if(feeType.equals(i.name())){
				    	 return i.getTypeNum();
				    }
			 }
			 return getMedicalFeeType("其他费用");
		}
		
		public String getFeeType() {
			return feeType;
		}


		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		
		public String getTypeNum() {
			return typeNum;
		}
	
	
		public void setTypeNum(String typeNum) {
			this.typeNum = typeNum;
		}
	
	  
	  
}
