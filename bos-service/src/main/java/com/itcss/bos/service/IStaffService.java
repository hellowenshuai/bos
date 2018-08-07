package com.itcss.bos.service;

import com.itcss.bos.domain.Staff;
import com.itcss.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public void restoreBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

}
