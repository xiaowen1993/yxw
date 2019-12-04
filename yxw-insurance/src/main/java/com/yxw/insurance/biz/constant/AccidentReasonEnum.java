package com.yxw.insurance.biz.constant;

/**
 * 出险原因
 * @author Administrator
 *
 */
public enum AccidentReasonEnum {
		
	
		DISEASE("疾病", "1"),
		ACCIDENT("意外", "2");
	
	  private String status;
	  private String index;

	  AccidentReasonEnum(String status, String index) {
	    this.index = index;
	    this.status = status;
	  }


	  public static String getStatus(String index){
		    for(AccidentReasonEnum i : AccidentReasonEnum.values()){
			      if(index.equals(i.getIndex())){
			    	  	return i.getStatus();
			      }
		    }
		    return "";
		  }


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}


	
	  
	  
}
