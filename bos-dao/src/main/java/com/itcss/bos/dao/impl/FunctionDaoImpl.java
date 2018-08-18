package com.itcss.bos.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.itcss.bos.dao.IFunctionDao;
import com.itcss.bos.dao.base.impl.BaseDaoImpl;
import com.itcss.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {

	public List<Function> findAll() {
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Function> findFunctionListBYUserId(String UserId) {
		
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u "
				+ "WHERE u.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql,UserId);
		return list;
	}

	@Override
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex ASC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Function> findMenuByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u "
				+ "WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex ASC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql,userId);
		return list;
	}


	


	
}
