/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月29日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.mvc.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.base.entity.BaseEntity;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.BaseService;

/**
 * service基类实现类
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年4月30日
 * @param <T>
 * @param <PK>
 */
public abstract class BaseServiceImpl<T extends BaseEntity, PK extends Serializable> implements BaseService<T, PK> {
	/**
	 * 获取数据库操作类
	 * 
	 * @return
	 */
	protected abstract BaseDao<T, PK> getDao();

	@Override
	public T find(T entity) {
		return getDao().find(entity);
	}

	@Override
	public T findById(PK id) {
		return getDao().findById(id);
	}

	@Override
	public List<T> findByIds(List<PK> ids) {
		return getDao().findByIds(ids);
	}

	@Override
	public List<T> findList(T entity) {
		return getDao().findList(entity);
	}

	@Override
	public PageInfo<T> findListByPage(Map<String, Object> parms, Page<T> page) {
		return getDao().findListByPage(parms, page);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public Long count() {
		return getDao().count();
	}

	@Override
	public Long count(T entity) {
		return getDao().count(entity);
	}

	@Override
	public PK add(T entity) {
		return getDao().add(entity);
	}

	@Override
	public void delete(T entity) {
		getDao().delete(entity);
	}

	@Override
	public void deleteById(PK id) {
		getDao().deleteById(id);
	}

	@Override
	public void deleteByIds(List<PK> ids) {
		getDao().deleteByIds(ids);
	}

	@Override
	public void deleteAll() {
		getDao().deleteAll();
	}

	@Override
	public void update(T entity) {
		getDao().update(entity);
	}

	@Override
	public boolean check(Map<String, Serializable> params) {
		return getDao().check(params);
	}

	@Override
	public void batchDelete(List<PK> ids) {
		getDao().batchDelete(ids);
	}

	@Override
	public void batchInsert(List<T> entitys) {
		getDao().batchInsert(entitys);
	}

	@Override
	public void batchUpdate(List<T> entitys) {
		getDao().batchUpdate(entitys);
	}

}
