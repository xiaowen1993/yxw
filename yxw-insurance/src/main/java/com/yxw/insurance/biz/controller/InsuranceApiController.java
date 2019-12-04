package com.yxw.insurance.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.insurance.biz.service.InsuranceService;
import com.yxw.insurance.biz.service.MzFeePayDetailService;

@Controller
@RequestMapping("/api")
public class InsuranceApiController {

	InsuranceService InsuranceService = SpringContextHolder.getBean(InsuranceService.class);

	private MzFeePayDetailService mzFeePayDetailService = SpringContextHolder.getBean(MzFeePayDetailService.class);

	/**
	 * 查询缴费记录列表
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getMzPayFeeList")
	@ResponseBody
	public List<ClinicRecord> getMzPayFeeList(PaidMZDetailCommParams params) {
		return InsuranceService.getMZPayFeeList(params);
	}

	/**
	 * 异步查询已缴费明细
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	public Object getDatas(ClinicPayVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, String> params = new HashMap<String, String>();
		params.put("hospitalCode", vo.getHospitalCode());
		params.put("branchHospitalCode", vo.getBranchHospitalCode());
		params.put("hisOrdNum", vo.getHisOrdNum());
		params.put("receiptNum", vo.getReceiptNum());
		params.put("mzFeeId", vo.getMzFeeId());
		resultMap.put("list", mzFeePayDetailService.findMzFeePayDetail(vo.getMzFeeId()));
		return new RespBody(Status.OK, resultMap);
	}
}
