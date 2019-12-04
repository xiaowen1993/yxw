/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.msgpush.MsgLibraryContent;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibrary;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibraryFunction;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.datas.manager.MsgTempManager;
import com.yxw.platform.msgpush.dao.MsgLibraryContentDao;
import com.yxw.platform.msgpush.dao.MsgTemplateLibraryDao;
import com.yxw.platform.msgpush.dao.MsgTemplateLibraryFunctionDao;
import com.yxw.platform.msgpush.service.MsgTemplateLibraryService;

/**
 * 模板库service实现类
 * 
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgTemplateLibraryServiceImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class MsgTemplateLibraryServiceImpl extends BaseServiceImpl<MsgTemplateLibrary, String> implements MsgTemplateLibraryService {

	@Autowired
	private MsgTemplateLibraryDao msgTemplateLibraryDao;
	@Autowired
	private MsgTempManager msgManager;
	@Autowired
	private MsgLibraryContentDao msgLibraryContentDao;
	@Autowired
	private MsgTemplateLibraryFunctionDao msgTemplateLibraryFunctionDao;

	@Override
	protected BaseDao<MsgTemplateLibrary, String> getDao() {
		return this.msgTemplateLibraryDao;
	}

	@Override
	public List<MsgTemplateLibrary> findListBySource(String source) {
		return msgTemplateLibraryDao.findListBySource(source);
	}

	@Override
	public String add(MsgTemplateLibrary entity) {
		msgTemplateLibraryDao.add(entity);
		List<MsgLibraryContent> list = entity.getMsgLibraryContents();
		List<MsgTemplateLibraryFunction> functions = entity.getMsgTemplateLibraryFunctions();
		if (list != null && list.size() > 0) {
			for (MsgLibraryContent msgLibraryContent : list) {
				msgLibraryContent.setTempLibraryId(entity.getId());
				msgLibraryContentDao.add(msgLibraryContent);
			}
		}
		if (functions != null && functions.size() > 0) {
			for (MsgTemplateLibraryFunction msgTemplateLibraryFunction : functions) {
				msgTemplateLibraryFunction.setTempLibraryId(entity.getId());
				msgTemplateLibraryFunctionDao.add(msgTemplateLibraryFunction);
			}
		}
		msgManager.updateMsgTemplateLibrary(entity);
		return entity.getId();
	}

	@Override
	public void update(MsgTemplateLibrary entity) {
		msgTemplateLibraryDao.update(entity);
		msgLibraryContentDao.deleteByTempLibraryId(entity.getId());
		List<MsgLibraryContent> list = entity.getMsgLibraryContents();
		if (list != null && list.size() > 0) {
			for (MsgLibraryContent msgLibraryContent : list) {
				msgLibraryContent.setTempLibraryId(entity.getId());
				msgLibraryContentDao.add(msgLibraryContent);
			}
		}
		msgTemplateLibraryFunctionDao.deleteByTempLibraryId(entity.getId());
		List<MsgTemplateLibraryFunction> functions = entity.getMsgTemplateLibraryFunctions();
		if (list != null && list.size() > 0) {
			for (MsgTemplateLibraryFunction msgTemplateLibraryFunction : functions) {
				msgTemplateLibraryFunction.setTempLibraryId(entity.getId());
				msgTemplateLibraryFunctionDao.add(msgTemplateLibraryFunction);
			}
		}
		msgManager.updateMsgTemplateLibrary(entity);
	}

}
