package com.itcss.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Role;
import com.itcss.bos.domain.User;
import com.itcss.bos.service.IUserService;
import com.itcss.bos.utils.BOSUtils;
import com.itcss.bos.utils.MD5Utils;
import com.itcss.bos.web.action.base.BaseAction;
import com.itcss.crm.Customer;
import com.itcss.crm.ICustomerService;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	//验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	//属性驱动
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	//service
	@Autowired
	private IUserService userService ; 
	
	/**
	 * 添加用户
	 * @return
	 */
	public String add(){
		userService.save(model,roleIds);
		return LIST;
	}
	
	/**
	 * 用户数据分页查询
	 * @return
	 */
	public String pageQuery(){
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
		
	}

	/**
	 * 使用shiro框架实现用户登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		//1.从session中获取输入的验证码
		String validatecode  = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//2.校验验证码是否正确
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validatecode)){
			//使用shiro方式进行验证
			Subject subject = SecurityUtils.getSubject();//获得当前登陆用户对象，状态‘未认证’
			//用户名密码令牌
			AuthenticationToken token =	 new UsernamePasswordToken(model.getUsername(),MD5Utils.md5(model.getPassword()));

			try {
				subject.login(token);
				User user =(User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
			return HOME;
		}else{
			//2.2.错误
			this.addActionError("验证码输入错误");
			return LOGIN;
		}
		
	}
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String login_bak() throws Exception {
//		List<Customer> all = proxy.findAll();
//		System.out.println(all);
		//1.从session中获取输入的验证码
		String validatecode  = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//2.校验验证码是否正确
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validatecode)){
			//2.1.正确
			User user = userService.login(model);
			if(user!=null){
				//登录成功
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			}else{
				this.addActionError("用户名或者密码输入错误");
				return LOGIN;
			}
			
		}else{
			//2.2.错误
			this.addActionError("验证码输入错误");
			return LOGIN;
		}
		
	}
	/**
	 * 用户注销
	 * @return
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
		
	}
	/**
	 * 修改当前用户密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		String f = "1";
		//获取当前登录用户
		User user = BOSUtils.getLoginUser();
		try{
			userService.editPassword(user.getId(),model.getPassword());
		}catch(Exception e){
			f = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
}
