package com.itcss.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IStaffDao;
import com.itcss.bos.domain.Staff;
import com.itcss.bos.service.IStaffService;
import com.itcss.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	
	@Autowired
	private IStaffDao staffDao;
	
	@Override
	public void save(Staff model) {
		staffDao.save(model);

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 取派员批量删除
	 * 逻辑删除，将deltag改为1
	 */
	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.executeUpdate("staff.delete", id);
			}
		}
		
	}

	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.executeUpdate("staff.restore", id);
			}
		}
		
	}

	@Override
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	@Override
	public void update(Staff staff) {
		staffDao.update(staff);
		
	}

	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件 deltag等于0，表示未删除
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(detachedCriteria);
	}

}
