package com.yxw.platform.terminal.dao;

import java.util.List;

import com.yxw.commons.entity.platform.terminal.Terminal;
import com.yxw.framework.mvc.dao.BaseDao;

public interface TerminalDao extends BaseDao<Terminal, String> {
	
	public List<Terminal> findByHospitalId(String hospitalId);

}
