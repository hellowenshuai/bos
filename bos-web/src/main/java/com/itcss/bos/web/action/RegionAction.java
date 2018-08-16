package com.itcss.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcss.bos.domain.Region;
import com.itcss.bos.service.IRegionService;
import com.itcss.bos.utils.PinYin4jUtils;
import com.itcss.bos.web.action.base.BaseAction;

/**
 * 区域管理
 * 
 * @author chenwen
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	// 属性驱动
	private File regionFile;

	@Autowired
	private IRegionService regionService;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	/**
	 * 导入Excel类型文件 @return @throws IOException @throws
	 */
	public String importXls() throws IOException {
		List<Region> regionList = new ArrayList<Region>();
		// 包装一个excel文件对象，传入前台导入的文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		// 读取第一sheet标签页
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		// 遍历标签页中的所有行
		for (Row row : hssfSheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			// 包装一个区域对象
			Region region = new Region(id, province, city, district, postcode, null, null, null);

			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			// 城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			regionList.add(region);
		}
		// 批量保存
		regionService.saveBatch(regionList);

		return NONE;
	}

	/**
	 * 区域分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pageQuery() throws Exception {
		regionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize", "subareas" });
		return NONE;
	}

	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 查询所有区域，写回json数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listajax() throws Exception {
		List<Region> list = null;// 
		if(StringUtils.isNotBlank(q)){
			list = regionService.findListByQ(q);
		}else{
			list = regionService.findAll();
		}
		this.java2Json(list, new String[] {"currentPage", "detachedCriteria", "pageSize", "subareas"});
		return NONE;
	}

	

}
