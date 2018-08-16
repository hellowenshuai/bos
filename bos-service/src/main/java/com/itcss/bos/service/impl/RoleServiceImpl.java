package com.itcss.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IRoleDao;
import com.itcss.bos.domain.Function;
import com.itcss.bos.domain.Role;
import com.itcss.bos.service.IRoleService;
import com.itcss.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;

	//保存一个角色，同时需要关联权限
	public void save(Role role,String functionIds) {
		roleDao.save(role);
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds =functionIds.split(",");
			for (String functionId : fIds) {
				//手动构造一个权限对象，设置id，对象状态为托管态
				Function function =new Function(functionId);
				//角色关联权限
				role.getFunctions().add(function);
			}
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
		return;
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
