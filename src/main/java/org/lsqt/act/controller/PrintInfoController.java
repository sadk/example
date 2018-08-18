package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;
import org.lsqt.act.service.PrintInfoService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;




@Controller(mapping={"/print_info"})
public class PrintInfoController {
	
	@Inject private PrintInfoService printInfoService; 
	
	@Inject private Db db;
	
	@Inject private PlatformDb db2;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PrintInfo> queryForPage(PrintInfoQuery query) throws IOException {
		return printInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PrintInfo> getAll() {
		return printInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PrintInfo saveOrUpdate(PrintInfo form) {
		if(StringUtil.isNotBlank(form.getPrintManUserId())) {
			User user = db2.getById(User.class, form.getPrintManUserId());
			form.setPrintMan(user.getUserName());
		}
		
		if(StringUtil.isNotBlank(form.getDocAdminUserId())) {
			User user = db2.getById(User.class, form.getDocAdminUserId());
			form.setDocAdmin(user.getUserName());
		}
		
		if(StringUtil.isNotBlank(form.getPrintArchiveUserId())) {
			User user = db2.getById(User.class, form.getPrintArchiveUserId());
			form.setPrintArchive(user.getUserName());
		}
		
		if(StringUtil.isNotBlank(form.getProtectManUserId())) {
			User user = db2.getById(User.class, form.getProtectManUserId());
			form.setProtectMan(user.getUserName());
		}
		return printInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public Long delete(String ids) {
		PrintInfoQuery q = new PrintInfoQuery();
		q.setIds(ids);
		List<PrintInfo> data = db.queryForList("queryForPage", PrintInfo.class, q);

		for (PrintInfo e : data) {
			if (e.getReferContract() != null && e.getReferContract() == PrintInfo.REFER_CONTRACT_YES) {
				return e.getId();
			}
		}
		
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		int cnt = printInfoService.deleteById(list.toArray(new Long[list.size()]));
		
		return Long.valueOf(cnt);
	}

}
