package com.itcss.bos.service;

import com.itcss.bos.domain.User;

public interface IUserService {

	User login(User model);

	void editPassword(String id, String password);

}
