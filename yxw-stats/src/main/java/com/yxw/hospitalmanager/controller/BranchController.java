package com.yxw.hospitalmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.hospitalmanager.entity.Branch;
import com.yxw.hospitalmanager.service.BranchService;
import com.yxw.hospitalmanager.vo.HospitalInfosVo;

@Controller
@RequestMapping(value="sys/hospital/branch/")
public class BranchController {
	
	private Logger logger = LoggerFactory.getLogger(BranchController.class);
	
	private BranchService service = SpringContextHolder.getBean(BranchService.class);
	
	@RequestMapping(value="list")
	public ModelAndView toBranchList(HttpServletRequest request, HospitalInfosVo vo) {
		ModelAndView view = new ModelAndView("hospital/branch");
		view.addObject("commonParams", vo);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value="getBranches", method = RequestMethod.POST)
	public RespBody getBranches(HttpServletRequest request, HospitalInfosVo vo) {
		try {
			List<Branch> branches = service.findAllByHospitalId(vo.getHospitalId());
			return new RespBody(Status.OK, branches);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="deleteBranch", method = RequestMethod.POST)
	public RespBody deleteBranch(HttpServletRequest request, Branch branch) {
		try {
			service.deleteById(branch.getId());
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="addBranch", method = RequestMethod.POST)
	public RespBody addBranch(HttpServletRequest request, Branch branch) {
		try {
			// 检测
			if (service.checkBranchExistsByName(branch.getName(), branch.getHospitalId())) {
				return new RespBody(Status.ERROR, "分院名称重复");
			}
			
			if (service.checkBranchExistsByCode(branch.getCode(), branch.getHospitalId())) {
				return new RespBody(Status.ERROR, "分院代码重复");
			}
			
			service.add(branch);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="updateBranch", method = RequestMethod.POST)
	public RespBody updateBranch(HttpServletRequest request, Branch branch) {
		try {
			// 检测
			if (service.checkExistsByNameOrCode(branch)) {
				return new RespBody(Status.ERROR, "分院名称或代码重复");
			}
			
			service.update(branch);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new RespBody(Status.ERROR);
		}
	}
	
}
