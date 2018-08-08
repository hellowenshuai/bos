package com.itcss.bos.service;

import com.itcss.bos.domain.Decidedzone;
import com.itcss.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

}
