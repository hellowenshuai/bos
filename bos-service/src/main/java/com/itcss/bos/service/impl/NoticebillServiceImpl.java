package com.itcss.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcss.bos.dao.IDecidedzoneDao;
import com.itcss.bos.dao.INoticebillDao;
import com.itcss.bos.dao.IWorkbillDao;
import com.itcss.bos.domain.Decidedzone;
import com.itcss.bos.domain.Noticebill;
import com.itcss.bos.domain.Staff;
import com.itcss.bos.domain.User;
import com.itcss.bos.domain.Workbill;
import com.itcss.bos.service.INoticebillService;
import com.itcss.bos.utils.BOSUtils;
import com.itcss.crm.ICustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
	
	@Autowired
	private INoticebillDao noticebillDao;

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	
	@Autowired
	private IWorkbillDao workbillDao;
	
	@Autowired
	private ICustomerService customerService;

	// 保存业务通知单，还有尝试自动分单
	public void save(Noticebill model) {
		User user = BOSUtils.getLoginUser();
		model.setUser(user);//设置当前登陆用户
		noticebillDao.save(model);
		//获得客户的取件地址
		String pickaddress = model.getPickaddress();
		//远程调用crm服务，根据取件地址获取客户所属定区id
		String  decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId !=null){
			//查询到了定区id，可以完成自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员对象
			model.setStaff(staff);
			//设置分单类型：自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//为取派员生成一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//创建时间，当前系统时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//取件状态，未取件
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);
			workbillDao.save(workbill);
			//调用短信平台，发送短信
		}else{
			//没有查询到定区id，不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		
	}

}
