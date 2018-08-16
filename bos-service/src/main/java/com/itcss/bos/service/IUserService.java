package com.itcss.bos.service;

import java.util.List;

import com.itcss.bos.domain.User;
import com.itcss.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
