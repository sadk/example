package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionFileQuery;
import org.lsqt.act.service.ApproveOpinionFileService;




@Controller(mapping={"/act/approve_opinion_file","/nv2/act/approve_opinion_file"})
public class ApproveOpinionFileController {
	
	@Inject private ApproveOpinionFileService ApproveOpinionFileService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApproveOpinionFile> queryForPage(ApproveOpinionFileQuery query) throws IOException {
		return ApproveOpinionFileService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApproveOpinionFile> getAll(ApproveOpinionFileQuery query) {
		return ApproveOpinionFileService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApproveOpinionFile saveOrUpdate(ApproveOpinionFile form) {
		return ApproveOpinionFileService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return ApproveOpinionFileService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
