package com.itcss.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IUserDao;
import com.itcss.bos.domain.Role;
import com.itcss.bos.domain.User;
import com.itcss.bos.service.IUserService;
import com.itcss.bos.utils.MD5Utils;
import com.itcss.bos.utils.PageBean;

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

	@Override
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		if(roleIds!=null&&roleIds.length>0){
			for (String roleId : roleIds) {
				//手动构造托管对象，
				Role role = new Role(roleId);
				//为用户对象关联角色对象
				user.getRoles().add(role);
			}
		}
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}




}
