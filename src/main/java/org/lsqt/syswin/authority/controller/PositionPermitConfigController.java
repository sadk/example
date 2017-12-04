package org.lsqt.syswin.authority.controller;

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
import org.lsqt.syswin.authority.model.PositionPermitConfig;
import org.lsqt.syswin.authority.model.PositionPermitConfigQuery;
import org.lsqt.syswin.authority.service.PositionPermitConfigService;




@Controller(mapping={"/positionPermitConfig"})
public class PositionPermitConfigController {
	
	@Inject private PositionPermitConfigService positionPermitConfigService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionPermitConfig> queryForPage(PositionPermitConfigQuery query) throws IOException {
		return positionPermitConfigService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionPermitConfig> getAll() {
		return positionPermitConfigService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionPermitConfig saveOrUpdate(PositionPermitConfig form) {
		return positionPermitConfigService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionPermitConfigService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	/** 暂时注释，未能提供通用实现！！！
	@RequestMapping(mapping = { "/export", "/m/export" })
	public void export(PositionPermitConfigQuery query, String exportFileType, String exportDataType) {
		Page<PositionPermitConfig> page = db.getEmptyPage();
		
		// 1.导出excel
		if (Dictionary.EXPORT_FILE_TYPE_EXCEL.equals(exportFileType)) {
			
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				
				page.setData(positionPermitConfigService.getAll());
				ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
				return ;
			} 
			
			page = positionPermitConfigService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
			
			return;
		}

		// 2.导出文本文件
		if (Dictionary.EXPORT_FILE_TYPE_TXT.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(positionPermitConfigService.getAll());
				ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
				return;
			}

			page = positionPermitConfigService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
			return;
		}
		
		// 3.导出doc文件
		if (Dictionary.EXPORT_FILE_TYPE_DOC.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(positionPermitConfigService.getAll());
				ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
				return ;
			}
			
			page = positionPermitConfigService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
			return;
		}

		// 4.导出pdf文件
		if (Dictionary.EXPORT_FILE_TYPE_PDF.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(positionPermitConfigService.getAll());
				ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
				return ;
			}
			
			page = positionPermitConfigService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
			return;
		}
		
	}
	**/
	
}
