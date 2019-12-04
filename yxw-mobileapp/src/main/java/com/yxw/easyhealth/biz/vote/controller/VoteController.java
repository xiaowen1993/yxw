package com.yxw.easyhealth.biz.vote.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.vote.VoteInfo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.biz.vote.service.VoteService;

@Controller
@RequestMapping("/easyhealth/vote/")
public class VoteController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(VoteController.class);

	private BaseDatasManager baseDatasManger = SpringContextHolder.getBean(BaseDatasManager.class);

	private VoteService voteService = SpringContextHolder.getBean(VoteService.class);

	MedicalCardService cardService = SpringContextHolder.getBean(MedicalCardService.class);

	/**
	 * 显示评价列表
	 * @param vo
	 * @param viewType    allVote/noVote/hadVote    全部/待评价/已评价
	 * @return
	 */
	@RequestMapping(value = "/listByOpenId")
	public ModelAndView listByOpenId(VoteInfo vo, String viewType) {
		String openId = vo.getOpenId();
		List<VoteInfo> voteInfos = voteService.findListByOpenId(openId, viewType);

		//对以前旧数据没有存储病人姓名的数据 补全病人姓名	
		Map<String, String> cardNoAndName = new HashMap<String, String>();
		//查找没有病人名称的记录
		for (VoteInfo voteInfo : voteInfos) {
			if (StringUtils.isBlank(voteInfo.getPatientName())) {
				cardNoAndName.put(voteInfo.getCardNo(), "病人姓名丢失");
			}
		}
		//存在没有病人名称的记录时   补全记录的病人姓名
		if (!CollectionUtils.isEmpty(cardNoAndName)) {
			List<MedicalCard> cards = cardService.findAllCardsByOpenId(openId);
			//卡号与病人名称的对应关系Map
			for (MedicalCard card : cards) {
				if (cardNoAndName.get(card.getCardNo()) != null) {
					cardNoAndName.put(card.getCardNo(), card.getPatientName());
				}
			}
		}
		List<VoteInfo> noVoteList = new ArrayList<VoteInfo>();
		List<VoteInfo> hadVoteList = new ArrayList<VoteInfo>();
		String patientName = null;
		for (VoteInfo voteInfo : voteInfos) {
			//病人名称不存在时,根据cardNoAndName中的卡号与病人名称的对应关系补全信息
			if (StringUtils.isBlank(voteInfo.getPatientName())) {
				patientName = cardNoAndName.get(voteInfo.getCardNo());
				if (StringUtils.isNotBlank(patientName)) {
					voteInfo.setPatientName(patientName);
				}
			}

			if (voteInfo.getIsHadVote().intValue() == BizConstant.VOTE_IS_HAD_YES) {
				hadVoteList.add(voteInfo);
			} else {
				noVoteList.add(voteInfo);
			}
		}
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/vote/voteInfoList");
		if (!CollectionUtils.isEmpty(noVoteList)) {
			Collections.sort(noVoteList);
		}
		if (!CollectionUtils.isEmpty(hadVoteList)) {
			Collections.sort(hadVoteList);
		}

		modelAndView.addObject("noVoteList", noVoteList);
		modelAndView.addObject("hadVoteList", hadVoteList);
		modelAndView.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return modelAndView;
	}

	/**
	 * 查看评价
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/toView")
	public ModelAndView toView(VoteInfo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/vote/viewVoteInfo");

		String orderNo = vo.getOrderNo();
		String openId = vo.getOpenId();
		String raterCode = vo.getRaterCode();
		String hospitalId = vo.getHospitalId();
		String id = vo.getId();
		VoteInfo info = null;
		if (StringUtils.isNotBlank(orderNo)) {
			info = voteService.findVoteInfo(id, orderNo, openId, hospitalId, raterCode);

			if (info != null && StringUtils.isBlank(info.getPatientName())) {

				MedicalCard medcalCard = cardService.findCardByHospitalIdAndOpenIdAndCardNo(hospitalId, openId, info.getCardNo());
				if (medcalCard != null) {
					String patientName = medcalCard.getPatientName();
					info.setPatientName(patientName);
				} else {
					info.setPatientName("病人姓名丢失");
				}
			}

			if (info == null) {
				String appCode = vo.getAppCode();
				String appId = vo.getAppId();
				HospIdAndAppSecretVo appInfo = baseDatasManger.findAppSecretByAppId(appCode, appId);
				if (appInfo != null) {
					vo.setHospitalCode(appInfo.getHospCode());
					vo.setHospitalId(appInfo.getHospId());
					vo.setHospitalName(appInfo.getHospName());
				} else {
					logger.error("absence params!appCode:{} , appId:{}", appCode, appId);
				}
			} else {
				String fowardUrl = vo.getFowardUrl();
				vo = info;
				vo.setFowardUrl(fowardUrl);
			}

			//查询医院回复

		}

		modelAndView.addObject(BizConstant.COMMON_ENTITY_KEY, vo);
		return modelAndView;
	}

	/**
	 * 编辑评价
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(VoteInfo vo) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/vote/editVoteInfo");

		String orderNo = vo.getOrderNo();
		String openId = vo.getOpenId();
		String raterCode = vo.getRaterCode();
		String hospitalId = vo.getHospitalId();
		String id = vo.getId();
		VoteInfo info = null;
		if (StringUtils.isNotBlank(orderNo)) {
			info = voteService.findVoteInfo(id, orderNo, openId, hospitalId, raterCode);
			if (info == null) {
				String appCode = vo.getAppCode();
				String appId = vo.getAppId();
				HospIdAndAppSecretVo appInfo = baseDatasManger.findAppSecretByAppId(appCode, appId);
				if (appInfo != null) {
					vo.setHospitalCode(appInfo.getHospCode());
					vo.setHospitalId(appInfo.getHospId());
					vo.setHospitalName(appInfo.getHospName());
				} else {
					logger.error("absence params!appCode:{} , appId:{}", appCode, appId);
				}
			} else {
				String fowardUrl = vo.getFowardUrl();
				vo = info;
				vo.setFowardUrl(fowardUrl);
			}
		}

		modelAndView.addObject(BizConstant.COMMON_ENTITY_KEY, vo);
		return modelAndView;
	}

	/**
	 * 生成挂号记录、订单
	 * 
	 * @param vo
	 * @param diseaseDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveVoteInfo", method = RequestMethod.POST)
	public RespBody saveVoteInfo(HttpServletRequest request, VoteInfo vo) {
		if (StringUtils.isEmpty(vo.getOpenId())) {
			vo.setOpenId(getOpenId(request));
		}

		vo.setIsHadVote(BizConstant.VOTE_IS_HAD_YES);

		Map<String, Object> resMap = voteService.saveVoteInfo(vo);
		return new RespBody(Status.OK, resMap);
	}

}
