package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.mvc.Default;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.sys.service.DictionaryService;

@Cache(Dictionary.class)
@Controller(mapping={"/dictionary"})
public class DictionaryController {
	@Inject private DictionaryService dictionaryService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Dictionary getById(Long id) throws IOException {
		return dictionaryService.getById(id);
	}
	
	@Cache(ignore = true)
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Dictionary> queryForPage(DictionaryQuery query) throws IOException {
		return dictionaryService.queryForPage(query);
	}
	
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Dictionary> getAll() throws IOException {
		return dictionaryService.getAll();
	}
	
	/**
	 * 
	 * @param query
	 * @param isEnableTreeQuery 是否启用树状查询
	 * @return
	 */
	@Cache(ignore = true)
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Dictionary> queryForList(DictionaryQuery query,Boolean isEnableTreeQuery) {
		  List<Dictionary> list = dictionaryService.queryForList(query);
		  
			if(ArrayUtil.isNotBlank(list) && (isEnableTreeQuery!=null && isEnableTreeQuery)) {
				List<String> nodePathList = new ArrayList<>();
				for(Dictionary e: list) {
					nodePathList.add(e.getNodePath());
				}
				if(ArrayUtil.isNotBlank(nodePathList)) {
					DictionaryQuery q = new DictionaryQuery();
					q.setNodePathList(nodePathList);
					return dictionaryService.queryForList(q);
				}
			}
		  return list;
	}
	
	@Cache(evict = true)
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Dictionary saveOrUpdate(Dictionary form) {
		return dictionaryService.saveOrUpdate(form);
	}
	
	@Cache(evict = true)
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return dictionaryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	//@Cache(key="dict",scope="session")
	//@Cache(key="",timeout="30s")
	//@Cache(key="",timeoutPoit="30s")
	//@Cache(scheduled = "1h") 
	@Cache(Dictionary.class)
	@RequestMapping(mapping = { "/option", "/m/option" },isTransaction = false)
	public List<Dictionary> option(@Default("1000") String  appCode,String code,Integer enable) {
		/*
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		*/
		return dictionaryService.getOptionByCode(code, appCode,enable);
	}
	
	@Cache(evict = true)
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" },text="修复节点路径")
	public void repairNodePath() {
		dictionaryService.repairNodePath();
	}
	
}
