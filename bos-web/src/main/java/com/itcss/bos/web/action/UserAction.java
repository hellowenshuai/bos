package com.itcss.bos.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.User;
import com.itcss.bos.service.IUserService;
import com.itcss.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	//验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	//service
	@Autowired
	private IUserService userService ; 

	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
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
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
		
	}
	
	
}
