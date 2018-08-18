package com.itcss.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itcss.bos.dao.IFunctionDao;
import com.itcss.bos.dao.IUserDao;
import com.itcss.bos.domain.Function;
import com.itcss.bos.domain.User;

public class BOSRealm extends AuthorizingRealm {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;

	// 认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("realm中的认证方法执行了");
		UsernamePasswordToken mytoken = (UsernamePasswordToken) token;
		String username = mytoken.getUsername();
		// 根据用户名查询数据库中的密码
		User user = userDao.findByUserName(username);
		if (user == null) {
			// 用户名不存在
			return null;
		}
		//如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
		AuthenticationInfo  info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		return info;
	}

	// 授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获得当前用户对象 2种方式
		User user = (User) SecurityUtils.getSubject().getPrincipal();
//		User user2 = (User) principals.getPrimaryPrincipal();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			list = functionDao.findByCriteria(detachedCriteria);
		}else{
			list = functionDao.findFunctionListBYUserId(user.getId());
		}
		
		//为用户授权
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}
