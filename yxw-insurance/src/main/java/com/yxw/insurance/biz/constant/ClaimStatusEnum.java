package com.yxw.insurance.biz.constant;

/**
 * 理赔状态
 * @author Administrator
 *
 */
public enum ClaimStatusEnum {
		
	
		CLAIMING("申请中", "1"),
		CLAIM_SUCCESS ("已申请", "2"),
		CLAIM_ERROR("无法在线理赔", "3");
	
	  private String status;
	  private String index;

	  ClaimStatusEnum(String status, String index) {
	    this.index = index;
	    this.status = status;
	  }


	  public static String getStatus(String index){
		    for(ClaimStatusEnum i : ClaimStatusEnum.values()){
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
