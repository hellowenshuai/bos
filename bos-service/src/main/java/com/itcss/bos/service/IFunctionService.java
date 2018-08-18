package com.itcss.bos.service;

import java.util.List;

import com.itcss.bos.domain.Function;
import com.itcss.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu();


}
