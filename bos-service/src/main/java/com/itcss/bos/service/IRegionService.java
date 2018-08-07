package com.itcss.bos.service;

import java.util.List;

import com.itcss.bos.domain.Region;
import com.itcss.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> regionList);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findListByQ(String q);

}
