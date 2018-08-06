package com.itcss.bos.web.interceptor;

import com.itcss.bos.domain.User;
import com.itcss.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//获取当前登陆用户
		User user = BOSUtils.getLoginUser();
		if(user==null){
			//没有登陆
			return "login";
		}
		//放行
		return invocation.invoke();
	}

}
