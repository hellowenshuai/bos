package com.itcss.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IUserDao;
import com.itcss.bos.domain.User;
import com.itcss.bos.service.IUserService;
import com.itcss.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public User login(User model) {
		String password = MD5Utils.md5(model.getPassword());
		return userDao.findUserByUsernameAndPassword(model.getUsername(),password);
	}

	@Override
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword",password,id);
	}

}
