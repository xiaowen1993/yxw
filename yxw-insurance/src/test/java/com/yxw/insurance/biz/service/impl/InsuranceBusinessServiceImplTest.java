package com.yxw.insurance.biz.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.utils.Junit4SpringContextHolder;
import com.yxw.insurance.biz.entity.ClaimOrder;
import com.yxw.insurance.biz.service.ClaimOrderService;
import com.yxw.insurance.biz.service.InsuranceBusinessService;
import com.yxw.insurance.init.InitDataServlet;

public class InsuranceBusinessServiceImplTest extends Junit4SpringContextHolder {

	@Test
	public void testUploadPrescriptionDetail() {

		try {

			String os_name = System.getProperties().get("os.name").toString().toLowerCase();
			if (os_name.indexOf("windows") != -1) {
				List<String> a =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/A.txt").toURI().getPath().substring(1)), "UTF-8");
				List<String> b =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/B.txt").toURI().getPath().substring(1)), "UTF-8");
				InitDataServlet.l1.addAll(a);
				InitDataServlet.l1.addAll(b);

				List<String> c =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/C.txt").toURI().getPath().substring(1)), "UTF-8");
				List<String> d =
						FileUtils.readLines(new File(InitDataServlet.class.getResource("/D.txt").toURI().getPath().substring(1)), "UTF-8");
				InitDataServlet.l2.addAll(c);
				InitDataServlet.l2.addAll(d);

			} else if (os_name.indexOf("linux") != -1) {
				List<String> a = FileUtils.readLines(new File(InitDataServlet.class.getResource("/A.txt").toURI().getPath()), "UTF-8");
				List<String> b = FileUtils.readLines(new File(InitDataServlet.class.getResource("/B.txt").toURI().getPath()), "UTF-8");
				InitDataServlet.l1.addAll(a);
				InitDataServlet.l1.addAll(b);

				List<String> c = FileUtils.readLines(new File(InitDataServlet.class.getResource("/C.txt").toURI().getPath()), "UTF-8");
				List<String> d = FileUtils.readLines(new File(InitDataServlet.class.getResource("/D.txt").toURI().getPath()), "UTF-8");
				InitDataServlet.l2.addAll(c);
				InitDataServlet.l2.addAll(d);
			}

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		InsuranceBusinessService insuranceBusinessService = SpringContextHolder.getBean(InsuranceBusinessService.class);
		ClaimOrderService claimOrderService = SpringContextHolder.getBean(ClaimOrderService.class);
		ClaimOrder order = claimOrderService.findClaimOrder("208cc16c71f743428647711fda8ed573");
		insuranceBusinessService.uploadPrescriptionDetail(order);
	}
}
