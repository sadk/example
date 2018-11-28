package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Variable;
import org.lsqt.sys.model.VariableQuery;
import org.lsqt.sys.service.VariableService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/variable"})
public class VariableController {
	
	@Inject private VariableService variableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Variable> queryForPage(VariableQuery query) throws IOException {
		return variableService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Variable> getAll(VariableQuery query) {
		return variableService.getAll(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Variable saveOrUpdate(Variable form) {
		return variableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return variableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/imp_variable", "/m/imp_variable" })
	public int impVariable(String jsonText,Long objId) {
		int cnt = 0;
		if (objId != null && StringUtil.isNotBlank(jsonText)) {
			String sql ="select count(1) from sys_variable where obj_id=? and binary code=?"; // code值字符区分大小写!!
			List<Variable> list =JSON.parseArray(jsonText, Variable.class);
			for (Variable e: list) {
				Integer count = db.executeQueryForObject(sql,Integer.class , objId,e.getCode());
				if(count==null || count>0) continue ;
				
				if(count == 0) {
					e.setId(null);
					e.setUseType(Variable.USE_TYPE_代码生成器变量);
					e.setUpdateTime(new Date());
					e.setObjId(objId);
					
					if(Variable.COMMON_VARIABLES_内置值.contains(e.getCode())) {
						e.setValueType(Variable.VALUE_TYPE_内置值);
					} 
					else if(Variable.COMMON_VARIABLES_固定值.contains(e.getCode())){
						e.setValueType(Variable.VALUE_TYPE_固定值);
					}
					else {
						e.setValueType(Variable.VALUE_TYPE_运行时);
					}
					db.save(e);
				}
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/batch_save_or_update", "/m/batch_save_or_update" })
	public int batchSaveOrUpdate(String jsonText,Long objId) {
		int cnt = 0;
		if(StringUtil.isBlank(jsonText) || objId==null) return 0;
		
		String sql = "delete from %s where obj_id=?";
		int tmp = db.executeUpdate(String.format(sql, db.getFullTable(Variable.class)),objId);
		cnt += tmp;
		List<Variable> list = JSON.parseArray(jsonText, Variable.class);
		
		for(Variable e: list) {
			e.setUseType(Variable.USE_TYPE_代码生成器变量);
			db.save(e);
			cnt ++ ;
		}
		
		
		return cnt;
	}
}
