package com.itcss.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IStaffDao;
import com.itcss.bos.domain.Staff;
import com.itcss.bos.service.IStaffService;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	
	@Autowired
	private IStaffDao staffDao;
	
	@Override
	public void save(Staff model) {
		staffDao.save(model);

	}

}
