package com.itcss.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Function;
import com.itcss.bos.domain.Role;
import com.itcss.bos.service.IRoleService;
import com.itcss.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	//属性驱动,接受权限的id
	
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	@Autowired
	private IRoleService roleService;
	
	//显示所有菜单
		public String listajax(){
			List<Role> list=roleService.findAll();
			this.java2Json(list, new String[]{"functions","users"});
			return NONE;
			
		}
	
	//添加角色
	public String add(){
		roleService.save(model,functionIds);
		return LIST;
	}
	//分页查询
	public String pageQuery(){
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
		
	}
}
