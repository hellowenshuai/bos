package com.itcss.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IDecidedzoneDao;
import com.itcss.bos.dao.ISubareaDao;
import com.itcss.bos.dao.base.impl.BaseDaoImpl;
import com.itcss.bos.domain.Decidedzone;
import com.itcss.bos.domain.Subarea;
import com.itcss.bos.service.IDecidedzoneService;
import com.itcss.bos.utils.PageBean;

@Service
// 事务注解，不但可以加载实现类上，方法上，还可以添加在接口上
@Transactional
public class DecidedzoneServiceImpl extends BaseDaoImpl<Decidedzone> implements IDecidedzoneService {

	@Autowired
	private IDecidedzoneDao decidedzoneDao;

	@Autowired
	private ISubareaDao subareaDao;

	// 添加定区，同时关联分区
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			// 单方维护外键关系，不建议使用 配置文件中添加inverse属性。放弃维护外键
			// model.setSubareas(subareas);
			subarea.setDecidedzone(model);// 多方维护外键关系
		}

	}
	
	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
