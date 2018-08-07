package com.itcss.bos.service;

import java.util.List;

import com.itcss.bos.domain.Region;

public interface IRegionService {

	public void saveBatch(List<Region> regionList);

}
