package com.itcss.bos.dao;

import java.util.List;

import com.itcss.bos.dao.base.IBaseDao;
import com.itcss.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region>{

	List<Region> findListByQ(String q);

}
