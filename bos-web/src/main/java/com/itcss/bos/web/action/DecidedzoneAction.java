package com.itcss.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Decidedzone;
import com.itcss.bos.service.IDecidedzoneService;
import com.itcss.bos.web.action.base.BaseAction;
import com.itcss.crm.Customer;
import com.itcss.crm.ICustomerService;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	// 属性驱动，接收多个分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	@Autowired
	private IDecidedzoneService decidedzoneService;

	/**
	 * 添加定区
	 * 
	 * @return
	 */
	public String add() {
		decidedzoneService.save(model, subareaid);
		return LIST;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		decidedzoneService.pageQuery(pageBean);
		this.java2Json(pageBean,
				new String[] { "currentPage", "detachedCriteria", "pageSize", "subareas", "decidedzones" });
		return NONE;
	}

	// 注入crm代理对象
	@Autowired
	private ICustomerService proxy;

	/**
	 * crm中未关联到定区的客户
	 * 
	 * @return
	 * @throws IOException
	 */
	public String findListNotAssociation() throws IOException {
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	/**
	 * crm中已经关联到定区的客户
	 * 
	 * @return
	 * @throws IOException
	 */
	public String findListHasAssociation() throws IOException {
		String id = model.getId();
		List<Customer> list = proxy.findListHasAssociation(id);
		this.java2Json(list, new String[] {});
		return NONE;
	}

	// 属性驱动，接收页面提交的多个客户id
	private List<Integer> customerIds;

	/**
	 * 远程调用crm服务，将客户关联到定区
	 * 
	 * @return
	 * @throws IOException
	 */
	public String assigncustomerstodecidedzone() throws IOException {
		proxy.assigncustomerstodecidedzone(model.getId(),customerIds);
		return LIST;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

}
