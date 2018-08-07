package com.itcss.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Staff;
import com.itcss.bos.service.IStaffService;
import com.itcss.bos.utils.PageBean;
import com.itcss.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	//忘记添加set/get方法，会查询出错
	private int page;
	private int rows;
	
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
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		//创建离线提交查询对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		staffService.pageQuery(pageBean);
		//使用json-lib将PageBean对象转为json，通过输出流写回页面中
		//JSONObject---将单一对象转为json
		//JSONArray----将数组或者集合对象转为json
		//设置json返回的格式
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		String json = JSONObject.fromObject(pageBean).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(json);
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
	
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	

}
