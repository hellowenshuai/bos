package com.itcss.bos.service;

import com.itcss.bos.domain.Subarea;
import com.itcss.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

}
