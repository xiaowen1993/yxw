package com.yxw.platform.terminal.service;

import java.util.List;

import com.yxw.commons.entity.platform.terminal.Terminal;
import com.yxw.framework.mvc.service.BaseService;

public interface TerminalService  extends BaseService<Terminal, String> {
	
	public List<Terminal> findByHospitalId(String hospitalId);

}
