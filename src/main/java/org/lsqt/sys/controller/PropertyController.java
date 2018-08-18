package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.lsqt.sys.service.PropertyService;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/property"})
public class PropertyController {
	
	@Inject private PropertyService propertyService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<Property> queryForPage(PropertyQuery query) throws IOException {
		
		return propertyService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<Property> getAll() {
		return propertyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public Property saveOrUpdate(Property form) {
		 
		return propertyService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return propertyService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/save_or_update_short", "/m/save_or_update_short" }, view = View.JSON)
	public void saveOrUpdateShort(String data) {
		 if(StringUtil.isNotBlank(data)) {
			List<Property> list =  JSON.parseArray(data, Property.class);
			for(Property d: list) {
				db.saveOrUpdate(d, "name","value","sn","remark");
			}
		 }
	}
}
