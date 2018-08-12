package com.itcss.bos.web.action;

import java.io.IOException;
import java.util.List;

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
	/**分页查询
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		return NONE;
	}
	
	private String ids;
	
	/**
	 * 删除取派员
	 * @return
	 * @throws Exception
	 */
	public String deleteBatch() throws Exception {
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	/**
	 * 还原取派员
	 * @return
	 * @throws Exception
	 */
	public String restoreBatch() throws Exception {
		staffService.restoreBatch(ids);
		return LIST;
	}
	/**
	 * 修改取派员
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		//获取选择修改的数据
		Staff staff = staffService.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		
		staffService.update(staff);
		
		return LIST;
	}
	/**
	 * 查询所有未删除的取派员，返回json
	 * @return
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
		
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	

}
