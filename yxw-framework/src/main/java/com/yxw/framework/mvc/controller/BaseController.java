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
package com.yxw.framework.mvc.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.base.entity.BaseEntity;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.utils.ReflectionUtils;
import com.yxw.utils.StringUtils;

/**
 * controller基类
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月2日
 * @param <T>
 * @param <PK>
 */
public abstract class BaseController<T extends BaseEntity, PK extends Serializable> {

	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 获取服务
	 * 
	 * @return BaseService
	 */
	protected abstract BaseService<T, PK> getService();

	public String getBasePath() {
		String entityName = ReflectionUtils.getSuperClassGenricType(this.getClass()).getSimpleName();
		return "/" + StringUtils.lowerFrist(entityName);
	}

	/**
	 * 获取会话中的数据
	 * 
	 * @param request
	 *            request请求参数
	 * @param key
	 *            键
	 * @return 返回属性
	 */
	public Object getSessionAttribute(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public RespBody delete(T entity) {
		getService().delete(entity);
		return new RespBody(Status.OK);
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteById")
	public RespBody deleteById(PK id) {
		if (id == null) {
			logger.error("要删除的ID号为null或空字符串！对象：{}", this.getClass().getName());
			return new RespBody(Status.ERROR, "没有传入要删除的ID号！");
		}
		getService().deleteById(id);
		return new RespBody(Status.OK);
	}

	/**
	 * 根据ID集合删除
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteByIds")
	public RespBody deleteByIds(List<PK> ids) {
		if (ids != null && ids.size() > 0) {
			logger.error("参数:{}不能为空！对象：{}", ids, this.getClass().getName());
			return new RespBody(Status.ERROR, "没有传入要删除的ID号数组！");
		}
		try {
			getService().batchDelete(ids);
		} catch (Exception e) {
			logger.error("批量删除对象失败！对象:" + this.getClass().getName(), e);
			return new RespBody(Status.ERROR, "批量删除失败！");
		}
		return new RespBody(Status.OK, ids.size());
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findById")
	public RespBody findById(PK id) {
		if (id == null) {
			logger.error("参数:{}不能为null或空字符串！对象:{}", id, this.getClass().getName());
			return new RespBody(Status.ERROR, "没有传入要查询的ID号！");
		}
		T t = getService().findById(id);
		return new RespBody(Status.OK, t);
	}

	/**
	 * 根据ID集合查询
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findByIds")
	public RespBody findByIds(List<PK> ids) {
		if (ids != null && ids.size() > 0) {
			logger.error("未设置批量删除对象的ID号！对象：{}", this.getClass().getName());
			return new RespBody(Status.ERROR, "没有传入要查询的ID集合！");
		}
		List<T> list = getService().findByIds(ids);
		return new RespBody(Status.OK, list);
	}

	/**
	 * 跳转列表页面
	 * 
	 * @param query
	 * @param pageBounds
	 * @return
	 */
	@RequestMapping(value = "/listView")
	public String listView() {
		return "/list";
	}

	/**
	 * 列表查询
	 * 
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findList")
	public RespBody findList(T entity) {
		List<T> list = getService().findList(entity);
		return new RespBody(Status.OK, list);
	}

	/**
	 * 查询所有记录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findAll")
	public RespBody findAll() {
		List<T> list = getService().findAll();
		return new RespBody(Status.OK, list);
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @param pageBounds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findListByPage")
	public RespBody findListByPage(T query, Page<T> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo<T> pageInfo = getService().findListByPage(map, page);
		map.put("page", pageInfo);
		map.put("query", query);
		return new RespBody(Status.OK, map);
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(PK id) {
		T t = null;
		if (id != null) {
			t = getService().findById(id);
		}
		return new ModelAndView(getBasePath() + "/edit", "entity", t);
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public RespBody save(T entity) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(entity.getId())) {
			getService().update(entity);
		} else {
			getService().add(entity);
		}
		return new RespBody(Status.OK, entity);
	}

	/**
	 * 检查数据是否已经存在
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check")
	public RespBody check(@RequestParam Map<String, Serializable> params) {
		boolean status = getService().check(params);
		if (status) {
			return new RespBody(Status.ERROR);
		} else {
			return new RespBody(Status.OK);
		}
	}
}
