package com.itcss.bos.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.itcss.bos.dao.IUserDao;
import com.itcss.bos.dao.base.impl.BaseDaoImpl;
import com.itcss.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		System.out.println("list"+list);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	

}
