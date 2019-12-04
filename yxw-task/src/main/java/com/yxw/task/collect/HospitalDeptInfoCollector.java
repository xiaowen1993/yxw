package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.task.callable.HospitalDeptInfoCollectCall;
import com.yxw.task.vo.HospitalBaseInfo;

/**
 * @Package: com.yxw.platform.quartz.collect
 * @ClassName: HospitalCollector
 * @Statement: <p>
 *             医院基本信息 科室/医生信息采集
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalDeptInfoCollector {
	public static Logger logger = LoggerFactory.getLogger(HospitalDeptInfoCollector.class);

	public void start() {
		// 根据采集的机器配置得出默认的线程数
		int threadNum = CollectConstant.threadNum;

		//		HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
		//		ConcurrentHashMap<String, CodeAndInterfaceVo> codeAndInterfaceMap = hospitalCache.getValidCodeAndInterfaceMap();

		/*ConcurrentHashMap<String, CodeAndInterfaceVo> codeAndInterfaceMap = null;
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getValidCodeAndInterfaceMap", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			codeAndInterfaceMap = (ConcurrentHashMap<String, CodeAndInterfaceVo>) results.get(0);
			if (codeAndInterfaceMap.size() < threadNum) {
				threadNum = codeAndInterfaceMap.size();
			}*/
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<CodeAndInterfaceVo> codeAndInterfaceLs = null;
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getValidCodeAndInterfaceMap", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			codeAndInterfaceLs = new ArrayList<CodeAndInterfaceVo>(results.size());
			codeAndInterfaceLs.addAll(JSON.parseArray(source, CodeAndInterfaceVo.class));

			if (codeAndInterfaceLs.size() < threadNum) {
				threadNum = codeAndInterfaceLs.size();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("需要采集医院科室的医院:{}", JSON.toJSONString(codeAndInterfaceLs));
			}
			if (threadNum > 0) {
				// 设置线程池的数量
				ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("deptInfo"));
				// map中的key为2种 dept/doctor
				List<FutureTask<HospitalBaseInfo>> taskList = new ArrayList<FutureTask<HospitalBaseInfo>>();

				for (CodeAndInterfaceVo vo : codeAndInterfaceLs) {
					if (HospitalConstant.HOSPITAL_VALID_STATUS == vo.getStatus()) {
						String hospitalCode = vo.getHospitalCode();
						String branchHospitalCode = vo.getBranchHospitalCode();
						String interfaceId = vo.getInterfaceId();
						String hospitalId = vo.getHospitalId();
						String hospitalName = vo.getHospitalName();
						String areaCode = vo.getAreaCode();

						HospitalDeptInfoCollectCall collectCall =
								new HospitalDeptInfoCollectCall(hospitalId, hospitalCode, hospitalName, branchHospitalCode, interfaceId, areaCode);
						// 创建每条指令的采集任务对象
						FutureTask<HospitalBaseInfo> collectTask = new FutureTask<HospitalBaseInfo>(collectCall);

						// 添加到list,方便后面取得结果
						taskList.add(collectTask);
						// 提交给线程池 exec.submit(task);
						collectExec.submit(collectTask);
					}
				}

				// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑

				for (FutureTask<HospitalBaseInfo> taskF : taskList) {
					// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
					try {
						taskF.get(180, TimeUnit.SECONDS);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("HospitalDeptInfoCollector get task result time out.errorMsg:{}", e.getMessage());
					}
				}

				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();
			}
		}
	}
}
