package com.itcss.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IFunctionDao;
import com.itcss.bos.domain.Function;
import com.itcss.bos.service.IFunctionService;
import com.itcss.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	
	@Autowired
	private IFunctionDao functionDao;
	//查询所有菜单
	public List<Function> findAll() {
		List<Function> list = functionDao.findAll();
		return list;
	}
	@Override
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction!=null&&parentFunction.getId().equals("")){
			//Function里面的父类不为空，且它的id不为空字符
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

}
