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
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;
import org.lsqt.sys.model.Tenant;
import org.lsqt.sys.service.ApplicationService;
import org.lsqt.sys.service.TenantService;

@Controller(mapping={"/channel"})
public class ChannelController {
	@Inject private ChannelService channelService; 
	@Inject private ApplicationService applicationService;
	@Inject private TenantService tenantService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Channel> queryForPage(ChannelQuery query) throws IOException {
		return channelService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Channel> queryForList(ChannelQuery query) throws IOException {
		return channelService.queryForList(query);
	}
	
	/*
	@RequestMapping(mapping = { "/tree_tenant_app", "/m/tree_tenant_app" },text="租户与应用树")
	public Collection<Channel> treeTenantApp() {
		Collection<Channel> data = new ArrayList<>();
		
		Collection<Tenant> tenantList = tenantService.getAll();
		
		if(ArrayUtil.isNotBlank(tenantList)) {
			long id = -1;
			for(Tenant t: tenantList) {
				Channel channel = new Channel();
				channel.setId(--id);
				channel.setPid(0L);
				channel.setName(t.getName() + " - 【租户】");
				 
				channel.setCode(t.getCode());
				channel.setStatus(t.getEnable());
				channel.setSn(0);
				channel.setCreateTime(t.getCreateTime());
				channel.setUpdateTime(t.getUpdateTime());
				channel.setNodePath(t.getId()+"");
				channel.setNodeType(Channel.NODE_TYPE_租户);
				data.add(channel);
			}
		}
		
		Collection<Channel> subData = new ArrayList<>();
		Collection<Application> apps = applicationService.getAll();
		if(ArrayUtil.isNotBlank(apps)) {
			
			for(Application t: apps) {
				Channel channel = new Channel();
				channel.setId(t.getId());
				//channel.setPid(0L);
				channel.setName(t.getName());
				 
				channel.setCode(t.getCode());
				channel.setStatus(t.getEnable());
				channel.setSn(0);
				channel.setCreateTime(t.getCreateTime());
				channel.setUpdateTime(t.getUpdateTime());
				channel.setNodePath(t.getId()+"");
				channel.setNodeType(Channel.NODE_TYPE_应用);
				
				for(Channel d : data) {
					if(d.getCode().equals(t.getTenantCode())) {
						channel.setPid(d.getId());
						break;
					}
				}
				subData.add(channel);
			}
		}
		
		data.addAll(subData);
		return data;
	}
	*/
	
	@RequestMapping(mapping = { "/tree", "/m/tree" },text="应用与栏目树")
	public Collection<Node> tree(String tenantCode) {
		List<Node> nodeList = new ArrayList<>();
		
		// 构建站点的栏目树
		ApplicationQuery appQuery = new ApplicationQuery();
		appQuery.setTenantCode(tenantCode);
		Collection<Application> apps = applicationService.queryForList(appQuery);
		
		long id = -1;
		List<Node> appList = new ArrayList<>(); // 应用
		for(Application a: apps) {
			Node node = new Node();
			node.id = --id;
			node.pid= 0L;
			node.name= a.getName();
			node.code = a.getCode();
			node.nodeType = Channel.NODE_TYPE_应用;
			appList.add(node);
		}
		

		List<Node> channelNodeList = new ArrayList<>();
		ChannelQuery cquery = new ChannelQuery();
		cquery.setTenantCode(tenantCode);
		Collection<Channel> list = channelService.queryForList(cquery); //所有栏目
		for(Channel c: list) {
			Node node = new Node();
			node.id = c.getId();
			node.name= c.getName();
			node.code = c.getCode();
			node.appCode = c.getAppCode();
			node.nodeType = Channel.NODE_TYPE_栏目;
			channelNodeList.add(node);
		}
		 
		// 所有栏目挂载到正确系统上
		for(Node e: channelNodeList) {
			for(Node app: appList) {
				if(e.appCode.equals(app.code)) {
					e.pid = app.id;
					break;
				}
			}
		}
		 
		nodeList.addAll(appList);
		nodeList.addAll(channelNodeList);
		return nodeList;
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
	
	class Node {
		public Long id;
		public Long pid;
		public String name;
		public String code;
		public String nodeType;
		
		public String appCode;
	}
}
