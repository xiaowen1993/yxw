/**
 * 
 */
package com.yxw.framework.cache.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import com.yxw.framework.exception.SystemException;

/**
 * @Package: com.yxw.framework.cache.redis
 * @ClassName: RedisService
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-8
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface RedisService {

	/**
	 * 得到redis 连接池
	 * 
	 * @return
	 */
	public JedisPool getRedisPool() throws SystemException;

	/**
	 * 获取jedis
	 * 
	 * @return
	 */
	public Jedis getRedisClient() throws SystemException;

	/**
	 * 将jedis放入pool
	 * 
	 * @param jedis
	 * @param broken
	 */
	public void returnResource(Jedis jedis, boolean broken);

	/**
	 * 保存String类型的值
	 * 
	 * @param key
	 * @param value
	 * @return status code reply
	 */
	public Boolean set(String key, String value) throws SystemException;

	/**
	 * 得到Stirng类型的值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) throws SystemException;

	/**
	 * 保存对象的值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean set(String key, Object value) throws SystemException;

	/**
	 * 得到List对象数据
	 * 
	 * @param key
	 * @param clazz
	 *            list中元素的Class
	 * @return
	 */
	public <T> List<T> getList(String key, Class<T> clazz) throws SystemException;

	/**
	 * 根据key 和对象的类型 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) throws SystemException;

	public <T> T hget(String key, String field, Class<T> clazz);

	/**
	 * 删除缓存中得对象，根据keys
	 * 
	 * @param keys
	 * @return
	 */
	public Boolean del(String... keys) throws SystemException;

	/**
	 * 删除缓存中得对象，根据key
	 * 
	 * @param key
	 * @return
	 */
	public Boolean del(String key) throws SystemException;

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(String key) throws SystemException;

	/**
	 * 返回key所储存的值的类型
	 * 
	 * @param key
	 * @return none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
	 */
	public String type(String key) throws SystemException;

	/**
	 * 设置key生存时间,当key过期时,它会被自动删除。
	 * 
	 * @param key
	 * @param seconds
	 * @return 影响的记录数
	 */
	public Long expire(String key, int seconds) throws SystemException;

	/**
	 * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
	 * 
	 * @param key
	 * @param unixTime
	 *            已秒为单位
	 * @return 影响的记录数
	 */
	public Long expireAt(String key, long unixTime) throws SystemException;

	/**
	 * 查询key的过期时间
	 * 
	 * @param key
	 * @return 以秒为单位的时间表示
	 */
	public Long ttl(String key) throws SystemException;

	/**
	 * 取消对key过期时间的设置
	 * 
	 * @param key
	 * @return
	 */
	public Long persist(String key) throws SystemException;

	/**
	 * 批量保存Hash结构的数据
	 * 
	 * @param cacheKey
	 *            要保存的key
	 * @param dataMap
	 *            要保存的hash dataMap的String为field,List<Object>为value
	 * @return
	 */
	public List<Object> pipelinedHash(String cacheKey, Map<String, List<Object>> dataMap) throws SystemException;

	/**
	 * 批量保存/获取
	 * 
	 * @param setDataMap
	 *            要保存的Key-Value Map String-String
	 * @param getDataKeys
	 *            要获取的keys列表
	 * @return
	 */
	public List<Object> pipelined(Map<String, String> setDataMap, List<String> getDataKeys) throws SystemException;

	/**
	 * 查找所有匹配给定的模式的键 eg：key为foo和foobar 则keys foo*返回foo foobar
	 * 
	 * @param pattern
	 *            key的表达式,*表示多个,?表示一个
	 * @return
	 */
	public Set<String> keys(String pattern) throws SystemException;

	/**
	 * 批量把hash数据写入redis缓存服务器
	 * 
	 * @param datas
	 * @return
	 */
	public Boolean pipelineDatas(Map<String, Map<String, String>> datas);

	/**
	 * 批量把链表数据写入redis缓存服务器
	 * 
	 * @param datas
	 * @return
	 */
	public Boolean pipelineLDatas(Map<String, List<String>> datas);

	/**
	 * @param keys
	 * @param field
	 * @param valueType
	 *            存储的数据类型
	 * @return
	 */
	public <T> List<T> pipelineGetHDatas(Collection<String> keys, String field, Class<T> valueType);

	/**
	 * 同时得到多个key的value值
	 * 
	 * @param keys
	 * @return 返回数据库中多个key的value值
	 */
	public List<String> mget(final String... keys) throws SystemException;

	/**
	 * 同时给多个String赋值 如mset key1 value1 key2 value2
	 * 
	 * @param keysvalues
	 * @return
	 */
	public String mset(final String... keysvalues) throws SystemException;

	/**
	 * 得到key原来的value值，并把value更新为参数中的value
	 * 
	 * @param key
	 * @param value
	 * @return 当key存在时，返回key原来的value值 当key不存在，返回'nil'
	 */
	public String getSet(String key, String value) throws SystemException;

	/**
	 * 当不存在参数中的key时,才向数据库中添加该key
	 * 
	 * @param key
	 * @param value
	 * @return 返回值1表示设置成功,0表示失败
	 */
	public Long setnx(String key, String value) throws SystemException;

	/**
	 * 向数据库中添加String(名称为key,值为value)的同时设定过期时间 等价于是set和expire两个命令的结合.该操作是原子的
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public String setex(String key, int seconds, String value) throws SystemException;

	/**
	 * 名称为key的value值减去integer
	 * 
	 * @param key
	 * @param integer
	 * @return 返回value最终值 如果value不是int类型则返回错误
	 */
	public Long decrBy(String key, long integer) throws SystemException;

	/**
	 * 名称为key的value值减去1
	 * 
	 * @param key
	 * @return 返回最终值 如果key不存在，则将其初值设为0并返回减1后的值 如果value值不是int则会返回错误
	 */
	public Long decr(String key) throws SystemException;

	/**
	 * 名称为key的value值加上integer
	 * 
	 * @param key
	 * @param integer
	 * @return 返回最终值 如果value值不是int则会返回错误
	 */
	public Long incrBy(String key, long integer) throws SystemException;

	/**
	 * 名称为key的value值加上1
	 * 
	 * @param key
	 * @return 返回最终值 如果key不存在，则将其初值设为0并返回加1后的值 如果value值不是int则会返回错误
	 */
	public Long incr(String key) throws SystemException;

	/**
	 * 如果key存在，并且是String类型，则在其value值后附加上传入的value值。
	 * 如果key不存在则创建该key并设置为空字符后附加上value的值
	 * 
	 * @param key
	 * @param value
	 * @return 返回值是append操作后字符串的总长度
	 */
	public Long append(String key, String value) throws SystemException;

	/**
	 * 获得字符串中的子串 start和end可以使负数 -1代表最后一个字符 -2代表倒数第二个字符
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 返回指定范围的key的value值的子串
	 */
	public String substr(String key, int start, int end) throws SystemException;

	/**
	 * 得到string的key的value的长度
	 * 
	 * @param key
	 * @return 返回值为key的value值的字符数 当key不存在返回0
	 */
	public Long strlen(final String key) throws SystemException;

	/**
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public boolean setbit(String key, long offset, boolean value) throws SystemException;

	/**
	 * @param key
	 * @param offset
	 * @return
	 */
	public boolean getbit(String key, long offset) throws SystemException;

	/**
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Long setrange(String key, long offset, String value) throws SystemException;

	/**
	 * @param key
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */
	public String getrange(String key, long startOffset, long endOffset) throws SystemException;

	/******************************** Hash API Interface ********************************/
	/**
	 * 为指定的Key设定Field/Value对,如果Key不存在,该命令将创建新Key以参数中的Field/Value对;
	 * 如果参数中的Field在该Key中已经存在，则用新值覆盖其原有值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 写入成功返回1,更新操作返回0
	 */
	public Long hset(String key, String field, String value) throws SystemException;

	/**
	 * 得到名称为key的hash中的field对应的value值
	 * 
	 * @param key
	 * @param field
	 * @return 返回名称为key的hash中的field对应的value值 如果field不存在或者key不存在，则返回'nil'
	 */
	public String hget(String key, String field) throws SystemException;

	/**
	 * 添加对应关系,只有在fieid不存在时才执行
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 1：成功,0：失败或者fieid已存 并且value值不变
	 */
	public Long hsetnx(String key, String field, String value) throws SystemException;

	/**
	 * 添加对应关系,如果对应关系已存在,则覆盖
	 * 
	 * @param key
	 * @param hash
	 * @return 返回ok或者exception
	 */
	public String hmset(String key, Map<String, String> hash) throws SystemException;

	/**
	 * 根据多个key,获取对应的value,返回List,如果指定的key不存在,List对应位置为null
	 * 
	 * @param key
	 * @param fields
	 * @return 例如：hget multi 1 2 3 返回值是与field对应的value值,顺序与请求顺序一致
	 */
	public List<String> hmget(String key, String... fields) throws SystemException;

	/**
	 * 在指定的存储位置加上指定的数字,存储位置的值必须可转为数字类型 如果key不存在则新建一个,并把初值设为0
	 * 
	 * @param key
	 *            存储位置
	 * @param field
	 * @param value
	 *            要增加的值,可以是负数
	 * @return 增加指定数字后,存储位置的值
	 */
	public Long hincrBy(String key, String field, long value) throws SystemException;

	/**
	 * 判断hash中指定的存储是否存
	 * 
	 * @param key
	 * @param field
	 * @return 返回1表示名称为key的hash中存在field 0表示不存在
	 */
	public Boolean hexists(String key, String field) throws SystemException;

	/**
	 * 删除keys对应的记录,可以是多个key
	 * 
	 * @param String
	 *            keys
	 * @return 删除的记录数
	 * */
	public Long del(byte[]... keys);

	/**
	 * 从hash中删除指定的存储
	 * 
	 * @param key
	 * @param field
	 *            存储的名字
	 * @return 状态码,1成功,0失败
	 */
	public Long hdel(String key, String... field) throws SystemException;

	/**
	 * 获取hash中存储的个数,类似Map中size方法
	 * 
	 * @param key
	 * @return
	 */
	public Long hlen(String key) throws SystemException;

	/**
	 * 返回指定hash中的所有存储名字,类似Map中的keySet方法
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key) throws SystemException;

	/**
	 * 获取hash中value的集合
	 * 
	 * @param key
	 * @return
	 */
	public List<String> hvals(String key) throws SystemException;

	/**
	 * 以Map的形式返回hash中的存储和值
	 * 
	 * @param key
	 * @return 返回名称为key的hash中的所有field及其对应的value值
	 */
	public Map<String, String> hgetAll(String key) throws SystemException;

	/*** ----------------------------list start ---------------------------- **/
	/**
	 * 从链表的尾部插入参数中给出的value,插入顺序是从左到右依次插入(先进先出 )
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rpush(String key, String... value) throws SystemException;

	/**
	 * key键不存在,该命令会创建该键及与其关联的List,之后在将参数中的value从左到右依次插入
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lpush(String key, String value) throws SystemException;

	/**
	 * 链表中元素的数量
	 * 
	 * @param key
	 * @return
	 */
	public Long llen(String key) throws SystemException;

	/**
	 * 返回指定范围内元素的列表
	 * 
	 * @param key
	 * @param start
	 *            从0开始
	 * @param end
	 *            -1表示最后元素
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) throws SystemException;

	/**
	 * 删除指定范围内元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String ltrim(String key, long start, long end) throws SystemException;

	/**
	 * 返回请求的元素，如果index超出范围，则返回nil
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index) throws SystemException;

	/**
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public String lset(String key, long index, String value) throws SystemException;

	/**
	 * 指定Key关联的链表中,删除前count个值等于value的元素. 如果count大于0,从头向尾遍历并删除
	 * 如果count小于0，则从尾向头遍历并删除. 如果count等于0，则删除链表中所有等于value的元素。 如果指定的Key不存在，则直接返回0
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return 被删除的元素数量
	 */
	public Long lrem(String key, long count, String value) throws SystemException;

	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素,即头部元素.如果该Key不存,返回nil
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) throws SystemException;

	/**
	 * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素，。如果该Key不存，返回nil
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) throws SystemException;

	/**
	 * 向Set添加一条记录，如果member已存在返回0,否则返回1
	 * 
	 * @param key
	 * @param member
	 * @return 操作码,0或1
	 */
	public Long sadd(String key, String member) throws SystemException;

	/**
	 * 返回集合中的所有成员
	 * 
	 * @param key
	 * @return 成员集合
	 */
	public Set<String> smembers(String key) throws SystemException;

	/**
	 * 将成员从源集合移出放入目标集合 <br/>
	 * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0<br/>
	 * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
	 * 
	 * @param srckey
	 *            源集合
	 * @param dstkey
	 *            目标集合
	 * @param member
	 *            源集合中的成员
	 * @return 状态码,1成功,0失败
	 */
	public Long smove(String srckey, String dstkey, String member) throws SystemException;

	/**
	 * 从集合中删除指定成员
	 * 
	 * @param key
	 * @param member
	 *            要删除的成员
	 * @return 状态码，成功返回1，成员不存在返回0
	 */
	public Long srem(String key, String member) throws SystemException;

	/**
	 * 从集合中删除成员
	 * 
	 * @param key
	 * @return 被删除的成员
	 */
	public String spop(String key) throws SystemException;

	/**
	 * 获取key对应set中的元素个数
	 * 
	 * @param key
	 * @return 元素个数
	 */
	public Long scard(String key) throws SystemException;

	/**
	 * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存
	 * 
	 * @param keys
	 * @return 合并后的结果集合
	 */
	public Set<String> sunion(String... keys) throws SystemException;

	/**
	 * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
	 * 
	 * @param keys
	 * @return 交集成员的集合
	 */
	public Set<String> sinter(String... keys) throws SystemException;

	/**
	 * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
	 * 
	 * @param newkey
	 *            新集合的key
	 * @param keys
	 * @return
	 */
	public Long sunionstore(String newkey, String... keys) throws SystemException;

	/**
	 * 这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
	 * 
	 * @param newkey
	 *            新结果集的key
	 * @param keys
	 *            比较的集合
	 * @return 新集合中的记录数
	 */
	public Long sinterstore(String newkey, String... keys) throws SystemException;

	/**
	 * 确定一个给定的值 在set中是否存在
	 * 
	 * @param key
	 * @param member
	 * @return 存在返回1,不存在返回0
	 */
	public Boolean sismember(String key, String member) throws SystemException;

	public String srandmember(String key) throws SystemException;

	/**
	 * 向集合中增加一条记录,如果这个值已存在,这个值对应的权重将被置为新的权重
	 * 
	 * @param key
	 * @param score
	 *            权重
	 * @param member
	 *            要加入的值
	 * @return 状态码 1成功,0已存在member的值
	 */
	public Long zadd(String key, double score, String member) throws SystemException;

	/**
	 * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
	 * 
	 * @param key
	 * @param start
	 *            开始位置(包含)
	 * @param end
	 *            结束位置(包含)
	 * @return
	 */
	public Set<String> zrange(String key, int start, int end) throws SystemException;

	/**
	 * 从集合中删除成员
	 * 
	 * @param key
	 * @param member
	 * @return 返回1成功
	 */
	public Long zrem(String key, String member) throws SystemException;

	/**
	 * 删除
	 * 
	 * @param key
	 * @return
	 */
	public Long zrem(String key) throws SystemException;

	/**
	 * 权重增加给定值，如果给定的member已存在
	 * 
	 * @param key
	 * @param score
	 *            要增的权重
	 * @param member
	 *            要插入的值
	 * @return 增后的权重
	 */
	public Double zincrby(String key, double score, String member) throws SystemException;

	/**
	 * 获取指定值在集合中的位置，集合排序从低到高
	 * 
	 * @param key
	 * @param member
	 * @return member所处的位置
	 */
	public Long zrank(String key, String member) throws SystemException;

	/**
	 * 获取指定值在集合中的位置,集合排序从高到低
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long zrevrank(String key, String member) throws SystemException;

	/**
	 * 获取给定区间的元素,原始按照权重由高到低排序
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, int start, int end) throws SystemException;

	public Set<Tuple> zrangeWithScores(String key, int start, int end) throws SystemException;

	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) throws SystemException;

	/**
	 * 获取集合中元素的数量
	 * 
	 * @param key
	 * @return 如果返回0则集合不存在
	 */
	public Long zcard(String key) throws SystemException;

	/**
	 * 获取给定值在集合中的权重
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member) throws SystemException;

	/**
	 * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
	 * 
	 * @param key
	 * @return 集合的全部记录
	 */
	public List<String> sort(String key) throws SystemException;

	/**
	 * 对List,Set,SortSet进行排序或limit
	 * 
	 * @param key
	 * @param sortingParams
	 *            定义排序类型或limit的起止位置.
	 * @return 全部或部分记录
	 */
	public List<String> sort(String key, SortingParams sortingParams) throws SystemException;

	/**
	 * 获取指定权重区间内集合的数量
	 * 
	 * @param key
	 * @param min
	 *            最小排序位置
	 * @param max
	 *            最大排序位置
	 * @return
	 */
	public Long zcount(String key, double min, double max) throws SystemException;

	/**
	 * 返回指定权重区间的元素集合
	 * 
	 * @param key
	 * @param min
	 *            上限权重
	 * @param max
	 *            下限权重
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double min, double max) throws SystemException;

	public Set<String> zrevrangeByScore(String key, double max, double min) throws SystemException;

	public Set<String> zrangeByScore(String key, String min, String max) throws SystemException;

	public Set<String> zrevrangeByScore(String key, String max, String min) throws SystemException;

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) throws SystemException;

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) throws SystemException;

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) throws SystemException;

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) throws SystemException;

	/**
	 * 删除给定位置区间的元素
	 * 
	 * @param key
	 * @param start
	 *            开始区间,从0开始(包含)
	 * @param end
	 *            结束区间,-1为最后一个元素(包含)
	 * @return 删除的数量
	 */
	public Long zremrangeByRank(String key, int start, int end) throws SystemException;

	/**
	 * 删除给定权重区间的元素
	 * 
	 * @param key
	 * @param start
	 *            下限权重(包含)
	 * @param end
	 *            上限权重(包含)
	 * @return 删除的数量
	 */
	public Long zremrangeByScore(String key, double start, double end) throws SystemException;

	/**
	 * 删除给定权重区间的元素
	 * 
	 * @param key
	 * @param start
	 *            下限权重(包含)
	 * @param end
	 *            上限权重(包含)
	 * @return 删除的数量
	 */
	public Long zremrangeByScore(String key, String start, String end) throws SystemException;

	public Long linsert(String key, LIST_POSITION where, String pivot, String value) throws SystemException;

	public String set(byte[] key, byte[] value) throws SystemException;

	public byte[] get(byte[] key) throws SystemException;

	public Boolean exists(byte[] key) throws SystemException;

	public String type(byte[] key) throws SystemException;

	public Long expire(byte[] key, int seconds) throws SystemException;

	public Long expireAt(byte[] key, long unixTime) throws SystemException;

	public Long ttl(byte[] key) throws SystemException;

	public byte[] getSet(byte[] key, byte[] value) throws SystemException;

	public Long setnx(byte[] key, byte[] value) throws SystemException;

	public String setex(byte[] key, int seconds, byte[] value) throws SystemException;

	public Long decrBy(byte[] key, long integer) throws SystemException;

	public Long decr(byte[] key) throws SystemException;

	public Long incrBy(byte[] key, long integer) throws SystemException;

	public Long incr(byte[] key) throws SystemException;

	public Long append(byte[] key, byte[] value) throws SystemException;

	public byte[] substr(byte[] key, int start, int end) throws SystemException;

	public Long hset(byte[] key, byte[] field, byte[] value) throws SystemException;

	public byte[] hget(byte[] key, byte[] field) throws SystemException;

	public Long hsetnx(byte[] key, byte[] field, byte[] value) throws SystemException;

	public String hmset(byte[] key, Map<byte[], byte[]> hash) throws SystemException;

	public List<byte[]> hmget(byte[] key, byte[]... fields) throws SystemException;

	public Long hincrBy(byte[] key, byte[] field, long value) throws SystemException;

	public Boolean hexists(byte[] key, byte[] field) throws SystemException;

	public Long hdel(byte[] key, byte[] field) throws SystemException;

	public Long hlen(byte[] key) throws SystemException;

	public Set<byte[]> hkeys(byte[] key) throws SystemException;

	public Collection<byte[]> hvals(byte[] key) throws SystemException;

	public Map<byte[], byte[]> hgetAll(byte[] key) throws SystemException;

	public Long rpush(byte[] key, byte[] string) throws SystemException;

	public Long lpush(byte[] key, byte[] string) throws SystemException;

	public Long llen(byte[] key) throws SystemException;

	public List<byte[]> lrange(byte[] key, int start, int end) throws SystemException;

	public String ltrim(byte[] key, int start, int end) throws SystemException;

	public byte[] lindex(byte[] key, int index) throws SystemException;

	public String lset(byte[] key, int index, byte[] value) throws SystemException;

	public Long lrem(byte[] key, int count, byte[] value) throws SystemException;

	public byte[] lpop(byte[] key) throws SystemException;

	public byte[] rpop(byte[] key) throws SystemException;

	public Long sadd(byte[] key, byte[] member) throws SystemException;

	public Set<byte[]> smembers(byte[] key) throws SystemException;

	public Long srem(byte[] key, byte[] member) throws SystemException;

	public byte[] spop(byte[] key) throws SystemException;

	public Long scard(byte[] key) throws SystemException;

	public Boolean sismember(byte[] key, byte[] member) throws SystemException;

	public byte[] srandmember(byte[] key) throws SystemException;

	public Long zadd(byte[] key, double score, byte[] member) throws SystemException;

	public Long zadd(String key, Map<String, Double> scoreMembers) throws SystemException;

	public Set<byte[]> zrange(byte[] key, int start, int end) throws SystemException;

	public Long zrem(byte[] key, byte[] member) throws SystemException;

	public Double zincrby(byte[] key, double score, byte[] member) throws SystemException;

	public Long zrank(byte[] key, byte[] member) throws SystemException;

	public Long zrevrank(byte[] key, byte[] member) throws SystemException;

	public Set<byte[]> zrevrange(byte[] key, int start, int end) throws SystemException;

	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) throws SystemException;

	public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) throws SystemException;

	public Long zcard(byte[] key) throws SystemException;

	public Double zscore(byte[] key, byte[] member) throws SystemException;

	public List<byte[]> sort(byte[] key) throws SystemException;

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) throws SystemException;

	public Long zcount(byte[] key, double min, double max) throws SystemException;

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) throws SystemException;

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) throws SystemException;

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) throws SystemException;

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) throws SystemException;

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) throws SystemException;

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) throws SystemException;

	public Long zremrangeByRank(byte[] key, int start, int end) throws SystemException;

	public Long zremrangeByScore(byte[] key, double start, double end) throws SystemException;

	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) throws SystemException;

	public <T> T eval(String script);

	public <T> T eval(String script, int keyCount, String... params);

	public <T> T eval(String script, List<String> keys, List<String> args);

}