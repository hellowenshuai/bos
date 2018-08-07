package com.itcss.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IRegionDao;
import com.itcss.bos.domain.Region;
import com.itcss.bos.service.IRegionService;
import com.itcss.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private IRegionDao regionDao;
	/**
	 * 区域数据批量保存
	 */
	public void saveBatch(List<Region> regionList) {
		for (Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}
	@Override
	public List<Region> findAll() {
		return regionDao.findAll();
	}
	@Override
	public List<Region> findListByQ(String q) {
		return regionDao.findListByQ(q);
	}

	

}
