package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.task.callable.RedisTestCollectCall;
import com.yxw.task.manager.User;

public class RedisTestCollector {
	public static Logger logger = LoggerFactory.getLogger(RedisTestCollector.class);

	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	public void start() {
		// 根据采集的机器配置得出默认的线程数 
		int threadNum = 2;

		List<User> results = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setId(UUID.randomUUID().toString());
			user.setName(user.getId() + i);
			user.setAge(String.valueOf(new Random().nextInt(100)));
			user.setSexy(String.valueOf(new Random().nextInt(2)));
			user.setMobile(getMobile(11));
			user.setAddress(UUID.randomUUID().toString());

			results.add(user);
		}

		if (CollectionUtils.isNotEmpty(results)) {

			if (threadNum > 0) {

				//设置线程池的数量
				ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("redisTest"));
				List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();
				for (User user : results) {

					RedisTestCollectCall collectCall = new RedisTestCollectCall(redisSvc, user);
					// 创建每条指令的采集任务对象
					FutureTask<String> collectTask = new FutureTask<String>(collectCall);
					// 添加到list,方便后面取得结果
					taskList.add(collectTask);
					// 提交给线程池 exec.submit(task);
					collectExec.submit(collectTask);

				}

				// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
				List<String> records = new ArrayList<String>();
				try {

					for (FutureTask<String> taskF : taskList) {
						// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
						String collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
						if (collectData != null) {
							if (!StringUtils.isEmpty(collectData)) {

							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();

			}
		}
	}

	public static String getMobile(int count) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int num = r.nextInt(10);
			sb.append(num);
		}
		return sb.toString();
	}
}
