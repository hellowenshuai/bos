package com.itcss.bos.service;



import java.util.List;

import com.itcss.bos.domain.Role;
import com.itcss.bos.utils.PageBean;
public interface IRoleService {

	public void save(Role role, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
