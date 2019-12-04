package com.yxw.platform.terminal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.terminal.Terminal;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.terminal.dao.TerminalDao;
import com.yxw.platform.terminal.service.TerminalService;

@Service(value = "terminalService")
public class TerminalServiceImpl extends BaseServiceImpl<Terminal, String> implements TerminalService {

	@Autowired
	private TerminalDao terminalDao;
	
	@Override
	public List<Terminal> findByHospitalId(String hospitalId) {
		return terminalDao.findByHospitalId(hospitalId);
	}

	@Override
	protected BaseDao<Terminal, String> getDao() {
		return terminalDao;
	}


}
