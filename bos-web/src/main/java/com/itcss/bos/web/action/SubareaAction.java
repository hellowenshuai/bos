package com.itcss.bos.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Subarea;
import com.itcss.bos.service.ISubareaService;
import com.itcss.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	
	@Resource
	private ISubareaService subareaService;
	/**
	 * 添加分区
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		subareaService.save(model);
		return LIST;
	}

	
	
}
