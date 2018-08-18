package com.itcss.bos.dao;

import java.util.List;

import com.itcss.bos.dao.base.IBaseDao;
import com.itcss.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	public List<Function> findAll();
	
	public List<Function> findFunctionListBYUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);

}
