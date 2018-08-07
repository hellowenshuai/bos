package com.itcss.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itcss.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	public static final java.lang.String HOME = "home";
	public static final java.lang.String LIST = "list";
	protected PageBean pageBean = new PageBean();
	//创建离线提交查询对象
	DetachedCriteria detachedCriteria  = null;
	protected T model;
	

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	// model对象如何创建
	@Override
	public T getModel() {
		return model;
	}

	/**
	 * 单个元素
	 * @param o
	 * @param exclueds
	 */
	public void java2Json(Object o,String[] exclueds){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 数组，集合
	 * @param o
	 * @param exclueds
	 */
	public void java2Json(List o,String[] exclueds){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclueds);
		String json = JSONArray.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 无参构造，利用反射生成model对象
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();

		// 获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);

		try {
			// 通过反射创建对象
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
