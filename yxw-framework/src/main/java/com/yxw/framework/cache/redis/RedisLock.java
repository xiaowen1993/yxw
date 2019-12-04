/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yxcom Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.cache.redis;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.yxw.framework.exception.SystemException;

/**
 * @Package: com.yxw.framework.cache.redis
 * @ClassName: RedisLock
 * @Statement: <p>redis的锁</p>
 * @JDK version used: 
 * @Author: Yuce
 * @Create Date: 2015-5-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 0
 */
public class RedisLock {
	public static Logger logger = LoggerFactory.getLogger(RedisLock.class);

	/** 加锁标志 */
	public static final String LOCKED = "TRUE";

	/** 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒 */
	public static final long MILLI_NANO_CONVERSION = 1000 * 1000L;

	/** 默认超时时间（毫秒） */
	public static final long DEFAULT_TIME_OUT = 10000;

	public static final Random RANDOM = new Random();

	/** 锁的超时时间（秒），过期删除 */
	public static final int EXPIRE = 3 * 60;

	/** 切片****/
	private ShardedJedisPool shardedJedisPool;
	private ShardedJedis shardedJedis;

	/** 非切片 **/
	private JedisPool redisPool;
	private Jedis singleJedis;

	/** 切片redis锁状态标志  ***/
	private boolean shardedLocked = false;

	/** 单机redis锁状态标志  ***/
	private boolean singleLocked = false;

	/**
	 * This creates a shared RedisLock 
	 * @param key
	 * @param redisPool  
	 */
	public RedisLock(final ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
		this.shardedJedis = this.shardedJedisPool.getResource();
	}

	/**
	 * This creates a single RedisLock 
	 * @param key
	 * @param redisPool  
	 */
	public RedisLock(final JedisPool redisPool) {
		try {
			this.redisPool = redisPool;
			this.singleJedis = this.redisPool.getResource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("redis server connect exception!", e);
			throw new SystemException(e);
		}
	}

	public void setShardedJedisPool(final ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
		this.shardedJedis = this.shardedJedisPool.getResource();
	}

	public void setRedisPool(final JedisPool redisPool) {
		this.redisPool = redisPool;
		this.singleJedis = this.redisPool.getResource();
	}

	/**
	 * 单机redis加锁
	 * singleLock(); try {  doSomething(); } finally {  singleLock();} 的方式调用 
	 * @param key
	 * @param timeout 超时时间 
	 * @return 成功或失败标志 
	 */
	public boolean singleLock(String key, long timeout) {
		long nano = System.nanoTime();
		key = key.concat("_lock");
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ( ( System.nanoTime() - nano ) < timeout) {
				if (this.singleJedis.setnx(key, LOCKED) == 1) {
					this.singleJedis.expire(key, EXPIRE);
					this.singleLocked = true;
					return this.singleLocked;
				}
				// 短暂休眠，避免出现活锁  
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
			//return false;
		}
		return false;
	}

	/** 
	 * 单机redis加锁 
	 * 应该以：singleLock lock(); try { doSomething(); } finally {   singleUnlock(); } 的方式调用 
	 * @param timeout 超时时间 
	 * @param expire 锁的超时时间（秒），过期删除 
	 * @return 成功或失败标志 
	 */
	public boolean singleLock(String key, long timeout, int expire) {
		key = key.concat("_lock");
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ( ( System.nanoTime() - nano ) < timeout) {
				if (this.singleJedis.setnx(key, LOCKED) == 1) {
					this.singleJedis.expire(key, expire);
					this.singleLocked = true;
					return this.singleLocked;
				}
				// 短暂休眠，避免出现活锁  
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
			//return false;
		}
		return false;
	}

	/** 
	 * 单机redis加锁 
	 * 应该以：singleLock lock(); try { doSomething(); } finally { singleUnlock(); } 的方式调用 
	 * @return 成功或失败标志 
	 */
	public boolean singleLock(String key) {
		return singleLock(key, DEFAULT_TIME_OUT);
	}

	/** 
	 * 单机redis加锁 
	 * 无论是否加锁成功，都需要调用unlock 
	 */
	public void singleUnlock(String key) {
		key = key.concat("_lock");
		try {
			if (this.singleLocked) {
				this.singleJedis.del(key);
			}
		} finally {
			this.redisPool.returnResource(this.singleJedis);
		}
	}

	/******************************* sharded redis ******************************************/
	/** 
	 * 对切片redis群加锁 
	 * 应该以：shardedLock lock(); try { doSomething(); } finally {    shardedUnlock(); } 的方式调用 
	 * @param timeout 超时时间 
	 * @return 成功或失败标志 
	 */
	public boolean shardedLock(String key, long timeout) {
		long nano = System.nanoTime();
		key = key.concat("_lock");
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ( ( System.nanoTime() - nano ) < timeout) {
				if (this.shardedJedis.setnx(key, LOCKED) == 1) {
					this.shardedJedis.expire(key, EXPIRE);
					this.shardedLocked = true;
					return this.shardedLocked;
				}
				// 短暂休眠，避免出现活锁  
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			//throw new RuntimeException("Locking error", e);
			return false;
		}
		return false;
	}

	/** 
	 * 对切片redis群加锁  应该以： 
	 * 应该以：shardedLock lock(); try { doSomething(); } finally {    shardedUnlock(); } 的方式调用 
	 * @param timeout 超时时间 
	 * @param expire 锁的超时时间（秒），过期删除 
	 * @return 成功或失败标志 
	 */
	public boolean shardedLock(String key, long timeout, int expire) {
		key = key.concat("_lock");
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ( ( System.nanoTime() - nano ) < timeout) {
				if (this.shardedJedis.setnx(key, LOCKED) == 1) {
					this.shardedJedis.expire(key, expire);
					this.shardedLocked = true;
					return this.shardedLocked;
				}
				// 短暂休眠，避免出现活锁  
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			//throw new RuntimeException("Locking error", e);
			return false;
		}
		return false;
	}

	/** 
	 * 对切片redis群加锁
	 * 应该以：shardedLock lock(); try { doSomething(); } finally {    shardedUnlock(); } 的方式调用 
	 * @return 成功或失败标志 
	 */
	public boolean shardedLock(String key) {
		return shardedLock(key, DEFAULT_TIME_OUT);
	}

	/** 
	 * 对切片redis群解锁 
	 * 无论是否加锁成功，都需要调用unlock 
	 */
	public void shardedUnlock(String key) {
		key = key.concat("_lock");
		try {
			if (this.shardedLocked) {
				this.shardedJedis.del(key);
			}
		} finally {
			//this.shardedJedisPool.returnResource(this.shardedJedis);
			this.shardedJedisPool.returnResourceObject(this.shardedJedis);
		}
	}

}
