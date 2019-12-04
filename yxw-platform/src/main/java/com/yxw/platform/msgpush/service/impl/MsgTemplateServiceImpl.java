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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateFunction;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.MsgPushHospitalVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.datas.manager.MsgTempManager;
import com.yxw.platform.msgpush.dao.MsgTemplateContentDao;
import com.yxw.platform.msgpush.dao.MsgTemplateDao;
import com.yxw.platform.msgpush.dao.MsgTemplateFunctionDao;
import com.yxw.platform.msgpush.service.MsgTemplateService;

/**
 * 模板消息service实现类
 * 
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgTemplateServiceImpl
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
public class MsgTemplateServiceImpl extends BaseServiceImpl<MsgTemplate, String> implements MsgTemplateService {

	@Autowired
	private MsgTemplateDao msgTemplateDao;
	@Autowired
	private MsgTemplateContentDao msgTemplateContentDao;
	@Autowired
	private BaseDatasManager baseDatasManager;
	@Autowired
	private MsgTempManager msgManager;
	@Autowired
	private MsgTemplateFunctionDao msgTemplateFunctionDao;

	@Override
	protected BaseDao<MsgTemplate, String> getDao() {
		return this.msgTemplateDao;
	}

	//TODO appCode暂时没加上 
	@Override
	public String add(MsgTemplate entity) {
		String appId = null;

		String appCode = ModeTypeUtil.getPlatformCode(entity.getSource());

		HospIdAndAppSecretVo vo = baseDatasManager.getHospitalEasyHealthAppInfo(entity.getHospitalId(), appCode);
		appId = vo.getAppId();

		entity.setAppId(appId);
		msgTemplateDao.add(entity);
		for (MsgTemplateContent msgTemplateContent : entity.getMsgTemplateContents()) {
			msgTemplateContent.setTemplateId(entity.getId());
			msgTemplateContentDao.add(msgTemplateContent);
		}
		List<MsgTemplateFunction> functions = entity.getMsgTemplateFunctions();
		if (functions != null && functions.size() > 0) {
			for (MsgTemplateFunction msgTemplateFunction : functions) {
				msgTemplateFunction.setTemplateId(entity.getId());
				msgTemplateFunctionDao.add(msgTemplateFunction);
			}
		}

		msgManager.updateMsgTemplate(entity);
		return entity.getId();
	}
	
	@Override
	public boolean delTemplate(String id) {
		try {
			MsgTemplate msgTemplate = msgTemplateDao.findById(id);
			if (msgTemplate != null) {
				msgManager.deleteMsgTemplate(msgTemplate);
			}
			
			if (StringUtils.isNotBlank(id)) {
				msgTemplateDao.deleteById(id);
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public void update(MsgTemplate entity) {
		msgTemplateDao.update(entity);
		msgTemplateContentDao.deleteByTemplateId(entity.getId());
		msgTemplateFunctionDao.deleteByTemplateId(entity.getId());
		for (MsgTemplateContent msgTemplateContent : entity.getMsgTemplateContents()) {
			msgTemplateContent.setTemplateId(entity.getId());
			msgTemplateContentDao.add(msgTemplateContent);
		}
		for (MsgTemplateFunction msgTemplateFunction : entity.getMsgTemplateFunctions()) {
			msgTemplateFunction.setTemplateId(entity.getId());
			msgTemplateFunctionDao.add(msgTemplateFunction);
		}
		msgManager.updateMsgTemplate(entity);
	}

	@Override
	public boolean check(Map<String, Serializable> params) {
		String appCode = ModeTypeUtil.getPlatformCode(Integer.valueOf(String.valueOf(params.get("source"))));
		HospIdAndAppSecretVo vo = baseDatasManager.getHospitalEasyHealthAppInfo(String.valueOf(params.get("hospitalId")), appCode);
		return msgManager.existsMsgTemplate(vo.getAppId(), String.valueOf(params.get("code")), String.valueOf(params.get("source")), String.valueOf(params.get("msgTarget")));
	}

	/**
	 * 获取消息模版医院列表
	 * */
	@Override
	public PageInfo<MsgPushHospitalVo> findHospListByPage(Map<String, Object> params, Page<MsgPushHospitalVo> page) {
		return msgTemplateDao.findHospListByPage(params, page);
	}
}
