package com.itcss.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Staff;
import com.itcss.bos.service.IStaffService;
import com.itcss.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	
	@Autowired
	private IStaffService staffService;
	
	public String add(){
		staffService.save(model);
		
		return LIST;
		
	}

}
