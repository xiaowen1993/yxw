package com.yxw.stats.task.project.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.stats.constants.CollectConstant;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.task.project.callable.DataMergeCollectCall;

public class DataMergeCollector {

	public static Logger logger = LoggerFactory.getLogger(DataMergeCollector.class);

	public void start() {
		// 根据采集的机器配置得出默认的线程数
		int threadNum = CollectConstant.threadNum;

		if (CommConstants.PLATFORMTYPES.size() < threadNum) {
			threadNum = CommConstants.PLATFORMTYPES.size();
		}

		if (threadNum > 0) {
			// 设置线程池的数量
			ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("dataMerge"));
			List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

			for (String platformType : CommConstants.PLATFORMTYPES) {
				DataMergeCollectCall collectCall = new DataMergeCollectCall(platformType);
				// 创建每条指令的采集任务对象
				FutureTask<String> collectTask = new FutureTask<String>(collectCall);

				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			}

			// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
			try {
				for (FutureTask<String> taskF : taskList) {
					// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
					String result = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
					if (!StringUtils.isBlank(result)) {
						//
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
			// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
			collectExec.shutdown();
		}
	}
}
