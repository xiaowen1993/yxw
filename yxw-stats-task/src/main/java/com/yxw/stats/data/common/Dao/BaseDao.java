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
package com.yxw.stats.data.common.Dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * dao基类接口
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年4月29日
 * @param <T>
 * @param <PK>
 */
public interface BaseDao<T, PK extends Serializable> {

	/**
	 * 查询
	 * 
	 * @param entity
	 * @return
	 */
	public T find(T entity);

	/**
	 * 通过Id查询
	 * 
	 * @param id
	 * @return
	 */
	public T findById(PK id);

	/**
	 * 根据ID集合来查询
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<PK> ids);

	/**
	 * 查询列表
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);

	/**
	 * 查询列表
	 * 
	 * @param params
	 * @param pageBounds
	 * @return
	 */
	public PageInfo<T> findListByPage(Map<String, Object> params, Page<T> page);

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 查询总记录数
	 * 
	 * @return
	 */
	public Long count();

	/**
	 * 查询总记录数
	 * 
	 * @param entity
	 * @return
	 */
	public Long count(T entity);

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	public PK add(T entity);

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 根据Id删除
	 * 
	 * @param id
	 */
	public void deleteById(PK id);

	/**
	 * 根据ID集合删除
	 * 
	 * @param ids
	 */
	public void deleteByIds(List<PK> ids);

	/**
	 * 删除所有记录
	 */
	public void deleteAll();

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 检查数据是否已经存在
	 * @param params
	 * @return
	 */
	public boolean check(Map<String, Serializable> params);

	/**
	 * 根据ID集合批量删除
	 * 
	 * @param ids
	 */
	public void batchDelete(List<PK> ids);

	/**
	 * 批量插入
	 * 
	 * @param entitys
	 */
	public void batchInsert(List<T> entitys);

	/**
	 * 批量更新
	 * 
	 * @param entitys
	 */
	public void batchUpdate(List<T> entitys);

}
