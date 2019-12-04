/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月30日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.mvc.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.base.entity.BaseEntity;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.utils.BeanUtils;

/**
 * 基础Dao接口实现类，实现该类的子类必须设置泛型类型
 * 
 * @author LiangJinXin
 * @version 1.0
 * @since 2015年4月30日
 * @param <T>
 * @param <PK>
 */
public class BaseDaoImpl<T extends BaseEntity, PK extends Serializable> implements BaseDao<T, PK> {
	private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	@Resource
	protected SqlSessionTemplate sqlSession;

	private final String SQLNAME_SEPARATOR = ".";

	private String sqlMapperNamespace = getDefaultSqlMapperNamespace();

	private final static String SQLNAME_FIND = "find";
	private final static String SQLNAME_FIND_BY_ID = "findById";
	private final static String SQLNAME_FIND_BY_IDS = "findByIds";
	private final static String SQLNAME_FIND_LIST = "findList";
	private final static String SQLNAME_FIND_LIST_BY_PAGE = "findListByPage";
	private final static String SQLNAME_FIND_ALL = "findAll";
	private final static String SQLNAME_ADD = "add";
	private final static String SQLNAME_DELETE = "delete";
	private final static String SQLNAME_DELETE_BY_ID = "deleteById";
	private final static String SQLNAME_DELETE_BY_IDS = "deleteByIds";
	private final static String SQLNAME_DELETE_ALL = "deleteAll";
	private final static String SQLNAME_UPDATE = "update";
	private final static String SQLNAME_CHECK = "check";
	private final static String SQLNAME_BATCH_DELETE = "batchDelete";
	private final static String SQLNAME_BATCH_INSERT = "batchInsert";
	private final static String SQLNAME_BATCH_UPDATE = "batchUpdate";
	private final static String SQLNAME_COUNT = "count";

	/**
	 * @return the sqlMapperNamespace
	 */

	public String getSqlMapperNamespace() {
		return sqlMapperNamespace;
	}

	/**
	 * @param sqlMapperNamespace
	 *            the sqlMapperNamespace to set
	 */

	public void setSqlMapperNamespace(String sqlMapperNamespace) {
		this.sqlMapperNamespace = sqlMapperNamespace;
	}

