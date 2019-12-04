/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.easyhealth.biz.register.service.HadRegDoctorService;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: SaveHadRegDoctorRunnable
 * @Statement: <p>子线程保存曾挂号医生信息</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SaveHadRegDoctorRunnable implements Runnable {
	private HadRegDoctor doctor;
	//	private HadRegDoctorCache hadRegDoctorCache = SpringContextHolder.getBean(HadRegDoctorCache.class);
	private HadRegDoctorService hadRegDoctorService = SpringContextHolder.getBean(HadRegDoctorService.class);

	public SaveHadRegDoctorRunnable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveHadRegDoctorRunnable(HadRegDoctor doctor) {
		super();
		this.doctor = doctor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (doctor != null) {
			if (StringUtils.isBlank(doctor.getId())) {
				doctor.setId(PKGenerator.generateId());
			}

			Boolean isAdd = true;
			HadRegDoctor removeDoctor = null;
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<HadRegDoctor> hadRegDoctors = new ArrayList<HadRegDoctor>();
			List<Object> params = new ArrayList<Object>();
			params.add(doctor.getOpenId());
			params.add(doctor.getHospitalCode());
			params.add(doctor.getBranchHospitalCode());
			List<Object> results = serveComm.get(CacheType.HAD_REG_DOCTOR_CACHE, "getHadRegDoctorsByOpenId", params);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				hadRegDoctors = JSON.parseArray(source, HadRegDoctor.class);

				for (HadRegDoctor d : hadRegDoctors) {
					if (d.getDoctorCode().equals(doctor.getDoctorCode())) {
						isAdd = false;
						break;
					}
				}
			}

			if (isAdd) {
				hadRegDoctors.add(doctor);

				if (hadRegDoctors.size() > 3) {
					removeDoctor = hadRegDoctors.remove(0);
				}

				// 放入缓存
				params.clear();
				params.add(hadRegDoctors);
				serveComm.set(CacheType.HAD_REG_DOCTOR_CACHE, "setDoctorsToCache", params);

				// 写入数据库
				hadRegDoctorService.saveAndRemoveDoctor(doctor, removeDoctor);
			}

			//			Map<String, Object> resMap = hadRegDoctorCache.setDoctorToCache(doctor);
			//			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			//			List<Object> params = new ArrayList<Object>();
			//			params.add(doctor);

			//			Boolean isAdd = (Boolean) resMap.get("isAdd");
			//			if (isAdd) {
			//				HadRegDoctor removeDoctor = (HadRegDoctor) resMap.get("removeDoctor");
			//				hadRegDoctorService.saveAndRemoveDoctor(doctor, removeDoctor);
			//			}
		}
	}
}
