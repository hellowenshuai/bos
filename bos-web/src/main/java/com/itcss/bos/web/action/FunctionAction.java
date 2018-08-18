package com.itcss.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Function;
import com.itcss.bos.service.IFunctionService;
import com.itcss.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	@Autowired
	private IFunctionService functionService;

	// 显示所有菜单
	public String listajax() {
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[] { "parentFunction", "roles" });
		return NONE;
	}

	// 添加权限
	public String add() {
		functionService.save(model);
		return LIST;
	}

	// 权限分页查询
	public String pageQuery() {
		// pageBean里面有page属性，function里面也有page属性，struts2默认封装到model里面。
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "parentFunction", "roles", "children" });
		return NONE;
	}

	// 根据当前登陆人查询对应的菜单数据,返回json
	public String findMenu() {
		List<Function> list = functionService.findMenu();
		this.java2Json(list, new String[] { "parentFunction", "roles" ,"children"});
		return NONE;
	}

}
