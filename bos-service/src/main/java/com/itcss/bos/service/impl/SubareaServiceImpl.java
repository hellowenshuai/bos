package com.itcss.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.ISubareaDao;
import com.itcss.bos.domain.Subarea;
import com.itcss.bos.service.ISubareaService;
import com.itcss.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Resource
	private ISubareaDao subareaDao;
	
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	//查询所有未关联到定区的分区
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件，分区对象中decidedzone属性为null
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}
	//依据定区id，查询关联的分区
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//单表查询，不需要连接
		detachedCriteria.add(Restrictions.eq("decidedzone.id",decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria);
	}

}
