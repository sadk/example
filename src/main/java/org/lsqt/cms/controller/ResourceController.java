package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.cms.model.Resource;
import org.lsqt.cms.model.ResourceQuery;
import org.lsqt.cms.service.ResourceService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.service.ApplicationService;

@Controller(mapping={"/resource"})
public class ResourceController {
	
	@Inject private ResourceService resourceService; 
	@Inject private ApplicationService applicationService;
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Resource> queryForPage(ResourceQuery query) throws IOException {
		return resourceService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Resource> getAll() {
		return resourceService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Resource> queryForList(ResourceQuery query) {
		return resourceService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Resource saveOrUpdate(Resource form) {
		return resourceService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return resourceService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/init_root", "/m/init_root" },text="初使化系统根目录")
	public void initRoot() throws IOException {
		Collection<Application> data = applicationService.getAll();
		if(ArrayUtil.isNotBlank(data)) {
			List<Resource> modeList = new ArrayList<>();
			int i=0;
			
			db.executeUpdate("delete from "+db.getFullTable(Resource.class));
			
			for(Application e: data) {
				Resource res = new Resource();
				res.setAlias(e.getName());
				res.setAppCode(e.getCode());
				res.setCode(System.currentTimeMillis()+"_"+i++);
				res.setEnable(Application.ENABLE_YES);
				res.setName(e.getName());
				res.setPid(-1L);
				res.setType(Resource.TYPE_目录);
				res.setSn(0);
				//res.setUrl("/apps");
				modeList.add(res);
				resourceService.saveOrUpdate(res);
			}
			
		}
	}

}
