package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Variable;
import org.lsqt.act.model.VariableQuery;
import org.lsqt.act.service.VariableService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;



/**
 * 流程发起变量管理
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/variable"})
public class VariableController {
	
	@Inject private VariableService variableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Variable> queryForPage(VariableQuery query) throws IOException {
		return variableService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Page<Variable> queryForList(VariableQuery query) throws IOException {
		return variableService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Variable> getAll() {
		return variableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Variable saveOrUpdate(Variable form) {
		return variableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_short", "/m/save_or_update_short" })
	public List<Variable> saveOrUpdateShort(String data) {
		List<Variable> rs = new ArrayList<>();
		if (StringUtil.isNotBlank(data)) {
			List<Variable> list = JSON.parseArray(data, Variable.class);
			for(Variable m:list) {
				db.saveOrUpdate(m,"format","dataType","code","requried","remark","defaultValue");
			}
			return list;
		}
		return rs;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return variableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/init_variable", "/m/init_variable" })
	public int init(String definitionId) {
		if(StringUtil.isNotBlank(definitionId)) {
			List<Variable> data= new ArrayList<>();
			Variable m1 = new Variable();
			m1.setDefinitionId(definitionId);
			m1.setName("流程发起人ID");
			m1.setCode("startUserId");
			m1.setDataType(Variable.DATA_TYPE_字符);
			m1.setRequried(Variable.REQURIED_YES);
			m1.setType(Variable.TYPE_CUSTOM);
			
			Variable m2 = new Variable();
			m2.setDefinitionId(definitionId);
			m2.setName("填制人部门ID");
			m2.setCode("createDeptId");
			m2.setDataType(Variable.DATA_TYPE_字符);
			m2.setRequried(Variable.REQURIED_YES);
			m2.setType(Variable.TYPE_CUSTOM);
			
			Variable m3 = new Variable();
			m3.setDefinitionId(definitionId);
			m3.setName("单据流水号");
			m3.setCode("flowNo");
			m3.setDataType(Variable.DATA_TYPE_字符);
			m3.setRequried(Variable.REQURIED_YES);
			m3.setType(Variable.TYPE_CUSTOM);
			
			Variable m4 = new Variable();
			m4.setDefinitionId(definitionId);
			m4.setName("单据标题");
			m4.setCode("title");
			m4.setDataType(Variable.DATA_TYPE_字符);
			m4.setRequried(Variable.REQURIED_YES);
			m4.setType(Variable.TYPE_CUSTOM);
			
			Variable m5 = new Variable();
			m5.setDefinitionId(definitionId);
			m5.setName("单据主键");
			m5.setCode("businessKey");
			m5.setDataType(Variable.DATA_TYPE_字符);
			m5.setRequried(Variable.REQURIED_YES);
			m5.setType(Variable.TYPE_CUSTOM);
			
			Variable m6 = new Variable();
			m6.setDefinitionId(definitionId);
			m6.setName("用印公司名称");
			m6.setCode("companyName");
			m6.setDataType(Variable.DATA_TYPE_字符);
			m6.setRequried(Variable.REQURIED_NO);
			m6.setType(Variable.TYPE_CUSTOM);
			
			data.addAll(Arrays.asList(m1,m2,m3,m4,m5,m6));
			
			String sql = "delete from %s where type=? and definition_id=? and code in (%s)";
			sql = String.format(sql, db.getFullTable(Variable.class),
					StringUtil.join(Arrays.asList("startUserId","createDeptId","flowNo","title","businessKey","companyName"), ",", "'", "'") );
			db.executeUpdate(sql, Variable.TYPE_CUSTOM,definitionId);
			return db.batchSave(data);
		}
		return 0;
	}
	
}
