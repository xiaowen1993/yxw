package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.task.manager.User;

public class RedisTestCollectCall implements Callable<String> {
	private static Logger logger = LoggerFactory.getLogger(RedisTestCollectCall.class);

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	private RedisService redisSvc;
	private User user;

	public RedisTestCollectCall(RedisService redisSvc, User user) {
		super();
		this.redisSvc = redisSvc;
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public String call() {
		String result = "FAIL";

		try {
			redisSvc.set(user.getId(), JSONObject.toJSONString(user));
			result = "OK";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	public RedisService getRedisSvc() {
		return redisSvc;
	}

	public void setRedisSvc(RedisService redisSvc) {
		this.redisSvc = redisSvc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
