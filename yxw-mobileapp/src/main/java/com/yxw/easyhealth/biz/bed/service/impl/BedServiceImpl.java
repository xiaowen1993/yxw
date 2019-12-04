/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年12月21日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.easyhealth.biz.bed.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.yxw.easyhealth.biz.bed.BedConstsant;
import com.yxw.easyhealth.biz.bed.dto.BedResponse;
import com.yxw.easyhealth.biz.bed.service.BedService;
import com.yxw.easyhealth.biz.bed.util.SoapUtil;
import com.yxw.easyhealth.common.callable.QueryHashTableCallable;
import com.yxw.framework.common.http.HttpClientUtil;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月21日
 */
@Service(value = "bedService")
public class BedServiceImpl implements BedService {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BedServiceImpl.class);

	@Override
	public BedResponse callObstetricsBedWebservice(String hospitalName) {
		long startTime = Calendar.getInstance().getTimeInMillis();

		BedResponse bed = new BedResponse(hospitalName);

		String reqString = getSoapReqString(hospitalName);

		try {
			//			HttpResponse response =
			//					new HttpClientUtil(150, 20000, 20000, true).post(BedConstsant.OBSTETRICS_BED_WEBSERVICE_URL, reqString, HttpConstants.XML_TYPE,
			//							"utf-8");
			String responseString = HttpClientUtil.getInstance().post(BedConstsant.OBSTETRICS_BED_WEBSERVICE_URL, reqString);
			if (responseString != null) {
				XStream xstream = new XStream();
				xstream.processAnnotations(BedResponse.class); // 识别类中的注解

				String xml =
						SoapUtil.getSoapResponseElement(BedConstsant.SERVICE_NAME_PREFIX.concat(BedConstsant.SERVICE_NAME), responseString)
								.element(BedConstsant.SERVICE_NAME).asXML();
				//				String xml =
				//						SoapUtil.getSoapResponseElement(BedConstsant.SERVICE_NAME_PREFIX.concat(BedConstsant.SERVICE_NAME),
				//								response.getResponseAsString()).element(BedConstsant.SERVICE_NAME).asXML();
				bed = (BedResponse) xstream.fromXML(xml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("调用产科床位接口耗时:{},hospitalName:{}", ( Calendar.getInstance().getTimeInMillis() - startTime ), hospitalName);
		return bed;
	}

	@Override
	public List<BedResponse> callObstetricsBedWebserviceToList() {
		long startTime = Calendar.getInstance().getTimeInMillis();

		List<BedResponse> beds = new ArrayList<BedResponse>();
		String[] hospitals = BedConstsant.OBSTETRICS_BED_HOSPITAL_NAMES.split(",");

		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(hospitals.length);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

		for (String hospitalName : hospitals) {
			//beds.add(callObstetricsBedWebservice(hospitalName));

			Object[] parameters = new Object[] { hospitalName };
			Class<?>[] parameterTypes = new Class[] { String.class };

			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(BedService.class, "callObstetricsBedWebservice", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				BedResponse bed = (BedResponse) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				beds.add(bed);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		logger.info("调用产科床位接口耗时:{},hospitalNames:{}", ( Calendar.getInstance().getTimeInMillis() - startTime ),
				BedConstsant.OBSTETRICS_BED_HOSPITAL_NAMES);
		return beds;
	}

	private String getSoapReqString(String hospitalName) {
		String namespace = "http://webservicepublish.bsoft.com/";
		String prefix = "web";

		Element body = SoapUtil.genBodyElement(prefix, BedConstsant.SERVICE_NAME_PREFIX.concat(BedConstsant.SERVICE_NAME));

		body.addElement("hospitalName").setText(hospitalName);

		return SoapUtil.genSoapRequestString(prefix, namespace, body);
	}

}
