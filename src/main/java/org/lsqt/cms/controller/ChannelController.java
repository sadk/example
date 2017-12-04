package org.lsqt.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.cms.model.Channel;
import org.lsqt.cms.model.ChannelQuery;
import org.lsqt.cms.service.ChannelService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.service.ApplicationService;

@Controller(mapping={"/channel"})
public class ChannelController {
	@Inject private ChannelService channelService; 
	@Inject private ApplicationService applicationService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Channel> queryForPage(ChannelQuery query) throws IOException {
		return channelService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Channel> all() {
		Collection<Channel> list = channelService.getAll();

		return list;
	}
	
	@RequestMapping(mapping = { "/tree", "/m/tree" },text="模板管理，树展开")
	public Collection<Channel> tree() {
		// 构建站点的栏目树
		Collection<Application> apps = applicationService.getAll();
		
		long id = -1;
		List<Channel> roots = new ArrayList<>();
		for(Application a: apps) {
			Channel channel = new Channel();
			channel.setId(--id);
			channel.setPid(0L);
			channel.setName(a.getName());
			channel.setAppCode(a.getCode());
			channel.setCode(a.getCode());
			channel.setStatus(a.getEnable());
			channel.setSn(0);
			channel.setCreateTime(a.getCreateTime());
			channel.setUpdateTime(a.getUpdateTime());
			channel.setNodePath(channel.getId()+"");
			channel.setNodeType(Channel.NODE_TYPE_应用);
			roots.add(channel);
		}
		
		
		Collection<Channel> list = channelService.getAll();
		for(Channel e: list) {
			e.setNodeType(Channel.NODE_TYPE_栏目);
			
			if(e.getPid()==null || e.getPid().intValue()==-1) {
				for(Channel r:roots) {
					if(r.getCode().equals(e.getAppCode())){
						e.setPid(r.getId());
					}
				}
			}
		}
		
		list.addAll(roots);
		
		return list;
	}
	

			
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Channel saveOrUpdate(Channel form) {
		return channelService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return channelService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
