package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;
import org.lsqt.act.service.InstanceVariableService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;




@Controller(mapping={"/instance_variable","/nv2/instance_variable"})
public class InstanceVariableController {
	
	@Inject private InstanceVariableService instanceVariableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<InstanceVariable> queryForPage(InstanceVariableQuery query) throws IOException {
		return instanceVariableService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<InstanceVariable> getAll() {
		return instanceVariableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public InstanceVariable saveOrUpdate(InstanceVariable form) {
		return instanceVariableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return instanceVariableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	/** 暂时注释，未能提供通用实现！！！
	@RequestMapping(mapping = { "/export", "/m/export" })
	public void export(InstanceVariableQuery query, String exportFileType, String exportDataType) {
		Page<InstanceVariable> page = db.getEmptyPage();
		
		// 1.导出excel
		if (Dictionary.EXPORT_FILE_TYPE_EXCEL.equals(exportFileType)) {
			
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				
				page.setData(instanceVariableService.getAll());
				ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
				return ;
			} 
			
			page = instanceVariableService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
			
			return;
		}

		// 2.导出文本文件
		if (Dictionary.EXPORT_FILE_TYPE_TXT.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(instanceVariableService.getAll());
				ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
				return;
			}

			page = instanceVariableService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
			return;
		}
		
		// 3.导出doc文件
		if (Dictionary.EXPORT_FILE_TYPE_DOC.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(instanceVariableService.getAll());
				ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
				return ;
			}
			
			page = instanceVariableService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
			return;
		}

		// 4.导出pdf文件
		if (Dictionary.EXPORT_FILE_TYPE_PDF.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(instanceVariableService.getAll());
				ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
				return ;
			}
			
			page = instanceVariableService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
			return;
		}
		
	}
	**/
	
}
