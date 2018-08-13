package com.itcss.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IWorkordermanageDao;
import com.itcss.bos.domain.Workordermanage;
import com.itcss.bos.service.IWorkordermanageService;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	
	@Autowired
	private IWorkordermanageDao WorkordermanageDao;
	@Override
	public void save(Workordermanage model) {
		WorkordermanageDao.saveOrUpdate(model);
	}

}