	/**
	 * @return the sqlSession
	 */

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	/**
	 * @param sqlSession
	 *            the sqlSession to set
	 */

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 获取泛型类型的实体对象类全名
	 * 
	 * @return
	 */
	private String getDefaultSqlMapperNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}

	/**
	 * 将sqlMapperNamespace与给定的sqlName组合在一起.
	 * 
	 * @param sqlName
	 * @return
	 */
	protected String getSqlName(String sqlName) {
		return sqlMapperNamespace + SQLNAME_SEPARATOR + sqlName;
	}

	@Override
	public T find(T entity) {
		try {
			Assert.notNull(entity);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND), entity);
		} catch (Exception e) {
			logger.error(String.format("查询对象出错！语句：%s", getSqlName(SQLNAME_FIND)), e);
			throw new SystemException(String.format("查询对象出错！语句：%s", getSqlName(SQLNAME_FIND)), e);
		}
	}

	@Override
	public T findById(PK id) {
		try {
			Assert.notNull(id);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ID), id);
		} catch (Exception e) {
			logger.error(String.format("根据ID查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
			throw new SystemException(String.format("根据ID查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ID)), e);
		}
	}

	@Override
	public List<T> findByIds(List<PK> ids) {
		try {
			Assert.notNull(ids);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_IDS), ids);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_BY_IDS)), e);
			throw new SystemException(String.format("根据ID集合查询对象出错！语句：%s", getSqlName(SQLNAME_FIND_BY_IDS)), e);
		}
	}

	@Override
	public List<T> findList(T entity) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST), entity);
		} catch (Exception e) {
			logger.error(String.format("查询对象列表出错！语句：%s", getSqlName(SQLNAME_FIND_LIST)), e);
			throw new SystemException(String.format("查询对象列表出错！语句：%s", getSqlName(SQLNAME_FIND_LIST)), e);
		}
	}

	@Override
	public List<T> findAll() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL));
		} catch (Exception e) {
			logger.error(String.format("查询所有对象列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
			throw new SystemException(String.format("查询所有对象列表出错！语句：%s", getSqlName(SQLNAME_FIND_ALL)), e);
		}
	}

	@Override
	public PageInfo<T> findListByPage(Map<String, Object> params, Page<T> page) {
		try {
			// List<T> list =
			// sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PAGE),
			// parms,
			// new RowBounds(page.getPageNum(), page.getPageSize()));
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<T> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PAGE), params);
			return new PageInfo<T>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
		}
	}

	@Override
	public Long count() {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_COUNT));
		} catch (Exception e) {
			logger.error(String.format("查询对象总记录数出错！语句：%s", getSqlName(SQLNAME_COUNT)), e);
			throw new SystemException(String.format("查询对象总记录数出错！语句：%s", getSqlName(SQLNAME_COUNT)), e);
		}
	}

	@Override
	public Long count(T entity) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_COUNT), entity);
		} catch (Exception e) {
			logger.error(String.format("查询对象总记录数出错！语句：%s", getSqlName(SQLNAME_COUNT)), e);
			throw new SystemException(String.format("查询对象总记录数出错！语句：%s", getSqlName(SQLNAME_COUNT)), e);
		}
	}

	@Override
	public void delete(T entity) {
		try {
			Assert.notNull(entity);
			sqlSession.delete(getSqlName(SQLNAME_DELETE), entity);
		} catch (Exception e) {
			logger.error(String.format("删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE)), e);
			throw new SystemException(String.format("删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE)), e);
		}
	}

	@Override
	public void deleteById(PK id) {
		try {
			Assert.notNull(id);
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_ID), id);
		} catch (Exception e) {
			logger.error(String.format("根据ID删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_ID)), e);
			throw new SystemException(String.format("根据ID删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_ID)), e);
		}
	}

	@Override
	public void deleteByIds(List<PK> ids) {
		try {
			Assert.notNull(ids);
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_IDS), ids);
		} catch (Exception e) {
			logger.error(String.format("根据ID集合删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_IDS)), e);
			throw new SystemException(String.format("根据ID集合删除对象出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_IDS)), e);
		}

	}

	@Override
	public void deleteAll() {
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_ALL));
		} catch (Exception e) {
			logger.error(String.format("删除所有对象出错！语句：%s", getSqlName(SQLNAME_DELETE_ALL)), e);
			throw new SystemException(String.format("删除所有对象出错！语句：%s", getSqlName(SQLNAME_DELETE_ALL)), e);
		}
	}

	@Override
	public PK add(T entity) {
		try {
			Assert.notNull(entity);
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(PKGenerator.generateId());
			}
			sqlSession.insert(getSqlName(SQLNAME_ADD), entity);
			return (PK) entity.getId();
		} catch (Exception e) {
			logger.error(String.format("保存对象出错！语句：%s", getSqlName(SQLNAME_ADD)), e);
			throw new SystemException(String.format("保存对象出错！语句：%s", getSqlName(SQLNAME_ADD)), e);
		}
	}

	@Override
	public void update(T entity) {
		try {
			Assert.notNull(entity);
			sqlSession.update(getSqlName(SQLNAME_UPDATE), entity);
		} catch (Exception e) {
			logger.error(String.format("更新对象出错！语句：%s", getSqlName(SQLNAME_UPDATE)), e);
			throw new SystemException(String.format("更新对象出错！语句：%s", getSqlName(SQLNAME_UPDATE)), e);
		}
	}

	@Override
	public boolean check(Map<String, Serializable> params) {
		try {
			T t = sqlSession.selectOne(getSqlName(SQLNAME_CHECK), params);
			if (t != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(String.format("检查数据是否已经存在出错！语句：%s", getSqlName(SQLNAME_CHECK)), e);
			throw new SystemException(String.format("检查数据是否已经存在出错！语句：%s", getSqlName(SQLNAME_CHECK)), e);
		}
	}

	@Override
	public void batchDelete(List<PK> ids) {
		try {
			Assert.notNull(ids);
			sqlSession.delete(getSqlName(SQLNAME_BATCH_DELETE), ids);
		} catch (Exception e) {
			logger.error(String.format("批量删除对象出错！语句：%s", getSqlName(SQLNAME_BATCH_DELETE)), e);
			throw new SystemException(String.format("批量删除对象出错！语句：%s", getSqlName(SQLNAME_BATCH_DELETE)), e);
		}
	}

	@Override
	public void batchInsert(List<T> entitys) {
		try {
			Assert.notNull(entitys);
			for (T entity : entitys) {
				/**
				 * 增加判断，如果添加数据已经存在ID则不需要设置 2015年7月7日 18:00:14 周鉴斌
				 */
				if (StringUtils.isBlank(entity.getId())) {
					entity.setId(PKGenerator.generateId());
				}
			}
			sqlSession.insert(getSqlName(SQLNAME_BATCH_INSERT), entitys);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT)), e);
		}
	}

	@Override
	public void batchUpdate(List<T> entitys) {
		try {
			Assert.notNull(entitys);
			sqlSession.update(getSqlName(SQLNAME_BATCH_UPDATE), entitys);
		} catch (Exception e) {
			logger.error(String.format("批量更新对象出错！语句：%s", getSqlName(SQLNAME_BATCH_UPDATE)), e);
			throw new SystemException(String.format("批量更新对象出错！语句：%s", getSqlName(SQLNAME_BATCH_UPDATE)), e);
		}
	}

	public static void main(String[] args) {
		System.out.println(PKGenerator.generateId());
	}
}
