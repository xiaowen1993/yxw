package com.yxw.platform.message;

import com.yxw.framework.mvc.controller.RespBody;

public class MixedMeterialRespBody extends RespBody {

	private static final long serialVersionUID = 4898190916325940161L;

	private String id;

	private String editTime;

	public MixedMeterialRespBody(Status status, String id, String editTime) {
		super(status);
		this.id = id;
		this.editTime = editTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

}
