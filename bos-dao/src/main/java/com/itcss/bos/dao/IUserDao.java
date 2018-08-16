package com.itcss.bos.dao;

import com.itcss.bos.dao.base.IBaseDao;
import com.itcss.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findByUserName(String username);

}
