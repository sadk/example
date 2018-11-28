package org.lsqt.cms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.cms.model.Channel;
import org.lsqt.cms.model.ChannelQuery;
import org.lsqt.cms.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService{
	
	@Inject private Db db;
	
	public Page<Channel>  queryForPage(ChannelQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Channel.class, query);
	}

	public List<Channel> getAll(){
		  return db.queryForList("queryForPage", Channel.class);
	}
	
	public List<Channel> queryForList(ChannelQuery query) {
		return db.queryForList("queryForPage", Channel.class, query);
	}
	
	public Channel saveOrUpdate(Channel model) {
		db.saveOrUpdate(model);
		
		// 循环向上，处理节点路径
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(model.getId());
		
		Channel parent = db.getById(Channel.class, model.getPid());
		while (parent != null) {
			parentIds.add(parent.getId());

			parent = db.getById(Channel.class, parent.getPid());
		}
		
		if (!parentIds.isEmpty()) {
			Collections.reverse(parentIds);
			model.setNodePath(StringUtil.join(parentIds, ","));
			db.update(model, "nodePath");
		}
		
		return model;
	}

	public int deleteById(Long ... ids) {
		if(ids == null || ids.length==0) {
			return 0;
		}
		int cnt = 0;
		for(Long id: ids){
			Channel Channel = db.getById(Channel.class, id);
			if(Channel!=null){
				String sql="delete from %s where node_path like %s";
				int temp = db.executeUpdate(String.format(sql, db.getFullTable(Channel.class),"'"+Channel.getNodePath()+"%'"));
				cnt += temp;
			}
		}
		return cnt;
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getOptionByCode(String code,String appCode) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		if(StringUtil.isBlank(code)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		return db.queryForList("getOptionByCode", Channel.class, code, appCode);
	}
}
