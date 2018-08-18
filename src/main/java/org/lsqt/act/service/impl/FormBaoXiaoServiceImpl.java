package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.FormBaoXiao;
import org.lsqt.act.model.FormBaoXiaoQuery;
import org.lsqt.act.service.FormBaoXiaoService;

@Service
public class FormBaoXiaoServiceImpl implements FormBaoXiaoService{
	
	@Inject private Db db;
	
	public Page<FormBaoXiao>  queryForPage(FormBaoXiaoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), FormBaoXiao.class, query);
	}

	public List<FormBaoXiao> getAll(){
		  return db.queryForList("getAll", FormBaoXiao.class);
	}
	
	public FormBaoXiao saveOrUpdate(FormBaoXiao model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(FormBaoXiao.class, Arrays.asList(ids).toArray());
	}
}
