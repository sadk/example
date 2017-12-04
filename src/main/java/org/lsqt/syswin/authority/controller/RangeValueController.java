package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.RangeValue;
import org.lsqt.syswin.authority.model.RangeValueQuery;
import org.lsqt.syswin.authority.service.RangeValueService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/syswin/range_value","/nv2/syswin/range_value"})
public class RangeValueController {
	
	@Inject private RangeValueService rangeValueService; 
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<RangeValue> queryForPage(RangeValueQuery query) throws IOException {
		return rangeValueService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<RangeValue> getAll() {
		return rangeValueService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public RangeValue saveOrUpdate(RangeValue form) {
		return rangeValueService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_short", "/m/save_or_update_short" },text="快速保存")
	public void saveOrUpdateShort(String data) {
		List<RangeValue> list = JSON.parseArray(data, RangeValue.class);
		for(RangeValue model: list) {
			model.setUpdateTime(new Date());
			db.saveOrUpdate(model, "code","rangeValue","description","updateTime","rangeType");
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return rangeValueService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	// ------------------------------------------------------------------
	/**
	 * 
	 * @param userId 登陆用户ID
	 * @param value Freemark模板内容
	 * @return
	 */
	@RequestMapping(mapping = { "/view_sql", "/m/view_sql" },text="预览查询权限的SQL")
	public Map<String,Object> viewSql(Long userId,String value) {
		Map<String,Object> rs = new HashMap<>();
		rs.put("data", "");
		try{
			if(userId!=null && StringUtil.isNotBlank(value)){
				rs.put("data",rangeValueService.viewSql(userId, value));
			}
		}catch(Exception ex) {
			rs.put("data",ExceptionUtil.getStackTrace(ex));
		}
		
		return rs;
	}
}
