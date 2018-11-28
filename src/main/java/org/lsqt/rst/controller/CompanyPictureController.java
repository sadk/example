package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.CompanyPicture;
import org.lsqt.rst.model.CompanyPictureQuery;
import org.lsqt.rst.service.CompanyPictureService;




@Controller(mapping={"/rst/company_picture"})
public class CompanyPictureController {
	
	@Inject private CompanyPictureService companyPictureService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public CompanyPicture getById(Long id) throws IOException {
		return companyPictureService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CompanyPicture> queryForPage(CompanyPictureQuery query) throws IOException {
		return companyPictureService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CompanyPicture> getAll() {
		return companyPictureService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CompanyPicture saveOrUpdate(CompanyPicture form) {
		return companyPictureService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			return companyPictureService.deleteById(list.toArray(new Long[list.size()]));
		}
		return 0;
	}
	
}
