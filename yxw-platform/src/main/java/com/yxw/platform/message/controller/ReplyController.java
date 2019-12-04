package com.yxw.platform.message.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.message.Keyword;
import com.yxw.commons.entity.platform.message.Meterial;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.RuleRespBody;
import com.yxw.platform.message.service.KeywordService;
import com.yxw.platform.message.service.MeterialService;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.message.service.ReplyService;
import com.yxw.platform.message.service.RuleService;
import com.yxw.platform.vo.MsgHospitalVO;

/**
 * 消息回复管理控制器
 * 
 * @author luob
 * @version 1.0
 * */
@Controller
@RequestMapping("/message/reply")
public class ReplyController extends BaseController<Reply, String> {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private RuleService ruleService;

	@Autowired
	private KeywordService keywordService;

	@Autowired
	private MeterialService meterialService;

	@Autowired
	private MixedMeterialService mixedMeterialService;

	@Override
	protected BaseService<Reply, String> getService() {
		return replyService;
	}

	/**
	 * 获取消息管理医院列表
	 * */
	@RequestMapping(params = "method=list")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize, String search, ModelMap modelMap,HttpServletRequest request) {

		List<Hospital> hospitalList = (List<Hospital>) request.getSession().getAttribute(UserConstant.HOSPITAL_LIST);
		PageInfo<MsgHospitalVO> hospitals = null;
		if (hospitalList.size() > 0) {
			String[] hospitalIds = new String[hospitalList.size()];
			for (int i = 0; i < hospitalList.size(); i++) {
				hospitalIds[i] = hospitalList.get(i).getId();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 设置搜索条件
			params.put("search", search);
			//把session中保存的ID做查询条件
			params.put("hospitalIds", hospitalIds);
			hospitals = replyService.findHospListByPage(params, new Page<MsgHospitalVO>(pageNum, pageSize));
		}
		modelMap.put("search", search);
		ModelAndView view = new ModelAndView("/sys/message/list", "hospitals", hospitals);
		return view;
	}

	/***
	 * TO编辑关键字规则 获取该规则的相关信息
	 * 
	 * @param ruleId
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(params = "method=editKeywordReply")
	public RespBody editKeywordReply(String ruleId) {
		try {
			Rule rule = ruleService.findById(ruleId);
			// 获取规则下的关键字
			List<Keyword> keywordList = keywordService.findByRuleId(ruleId);
			List<Reply> replyList = replyService.findByRuleId(ruleId);
			RuleRespBody ruleRespBody = new RuleRespBody();
			ruleRespBody.setRule(rule);
			ruleRespBody.setKeywordList(keywordList);
			ruleRespBody.setReplies(replyList);
			ruleRespBody.setStatus(Status.OK);
			return ruleRespBody;
		} catch (Exception e) {
			RuleRespBody ruleRespBody = new RuleRespBody();
			ruleRespBody.setStatus(Status.ERROR);
			return ruleRespBody;
		}
	}

	/***
	 * 删除规则下绑定的回复
	 * 
	 * @param ruleId
	 * @param replyId
	 * */
	@ResponseBody
	@RequestMapping(params = "method=deleteRuleReply")
	public RespBody deleteRuleReply(String ruleId, String replyId) {
		try {
			Reply reply = replyService.findById(replyId);
			if (reply != null) {
				// 1.若是该回复类型是文字或者图片，则删除回复与规则的关联关系、删除回复本身、
				// 2.若是该回复类型是图文，则还需删除回复图文关联关系-----上传过的图文则保留在素材库以供下次上传的时候直接选择
				if (reply.getContentType() == 3) {
					// 删除回复与图文的关联关系
					replyService.deleteReplyMixed(replyId);
				}
				if (reply.getContentType() == 2) {
					// 删除上传图片
					String deletePath = FileUploadController.DISK + reply.getPicPaths();
					File file = new File(deletePath);
					if (file.exists()) {
						file.delete();
					}
				}
				// 删除回复本身
				replyService.deleteById(replyId);
				// 删除回复与规则的关联关系
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ruleId", ruleId);
				params.put("replyId", replyId);
				ruleService.deleteRuleReply(params);
				return new RespBody(Status.OK);
			} else {
				return new RespBody(Status.ERROR);
			}
		} catch (Exception e) {
			return new RespBody(Status.ERROR);
		}
	}

	/***
	 * 删除规则
	 * 
	 * @param ruleId
	 * */
	@ResponseBody
	@RequestMapping(params = "method=deleteRule")
	public RespBody deleteRule(String ruleId) {
		try {
			// 删除该规则下的所有回复
			List<Reply> replys = replyService.findByRuleId(ruleId);
			List<Keyword> keywords = keywordService.findByRuleId(ruleId);
			if (replys != null && replys.size() > 0) {
				for (Reply reply : replys) {
					String replyId = reply.getId();
					if (reply.getContentType() == 3) {
						// 删除回复与图文的关联关系
						replyService.deleteReplyMixed(replyId);
					}
					if (reply.getContentType() == 2) {
						// 删除上传图片
						String deletePath = FileUploadController.DISK + reply.getPicPaths();
						File file = new File(deletePath);
						if (file.exists()) {
							file.delete();
						}
					}
					// 删除回复本身
					replyService.deleteById(replyId);
					// 删除回复与规则的关联关系
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("ruleId", ruleId);
					params.put("replyId", replyId);
					ruleService.deleteRuleReply(params);
				}
			}
			if (keywords != null && keywords.size() > 0) {
				for (Keyword keyword : keywords) {
					keywordService.deleteById(keyword.getId());
				}
			}
			ruleService.deleteById(ruleId);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 保存新建的规则 ，包括规则、关键字集合、回复集合
	 * 
	 * @param hospitalId
	 *            医院ID
	 * @param ruleName
	 *            规则名
	 * @param keyword
	 *            关键字集合
	 * @param keywordType
	 *            关键字类型集合
	 * @param replyText
	 *            回复文字集合
	 * @param replyImgSrc
	 *            回复图片集合
	 * @param singleIds
	 *            单图文ID集合
	 * @param multiIds
	 *            多图文ID集合
	 * @param ruleReplyType
	 *            规则类型 回复全部或者随机回复
	 * */
	@ResponseBody
	@RequestMapping(params = "method=saveKeywordReply")
	public RespBody saveKeywordReply(String hospitalId, String ruleName, String thirdType, String ruleReplyType, String[] keyword,
			String[] keywordType, String[] replyText, String[] replyImgSrc, String[] singleIds, String[] multiIds, HttpServletRequest request) {
		try {
			// 权限控制
			User loginUser = (User) request.getSession().getAttribute(MessageConstant.LOGINED_USER);
			String editPerson = null;
			if (loginUser != null) {
				editPerson = loginUser.getId();
			}
			// 1.新建规则
			Rule rule = new Rule();
			rule.setHospitalId(hospitalId);// 医院ID
			rule.setRuleName(ruleName);// 规则名
			rule.setThirdType(Integer.valueOf(thirdType));// 平台类型
			rule.setState(MessageConstant.NORMAL);
			rule.setType(ruleReplyType != null ? Integer.valueOf(ruleReplyType) : 1);// 规则类型：回复全部或者随机
			String ruleId = ruleService.add(rule);
			// 2.保存关键字
			/** -----------关键字---------- **/
			Keyword kw = null;
			if (keyword.length == keywordType.length) {
				List<Keyword> kwList = new ArrayList<Keyword>();
				for (int i = 0; i < keyword.length; i++) {
					kw = new Keyword();
					kw.setId(PKGenerator.generateId());
					kw.setContent(keyword[i]);
					kw.setRuleId(ruleId);
					kw.setType(Integer.valueOf(keywordType[i]));
					kw.setState(MessageConstant.NORMAL);
					kwList.add(kw);
				}
				keywordService.batchInsert(kwList);
			} else {
				return new RespBody(Status.ERROR, "获取关键字和关键字类型参数不匹配");
			}
			// 3.保存回复
			/** -----------回复文字---------- */
			List<String> replyIds = new ArrayList<String>(); // 记录所有新建的回复ID
			Reply reply = null;
			if (replyText != null && replyText.length > 0) {
				for (String text : replyText) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.TEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					reply.setContent(text);
					replyIds.add(replyService.add(reply));
				}
			}
			/** -----------回复图片---------- */
			if (replyImgSrc != null && replyImgSrc.length > 0) {
				for (String imgsrc : replyImgSrc) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGE, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					reply.setPicPaths(imgsrc);
					replyIds.add(replyService.add(reply));
				}
			}
			/** -----------回复单图文---------- */
			Map<String, Object> params = null;
			if (singleIds != null && singleIds.length > 0) {
				for (String singleId : singleIds) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGETEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					String replyId = replyService.add(reply);
					params = new HashMap<String, Object>();
					params.put("id", PKGenerator.generateId());
					params.put("replyId", replyId);
					params.put("MeterialId", singleId);
					// 保存回复图文关系
					replyService.addReplyMeterial(params);
					replyIds.add(replyId);
				}
			}
			/** -----------回复多图文---------- */
			if (multiIds != null && multiIds.length > 0) {
				for (String multiId : multiIds) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGETEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					String replyId = replyService.add(reply);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (String s : multiId.split(",")) {
						params = new HashMap<String, Object>();
						params.put("id", PKGenerator.generateId());
						params.put("replyId", replyId);
						params.put("MeterialId", s);
						list.add(params);
					}
					// 保存回复图文关系
					replyService.addReplyMeterials(list);
					replyIds.add(replyId);
				}
			}
			// 保存回复与规则的关系
			if (replyIds.size() > 0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (String s : replyIds) {
					params = new HashMap<String, Object>();
					params.put("id", PKGenerator.generateId());
					params.put("ruleId", ruleId);
					params.put("replyId", s);
					list.add(params);
				}
				replyService.addReplyRule(list);
			}
			List<Keyword> keywordList = keywordService.findByRuleId(ruleId);
			// 封装返回的数据
			RuleRespBody ruleRespBody = new RuleRespBody();
			// ruleRespBody.setRuleType(ruleReplyType != null ?
			// Integer.valueOf(ruleReplyType) : 1);
			ruleRespBody.setRuleId(ruleId);
			ruleRespBody.setStatus(Status.OK);
			ruleRespBody.setRuleName(ruleName);
			int textNum = replyText != null ? replyText.length : 0;// 文字数
			int imgNum = replyImgSrc != null ? replyImgSrc.length : 0;// 图片数
			int singleNum = singleIds != null ? singleIds.length : 0;// 单图文数
			int multiNum = multiIds != null ? multiIds.length : 0;// 多图文数
			ruleRespBody.setTextNum(textNum);
			ruleRespBody.setImageNum(imgNum);
			ruleRespBody.setImageTextNum(singleNum + multiNum);
			ruleRespBody.setKeywordList(keywordList);
			ruleRespBody.setReplyNum(textNum + imgNum + singleNum + multiNum);
			return ruleRespBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 保存新建 规则 、关键字、回复
	 * 
	 * @param hospitalId
	 *            医院ID
	 * @param ruleName
	 *            规则名
	 * @param keyword
	 *            关键字集合
	 * @param keywordType
	 *            关键字类型集合
	 * @param replyText
	 *            回复文字集合
	 * @param replyImgSrc
	 *            回复图片集合
	 * @param singleIds
	 *            单图文ID集合
	 * @param multiIds
	 *            多图文ID集合
	 * @param
	 * */
	@ResponseBody
	@RequestMapping(params = "method=updateKeywordReply")
	public RespBody updateKeywordReply(String ruleId, String hospitalId, String ruleReplyType, String ruleName, String[] keywordId, String[] keyword,
			String[] keywordType, String[] replyText, String[] replyImgSrc, String[] singleIds, String[] multiIds, HttpServletRequest request) {
		try {
			// 权限控制
			User loginUser = (User) request.getSession().getAttribute(MessageConstant.LOGINED_USER);
			String editPerson = null;
			if (loginUser != null) {
				editPerson = loginUser.getId();
			}
			// 1.新建规则
			Rule rule = ruleService.findById(ruleId);
			rule.setRuleName(ruleName);
			rule.setType(ruleReplyType != null ? Integer.valueOf(ruleReplyType) : 1);
			ruleService.update(rule);
			// 2.保存关键字
			/** -----------关键字---------- **/
			Keyword kw = null;
			if (keyword.length == keywordType.length) {
				for (int i = 0; i < keyword.length; i++) {
					kw = keywordService.findById(keywordId[i]);
					if (keyword[i].length() > 30) {
						return new RespBody(Status.ERROR, "关键字的内容不得大于30个字符");
					}
					kw.setContent(keyword[i]);
					kw.setRuleId(ruleId);
					kw.setType(Integer.valueOf(keywordType[i]));
					kw.setState(MessageConstant.NORMAL);
					keywordService.update(kw);
				}
			} else {
				return new RespBody(Status.ERROR, "获取关键字和关键字类型参数不匹配");
			}
			// 3.保存回复
			/** -----------回复文字---------- */
			List<String> replyIds = new ArrayList<String>(); // 记录所有新建的回复ID
			Reply reply = null;
			if (replyText != null && replyText.length > 0) {
				for (String text : replyText) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.TEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					reply.setContent(text);// 设置回复文字内容
					replyIds.add(replyService.add(reply));
				}
			}
			/** -----------回复图片---------- */
			if (replyImgSrc != null && replyImgSrc.length > 0) {
				for (String imgsrc : replyImgSrc) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGE, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					reply.setPicPaths(imgsrc);// 设置回复的图片路径
					replyIds.add(replyService.add(reply));
				}
			}
			/** -----------回复单图文---------- */
			Map<String, Object> params = null;
			if (singleIds != null && singleIds.length > 0) {
				for (String singleId : singleIds) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGETEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					String replyId = replyService.add(reply);
					params = new HashMap<String, Object>();
					params.put("id", PKGenerator.generateId());
					params.put("replyId", replyId);
					params.put("MeterialId", singleId);
					// 保存回复图文关系
					replyService.addReplyMeterial(params);
					replyIds.add(replyId);
				}
			}
			/** -----------回复多图文---------- */
			if (multiIds != null && multiIds.length > 0) {
				for (String multiId : multiIds) {
					reply =
							new Reply(MessageConstant.REPLY_KEYWORD, MessageConstant.THIRD_WECHAT, MessageConstant.IMAGETEXT, hospitalId,
									MessageConstant.NORMAL);
					reply.setEp(editPerson);
					reply.setCp(editPerson);
					String replyId = replyService.add(reply);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (String s : multiId.split(",")) {
						params = new HashMap<String, Object>();
						params.put("id", PKGenerator.generateId());
						params.put("replyId", replyId);
						params.put("MeterialId", s);
						list.add(params);
					}
					// 保存回复图文关系
					replyService.addReplyMeterials(list);
					replyIds.add(replyId);
				}
			}
			// 保存回复与规则的关系
			if (replyIds.size() > 0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (String s : replyIds) {
					params = new HashMap<String, Object>();
					params.put("id", PKGenerator.generateId());
					params.put("ruleId", ruleId);
					params.put("replyId", s);
					list.add(params);
				}
				replyService.addReplyRule(list);
			}
			List<Reply> replyList = replyService.findByRuleId(ruleId);
			int textNum = 0;
			int imgNum = 0;
			int imageTextNum = 0;
			for (Reply r : replyList) {
				if (r.getContentType() == 1) {
					textNum++;
				} else if (r.getContentType() == 2) {
					imgNum++;
				} else if (r.getContentType() == 3) {
					imageTextNum++;
				}
			}
			List<Keyword> keywordList = keywordService.findByRuleId(ruleId);
			// 封装返回数据
			RuleRespBody ruleRespBody = new RuleRespBody();
			// 规则
			ruleRespBody.setRuleId(ruleId);
			// ruleRespBody.setRuleType(ruleReplyType != null ?
			// Integer.valueOf(ruleReplyType) : 1);
			ruleRespBody.setStatus(Status.OK);
			ruleRespBody.setRuleName(ruleName);
			ruleRespBody.setTextNum(textNum);
			ruleRespBody.setImageNum(imgNum);
			ruleRespBody.setImageTextNum(imageTextNum);
			// 关键字集合
			ruleRespBody.setKeywordList(keywordList);
			ruleRespBody.setReplyNum(replyList.size());
			return ruleRespBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/***
	 * 保存单个回复，用于被关注回复 contentType 回复内容类型 type 回复类型1 被关注回复 2 关键字回复 textContent
	 * 
	 * @param contentType
	 *            回复内容类型
	 * @param thirdType
	 *            平台类型
	 * @param type
	 *            回复类型 文字 图片 图文
	 * @param hospitalId
	 *            医院
	 * @param textContent
	 *            文字内容
	 * @param picPath
	 *            图片路径
	 * @param singleMeterialId
	 *            单图文ID
	 * @param multiMeterialIds
	 *            多图文ID字符串 ,分割
	 * */
	@ResponseBody
	@RequestMapping(params = "method=saveOneReply")
	public RespBody saveOneReply(int contentType, int thirdType, int type, String hospitalId, String textContent, String picPath,
			String singleMeterialId, String multiMeterialIds, HttpServletRequest request) {
		try {
			// 权限控制
			User loginUser = (User) request.getSession().getAttribute(MessageConstant.LOGINED_USER);
			String editPerson = null;
			if (loginUser != null) {
				editPerson = loginUser.getId();
			}
			//
			Reply reply = new Reply();
			// reply.setHospitalBranchId(Long.valueOf(hospitalBranchId));
			reply.setHospitalId(hospitalId);
			reply.setState(MessageConstant.NORMAL);
			reply.setContentType(contentType);
			reply.setType(type);
			reply.setThirdType(thirdType);
			reply.setEp(editPerson);
			reply.setCp(editPerson);
			// 关注回复只能设置一个
			// 删除该医院之前设置的自动回复
			Map<String, Object> replyParams = new HashMap<String, Object>();
			replyParams.put("hospId", hospitalId);
			replyParams.put("thirdType", thirdType);
			Reply deletingReply = replyService.getFocusedReply(replyParams);
			if (deletingReply != null) {
				/*
				 * if (deletingReply.getContentType() == 2) { // 删除上传图片 String
				 * deletePath = FileUploadController.DISK +
				 * deletingReply.getPicPaths(); File file = new
				 * File(deletePath); if (file.exists()) { file.delete(); } }
				 */
				// 删除原来的回复
				replyService.deleteById(deletingReply.getId());
			}
			// 保存文字
			if (contentType == MessageConstant.TEXT) {
				reply.setContent(textContent);
				replyService.add(reply);
			}
			// 保存图片
			else if (contentType == MessageConstant.IMAGE) {
				reply.setPicPaths(picPath);
				replyService.add(reply);
				// 判断这张图片是否已经存在素材库中
				boolean isExist = meterialService.finByPicPath(picPath);
				if (!isExist) {
					// 不存在则将被关注回复的内容自动加入到素材库中
					Meterial entity = new Meterial();
					entity.setId(PKGenerator.generateId());
					entity.setCoverPicPath(picPath);
					entity.setType(1);
					entity.setState(1);
					entity.setPath(picPath);
					entity.setHospitalId(hospitalId);
					meterialService.add(entity);
				}
			}
			// 保存图文
			else if (contentType == MessageConstant.IMAGETEXT) {
				String replyId = replyService.add(reply);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("replyId", replyId);
				if (!StringUtils.isBlank(singleMeterialId)) {
					params.put("id", PKGenerator.generateId());
					params.put("MeterialId", singleMeterialId);
					replyService.addReplyMeterial(params);
				} else if (!StringUtils.isBlank(multiMeterialIds)) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (String MeterialId : multiMeterialIds.split(",")) {
						params.put("id", PKGenerator.generateId());
						params.put("replyId", replyId);
						params.put("MeterialId", MeterialId);
						list.add(params);
					}
					// 保存图文
					replyService.addReplyMeterials(list);
				}
			}
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 页面跳转----单图文回复 TO singleText
	 * */
	@RequestMapping(params = "method=toSingleText")
	public String toSingleText(String hospitalId, String index, String type, String thirdType, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("index", index);
		request.setAttribute("type", type);
		request.setAttribute("thirdType", thirdType);
		request.setAttribute("flagTime", request.getParameter("flagTime"));
		return "/sys/message/singleText";
	}

	/**
	 * 页面跳转----- 多图文回复 TO toMulti
	 * */
	@RequestMapping(params = "method=toMulti")
	public String toMulti(String hospitalId, String index, String type, String thirdType, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("index", index);
		request.setAttribute("type", type);
		request.setAttribute("thirdType", thirdType);
		request.setAttribute("flagTime", request.getParameter("flagTime"));
		return "/sys/message/multiText";
	}

	/**
	 * 进入微信消息管理--被关注回复界面
	 * 
	 * @param hospitalId
	 *            医院
	 * @param thirdType
	 *            平台类型
	 * */
	@RequestMapping(params = "method=toFocusedReply")
	public String toFocusedReply(String hospitalId, String thirdType, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospId", hospitalId);
		params.put("thirdType", thirdType);
		Reply reply = replyService.getFocusedReply(params);
		if (reply != null) {
			List<MixedMeterial> mixmeList = reply.getMixedMeterialList();
			MixedMeterial mixedMeterial = null;
			if (mixmeList != null && mixmeList.size() > 0) {
				mixedMeterial = mixmeList.get(0);
				request.setAttribute("mixedMeterial", mixedMeterial);
				if (mixedMeterial.getType() == 1) {
					request.setAttribute("singleMeterialId", mixedMeterial.getId());
				} else if (mixedMeterial.getType() == 2) {
					request.setAttribute("multiMeterialIds", mixedMeterial.getId());
				}
			}
			request.setAttribute("reply", reply);
		}
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("thirdType", thirdType);
		if ("1".equals(thirdType)) {
			return "/sys/message/wechat/focused";
		} else {
			return "/sys/message/alipay/focused";
		}
	}

	/**
	 * 进入微信消息管理--关键字回复界面
	 * 
	 * @param hospitalId
	 *            医院
	 * @param thirdType
	 *            平台类型
	 * */
	@RequestMapping(params = "method=toKeywordReply")
	public String toKeywordReply(String hospitalId, String thirdType, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("thirdType", thirdType);
		params.put("hospitalId", hospitalId);
		// 获取该医院的规则
		List<Rule> rules = ruleService.findByHospId(params);
		for (Rule rule : rules) {
			int textNum = 0;
			int imgNum = 0;
			int imageTextNum = 0;
			// 规则关键字
			List<Keyword> keywords = keywordService.findByRuleId(rule.getId());
			// 规则下设置的回复
			List<Reply> replies = replyService.findByRuleId(rule.getId());
			for (Reply reply : replies) {
				if (reply.getContentType() == 1) {
					textNum++;
				} else if (reply.getContentType() == 2) {
					imgNum++;
				} else if (reply.getContentType() == 3) {
					imageTextNum++;
				}
			}
			rule.setKeywords(keywords);
			rule.setTextNum(textNum);
			rule.setImgNum(imgNum);
			rule.setImageTextNum(imageTextNum);
			rule.setTotalNum(textNum + imgNum + imageTextNum);
		}
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("thirdType", thirdType);
		request.setAttribute("rules", rules);
		if ("1".equals(thirdType)) {
			return "/sys/message/wechat/keywords";
		} else {
			return "/sys/message/alipay/keywords";
		}
	}

	/**
	 * 页面跳转 ----- 访问diglog-pic.ftl
	 * */
	@RequestMapping(params = "method=toDialogPic")
	public String toDialogPic(String hospitalId, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		return "/sys/message/dialog-pic";
	}

	/**
	 * 页面跳转 ----- 访问diglog-words.ftl
	 * */
	@RequestMapping(params = "method=toDialogWords")
	public String toDialogWords() {
		return "/sys/message/dialog-words";
	}

	/**
	 * 页面跳转 ----- 访问diglog-imagetext.ftl
	 * */
	@RequestMapping(params = "method=toDialogImageText")
	public String toDialogImageText(String hospitalId, String thirdType, String type, HttpServletRequest request) {
		request.setAttribute("hospitalId", hospitalId);
		request.setAttribute("thirdType", thirdType);
		request.setAttribute("type", type);
		return "/sys/message/dialog-imagetext";
	}

	/**
	 * 取消编辑--关键字回复
	 * 
	 * @param ruleId
	 * */
	@ResponseBody
	@RequestMapping(params = "method=cancelEditKeywordReply")
	public RespBody cancelEditKeywordReply(String ruleId) {
		try {
			Rule rule = ruleService.findById(ruleId);
			// 获取规则下的关键字
			List<Keyword> keywordList = keywordService.findByRuleId(ruleId);
			List<Reply> replyList = replyService.findByRuleId(ruleId);
			int textNum = 0;
			int imgNum = 0;
			int imageTextNum = 0;
			for (Reply r : replyList) {
				if (r.getContentType() == 1) {
					textNum++;
				} else if (r.getContentType() == 2) {
					imgNum++;
				} else if (r.getContentType() == 3) {
					imageTextNum++;
				}
			}
			// 封装返回数据
			RuleRespBody ruleRespBody = new RuleRespBody();
			// 规则
			ruleRespBody.setRuleId(ruleId);
			// ruleRespBody.setRuleType(ruleReplyType != null ?
			// Integer.valueOf(ruleReplyType) : 1);
			ruleRespBody.setStatus(Status.OK);
			ruleRespBody.setRuleName(rule.getRuleName());
			ruleRespBody.setTextNum(textNum);
			ruleRespBody.setImageNum(imgNum);
			ruleRespBody.setImageTextNum(imageTextNum);
			// 关键字集合
			ruleRespBody.setKeywordList(keywordList);
			ruleRespBody.setReplyNum(replyList.size());
			return ruleRespBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}
}
