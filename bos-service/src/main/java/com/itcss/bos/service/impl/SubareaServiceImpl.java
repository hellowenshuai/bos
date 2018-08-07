package com.itcss.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.ISubareaDao;
import com.itcss.bos.domain.Subarea;
import com.itcss.bos.service.ISubareaService;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Resource
	private ISubareaDao subareaDao;
	
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

}
