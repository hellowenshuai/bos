package com.itcss.bos.service;

import java.util.List;

import com.itcss.bos.domain.Subarea;
import com.itcss.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public List<Subarea> findListNotAssociation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

}
