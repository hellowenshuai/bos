package com.itcss.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itcss.bos.dao.base.IBaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	// 代码某个实体的类型
	private Class<T> entityClass;

	@Resource // 根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	// 父类的构造方法，子类继承父类，会先调用父类的构造方法
	public BaseDaoImpl() {
		// this表示当前子类
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取父类上声明的泛型数组
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);

	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);

	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);

	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	// 执行更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			// 为HQL语句中的？赋值
			query.setParameter(i++, object);
		}
		// 执行更新
		query.executeUpdate();
	}
}
