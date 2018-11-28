package org.lsqt.cms.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.cms.model.Channel;
import org.lsqt.cms.model.ChannelQuery;

public interface ChannelService {
	
	Page<Channel> queryForPage(ChannelQuery query);

	Channel saveOrUpdate(Channel model);

	int deleteById(Long... ids);
	
	Collection<Channel> getAll();
	
	List<Channel> queryForList(ChannelQuery query);
	
}
