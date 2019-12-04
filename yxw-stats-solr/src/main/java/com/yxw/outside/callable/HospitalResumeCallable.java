package com.yxw.outside.callable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.yxw.biz.common.service.HospitalInfoService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.outside.constant.OutsideConstants;
import com.yxw.vo.AreaHospitalInfosVo;

public class HospitalResumeCallable implements Callable<Map<String, Object>> {

	@Override
	public Map<String, Object> call() throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 查库获取相应的信息
		HospitalInfoService service = SpringContextHolder.getBean(HospitalInfoService.class);
		List<AreaHospitalInfosVo> vos = service.getAreaHospitalInfos();
		
		resultMap.put(OutsideConstants.AREA_HOSPITAL_INFOS, vos);
		
		return resultMap;
	}

}
