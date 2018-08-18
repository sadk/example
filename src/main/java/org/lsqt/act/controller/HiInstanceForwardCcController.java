package org.lsqt.act.controller;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.HiInstanceForwardCc;
import org.lsqt.act.model.HiInstanceForwardCcQuery;
import org.lsqt.act.service.HiInstanceForwardCcService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;




@Controller(mapping={"/hi_instance_forward_cc"})
public class HiInstanceForwardCcController {
	
	@Inject private HiInstanceForwardCcService hiInstanceForwardCcService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<HiInstanceForwardCc> queryForPage(HiInstanceForwardCcQuery query) {
		return hiInstanceForwardCcService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<HiInstanceForwardCc> getAll() {
		return hiInstanceForwardCcService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public HiInstanceForwardCc saveOrUpdate(HiInstanceForwardCc form) {
		return hiInstanceForwardCcService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return hiInstanceForwardCcService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
